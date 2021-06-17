package com.techsters.aasthaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.techsters.aasthaapp.Adapters.ChatAdapter;

import com.techsters.aasthaapp.Models.MessageModel;
import com.techsters.aasthaapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class chatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    String URL ="https://fcm.googleapis.com/fcm/send";
    RequestQueue requestQueue;
    String receiveId;
    String userName;
    FirebaseUser mUser;
    String otherUsrName;
    DatabaseReference mUserRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mUser=auth.getCurrentUser();
        requestQueue = Volley.newRequestQueue(this);
        mUserRef= FirebaseDatabase.getInstance().getReference().child("Users");


        final String senderId = auth.getUid();
        receiveId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("username");
        String profilepic = getIntent().getStringExtra("profilePic");

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    otherUsrName= snapshot.child("username").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("presence").child(receiveId).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String status = snapshot.getValue(String.class);
                            if (!status.isEmpty()) {
                                if (status.equals("Offline")) {
                                    binding.status.setVisibility(View.GONE);
                                } else {
                                    binding.status.setText(status);
                                    binding.status.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.userName.setText(userName);
        Picasso.get().load(profilepic).placeholder(R.drawable.user).into(binding.profileImage);


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chatDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this, receiveId);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(chatDetailActivity.this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + receiveId;
        final String receiverRoom = receiveId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());
                            messageModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        final Handler handler = new Handler();
        binding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                database.getReference().child("presence").child(senderId).setValue("typing...");
                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(userStoppedTyping, 1000);


            }

            Runnable userStoppedTyping = new Runnable() {
                @Override
                public void run() {
                    database.getReference().child("presence").child(senderId).setValue("Online");
                }
            };
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etMessage.getText().toString().isEmpty()) {
                    binding.etMessage.setError("Enter your message");
                    return;
                }

                String message = binding.etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");
                sendNotification(message);

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child("chats").child(receiverRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                            }
                        });


                    }
                });
            }
        });
    }

    private void sendNotification(String message) {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("to","/topics/"+receiveId);
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("title","Message from " + otherUsrName);
            jsonObject1.put("body", message);

            JSONObject jsonObject2=new JSONObject();
            jsonObject2.put("userID", mUser.getUid());
            jsonObject2.put("type", "message");

            jsonObject.put("notification",jsonObject1);
            jsonObject.put("data",jsonObject2);

            JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,URL,jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map=new HashMap<>();
                    map.put("content-type", "application/json");
                    map.put("authorization", "key=AAAAhFzeEcI:APA91bGV9LU6jHErO4sOHIOFpo-qlRtaose7xoLdfiN35_HdTPHMCePRJ7p4qoBGhb6sV0xSUD-Tgha2_BV89O4DifoBpFQEcnJ2Mt9lKIH2JEUPcDp3CZlVGq-XGBkW2Btv3zKIfzY7");

                    return map;
                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentId).setValue("Online");
        super.onResume();

    }
    @Override
    protected void onPause() {
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentId).setValue("Offline");
        super.onPause();
    }


}