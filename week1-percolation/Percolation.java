
//Percolation class represents a 2D grid of cells that can be in an open or closed state.
//Its purpose is to be able to tell if a certain system percolates, i.e. water poured in the top can reach the bottom.
public class Percolation {
	
	//The length of one side of the squire grid
	private int gridLength;
	
	//Keep track of which cells are open
	private boolean[] openCells;
	
	//The quick union algorithm
	private WeightedQuickUnionUF unionAlgo;
	
	//Index of virtual top cell
	private int virtualTopCell;
	
	//Index of virtual bottom cell
	private int virtualBottomCell;
	
	//Constructor, accepts in length of grid
	public Percolation(int gridLength) {
		this.gridLength = gridLength;
		this.virtualTopCell = 0;
		this.virtualBottomCell = gridLength * gridLength + 1;
		
		this.openCells = new boolean[virtualBottomCell + 1];
		this.unionAlgo = new WeightedQuickUnionUF(virtualBottomCell + 1);
		
		//Open the virtual top and bottom cells
		openCells[virtualTopCell] = true;
		openCells[virtualBottomCell] = true;
	}
	
	//Open method will open a specific cell and, if appropriate unions it to open cardinal neighbours
	//i and j are the coordinates of cell in the grid, (1,1) being the top left cell
	public void open(int i, int j) {
		int arrayIndex = getIndexFromCoords(i, j);
		
		openCells[arrayIndex] = true;
		
		//check cell above and union if also open
		if (j == 1){
			unionAlgo.union(arrayIndex, virtualTopCell);
		} else {
			if (openCells[arrayIndex - gridLength]) {
				unionAlgo.union(arrayIndex, arrayIndex - gridLength);
			}
		}
		
		//check cell below and union if also open
		if (j == gridLength) {
			unionAlgo.union(arrayIndex, virtualBottomCell);
		} else {
			if(openCells[arrayIndex + gridLength]) {
				unionAlgo.union(arrayIndex, arrayIndex + gridLength);
			}
		}
		
		//check cell to the left and union if also open
		if(i > 1) {
			if(openCells[arrayIndex - 1]) {
				unionAlgo.union(arrayIndex,  arrayIndex - 1);
			}
		}
		
		//check cell to the right and union if also open
		if(i < gridLength) {
			if(openCells[arrayIndex + 1]) {
				unionAlgo.union(arrayIndex,  arrayIndex + 1);
			}
		}
	}
	
	//Returns true if the cell at coordinates (i,j) is open, false if it is closed
	public boolean isOpen(int i, int j) {
		int arrayIndex = getIndexFromCoords(i, j);
		return openCells[arrayIndex];
	}
	
	//Returns true if the cell at (i,j) is connected to the virtual top cell, false if it is not connected
	public boolean isFull(int i, int j) {
		int arrayIndex = getIndexFromCoords(i, j);
		return unionAlgo.connected(arrayIndex, virtualTopCell);
	}
	
	//Returns true if the grid can be percolated, i.e. there is a connection between the virtual top and bottom cell
	public boolean percolates() {
		return unionAlgo.connected(virtualTopCell, virtualBottomCell);
	}
	
	//checks the coordinates (i,j) are within the grid
	private void checkForValidIndex(int i, int j) {
		if (i <= 0
				|| j <= 0
				|| i > gridLength
				|| j > gridLength) {
			throw new IndexOutOfBoundsException("Attempting to access a cell outside of the grid");
		}
	}
	
	//converts the 2D coordinate (i,j) into the equivalent 1D index
	private int getIndexFromCoords(int i, int j) {
		checkForValidIndex(i,j);
		return (j - 1) * gridLength + i;
	}
}
