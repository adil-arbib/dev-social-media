package com.arbib.admin_panel.objects;


public class Post {
    private String id;
    private String content;
    private String posted_at;

    public Post(String content, String posted_at) {
        this.content = content;
        this.posted_at = posted_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPosted_at() {
        return posted_at;
    }

    public void setPosted_at(String posted_at) {
        this.posted_at = posted_at;
    }
}
