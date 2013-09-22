import java.util.LinkedList;
import java.util.List;

/** Class board represents the state of a 2d NxN puzzle board. */
public class Board {
	private final int[][] blocks;
	private int moves;
	
	/** CONSTRUCTOR: Requires the starting blocks in a 2d integer array.
	 * If no move count is supplied, it is assumed to be a new board, so moves = 0. */
	public Board(int[][] blocks) {
		this(blocks, 0);
	}
	
	private Board(int[][] blocks, int moves) {
		this.moves = moves;
		this.blocks = new int[blocks.length][blocks.length];
		
		for (int row = 0; row < this.blocks.length; row++) {
			for (int col = 0; col < this.blocks.length; col++) {
				this.blocks[row][col] = blocks[row][col];
			}
		}
	}
	
	/** Returns the dimension (N) of the NxN puzzle board. */
	public int dimension() {
		return blocks.length;
	}
	
	/** Returns the Hamming priority function of the board.
	 * Defined as number of moves, plus 1 for each block out of position. */
	public int hamming() {
		int hammingScore = moves;
		
		for (int row = 0; row < this.dimension(); row++) {
			for (int column = 0; column < this.dimension(); column++) {
				if (blocks[row][column] != 0 && blocks[row][column] != getGoalValueForBlock(row, column)) {
					hammingScore++;
				}
			}
		}
		
		return hammingScore;
	}
	
	/** Returns the Manhattan priority function of the board.
	 * Defined as number of moves plus row + column offset of each square. */
    public int manhattan() {
    	int manhattanScore = moves;
    	
    	for (int row = 0; row < this.dimension(); row++) {
    		for (int column = 0; column < this.dimension(); column++) {
    			if (blocks[row][column] != 0) {
	    			int compareValue = blocks[row][column];
	    			
	    			int expectedRow = (compareValue - 1 ) / this.dimension();
	    			int expectedColumn = (compareValue - 1) - (expectedRow * this.dimension());
	    			
	    			manhattanScore += (Math.abs(expectedRow - row) + Math.abs(expectedColumn - column));
    			}
    		}
    	}
    	
    	return manhattanScore;
    }
    
    /** Returns true when board is equal to the goal board */
    public boolean isGoal() {
    	for (int row = 0; row < this.dimension(); row++) {
    		for (int column = 0; column < this.dimension(); column++) {
    			if (blocks[row][column] != getGoalValueForBlock(row, column)) {
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    /** A board obtained by exchanging 2 blocks in the same row */
    public Board twin() {
    	int[][] newBlocks = new int[dimension()][dimension()];
    	for (int row = 0; row < dimension(); row++) {
    		for (int col = 0; col < dimension(); col++) {
    			newBlocks [row][col] = blocks[row][col];
    		}
    	}
    	
    	//Swap 2 blocks that are not 0
    	int rowSwap = 0;
    	if (newBlocks[0][0] == 0 || newBlocks[0][1] == 0) {
    		rowSwap = 1;
    	}
    	
    	int temp = newBlocks[rowSwap][0];
    	newBlocks[rowSwap][0] = newBlocks[rowSwap][1];
    	newBlocks[rowSwap][1] = temp;
    	
    	//Create new board to return
    	return new Board(newBlocks, moves);
    }
    
    /** Returns true if this board is equal to Object that */
    public boolean equals(Object y) {
    	if (y == null) {
    		return false;
    	}
    	
    	if (this == y) {
    		return true;
    	}
    	
    	if (this.getClass() != y.getClass()) {
    		return false;
    	}
    	
    	Board that = (Board) y;
    	
    	if (this.dimension() != that.dimension()) {
    		return false;
    	}
    	
    	for (int row = 0; row < this.dimension(); row++) {
    		for (int col = 0; col < this.dimension(); col++) {
    			if (this.blocks[row][col] != that.blocks[row][col]) {
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    /** All Boards that can legally be made by a single move */
    public Iterable<Board> neighbors() {
    	int spaceRowPos = 0;
    	int spaceColPos = 0;
    	
    	//Find the empty square
    	for (int row = 0; row < dimension(); row++) {
    		for (int column = 0; column < dimension(); column++) {
    			if (blocks[row][column] == 0) {
    				spaceRowPos = row;
    				spaceColPos = column;
    			}
    		}
    	}
    	
    	List<Board> neighbors = new LinkedList<Board>();
    	
    	//Down
    	if (spaceRowPos < dimension() - 1) {
    		int[][] downBlocks = new int[dimension()][dimension()];
    		for (int row = 0; row < dimension(); row++) {
        		for (int col = 0; col < dimension(); col++) {
        			downBlocks[row][col] = blocks[row][col];
        		}
        	}
    		
    		int temp = downBlocks[spaceRowPos][spaceColPos];
        	downBlocks[spaceRowPos][spaceColPos] = downBlocks[spaceRowPos + 1][spaceColPos];
        	downBlocks[spaceRowPos + 1][spaceColPos] = temp;
        	
    		neighbors.add(new Board(downBlocks, moves + 1));
    	}
    	
    	//Up
    	if (spaceRowPos > 0) {
    		int[][] upBlocks = new int[dimension()][dimension()];
    		for (int row = 0; row < dimension(); row++) {
        		for (int col = 0; col < dimension(); col++) {
        			upBlocks[row][col] = blocks[row][col];
        		}
        	}
    		
    		int temp = upBlocks[spaceRowPos][spaceColPos];
        	upBlocks[spaceRowPos][spaceColPos] = upBlocks[spaceRowPos - 1][spaceColPos];
        	upBlocks[spaceRowPos - 1][spaceColPos] = temp;
        	
    		neighbors.add(new Board(upBlocks, moves + 1));
    	}
    	
    	//Left
    	if (spaceColPos > 0) {
    		int[][] leftBlocks = new int[dimension()][dimension()];
    		for (int row = 0; row < dimension(); row++) {
        		for (int col = 0; col < dimension(); col++) {
        			leftBlocks[row][col] = blocks[row][col];
        		}
        	}
    		
    		int temp = leftBlocks[spaceRowPos][spaceColPos];
        	leftBlocks[spaceRowPos][spaceColPos] = leftBlocks[spaceRowPos][spaceColPos - 1];
        	leftBlocks[spaceRowPos][spaceColPos - 1] = temp;
        	
    		neighbors.add(new Board(leftBlocks, moves + 1));
    	}
    	
    	//Right
    	if (spaceColPos < dimension() - 1) {
    		int[][] rightBlocks = new int[dimension()][dimension()];
    		for (int row = 0; row < dimension(); row++) {
        		for (int col = 0; col < dimension(); col++) {
        			rightBlocks[row][col] = blocks[row][col];
        		}
        	}
    		
    		int temp = rightBlocks[spaceRowPos][spaceColPos];
        	rightBlocks[spaceRowPos][spaceColPos] = rightBlocks[spaceRowPos][spaceColPos + 1];
        	rightBlocks[spaceRowPos][spaceColPos + 1] = temp;
        	
    		neighbors.add(new Board(rightBlocks, moves + 1));
    	}
    	
    	return neighbors;
    }
    
    /** A string representation of the board */
    public String toString() {
    	StringBuilder sb = new StringBuilder(dimension() + " \n ");
    	
    	for (int row = 0; row < dimension(); row++) {
    		for (int column = 0; column < dimension(); column++) {
    			sb.append(blocks[row][column]);
    			sb.append(" ");
    		}
    		
    		sb.append("\n ");
    	}
    	
    	return sb.toString();
    }
    
    private int getGoalValueForBlock(int row, int column) {
    	//Last block is assumed to be the empty one, i.e. 0
    	if (row == dimension() - 1 && column == dimension() - 1) {
    		return 0;
    		
    	} else {
    		return (row * dimension()) + column + 1;
    	}
    }
    
    public static void main(String[] args) {
    	int[][] input = new int[][]{{1, 2, 3, 4}, {5, 6, 0, 8}, {9, 10, 11, 12}, {13, 14, 15, 7}};
    	Board testBoard = new Board(input);
    	
    	StdOut.println(testBoard.toString());
    	
    	Iterable<Board> result = testBoard.neighbors();
    	
    	for (Board b : result) {
    		StdOut.println(b.toString());
    	}
    }
}
