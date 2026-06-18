package com.gabriel.gamebacklog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gabriel.gamebacklog.model.Game;
import com.gabriel.gamebacklog.model.GameService;

@Controller
public class PaginaController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/addgame")
    public String formGame(Model model){
        model.addAttribute("game", new Game());
        return "addgame";
    }

    @PostMapping("/addgame")
    public String addGame(@ModelAttribute Game game, Model model) {
        GameService cs = context.getBean(GameService.class);
        cs.insertGame(game);
        return "sucesso";
    }

    @GetMapping("/game/{uuid}")
    public String showGame(@PathVariable String uuid, Model model){
        GameService cs = context.getBean(GameService.class);
        Game game = cs.showGame(uuid);
        model.addAttribute("gameId",game.getId());
        model.addAttribute("gameName",game.getName());
        model.addAttribute("gamePlatform",game.getPlatform());
        model.addAttribute("gameStatus",game.getStatus());
        model.addAttribute("gameRating",game.getRating());
        return "gamepage";
    }

    @GetMapping("/gamelist")
    public String listGames(Model model){
        GameService cs = context.getBean(GameService.class);
        List<Game> games = cs.listGames();
        model.addAttribute("games", games);
        return "gamelist";
    }

    @GetMapping("/game/{id}/edit")
    public String formEditGame(@PathVariable("id") String uuid, Model model) {
        GameService cs = context.getBean(GameService.class);
        Game game = cs.showGame(uuid);
        model.addAttribute("game", game);
        model.addAttribute("id", uuid);
        return "editgame";
    }

    @PostMapping("/game/{id}/edit")
    public String updateGame(@PathVariable("id") String id,
                             @ModelAttribute Game game,
                             Model model){
        GameService cs = context.getBean(GameService.class);
        cs.updateGame(game, id);
        return "redirect:/gamelist";
    }

    @PostMapping("/game/{id}/delete")
	public String deletarAluno(@PathVariable("id") String id, 
			                       Model model) {
		GameService cdao = context.getBean(GameService.class);
		cdao.deleteGame(id);
		return "redirect:/gamelist";
	}
}
