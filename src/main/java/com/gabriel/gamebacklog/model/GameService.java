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

}
