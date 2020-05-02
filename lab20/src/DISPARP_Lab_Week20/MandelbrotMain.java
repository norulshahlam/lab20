package DISPARP_Lab_Week20;

public class MandelbrotMain {

	static int P=5;
	public static void main(String[] args) {
		
		ParallelMandelbrot new1 = new ParallelMandelbrot(P);
		new1.Mandelbrot(8);

	}

}
