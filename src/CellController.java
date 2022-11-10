// Sophie Halish
//Controller in MVC

import java.awt.*;

/**
 * The size of the grid should be determined through the user interface.
 * The “burn time” of a tree will be 1, but may be changed by the user.
 *      This refers to how many cycles it takes for a tree to go from “burning” to “burnt-down” status.
 * The “spread probability” of the fire will start at .4, but may be changed by the user.
 *      This refers to how likely the fire is to spread to a neighbor tree.
 * The “forest density” will be 1, but may be changed by the user.
 *      This refers to the probability that a cell is initialized with a tree.
 *       At 1, all cells in the field will be trees except the edges.
 * The “burning trees #” variable will initially be 1, but may be changed by the user.
 *      This refers to the number of cells initialized as a burning tree.
 * Originally, the cells (other than the edges) should randomly have a live tree
 *      (with probability = forest density), and empty otherwise.
 *      Then randomly choose “burning trees #” cells and set them to burning status.
 */
public class CellController {
    private int burnTime=1;
    private double spreadProbability=.4;
    private int forestDensity=1;
    private int burningTreesNum=1;

    private CellView viewCell;

    private CellModel modelCell;

    public CellController(){

    }

    public void newForest(){
        viewCell.createForest();
        modelCell.redraw();

    }

    public void changeBurnTime(int userInput){
        burnTime=userInput;
    }

    public void changeSpreadProbability(int userInput){
        spreadProbability=userInput;
    }

    public void changeForestDensity(int userInput){
        forestDensity=userInput;
    }

    public void changeNumberOfBurningTrees(int userInput){
        burningTreesNum=userInput;
    }

    public int getCellState(Point position)
    {
        return modelCell.get(position);
    }

}
