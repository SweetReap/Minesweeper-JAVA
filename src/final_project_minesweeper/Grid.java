//package final_project_minesweeper;

// CODE TO VIDEO: https://www.youtube.com/watch?v=Y3zssR9oA4s
import java.util.Random;

public class Grid {
	
	private boolean[][] bombGrid;
	private int[][] countGrid;
	private int numRows;
	private int numColumns;
	private int numBombs;
	
	public Grid() {
		this.numRows = 10;
		this.numColumns = 10;
		this.numBombs = 25;
		
		this.bombGrid = new boolean[this.numRows][this.numColumns];
		this.countGrid = new int[this.numRows][this.numColumns];
		
		this.createBombGrid();
		this.createCountGrid();
	}
	
	public Grid(int rows, int columns) {
		this.numRows = rows;
		this.numColumns = columns;
		this.numBombs = 25;
		
		this.bombGrid = new boolean[this.numRows][this.numColumns];
		this.countGrid = new int[this.numRows][this.numColumns];
		
		this.createBombGrid();
		this.createCountGrid();
	}
	
	public Grid(int rows, int columns, int numBombs) {
		this.numRows = rows;
		this.numColumns = columns;
		this.numBombs = numBombs;
		
		this.bombGrid = new boolean[this.numRows][this.numColumns];
		this.countGrid = new int[this.numRows][this.numColumns];
		
		this.createBombGrid();
		this.createCountGrid();
	}

	public boolean[][] getBombGrid() {
		boolean[][] copy = new boolean[this.numRows][this.numColumns];
		
		for(int i = 0;i < this.numRows; i++) {
			for(int j = 0;j < this.numColumns;j++) {
				copy[i][j] = this.bombGrid[i][j];
			}
		}
		
		return copy;
	}

	public int[][] getCountGrid() {
		int[][] copy = new int[this.numRows][this.numColumns];
		
			for(int i = 0;i < this.numRows; i++) {
				for(int j = 0;j < this.numColumns;j++) {
					copy[i][j] = this.countGrid[i][j];
				}
			}
			

		return copy;
	}

	public int getNumRows() {
		return this.numRows;
	}

	public int getNumColumns() {
		return this.numColumns;
	}

	public int getNumBombs() {
		return this.numBombs;
	}
	
	public boolean isBombAtLocation(int row, int column) { 
		return (this.bombGrid[row][column] ? true:false);
	}
	
	public int getCountAtLocation(int row, int column) {
		return this.countGrid[row][column];
	}
	
	private void createBombGrid() {
		int bombs = this.numBombs;
		
		for(int i = 0; i < this.numRows; i++) {
			
			for (int j = 0; j < this.numColumns;j++) {
				if(bombs > 0) {
					boolean currVal = new Random().nextBoolean();
					this.bombGrid[i][j] = currVal; 
					if(currVal) bombs--;	
				}else {
					this.bombGrid[i][j] = false;
				}
			}
		}
		
		for(int i = 0; i < this.numRows;i++) {
			for(int j = 0; j < this.numColumns;j++) {
				System.out.printf("[ %b ]",this.bombGrid[i][j]);	
			}
			System.out.println();
		}
		System.out.print("# of bombs remaining: " + bombs + " " + this.numBombs);	
	}
	
	
	private void createCountGrid() { //big array search log(n^inf) LOL
		
		for(int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numColumns;j++) {
				int surroundingBombs = 0;
				for(int x = i-1;x <= i+1;x++) {
					for(int y = j-1;y <= j+1;y++) {
						if(this.insideOfBounds(x, y)) { //checking out of bound locations
							if(this.bombGrid[x][y] == true) {
								surroundingBombs++;
							}
						}	
					}
				}	
				this.countGrid[i][j] = surroundingBombs;
			}
		}
//		for(int i = 0; i < this.numRows;i++) {
//			for(int j = 0; j < this.numColumns;j++) {
//				System.out.println(" [ Position: " +"("+i+ "," +j+") Surrounding Bombs: " + this.countGrid[i][j] + " ]");	
//			}
//		System.out.println();
//		}
	}
//	
//	private boolean getRandBool(int numBombs) { //using helper random func
//		double spread = (numBombs+38)/ 100.0;
//		return Math.random() > spread;
//	}

	private boolean insideOfBounds(int row, int column) {
		if(row >= 0 && row < this.numRows && column >= 0 && column < this.numColumns) {
			return true;
		}else {
			return false;
		}
	}
}
