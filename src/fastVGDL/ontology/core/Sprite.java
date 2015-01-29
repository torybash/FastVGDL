package fastVGDL.ontology.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.imageio.ImageIO;

import fastVGDL.ontology.Types;
import fastVGDL.ontology.Types.ACTIONS;
import fastVGDL.tools.Utils;
import fastVGDL.tools.Vector2i;
import fastVGDL.core.game.Game;

public abstract class Sprite {

	public boolean isAvatar = false;
	public String name;
	public int id;
	
	public int groupId;
	
//	public Color color;
	public String img;
	public int orientation = -1;
//	public double speed;
//	public double shrinkfactor;
	
    /**
     * Rectangle that this sprite occupies on the screen.
     */
    public Rectangle rect;


    
    
    public Vector2i position;
    
    public Vector2i lastPosition;
    
    Vector2i direction = new Vector2i();
    Image image;
    public Color color;
    
    public void update(Game game){
    	lastPosition.x = position.x;
    	lastPosition.y = position.y;
    }
    
    
    public void move(Vector2i action2d){
    	position.add(action2d);
    }
	
    /**
     * Draws this sprite (both the not oriented and, if appropriate, the oriented part)
     * @param gphx graphics object to draw in.
     * @param game reference to the game that is being played now.
     */
    public void draw(Graphics2D gphx, Game game) {

    	if (rect == null){
    		rect = new Rectangle();
        	rect.height = game.getBlockSize();
        	rect.width = game.getBlockSize();
    	}
    	rect.x = position.x * game.getBlockSize();
    	rect.y = position.y * game.getBlockSize();

    	
//        if(!invisible)
        {
            if(image != null)
                _drawImage(gphx, game);
            else
                _draw(gphx, game);

//            if(resources.size() > 0)
//            {
//                _drawResources(gphx, game);
//            }
//
//            if (is_oriented)
//                _drawOriented(gphx);
        }
    }

    /**
     * In case this sprite is oriented and has an arrow to draw, it draws it.
     * @param g graphics device to draw in.
     */
//    public void _drawOriented(Graphics2D g)
//    {
//        if(draw_arrow)
//        {
//            Color arrowColor = new Color(color.getRed(), 255-color.getGreen(), color.getBlue());
//            Polygon p = Utils.triPoints(rect, orientation);
//
//            g.setColor(arrowColor);
//            //g.drawPolygon(p);
//            g.fillPolygon(p);
//        }
//    }

    /**
     * Draws the not-oriented part of the sprite
     * @param gphx graphics object to draw in.
     * @param game reference to the game that is being played now.
     */
    public void _draw(Graphics2D gphx, Game game)
    {
        Rectangle r = new Rectangle(rect);
//        if(shrinkfactor != 1)
//        {
//            r.width *= shrinkfactor;
//            r.height *= shrinkfactor;
//            r.x += (rect.width-r.width)/2;
//            r.y += (rect.height-r.height)/2;
//        }

        gphx.setColor(color);

//        if(is_avatar)
//        {
//            gphx.fillOval((int) r.getX(), (int) r.getY(), r.width, r.height);
//        }else if(!is_static)
//        {
//            gphx.fillRect(r.x, r.y, r.width, r.height);
//        }else
        {
            gphx.fillRect(r.x, r.y, r.width, r.height);
        }
    }

    /**
     * Draws the not-oriented part of the sprite, as an image. this.image must be not null.
     * @param gphx graphics object to draw in.
     * @param game reference to the game that is being played now.
     */
    public void _drawImage(Graphics2D gphx, Game game)
    {
        Rectangle r = new Rectangle(rect);
//        if(shrinkfactor != 1)
//        {
//            r.width *= shrinkfactor;
//            r.height *= shrinkfactor;
//            r.x += (rect.width-r.width)/2;
//            r.y += (rect.height-r.height)/2;
//        }

        int w = image.getWidth(null);
        int h = image.getHeight(null);
        float scale = (float)r.width/w; //assume all sprites are quadratic.

        gphx.drawImage(image, r.x, r.y, (int) (w*scale), (int) (h*scale), null);

        //uncomment this to see lots of numbers around
        //gphx.setColor(Color.BLACK);
        //if(bucketSharp)   gphx.drawString("["+bucket+"]",r.x, r.y);
        //else              gphx.drawString("{"+bucket+"}",r.x, r.y);


    }

    /**
     * Draws the resources hold by this sprite, as an horizontal bar on top of the sprite.
     * @param gphx graphics to draw in.
     * @param game game being played at the moment.
     */
//    protected void _drawResources(Graphics2D gphx, Game game)
//    {
//        int numResources = resources.size();
//        double barheight = rect.getHeight() / 3.5f / numResources;
//        double offset = rect.getMinY() + 2*rect.height / 3.0f;
//
//        Set<Map.Entry<Integer, Integer>> entries = resources.entrySet();
//        for(Map.Entry<Integer, Integer> entry : entries)
//        {
//            int resType = entry.getKey();
//            int resValue = entry.getValue();
//
//            double wiggle = rect.width/10.0f;
//            double prop = Math.max(0,Math.min(1,resValue / (double)(game.getResourceLimit(resType))));
//
//            Rectangle filled = new Rectangle((int) (rect.x+wiggle/2), (int) offset, (int) (prop*(rect.width-wiggle)), (int) barheight);
//            Rectangle rest   = new Rectangle((int) (rect.x+wiggle/2+prop*(rect.width-wiggle)), (int)offset, (int) ((1-prop)*(rect.width-wiggle)), (int)barheight);
//
//            gphx.setColor(game.getResourceColor(resType));
//            gphx.fillRect(filled.x, filled.y, filled.width, filled.height);
//            gphx.setColor(Types.BLACK);
//            gphx.fillRect(rest.x, rest.y, rest.width, rest.height);
//            offset += barheight;
//        }
//
//    }

    
    /**
     * Loads the image that represents this sprite, using its string name as reference.
     * @param str name of the image to load.
     */
    
    String path = "../gvgai/sprites/";
	
    public void loadImage(String str)
    {
    	img = str;
    	loadImage();
    }
    
    public void loadImage()
    {
//    	System.out.println(img);
        if(image == null && img != null)
        {
            //load image.
            try {
                if (!(img.contains(".png"))) img = img + ".png";
                String image_file = path +  img;
                if((new File(image_file).exists())) {
                    image = ImageIO.read(new File(image_file));
                }
                else {
                    //System.out.println(image_file);
                    image = ImageIO.read(this.getClass().getResource("/" + image_file));
                }

            } catch (IOException e) {
                System.out.println("Image " + img + " could not be found.");
                e.printStackTrace();
            }
        }
    }

    
    public void performActiveMovement(ACTIONS act)
    {
        lastPosition.x = position.x;
        lastPosition.y = position.y;
        Vector2i action2D = Utils.processMovementActionKeys(act);
        move(action2D);
    }

	public Vector2i getDirection() {
		
		direction.x = position.x - lastPosition.x;
		direction.y = position.y - lastPosition.y;
		
		return direction;
	}


	public abstract Sprite copy(); //{


	public void copyTo(Sprite targetSprite) {
		targetSprite.name = name;
		targetSprite.position = position.copy();
		targetSprite.lastPosition = lastPosition.copy();
		targetSprite.id = id;
		targetSprite.groupId = groupId;
		targetSprite.isAvatar = isAvatar;
		targetSprite.orientation = orientation;
		targetSprite.color = color;
	}


	public void parseParameters(HashMap<String, String> parameters) {
		//Get all fields from the class and store it as key->field
        Field[] fields = getClass().getFields();
        HashMap<String, Field> fieldMap = new HashMap<String, Field>();
        for (Field field : fields)
        {
            String strField = field.toString();
            int lastDot = strField.lastIndexOf(".");
            String fieldName = strField.substring(lastDot + 1).trim();

            fieldMap.put(fieldName, field);
        }
        Object objVal = null;
        Field cfield = null;
        //Check all parameters from content
        for (String parameter : parameters.keySet())
        {
            String value = parameters.get(parameter);
            if (fieldMap.containsKey(parameter))
            {

                try {
                    cfield = Types.class.getField(value);
                    objVal = cfield.get(null);
                } catch (Exception e) {
                    try {
                        objVal = Integer.parseInt(value);

                    } catch (NumberFormatException e1) {
                        try {
                            objVal = Double.parseDouble(value);
                        } catch (NumberFormatException e2) {
                            try {
                                if(value.equalsIgnoreCase("true") ||
                                   value.equalsIgnoreCase("false")  )
                                    objVal = Boolean.parseBoolean(value);
                                else
                                    objVal = value;
                            } catch (NumberFormatException e3) {
                                objVal = value;
                            }
                        }
                    }
                }
                try {
                    fieldMap.get(parameter).set(this, objVal);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("Unknown field (" + parameter + "=" + value +")");
            }
        }
		
        
  	}
	
	
	public String toString() {
		return "Sprite: " + name + " (" + id + ")" + ", groupId: " + groupId + ", pos: " + position + ", lastPos: " + lastPosition;
	}


	public void loadColor() {
		
		
		
	}
		
//		Sprite sp = null;
//		public String name;
//		public int id;
//		
//		public int groupId;
//		
//	    /**
//	     * Rectangle that this sprite occupies on the screen.
//	     */
//	    public Rectangle rect;
//
//	    /**
//	     * Rectangle occupied for this sprite in the previous game step.
//	     */
//	    public Rectangle lastrect;
//	    
//	    
//	    
//	    public Vector2i position;
//	    
//	    public Vector2i lastPosition;
//		return null;
//	}
}
