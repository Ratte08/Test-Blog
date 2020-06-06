package com.example.testblog;

import java.util.List;

public class ServerResponse {
    private String type;
    private List<Post> value;

    public ServerResponse(String type, List<Post> value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Post> getValue() {
        return value;
    }

    public void setValue(List<Post> value) {
        this.value = value;
    }
}
