package application;

import javax.swing.JTextField;

public class SudokuAlgorithm {
	
	int size = 9;
	int[][] sudoku = new int[size][size];
	JTextField[] tf = new JTextField[size*size];

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private boolean solveSudoku()
	{
		int row = 0, col = 0;
		boolean flag1 = false;
		
		for(row = 0; row < size; row++)
		{
			for(col = 0; col < size; col++)
			{
				//find empty location in sudoku grid
				if(sudoku[row][col] == 0)
				{
					flag1 = true;
					break;
				}
			}
			if(flag1 == true)
				break;
			
		}
		//find occupied location in sudoku grid
		if(flag1 == false)
			return true;
		//try all possible numbers from 1 to 9
		for(int n = 1; n <= size; n++)
		{
			if(isSafe(row,col,n))
			{
				//make assignment
                sudoku[row][col] = n ;
                //print output;
                (tf[(row)*size + col]).setText(Integer.toString(n));
               
                //return when it works
				if(solveSudoku())
					return true;
			
				//else undo and try again
				sudoku[row][col] = 0;
			}
		}
		//initiates backtracking
		return false;
	}
	private boolean isSafe(int r , int c , int n)
	{
		return ( !usedInRow(r,c,n) && !usedInCol(r,c,n) ) ;
	}
	private boolean usedInRow(int r , int c, int n)
    { 
        for(int col=0 ; col<size ; col++ )
        {
            if( col != c && sudoku[r][col] == n )
            {
                return true;
            }
        }
        
        
        return false;
    }
    private boolean usedInCol(int r,int c , int n)
    {
        for(int row=0 ; row < size ; row++ )
        {
            if( row != r && sudoku[row][c] == n )
            {
                return true;
            }
        }
        
        return false;
    }

}
