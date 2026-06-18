package com.gabriel.gamebacklog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    GameDAO gameDAO;

    @Autowired
    private UserRepository userRepository;

    public void insertGame(Game game){
        gameDAO.insertGame(game);
    }

    public Game showGame(String uuid){
        return gameDAO.showGame(uuid);
    }

    public ArrayList<Game> listGames(){
        return gameDAO.listGames();
    }

    public List<Game> listGamesByUser(UUID userId) {
        return gameDAO.listGamesByUser(userId);
    }

    public void updateGame(Game novo, String uuid){
        gameDAO.updateGame(novo, uuid);
    }

    public void deleteGame(String uuid){
        gameDAO.deleteGame(uuid);
    }

    public long countGames(UUID userId) {
        return gameDAO.countGamesByUser(userId);
    }

    public long countByStatus(UUID userId, String status) {
        return gameDAO.countGamesByUserAndStatus(userId, status);
    }

    public List<Game> findPlayingGames(UUID userId) {
        return gameDAO.findByUserAndStatus(userId, "jogando");
    }

    public long countAllGames() {
        return gameDAO.countAllGames();
    }

    public long countAllByStatus(String status) {
        return gameDAO.countAllByStatus(status);
    }

    public List<Game> findTopRatedByUser(UUID userId) {
        return gameDAO.findTopRatedByUser(userId);
    }

    public String findFavoritePlatform(UUID userId) {
        return gameDAO.findFavoritePlatform(userId);
    }

    public double averageGamesPerUser() {
        long totalGames = gameDAO.countAllGames();
        long totalUsers = userRepository.count();

        if (totalUsers == 0) {
            return 0.0;
        }

        return (double) totalGames / totalUsers;
    }

    public List<PlatformRanking> topPlatforms() {
        return gameDAO.topPlatforms();
    }

    public List<UserRanking> topUsers() {
        return gameDAO.topUsers();
    }

}
