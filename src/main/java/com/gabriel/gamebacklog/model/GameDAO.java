package com.gabriel.gamebacklog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        String sql = "INSERT INTO game(name, platform, status, rating, user_id) VALUES(?,?,?,?,?)";

        Object[] obj = new Object[5];
        obj[0] = game.getName();
        obj[1] = game.getPlatform();
        obj[2] = game.getStatus();
        obj[3] = game.getRating();
        obj[4] = game.getUser().getId();


        jdbc.update(sql,obj);
    }

    public Game showGame(String uuid){
        String sql = 
            "SELECT g.*, u.id as user_id, u.username " +
            "FROM game g " +
            "JOIN users u ON g.user_id = u.id " +
            "WHERE g.id = ?::uuid";
        return Game.convert(jdbc.queryForMap(sql,uuid));
    }

    public ArrayList<Game> listGames(){
        String sql = 
            "SELECT g.*, u.id as user_id, u.username " +
            "FROM game g " +
            "JOIN users u ON g.user_id = u.id";
        return Game.convertAll(jdbc.queryForList(sql));
    }

    public List<Game> listGamesByUser(UUID userId) {
        String sql =
            "SELECT g.*, u.id as user_id, u.username " +
            "FROM game g " +
            "JOIN users u ON g.user_id = u.id " +
            "WHERE u.id = ?::uuid";

        return Game.convertAll(jdbc.queryForList(sql, userId));
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
