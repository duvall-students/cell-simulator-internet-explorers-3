// Sophie Halish
//Controller in MVC

import javafx.scene.control.Cell;

import java.awt.*;
import java.util.Collection;
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

    private CellView viewCell=new CellView();

    private final Random die=new Random();

    private CellModel modelCell;

    public CellController(int numRows, int numCols, CellView thisDisplay){
        modelCell=new CellModel(numRows, numCols, burningTreesNum, forestDensity);
        viewCell=thisDisplay;
    }

    public void newForest(int row, int col){
    	modelCell=new CellModel(row, col, burningTreesNum, forestDensity);
        viewCell.redraw();
    }

    public void setBurnTime(int entry)
    {
        burnTime=entry;
    }

    public void setSpreadProbability(Double entry)
    {
        spreadProbability=entry;
    }

    public void setForestDensity(double entry)
    {
        forestDensity=entry;
    }

    public void setBurningTreesNum(int entry)
    {
        burningTreesNum=entry;
    }

//    public int increaseBurnTime(){
//        burnTime++;
//        return burnTime;
//    }
//    public int decreaseBurnTime(){
//        burnTime--;
//        return burnTime;
//    }
//
//    public double increaseSpreadProbability(){
//        spreadProbability+=.1;
//        return spreadProbability;
//    }
//    public double decreaseSpreadProbability(){
//        spreadProbability-=.1;
//        return spreadProbability;
//    }
//
//    public double increaseForestDensity(){
//        forestDensity+=.1;
//        return forestDensity;
//    }
//    public double decreaseForestDensity(){
//        forestDensity-=.1;
//        return forestDensity;
//    }
//
//    public int increaseNumberOfBurningTrees(){
//        burningTreesNum++;
//        return burningTreesNum;
//    }
//    public int decreaseNumberOfBurningTrees(){
//        burningTreesNum--;
//        return burningTreesNum;
//    }
    public int getCellState(Point position)
    {
        return modelCell.getStatus(position);
    }

    //true if the tree is alive
    public boolean shouldBeLive() {
        double liveProbability=die.nextDouble();
        return(forestDensity==1 || liveProbability>=forestDensity);
    }

    //true if the tree is now burning
    public boolean shouldBurn(){
        double burnProbability=die.nextDouble();
        return(burnProbability<=spreadProbability);
    }

    public void doOneStep(double elapsedTime){
      //  if(!modelCell.isForestBurned()){step();}
        viewCell.redraw();
    }

    //updates burning trees, and their neighbors
    public void step()
    {
        Collection<Point> burningCells=modelCell.getBurningCells();
        for(Point burningCell: burningCells){
            burnNeighbors(burningCell);
            modelCell.nowBurnt(burningCell);
        }
    }

    public void burnNeighbors(Point tree){
        Collection<Point> neighbors= modelCell.getNeighbors(tree);
        for(Point checkThisCell : neighbors){
            if(modelCell.isAlive(checkThisCell)) //checks if alive
            {
                if(shouldBurn()){//if control decides it should burn
                    modelCell.nowBurning(checkThisCell); //changes to burning
                }
            }
        }
    }
    // ***how to fix this if the burn time isn't 1
}
