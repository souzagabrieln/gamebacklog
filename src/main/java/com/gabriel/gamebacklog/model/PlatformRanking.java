package com.gabriel.gamebacklog.model;

public class PlatformRanking {

    private String platform;
    private long total;

    public PlatformRanking(String platform, long total) {
        this.platform = platform;
        this.total = total;
    }

    public String getPlatform() { return platform; }
    public long getTotal() { return total; }
}
