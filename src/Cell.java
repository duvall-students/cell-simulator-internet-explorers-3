import java.awt.Point;

public class Cell {
	
	private int cellStatus;
	private int timeBurning;
	private Point cell;
	
	public Cell(int status, Point c) {
		cell = new Point(c.x, c.y);
		cellStatus = status;
		timeBurning = 0;
	}
	
	
	public int getX() {
		return cell.x;
	}
	
	public int getY() {
		return cell.y;
	}
	
	public void changeStatus(int status) {
		cellStatus = status;
	}
	
	
	public int getCellStatus() {
		return cellStatus;
	}
	
	public int getTimeBurning() {
		return timeBurning;
	}
	
	public void increaseTimeBurning() {
		timeBurning++;
	}

}
