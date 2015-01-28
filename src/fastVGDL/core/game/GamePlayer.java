package fastVGDL.core.game;

import fastVGDL.parsing.core.VGDLFactory;
import fastVGDL.parsing.core.VGDLParser;
import fastVGDL.core.game.results.GameResults;
import fastVGDL.core.player.AbstractPlayer;
import fastVGDL.core.game.Game;

public class GamePlayer {

    
    
	public static GameResults playGame(String gamePath, String levelPath, String controllerName, boolean visuals){
//        String game_desc = "../gvgai/examples/gridphysics/"+gameTitle+".txt";
//        String level_desc = "../gvgai/examples/gridphysics/"+gameTitle+"_lvl"+levelIdx+".txt";

        Game game = VGDLParser.GetInstance().parseGame(gamePath);
//        if (game == null){
//            game = VGDLParser.GetInstance().parseGame(gamePath);
//        }else{
//            game.reset();
//        }

        game.visuals = visuals;
        VGDLParser.GetInstance().parseLevel(game, levelPath);

        AbstractPlayer controller = VGDLFactory.GetInstance().createController(controllerName);
//        AbstractPlayer puzzlePlayer = VGDLFactory.GetInstance().createController("controllers.puzzleSolverPlus.Agent");
//        AbstractPlayer bestFirstPlayer = VGDLFactory.GetInstance().createController("controllers.bestFirst.Agent");

        game.playGame(controller, visuals);

		
        GameResults results = new GameResults(game);
        
        return results;
	}
}
