package thread;

import java.io.*;

public class Matrix{
	private int row;
	private int col;
	public double[][] data;

	public Matrix(int row, int col) {
		this.row = row;
		this.col = col;
		data = new double[row][];
		for (int i = 0; i < row; i++) {
			data[i] = new double[col];
		}
	}

	public void matrixFillRandom() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				data[i][j] = Math.random();
			}

		}

	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void printMatrix() {
		System.out.printf("%d %d\n",row, col);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(String.format("%.4f", data[i][j]) + " ");
			}
			System.out.println();
		}

	}

	public void matrixAdd(Matrix a) throws Exception {
		if ((a.row != row) || (a.col != col)) {
			throw new MatrixCompatibleException(
					"Matrices size not compatiable!");

		}

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				data[i][j] += a.data[i][j];
			}
		}
	}

	public void fprintMatrix(String fname) throws IOException {
		PrintStream output = new PrintStream(
				new BufferedOutputStream(
						new FileOutputStream(fname)));
		
		//output.printf("%d %d\n",row, col);
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				output.printf("%.4f ",data[i][j]);
			}
			output.printf("\n");
		}
		output.close();		
	}
	
	
	
	public static void main(String[] args){
		
		Matrix a = new Matrix(10,10);
		a.matrixFillRandom();
		a.printMatrix();
		try {
			a.fprintMatrix("a.data");
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		
	}
	
}

@SuppressWarnings("serial")
class MatrixCompatibleException extends Exception {
	public MatrixCompatibleException() {
	}

	public MatrixCompatibleException(String msg) {
		super(msg);
	}
}
