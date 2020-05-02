package DISPARP_Lab_Week20;

import org.omg.SendingContext.RunTime;

public class test {

	public static void main(String[] args) {
		
		int size = 5;
		MatrixParallelMain parallel = new MatrixParallelMain();
		parallel.parallel(size); //starts from size 2 to (+5)
		MatrixSequentialMain sequential = new MatrixSequentialMain();
		sequential.sequential(500);
		//int threads = RunTime.getRuntime().availableProcessors();
		System.out.println("time taken for parallel "+parallel.timetaken+ " ms");
		System.out.println("time taken for sequential "+sequential.timetaken+ " ms");	
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println("cores: "+cores);
	}
}
