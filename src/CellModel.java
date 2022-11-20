// Sibel Tanik
/*
 * The size of the grid should be determined through the user interface.
The “burn time” of a tree will be 1, but may be changed by the user.  
This refers to how many cycles it takes for a tree to go from “burning” to “burnt-down” status. 

The “spread probability” of the fire will start at .4, but may be changed by the user.  
This refers to how likely the fire is to spread to a neighbor tree.

The “forest density” will be 1, but may be changed by the user.  
This refers to the probability that a cell is initialized with a tree.  
At 1, all cells in the field will be trees except the edges.

The “burning trees #” variable will initially be 1, but may be changed by the user.  
This refers to the number of cells initialized as a burning tree.


Originally, the cells (other than the edges)  
should randomly have a live tree (with probability = forest density), and empty otherwise.  
Then randomly choose “burning trees #” cells and set them to burning status. 

 */

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CellModel {
	// Possible states of squares that make up a cells
	private static final int EDGE = 0;	
	private static final int EMPTY = 1;	
	private static final int ALIVE = 2;	
	private static final int BURNING = 3;
	private static final int BURNT = 4;
	private Random random;
	private int [][] tester = new int[8][3];
	private Point cell;

	
	

	private int[][] cells;	// The squares making up the cells

	public CellModel(int rows, int columns){
		assert(rows > 0 && columns > 0);
		createGrid(tester, 8, 3, random);
	}
	
	
	public int[][] createGrid(int[][] area, int sizeCOLUMN,int sizeROW, Random rand) {
	// create empty grid
		for (int i=0; i<sizeCOLUMN; i++) {
		      for (int j=0; j<sizeROW; j++)
		        area[i][j] = 0;
		    }
//	    for (int i=0; i<size; i++) {
//	        for (int j=0; j<size; j++)
//	            if (area[i][j] == EMPTY) {
//	              area[i][j] = ALIVE; 	// tree is now alive if it was empty
//	            }
//	            else if (area[i][j] == BURNING){
//	              area[i][j] = BURNT;  // if tree was burning, it is now burnt
//	          }
//	      }
	    return area;
	}

	public int getNumRows(){
		assert(cells!=null);
		return cells.length;
	}

	public int getNumCols(){
		assert(cells!=null);
		return cells[0].length;
	}
	
	
	/*
	 * Check to see if the cell is inside the outer walls of the cells
	 */
	public boolean inBounds(Point c){
		assert(cells!=null);
		return (c!= null && c.x < cells.length-1 && c.x > 0 && c.y < cells[0].length-1 && c.y >0);
	}

	/*
	 * Check to see if the cell is in bounds (won't cause out-of-bounds or null errors)
	 */
	public boolean validCell(Point c){
		assert(cells!=null);
		return (c!=null && c.x < cells.length && c.x >= 0 && c.y < cells[0].length && c.y >= 0);
	}

	/*
	 * get - returns a cell state at the given position.
	 */
	public int getStatus(Point cell){
		assert(validCell(cell));
		return cells[cell.x][cell.y];
	}
	
	/*
	 * isAlive - tells us if a cell is alive or not
	 */
	public boolean isAlive(Point cell) {
		if((cells[cell.x][cell.y]) == ALIVE) {
			return true;
		}
		return false;
	}
	
	
	public Collection<Point> getNeighbors(Point c) {
		List<Point> maybeNeighbors = new ArrayList<>();
		maybeNeighbors.add(new Point(c.x-1,c.y));
		maybeNeighbors.add(new Point(c.x+1,c.y));
		maybeNeighbors.add(new Point(c.x,c.y+1));
		maybeNeighbors.add(new Point(c.x,c.y-1));
		List<Point> neighbors = new ArrayList<>();
		for(Point cell: maybeNeighbors){
			if(cells[cell.x][cell.y] != EDGE) {
				neighbors.add(cell);
			}
		}
		return neighbors;
	}
	
	public Collection<Point> getBurningCells() {
		List<Point> burningCells = new ArrayList<>();
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells.length; y++) {
				if((cells[x][y] == BURNING)) {
					burningCells.add(new Point(x,y));
				}
			}
		}
		return burningCells;
		
	}
	
	/*
	 * changeNeighorStatus - checks if tree is alive, starts burning neighbors
	 */
	public void changeNeighborStatus(Point cell) {
		assert(validCell(cell));
		if(cells[cell.x][cell.y] == BURNING) {
			if((cells[cell.x+-1][cell.y+-1]) == ALIVE);
				cells[cell.x=-1][cell.y+-1] = BURNING;
		}
	}
	
	
	/*
	 * nowEmpty - turns cell into empty cell (brown)
	 */
	
	public void nowEmpty(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = EMPTY;
	}
	
	/*
	 * nowAlive - turns cell into alive tree (green)
	 */
	public void nowAlive(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = ALIVE;
	}

	/*
	 * nowBurning - turns cell into burning tree (red)
	 */
	public void nowBurning(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = BURNING;
	}
	
	/*
	 * nowBurnt - turns cell into burnt tree (yellow)
	 */
	public void nowBurnt(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = BURNT;
	}
	

	
	
	
	
}
