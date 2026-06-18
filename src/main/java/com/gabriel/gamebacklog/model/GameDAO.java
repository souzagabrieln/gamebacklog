package com.gabriel.gamebacklog.model;

import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class GameDAO {
    @Autowired
    DataSource dataSource;

    JdbcTemplate jdbc;

    @PostConstruct
    private void initialize(){
        jdbc = new JdbcTemplate(dataSource);
    }

    public void insertGame(Game game){
        String sql = "INSERT INTO game(name, platform, status, rating) VALUES(?,?,?,?)";

        Object[] obj = new Object[4];
        obj[0] = game.getName();
        obj[1] = game.getPlatform();
        obj[2] = game.getStatus();
        obj[3] = game.getRating();

        jdbc.update(sql,obj);
    }

    public Game showGame(String uuid){
        String sql = "SELECT * FROM game where id=?::uuid";
        return Game.convert(jdbc.queryForMap(sql,uuid));
    }

    public ArrayList<Game> listGames(){
        String sql = "SELECT * FROM game";
        return Game.convertAll(jdbc.queryForList(sql));
    }

    public void updateGame(Game novo, String uuid){
		String sql = "UPDATE game " + 
			"SET name = ?, platform = ?, status = ?, rating = ? WHERE id = ?::uuid";
		Object[] obj = new Object[5];
		obj[0] = novo.getName();
		obj[1] = novo.getPlatform();
        obj[2] = novo.getStatus();
        obj[3] = novo.getRating();
		obj[4] = uuid;
		jdbc.update(sql,obj);
	}

    public void deleteGame(String uuid){
        String sql = "DELETE FROM game where id = ?::uuid";
        jdbc.update(sql,uuid);
    }

}
