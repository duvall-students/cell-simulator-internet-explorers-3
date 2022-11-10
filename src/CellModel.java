import java.awt.Point;

public class CellModel {
	// Possible states of squares that make up a cells
	public static final int EDGE = 0;	
	public static final int EMPTY = 1;	
	public static final int ALIVE = 2;	
	public static final int BURNING = 3;
	public static final int BURNT = 4;
	

	private int[][] cells;	// The squares making up the cells

	public CellModel(int rows, int columns){
		assert(rows > 0 && columns > 0);
		createGrid(rows, columns);
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
	 * Check to see if the square is inside the outer walls of the cells
	 */
	public boolean inBounds(Point p){
		assert(cells!=null);
		return (p!= null && p.x < cells.length-1 && p.x > 0 && p.y < cells[0].length-1 && p.y >0);
	}

	/*
	 * Check to see if the point is in bounds (won't cause out-of-bounds or null errors)
	 */
	public boolean validPoint(Point p){
		assert(cells!=null);
		return (p!=null && p.x < cells.length && p.x >= 0 && p.y < cells[0].length && p.y >= 0);
	}

	/*
	 * get - returns a square state at the given position.
	 */
	public int get(Point square){
		assert(validPoint(square));
		return cells[square.x][square.y];
	}
	
	
	
	/*
	 * Create a new random cells of the given dimensions and store the result.
	 * cells has no cycles.
	 */

	public void createGrid(int rows, int cols) {
		assert(rows > 0 && cols > 0);
		cells = new int[rows][cols];
		// Create a random cells.  The strategy is to start with
		// a grid of disconnected "rooms" separated by walls,
		// then look at each of the separating walls, in a random
		// order.  If tearing down a wall would not create a loop
		// in the cells, then tear it down.  Otherwise, leave it in place.
		int i,j;
		int emptyCt = 0; // number of rooms
		int wallCt = 0;  // number of walls
		int[] wallrow = new int[(rows*cols)/2];  // position of walls between rooms
		int[] wallcol = new int[(rows*cols)/2];
		for (i = 0; i<rows; i++)  // start with everything being a wall
			for (j = 0; j < cols; j++)
				cells[i][j] = WALL;
		for (i = 1; i<rows-1; i += 2)  { // make a grid of empty rooms
			for (j = 1; j<cols-1; j += 2) {
				emptyCt++;
				cells[i][j] = -emptyCt;  // each room is represented by a different negative number
				if (i < rows-2) {  // record info about wall below this room
					wallrow[wallCt] = i+1;
					wallcol[wallCt] = j;
					wallCt++;
				}
				if (j < cols-2) {  // record info about wall to right of this room
					wallrow[wallCt] = i;
					wallcol[wallCt] = j+1;
					wallCt++;
				}
			}
		}
		int r;
		for (i=wallCt-1; i>0; i--) {
			r = (int)(Math.random() * i);  // choose a wall randomly and maybe tear it down
		//	tearDown(wallrow[r],wallcol[r]);
			wallrow[r] = wallrow[i];
			wallcol[r] = wallcol[i];
		}
		for (i=1; i<rows-1; i++)  // replace negative values in cells[][] with emptyCode
			for (j=1; j<cols-1; j++)
				if (cells[i][j] < 0)
					cells[i][j] = EMPTY;
	}
}
