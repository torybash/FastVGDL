package fastVGDL.parsing.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

import fastVGDL.ontology.Types;
import fastVGDL.ontology.core.Termination;
import fastVGDL.ontology.effects.Interaction;
import fastVGDL.ontology.effects.binary.AttractGaze;
import fastVGDL.ontology.effects.binary.BounceForward;
import fastVGDL.ontology.effects.binary.CollectResource;
import fastVGDL.ontology.effects.binary.KillIfFromAbove;
import fastVGDL.ontology.effects.binary.KillIfOtherHasMore;
import fastVGDL.ontology.effects.binary.PullWithIt;
import fastVGDL.ontology.effects.binary.TeleportToExit;
import fastVGDL.ontology.effects.binary.WallStop;
import fastVGDL.ontology.effects.unary.ChangeResource;
import fastVGDL.ontology.effects.unary.CloneSprite;
import fastVGDL.ontology.effects.unary.FlipDirection;
import fastVGDL.ontology.effects.unary.KillIfHasLess;
import fastVGDL.ontology.effects.unary.KillIfHasMore;
import fastVGDL.ontology.effects.unary.KillSprite;
import fastVGDL.ontology.effects.unary.ReverseDirection;
import fastVGDL.ontology.effects.unary.SpawnIfHasMore;
import fastVGDL.ontology.effects.unary.StepBack;
import fastVGDL.ontology.effects.unary.TransformTo;
import fastVGDL.ontology.effects.unary.TurnAround;
import fastVGDL.ontology.effects.unary.UndoAll;
import fastVGDL.ontology.effects.unary.WrapAround;
import fastVGDL.ontology.termination.MultiSpriteCounter;
import fastVGDL.ontology.termination.SpriteCounter;
import fastVGDL.ontology.termination.Timeout;
import fastVGDL.tools.ElapsedCpuTimer;
import fastVGDL.core.player.AbstractPlayer;

public class VGDLFactory {

	static VGDLFactory factory = null;
	
    private Class[] interactionClasses = new Class[]
        {
            StepBack.class, TurnAround.class, KillSprite.class, TransformTo.class, WrapAround.class,ChangeResource.class,
            KillIfHasLess.class, KillIfHasMore.class, CloneSprite.class, FlipDirection.class, ReverseDirection.class,
            UndoAll.class, SpawnIfHasMore.class,
            PullWithIt.class, WallStop.class, CollectResource.class, KillIfOtherHasMore.class, KillIfFromAbove.class,
            TeleportToExit.class, BounceForward.class, AttractGaze.class
        };

    private Class[] terminationClasses = new Class[]
        {
            Timeout.class, SpriteCounter.class, MultiSpriteCounter.class
        };
	
    public static VGDLFactory GetInstance(){
        if(factory == null)
        	factory = new VGDLFactory();
        return factory;
    }

	public Interaction makeInteraction(Node n) {
		String[] pieces = n.contentLine.split(" ");
		String function = pieces[3];
		
		
		Class interClass = null;
		for (int i = 0; i < interactionClasses.length; i++) {
			if (function.equalsIgnoreCase(interactionClasses[i].getSimpleName())){
				interClass = interactionClasses[i];
			}
		}
		
		Interaction inter = null;
		try {
			Constructor interConstructor = interClass.getConstructor();
			inter = (Interaction) interConstructor.newInstance();
			parseParameters(n, inter);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		return inter;
	}
	
	public Termination makeTermination(Node n) {
		String[] pieces = n.contentLine.split(" ");
		String termType = pieces[0];
		
		
		Class termClass = null;
		for (int i = 0; i < terminationClasses.length; i++) {
			if (termType.equalsIgnoreCase(terminationClasses[i].getSimpleName())){
				termClass = terminationClasses[i];
			}
		}
		
		Termination term = null;
		try {
			Constructor termConstructor = termClass.getConstructor();
			term = (Termination) termConstructor.newInstance();
			parseParameters(n, term);
			term.initialize();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		return term;
	}
	
	
    public void parseParameters(Node n, Object obj){
//    	System.out.println("Parsing params for: " + obj.getClass().getSimpleName());
    	
    	HashMap<String,String> parameters = new HashMap<String,String>();
    	String[] pieces = n.contentLine.split(" ");
    	for (int i = 0; i < pieces.length; i++) {
			String piece = pieces[i];
			if (piece.contains("=")){
				String[] keyVal = piece.split("=");
				parameters.put(keyVal[0], keyVal[1]);
			}
		}
    	
    	
        //Get all fields from the class and store it as key->field
        Field[] fields = obj.getClass().getFields();
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
                	
                    fieldMap.get(parameter).set(obj, objVal);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("Unknown field (" + parameter + "=" + value +
                        ") from " + n.toString());
            }
        }

    }
	
    int INITIALIZATION_TIME = 100;
    
    public AbstractPlayer createController(String playerName) throws RuntimeException
    {
        AbstractPlayer player = null;
        try
        {
            Class<? extends AbstractPlayer> controllerClass = Class.forName(playerName).asSubclass(AbstractPlayer.class);
//            Class[] gameArgClass = new Class[]{StateObservation.class, ElapsedCpuTimer.class};
            Constructor controllerArgsConstructor = controllerClass.getConstructor();

            //Determine the time due for the controller creation.
            ElapsedCpuTimer ect = new ElapsedCpuTimer(ElapsedCpuTimer.TimerType.CPU_TIME);
            ect.setMaxTimeMillis(INITIALIZATION_TIME);

            //Call the constructor with the appropriate parameters.
//            Object[] constructorArgs = new Object[] {};
            player = (AbstractPlayer) controllerArgsConstructor.newInstance();


        }catch(Exception e)
        {
            e.printStackTrace();

        }

        return player;
    }


}
