package thread;

import java.io.IOException;

public class MatrixProd extends Thread{
	private final static int NUMTHREADS = 4;
	
	private static Matrix a;
	private static Matrix b;
	private static Matrix c;
	private int t;
	
	
	
	
	public MatrixProd(int offset){
		t = offset;		
	}
	
	@Override
	public void run() { 
		
		
		int i,j,k;
		int offset = t;
		int start,end,len;
		double sum;
		
		
		int rowA = a.getRow();
		int colA = a.getCol();
		int rowB = b.getRow();
		int colB = b.getCol();

		if (colA != rowB){
			throw new RuntimeException("The size of two matrices is not compatible.");
		}

		//calculate the block of matrix to do the multiplication
		len = (int) Math.ceil(colA / NUMTHREADS);
		start = len*offset;

		if (offset == NUMTHREADS-1)
			end = colA;
		else 
			end = start+len;
		
		System.out.println(Thread.currentThread().getName()+":"+t);
		System.out.printf("Thread-%d: len=%d, start=%d, end=%d\n", t, len, start,end);
		//create a local matrix to store the block multiplication result
		Matrix local = new Matrix(rowA,colB);
		
		

		for (i=0; i<rowA; i++){
			for (j=0; j<colB; j++){
				sum=0;	
				for (k=start;k<end;k++){
					sum += a.data[i][k]*b.data[k][j];
				}		
				local.data[i][j]=sum;
			}
		}
		
		
		//add the local result to the final
		synchronized(c){
			try {
				System.out.println(this.getName()+" starts.");
				c.matrixAdd(local);
				System.out.println(this.getName()+" ends.");
				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException{
		int size = Integer.parseInt(args[0]);
		a = new Matrix(size,size);
		a.matrixFillRandom();
		b = new Matrix(size,size);
		b.matrixFillRandom();
		c = new Matrix(size,size);
		
		
		
		
		MatrixProd []thread = new MatrixProd[NUMTHREADS];
		
		for (int i=0; i<NUMTHREADS; i++){
			thread[i] = new MatrixProd(i);
			thread[i].start();
		}
		
		for (int i=0; i<NUMTHREADS; i++){
			try {
				thread[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		a.fprintMatrix("a.data");
		b.fprintMatrix("b.data");
		c.fprintMatrix("c.data");
		/*
		a.printMatrix();
		b.printMatrix();
		c.printMatrix();
		*/
	}	
}
