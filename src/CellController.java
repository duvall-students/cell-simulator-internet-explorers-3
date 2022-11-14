// Sophie Halish
//Controller in MVC

import java.awt.*;
import java.util.Random;

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
    private double forestDensity=1.0;
    private int burningTreesNum=1;

    private CellView viewCell;

    private Random die=new Random();

    private CellModel modelCell;

    public CellController(){

    }

    public void newForest(){
        viewCell.createForest();
        modelCell.redraw();

    }

    public int increaseBurnTime(){
        burnTime++;
        return burnTime;
    }
    public int decreaseBurnTime(){
        burnTime--;
        return burnTime;
    }

    public double increaseSpreadProbability(){
        spreadProbability+=.1;
        return spreadProbability;
    }
    public double decreaseSpreadProbability(){
        spreadProbability-=.1;
        return spreadProbability;
    }

    public double increaseForestDensity(){
        forestDensity+=.1;
        return forestDensity;
    }
    public double decreaseForestDensity(){
        forestDensity-=.1;
        return forestDensity;
    }

    public int increaseNumberOfBurningTrees(){
        burningTreesNum++;
        return burningTreesNum;
    }
    public int decreaseNumberOfBurningTrees(){
        burningTreesNum--;
        return burningTreesNum;
    }
    public int getCellState(Point position)
    {
        return modelCell.get(position);
    }

    public boolean shouldBeLive() {
        double liveProbability=die.nextDouble();
        return(forestDensity==1 || liveProbability>=forestDensity);
    }

    public boolean isNowBurning(){
        double burnProbability=die.nextDouble();
        return(burnProbability<=spreadProbability);
    }

    public void doOneStep(double elapsedTime){

        viewCell.redraw();
    }

}
