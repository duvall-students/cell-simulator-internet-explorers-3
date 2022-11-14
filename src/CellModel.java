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
	
	

	private int[][] cells;	// The squares making up the cells

	public CellModel(int rows, int columns){
		assert(rows > 0 && columns > 0);
		createGrid(tester, 8, random);
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
	 * changeNeighorStatus - checks if tree is alive, starts burning neighbors
	 */
	public void changeNeighborStatus(Point cell) {
		assert(validCell(cell));
		if(cells[cell.x][cell.y] == ALIVE) {
			cells[cell.x+-1][cell.y] = BURNING;
			cells[cell.x][cell.y+-1] = BURNING;
		}
	}
	
	
	
	/*
	 * isEmpty - turns cell into empty cell (brown)
	 */
	
	public void isEmpty(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = EMPTY;
	}
	
	/*
	 * isAlive - turns cell into alive tree (green)
	 */
	public void isAlive(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = ALIVE;
	}

	/*
	 * isBurning - turns cell into burning tree (red)
	 */
	public void isBurning(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = BURNING;
	}
	
	/*
	 * isBurnt - turns cell into burnt tree (yellow)
	 */
	public void isBurnt(Point cell) {
		assert(validCell(cell));
		cells[cell.x][cell.y] = BURNT;
	}
	
	
	public int[][] createGrid(int[][] area, int size, Random rand) {
	// create empty grid
		for (int i=0; i<size; i++) {
		      for (int j=0; j<size; j++)
		        area[i][j] = 0;
		    }
	    for (int i=0; i<size; i++) {
	        for (int j=0; j<size; j++)
	            if (area[i][j] == EMPTY) {
	              area[i][j] = ALIVE; 	// tree is now alive if it was empty
	            }
	            else if (area[i][j] == BURNING){
	              area[i][j] = BURNT;  // if tree was burning, it is now burnt
	          }
	      }
	    return area;
	}
	
	
	
	
}
