package DISPARP_Lab_Week20;
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

public class MatrixParallelMain {
	public static ArrayList<Integer> valueMAtA = new ArrayList<Integer>();
	public static ArrayList<Integer> valueMAtB = new ArrayList<Integer>();
	
	long timetaken;

	public static ArrayList<Integer> getValueMAtA() {
		return valueMAtA;
	}
	public static ArrayList<Integer> getValueMAtB() {
		return valueMAtB;
	}
	public void parallel(int size)
	{      
		Random ran = new Random();
		int rA,cA,rB,cB;
		long startTime = System.currentTimeMillis();

		for(int k=2; k<size+3; k++)	//start with array 2x2
		{
			rA =k; cA=k; rB=k; cB=k;

			System.out.println("\nMatrix size: "+rA+" x "+cB+ " parallel");
			int[][] A=new int[rA][cA];
			int[][] B=new int[rB][cB];
			int[][] C=new int[rA][cB];
			MatrixParallel[][] thrd= new MatrixParallel[rA][cB];
			System.out.print("Matrix A\n");
			for(int i=0;i<rA;i++)	//input val matrix A
			{

				for(int j=0;j<cA;j++)
				{
					A[i][j]= ran.nextInt(900) + 100;	//50-100
					valueMAtA.add(A[i][j]);
					System.out.print(i+","+j+" = ");
					System.out.println(A[i][j]);
				}
			}      
			System.out.print("Matrix B\n");
			for(int i=0;i<rB;i++)	//matrix B
			{

				for(int j=0;j<cB;j++)
				{
					B[i][j]=ran.nextInt(900) + 100;	//50-100
					valueMAtB.add(B[i][j]);
					System.out.print(i+","+j+" = ");
					System.out.println(B[i][j]);
				}        
			}
			int thread=0;
			for(int i=0;i<rA;i++)
			{
				for(int j=0;j<cB;j++)
				{
					thrd[i][j]=new MatrixParallel(A,B,C,i,j,cA);
					thrd[i][j].start();
					thread++;
				}
			}

			for(int i=0;i<rA;i++)
			{
				for(int j=0;j<cB;j++)
				{
					try{
						thrd[i][j].join();
					}
					catch(InterruptedException e){}
				}
			}        

			System.out.println("Result for parallel "+rA+" x "+cB);
			for(int i=0;i<rA;i++)
			{
				for(int j=0;j<cB;j++)
				{
					System.out.print(C[i][j]+" ");
				}    
				System.out.println();            
			} 
			System.out.println("----------------------------"); 
		} 
		long endTime = System.currentTimeMillis();
		timetaken = endTime-startTime;
		//System.out.println("\nCalculated in " + (endTime - startTime) + " ms");

	}
}

