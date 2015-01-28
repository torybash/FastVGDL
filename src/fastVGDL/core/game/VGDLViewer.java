package fastVGDL.core.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import fastVGDL.ontology.Types;
import fastVGDL.ontology.core.Sprite;
import fastVGDL.parsing.core.SpriteGroup;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 24/10/13
 * Time: 10:54
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class VGDLViewer extends JComponent
{
    /**
     * Reference to the game to be painted.
     */
    public Game game;

    /**
     * Dimensions of the window.
     */
    private Dimension size;

    /**
     * Sprites to draw
     */
    public SpriteGroup[] spriteGroups;

    /**
     * Player of the game
     */
//    public AbstractPlayer player;


    /**
     * Creates the viewer for the game.
     * @param game game to be displayed
     */
    public VGDLViewer(Game game) //, AbstractPlayer player)
    {
        this.game = game;
        this.size = game.getScreenSize();
//        this.player = player;
        
    }

    /**
     * Main method to paint the game
     * @param gx Graphics object.
     */
    public void paintComponent(Graphics gx)
    {
        Graphics2D g = (Graphics2D) gx;

        //For a better graphics, enable this: (be aware this could bring performance issues depending on your HW & OS).
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //g.setColor(Types.LIGHTGRAY);
        g.setColor(Types.BLACK);
        g.fillRect(0, size.height, size.width, size.height);

        //Possible efficiency improvement: static image with immovable objects.
        /*
        BufferedImage mapImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gImage = mapImage.createGraphics();
        */

        int avatarGroupIdx = -1;
        for (int i = 0; i < game.spriteGroups.length; i++) {
			SpriteGroup sg = game.spriteGroups[i];
			
			if (sg.isAvatar){
				if (sg.sprites.size() > 0) avatarGroupIdx = i;
				continue;
			}
			for (Integer id : sg.sprites.keySet()) {
				Sprite sp = sg.sprites.get(id); 
                if(sp != null)
                    sp.draw(g, game);
			}
		}
        
        SpriteGroup avatarSg = game.spriteGroups[avatarGroupIdx];
		for (Integer id : avatarSg.sprites.keySet()) {
			Sprite sp = avatarSg.sprites.get(id); 
            if(sp != null)
                sp.draw(g, game);
		}
//        int[] gameSpriteOrder = game.getSpriteOrder();
//        if(this.spriteGroups != null) for(Integer spriteTypeInt : gameSpriteOrder)
//        {
//            Integer[] keys = spriteGroups[spriteTypeInt].getKeys();
//            if(keys!=null) for(Integer spriteKey : keys)
//            {
//                VGDLSprite sp = spriteGroups[spriteTypeInt].getSprite(spriteKey);
//                if(sp != null)
//                    sp.draw(g, game);
//            }
//        }

        g.setColor(Types.BLACK);
//        player.draw(g);
    }


    /**
     * Paints the sprites.
     * @param spriteGroupsGame sprites to paint.
     */
    public void paint()
    {
        this.repaint();
    }

    /**
     * Gets the dimensions of the window.
     * @return the dimensions of the window.
     */
    public Dimension getPreferredSize() {
        return size;
    }

}