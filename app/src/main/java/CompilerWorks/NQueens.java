package CompilerWorks;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NQueens {
	
	public static void main(String[] args) {
		NQueens nqueens = new NQueens();
    	int numberOfQueens = nqueens.getNumberOfQueens();
    	char[][] chessBoard = new char [numberOfQueens][numberOfQueens];
    	nqueens.initializeChessBoard(chessBoard);
    	chessBoard = nqueens.solveNQueens(numberOfQueens, 0, chessBoard);
    	
    	if (chessBoard == null) {
    		System.out.println("\n\nNo solution exists!");
    	}
    	else {
    		nqueens.printChessBoard(chessBoard);
    	}
    }
	
	private char[][] solveNQueens(int numberOfQueens, int row, char[][] chessBoard) {
		if (row == numberOfQueens) {
			// We have successfully placed all the queens - return the chessboard
			return chessBoard;
		}
		else {
			for (int i = 0; i < chessBoard[row].length; i++) {
				
				chessBoard[row][i] = 'Q';
				
				if (isValidPlacement(chessBoard, row, i)) {
					if (solveNQueens(numberOfQueens, row + 1, chessBoard) != null) {
						return chessBoard;
					}
					else {
						//Backtrack to above row and start the process from the next column
						chessBoard[row][i] = '_';
					}
				}
				else {
					//not valid placement - reset the queen's position
					chessBoard[row][i] = '_';
				}
			}
		}
		return null;
	}
	
	private boolean isValidPlacement(char[][] chessBoard, int row, int col) {
		
		//check vertically above the current queen's placement
		for (int i = 0; i < row; i++) {
			if (chessBoard[i][col] == 'Q') {
				return false;
			}
		}
		
		//check diagonally to the left and above of current queen's placement
		for (int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--) {
			if (chessBoard[i][j] == 'Q') {
				return false;
			}
		}
		
		//check diagonally to the right and above of current queen's placement
		for (int i = row-1, j = col+1; i >= 0 && j < chessBoard[row].length; i--, j++) {
			if (chessBoard[i][j] == 'Q') {
				return false;
			}
		}
		
		//check for 3 queens in a straight line AFTER row index '1' - 0,'1' -- because, before that, possibility does not arise
		if (row > 1) {
			int x1,y1 = -1;
			int x2 = row; int y2 = col; //x2,y2 is the current queen's placement
			
			for (int i = chessBoard.length - 1; i >= 0; i--) {
				for (int j = row-1; j >= 0; j--) {
					if (chessBoard[j][i] == 'Q') {
						x1 = j; y1 = i;
						
						//Equation of a straight line is: y = mx + c -- m is the slope and 'c' is the intercept 
						double lineSlope = (double)(y2-y1)/(double)(x2-x1);
						double c = y2 - (lineSlope * x2);
						int numQueensInStraightLine = calcNumQueensInStraightLine(lineSlope, c, chessBoard);
						
						if (numQueensInStraightLine >= 3) {
							return false;
						}
					}
				} 
			}
		}
		
		return true;
	}
	
	private int calcNumQueensInStraightLine(double lineSlope, double intercept, char[][] chessBoard) {
		int numQueensInStraightLine = 0;
		
		for (int x = 0; x < chessBoard.length; x++) {
			for(int y = 0; y < chessBoard[x].length; y++) {
				//check if x,y falls on the line with slope=lineSlope and intercept=intercept
				if (chessBoard[x][y] == 'Q' && y == (lineSlope * x) + intercept) {
					numQueensInStraightLine++;
				}
			}
		}
		return numQueensInStraightLine;
	}
	
	private void printChessBoard(char chessBoard[][]) {
		System.out.print("\n\n********************************************\n\n");
		for (int i = 0; i < chessBoard.length; i++) {
			for (int j = 0; j < chessBoard[i].length; j++) {
				System.out.print(chessBoard[i][j] + " ");
			}
			System.out.println();
		}
	};
	
	private void initializeChessBoard(char chessBoard[][]) {
		for (int i = 0; i < chessBoard.length; i++) {
			for (int j = 0; j < chessBoard[i].length; j++) {
				chessBoard[i][j] = '_';
			}
		}
	}
	
	private int getNumberOfQueens() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int number = -1;
		
    	System.out.print("Enter number of queens ('N' -- Execution time increases considerably after N=21) --> ");
    	try {
    		number = Integer.parseInt(br.readLine());
    		if (number < 0 || number == 0) {
    			throw new Exception();
    		}
    	}
    	catch (Exception e) {
    		System.out.println("Please enter valid input (1....N). Program will exit now.");
    		System.exit(0);
    	}
    	return number;
	};
}