package solver;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import solver.DancingLinks.Cell;


public class SudokuSolver {
	 private final Board board;
	 public List<Integer> list = new ArrayList<Integer>();
	 int[][] array = new int[9][9];

	    public SudokuSolver(Board board) {
	        this.board = board;
	    }

	    private List<Cell> segment(int toggleOne) {
	        Integer size = board.size * board.size;

	        List<Cell> result = Lists.newArrayListWithCapacity(size);

	        for(int i = 0; i < size; i++) {
	            result.add(Cell._0);
	        }

	        result.set(toggleOne, Cell._1);

	        return result;
	    }

	    private List<Cell> encodeRow(Integer row, Integer col, Integer num) {

	        List<Cell> positionConstrain = segment(board.size * row + col);
	        List<Cell> rowConstrain = segment(board.size * row + num);
	        List<Cell> columnConstrain = segment(board.size * col + num);

	        Integer region = Double.valueOf(Math.ceil(row / board.cellSize)*board.cellSize +
	                Math.ceil(col / board.cellSize)).intValue() ;

	        List<Cell> regionConstrain = segment(board.size * region + num);

	        Iterable<Cell> result = Iterables.concat(positionConstrain, rowConstrain, columnConstrain,
	                regionConstrain);

	        return Lists.newArrayList(result);
	    }


	    private void decodeRow(Board board, List<String> row) {
	        Integer value = 0;
	        Integer m_row = 0;
	        Integer m_col = 0;

	        for(String atom : row) {
	        	String[] atoms = atom.split(" ");
	            String constrain = atoms[0];

	            if (constrain.equals("ROW")) {
	                m_row = Integer.valueOf(atoms[1]);
	                value = Integer.valueOf(atoms[2]);

	            } else if (constrain.equals("COL")) {
	                m_col = Integer.valueOf(atoms[1]);
	            }
	        }

	        board.m[m_row][m_col] = value;
	        array[m_row][m_col] = value;
	    }

	    public int[][] solve() {
	        DancingLinks x = new DancingLinks();

	        /* Encode Constrains */

	        // A position constraint: Only 1 number can occupy a cell
	        for(int row = 0; row < board.size; row++) {
	            for(int col = 0; col < board.size; col++) {
	                x.addColumn("POS " + row + " " + col);
	            }
	        }

	        // A row constraint: Only 1 instance of a number can be in the row
	        for(int row = 0; row < board.size; row++) {
	            for(int num = 1; num <= board.size; num++) {
	                x.addColumn("ROW " + row + " " + num);
	            }
	        }

	        // A column constraint: Only 1 instance of a number can be in a column
	        for(int col = 0; col < board.size; col++) {
	            for(int num = 1; num <= board.size; num++) {
	                x.addColumn("COL " + col + " " + num);
	            }
	        }

	        //A region constraint: Only 1 instance of a number can be in a region
	        for(int reg = 0; reg < board.size; reg++) {
	            for(int num = 1; num <= board.size; num++) {
	                x.addColumn("REG " + reg + " " + num);
	            }
	        }

	        /* encode rows */
	        for(int row = 0; row < board.size; row++) {
	            for(int col = 0; col < board.size; col++) {
	                Integer value = board.m[row][col];

	                if ( value == 0) {
	                    for (int i = 0; i < board.size; i++)  {
	                        x.addRow(encodeRow(row,col,i));
	                    }
	                }
	                else {
	                    List<Cell> curRow = encodeRow(row, col, value - 1);
	                    x.addRow(curRow);
	                }
	            }
	        }
	        Board result = Board.of(board.size);

	        for (List<String> row : x.search()) {
	            decodeRow(result, row);
	        }
	        return array;
	        //return result;
	   }

}
