package fastVGDL.parsing.core;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Diego
 * Date: 04/10/13
 * Time: 16:34
 * This is a Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class Node
{
    /**
     * Parent of this node.
     */
    public Node parent;

    /**
     *  Indent of the node in the tree.
     */
    public int indent;

    
    public String identifier; //first word in line
    
    /**
     * Children of this node.
     */
    public ArrayList<Node> children;

    
    public String contentLine;
    
    public boolean isDefinition = false;
    
    public int set = 0;

    public int id = -1;
    /**
     * Constructor of the node.
     * @param contentLine string with the node information
     * @param indent indent level of this node, to determine its place on the tree.
     * @param parent indicates the parent of the new node, if any.
     */
    public Node(String contentLine, int indent, Node parent, int set)
    {
        children = new ArrayList<Node>();
        this.contentLine = contentLine;
        
        String pieces[] = contentLine.split(" ");
        identifier = pieces[0].trim();
        
        if (contentLine.contains(">")) isDefinition = true;
        
        this.indent = indent;
        if(parent == null)
            this.parent = null;
        else
            parent.insert(this);
        
        this.set = set;
    }



    /**
     * Inserts a new node in the tree structure. Navigates from this node up towards the
     * root, inserting the new node at the correct indent.
     * @param node new node to add.
     */
    public void insert(Node node)
    {
        if(this.indent < node.indent)
        {
            if(this.children.size() > 0)
            {
                if(this.children.get(0).indent != node.indent)
                    throw new RuntimeException("children indentations must match");
            }
            this.children.add(node);
            node.parent = this;

        }else
        {
            if(this.parent == null)
                throw new RuntimeException("Root node too indented?");
            this.parent.insert(node);
        }
    }


    /**
     * Returns the root of the tree structure
     * @return the root
     */
    public Node getRoot()
    {
        if(this.parent != null)
            return this.parent.getRoot();
        else
            return this;
    }

}
