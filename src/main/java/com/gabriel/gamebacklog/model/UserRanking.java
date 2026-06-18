package com.gabriel.gamebacklog.model;

public class UserRanking {

    private String username;
    private long total;

    public UserRanking(String username, long total) {
        this.username = username;
        this.total = total;
    }

    public String getUsername() { return username; }
    public long getTotal() { return total; }
}
