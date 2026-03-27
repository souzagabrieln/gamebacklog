package com.gabriel.gamebacklog.model;

import java.util.ArrayList;

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
}
