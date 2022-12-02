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

	private Cell[][] cells;	// The squares making up the cells

	
	
	public CellModel(int rows, int columns, int burningTrees, double forestDensity){
		assert(rows > 0 && columns > 0);
		createGrid(rows, columns, burningTrees, forestDensity);
		
	}
	
	
	public void createGrid(int rows, int cols, int burningTrees, double density) {
		assert (rows > 0 && cols > 0);
		cells = new Cell[rows][cols];
		int i, j;
		for (i = 0; i < rows; i++)  // start with everything being an edge
			for (j = 0; j < cols; j++)
				cells[i][j] = new Cell(EDGE, new Point(i, j));
		for (i = 1; i < rows - 1; i++)  // replace negative values in cells[][] with empty cells
			for (j = 1; j < cols - 1; j++)
				if (density == 1) {
					cells[i][j] = new Cell(ALIVE, new Point(i, j));
				} else {
					double probability = random.nextDouble();
					if (probability <= density) {
						cells[i][j] = new Cell(ALIVE, new Point(i, j));
					} else {
						cells[i][j] = new Cell(EMPTY, new Point(i, j));
					}
				}
		cells[2][2].changeStatus(BURNING);
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
	public boolean validCell(Cell c){
		assert(cells!=null);
		return (c!=null && c.getX() < cells.length && c.getX() >= 0 && c.getY() < cells[0].length && c.getY() >= 0);
	}

	/*
	 * get - returns a cell state at the given position.
	 */
	public int getStatus(int x, int y){
//		assert(validCell(cell));
		return cells[x][y].getCellStatus();
	}
	
	/*
	 * isAlive - tells us if a cell is alive or not
	 */
	public boolean isAlive(Cell cell) {
		if((cells[cell.getX()][cell.getY()]).getCellStatus() == ALIVE) {
			return true;
		}
		return false;
	}
	
	
	public Collection<Cell> getNeighbors(Cell c) {
		List<Cell> maybeNeighbors = new ArrayList<>();
		maybeNeighbors.add(cells[c.getX()-1][c.getY()]);
		maybeNeighbors.add(cells[c.getX()+1][c.getY()]);
		maybeNeighbors.add(cells[c.getX()][c.getY()+1]);
		maybeNeighbors.add(cells[c.getX()][c.getY()-1]);
		List<Cell> neighbors = new ArrayList<>();
		for(Cell cell: maybeNeighbors){
			if(cells[cell.getX()][cell.getY()].getCellStatus() != EDGE) {
				neighbors.add(cell);
			}
		}
		return neighbors;
	}
	
	public Collection<Cell> getBurningCells() {
		List<Cell> burningCells = new ArrayList<>();
		for(int x = 0; x < cells.length; x++) {
			for(int y = 0; y < cells.length; y++) {
				if((cells[x][y].getCellStatus() == BURNING)) {
					burningCells.add(cells[x][y]);
				}
			}
		}
		return burningCells;
		
	}
	
	/*
	 * changeNeighorStatus - checks if tree is alive, starts burning neighbors
	 */
	public void changeNeighborStatus(Cell cell) {
		assert(validCell(cell));
		if(cells[cell.getX()][cell.getY()].getCellStatus() == BURNING) {
			if((cells[cell.getX()+-1][cell.getY()+-1]).getCellStatus() == ALIVE){
				cells[cell.getX()+-1][cell.getY()+-1].changeStatus(BURNING);
			}

		}
	}
	
	
	/*
	 * nowEmpty - turns cell into empty cell (brown)
	 */
	
	public void nowEmpty(Cell cell) {
		assert(validCell(cell));
		cells[cell.getX()][cell.getY()].changeStatus(EMPTY);
	}
	
	/*
	 * nowAlive - turns cell into alive tree (green)
	 */
	public void nowAlive(Cell cell) {
		assert(validCell(cell));
		cells[cell.getX()][cell.getY()].changeStatus(ALIVE);
	}

	/*
	 * nowBurning - turns cell into burning tree (red)
	 */
	public void nowBurning(Cell cell) {
		assert(validCell(cell));
		cells[cell.getX()][cell.getY()].changeStatus(BURNING);
	}
	
	/*
	 * nowBurnt - turns cell into burnt tree (yellow)
	 */
	public void nowBurnt(Cell cell) {
		assert (validCell(cell));
		cells[cell.getX()][cell.getY()].changeStatus(BURNT);
	}
	
	
	
}
