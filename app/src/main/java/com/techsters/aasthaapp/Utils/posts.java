package com.techsters.aasthaapp.Utils;

public class posts {
    private String datePost, postDesc, postImageUrl, username, profilepic;

    public posts() {
    }

    public posts(String datePost, String postDesc, String postImageUrl, String username, String profilepic) {
        this.datePost = datePost;
        this.postDesc = postDesc;
        this.postImageUrl = postImageUrl;
        this.username = username;
        this.profilepic = profilepic;
    }

    public String getDatePost() {
        return datePost;
    }

    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}

