package com.gabriel.gamebacklog.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gabriel.gamebacklog.model.Game;
import com.gabriel.gamebacklog.model.GameService;
import com.gabriel.gamebacklog.model.User;
import com.gabriel.gamebacklog.model.UserService;

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
        public String addGame(@ModelAttribute Game game) {

        GameService cs = context.getBean(GameService.class);
        UserService us = context.getBean(UserService.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User user = us.findByUsername(username);
        
        game.setUser(user);

        cs.insertGame(game);

        return "redirect:/gamelist";
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
        UserService us = context.getBean(UserService.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = us.findByUsername(username);

        List<Game> games = cs.listGamesByUser(user.getId());
        model.addAttribute("games", games);
        return "gamelist";
    }

    @GetMapping("/game/list-all")
    public String listAllGames(Model model) {

        GameService gs = context.getBean(GameService.class);

        List<Game> games = gs.listGames(); // todos os jogos

        model.addAttribute("games", games);

        return "gamelist-all";
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
	public String deleteGame(@PathVariable("id") String id, 
			                       Model model) {
		GameService cdao = context.getBean(GameService.class);
		cdao.deleteGame(id);
		return "redirect:/gamelist";
	}

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        GameService gs = context.getBean(GameService.class);
        UserService us = context.getBean(UserService.class);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = us.findByUsername(username);

        UUID userId = user.getId();

        model.addAttribute("totalGames", gs.countGames(userId));
        model.addAttribute("playing", gs.countAllByStatus("jogando"));
        model.addAttribute("completed", gs.countAllByStatus("zerado"));
        model.addAttribute("dropped", gs.countAllByStatus("dropado"));
        model.addAttribute("backlog", gs.countAllByStatus("backlog"));

        model.addAttribute("topRatedGames", gs.findTopRatedByUser(user.getId()));

        model.addAttribute("favoritePlatform", gs.findFavoritePlatform(user.getId()));

        model.addAttribute("playingGames", gs.findPlayingGames(userId));

        return "dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        GameService gs = context.getBean(GameService.class);
        UserService us = context.getBean(UserService.class);

        model.addAttribute("totalUsers", us.countUsers());
        model.addAttribute("totalGames", gs.countAllGames());
        model.addAttribute("avgGamesPerUser", gs.averageGamesPerUser());

        model.addAttribute("topPlatforms", gs.topPlatforms());
        model.addAttribute("topUsers", gs.topUsers());

        model.addAttribute("playing", gs.countAllByStatus("jogando"));
        model.addAttribute("completed", gs.countAllByStatus("zerado"));
        model.addAttribute("dropped", gs.countAllByStatus("dropado"));
        model.addAttribute("backlog", gs.countAllByStatus("backlog"));

        return "admin-dashboard";
    }
}
