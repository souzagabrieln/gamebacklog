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

    public long countGamesByUser(UUID userId) {
        String sql = "SELECT COUNT(*) FROM game WHERE user_id = ?::uuid";
        return jdbc.queryForObject(sql, Long.class, userId);
    }

    public long countGamesByUserAndStatus(UUID userId, String status) {
        String sql = "SELECT COUNT(*) FROM game WHERE user_id = ?::uuid AND status = ?";
        return jdbc.queryForObject(sql, Long.class, userId, status);
    }

    public List<Game> findByUserAndStatus(UUID userId, String status) {
        String sql =
            "SELECT g.*, u.id as user_id, u.username " +
            "FROM game g " +
            "JOIN users u ON g.user_id = u.id " +
            "WHERE g.user_id = ?::uuid AND g.status = ?";

        return Game.convertAll(jdbc.queryForList(sql, userId, status));
    }

    public long countAllGames() {
        String sql = "SELECT COUNT(*) FROM game";
        return jdbc.queryForObject(sql, Long.class);
    }

    public long countAllByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM game WHERE status = ?";
        return jdbc.queryForObject(sql, Long.class, status);
    }

    public long countUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        return jdbc.queryForObject(sql, Long.class);
    }

    public List<Game> findTopRatedByUser(UUID userId) {
        String sql =
            "SELECT g.*, u.id as user_id, u.username " +
            "FROM game g " +
            "JOIN users u ON g.user_id = u.id " +
            "WHERE u.id = ?::uuid " +
            "ORDER BY g.rating DESC NULLS LAST " +
            "LIMIT 3";

        return Game.convertAll(jdbc.queryForList(sql, userId));
    }

    public String findFavoritePlatform(UUID userId) {
        String sql =
            "SELECT platform " +
            "FROM game " +
            "WHERE user_id = ?::uuid " +
            "GROUP BY platform " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1";

        List<String> result = jdbc.queryForList(sql, String.class, userId);

        return result.isEmpty() ? "Nenhuma" : result.get(0);
    }

    public List<PlatformRanking> topPlatforms() {
        String sql =
            "SELECT platform, COUNT(*) as total " +
            "FROM game " +
            "GROUP BY platform " +
            "ORDER BY total DESC " +
            "LIMIT 3";

        return jdbc.query(sql, (rs, rowNum) ->
            new PlatformRanking(
                rs.getString("platform"),
                rs.getLong("total")
            )
        );
    }

    public List<UserRanking> topUsers() {
        String sql =
            "SELECT u.username, COUNT(g.id) as total " +
            "FROM users u " +
            "JOIN game g ON g.user_id = u.id " +
            "GROUP BY u.username " +
            "ORDER BY total DESC " +
            "LIMIT 3";

        return jdbc.query(sql, (rs, rowNum) ->
            new UserRanking(
                rs.getString("username"),
                rs.getLong("total")
            )
        );
    }

}
