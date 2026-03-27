
package com.gabriel.gamebacklog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Game {

    private String id, name, platform, status;
    private Double rating;

    //form
    public Game(){}

    //select
    public Game(String id, String name, String platform, String status, Double rating){
        this.id = id;
        this.name = name;
        this.platform = platform;
        this.status = status;
        this.rating = rating;
    }

    //insert
    public Game( String name, String platform, String status, Double rating){
        this.name = name;
        this.platform = platform;
        this.status = status;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public static Game convert(Map<String, Object> record){
        UUID id = (UUID) record.get("id");
        String name = (String) record.get("name");
        String platform = (String) record.get("platform");
        String status = (String) record.get("status");
        Double rating = (Double) record.get("rating");
        return new Game(id.toString(), name, platform, status, rating);
    }

    public static ArrayList<Game> convertAll (List<Map<String, Object>> records) {
        ArrayList<Game> aux = new ArrayList<>();
        
        for(Map<String, Object> record: records){
            aux.add(convert(record));
        }
        return aux;
    }

}
