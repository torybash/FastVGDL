package fastVGDL.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/10/13
 * Time: 16:56
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class IO
{
    /**
     * Default constructor
     */
    public IO(){}

    /**
     * Reads a file and returns its content as a String[]
     * @param filename file to read
     * @return file content as String[], one line per element
     */
    public String[] getDescLinesFromFile(String filename)
    {
        ArrayList<String> lines = new ArrayList<String>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
            in.close();
        }catch(Exception e)
        {
            System.out.println("Error reading the file " + filename + ": " + e.toString());
            e.printStackTrace();
            return null;
        }
        return lines.toArray(new String[lines.size()]);
    }
    
    
    public String getDescFromFile(String filename)
    {
        String res = "";
        try{
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = in.readLine()) != null) {
            	res += line + "\n";
            }
            in.close();
        }catch(Exception e)
        {
            System.out.println("Error reading the file " + filename + ": " + e.toString());
            e.printStackTrace();
            return null;
        }
        return res;
    }
    
    
    public static void storeString(String game_desc, String folderPath, String title){
        String[] lines = game_desc.split("\\n");
        PrintWriter writer;
        String path = folderPath + title + ".txt";
        try {
            writer = new PrintWriter(path, "UTF-8");
            for (int i = 0; i < lines.length; i++) {
                    writer.println(lines[i]);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
