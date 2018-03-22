package application;

public class SudokuAlgorithm {
	
	int size = 9;
	int[][] sudoku = new int[size][size];

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private boolean solveSudoku()
	{
		int row = 0, col = 0;
		boolean flag1 = false;
		
		for(int i = row; i < size; i++)
		{
			for(int j = col; j < size; j++)
			{
				//find empty location in sudoku grid
				if(sudoku[i][j] == 0)
				{
					flag1 = true;
					break;
				}
				col = j;
			}
			if(flag1 == true)
				break;
			
			row = i;
		}
		//find occupied location in sudoku grid
		if(flag1 == false)
			return true;
		//try all possible numbers from 1 to 9
		for(int n = 1; n <= size; n++)
		{
			//return when it works
			if(solveSudoku())
				return true;
			
			//else undo and try again
			sudoku[row][col] = 0;
		}
		
		return false;
	}

}
