import parsing.core.VGDLFactory;
import parsing.core.VGDLParser;
import core.game.Game;
import core.player.AbstractPlayer;



public class Test {
    public static void main(String[] args) {

        String game_title = "realsokoban";
        int level = 1;

        String game_desc = "../gvgai/examples/gridphysics/"+game_title+".txt";
        String level_desc = "../gvgai/examples/gridphysics/"+game_title+"_lvl"+level+".txt";

        Game game = VGDLParser.GetInstance().parseGame(game_desc);

        game.visuals = true;
        VGDLParser.GetInstance().parseLevel(game, level_desc);

        AbstractPlayer humanPlayer = VGDLFactory.GetInstance().createController("controllers.human.Agent");
        AbstractPlayer puzzlePlayer = VGDLFactory.GetInstance().createController("controllers.puzzleSolverPlus.Agent");

        game.playGameWithGraphics(puzzlePlayer);
//		game.playGameWithGraphics(humanPlayer);
//		game.playGame(puzzlePlayer);

    }
	
	
}
