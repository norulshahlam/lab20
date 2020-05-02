package DISPARP_Lab_Week20;

import java.awt.Color; import java.awt.image.BufferedImage ;
import javax.imageio.ImageIO; import java.io.File ;
import java.net.SocketTimeoutException;

public class ParallelMandelbrot extends Thread  {
	
	final static int P = 4;	// no of threads
	final static int N = 4096, CUTOFF = 100 ;
	static int [] [] set = new int [N] [N] ;
	
	public static void Mandelbrot(int P)	{

		long startTime = System.currentTimeMillis();
		
		ParallelMandelbrot [] threads = new ParallelMandelbrot [P] ;
		for(int me = 0 ; me < P ; me++) {
			threads [me] = new ParallelMandelbrot(me) ;
			threads [me].start() ;
			
			}
		for(int me = 0 ; me < P ; me++) {
			try {
				threads [me].join() ;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long endTime = System.currentTimeMillis();
		System.out.println("completed in " + (endTime - startTime) + " ms for " +P+ " threads");
		// Plot image
		BufferedImage img = new BufferedImage(N, N,
				BufferedImage.TYPE_INT_ARGB) ;
		// Draw pixels
		for (int i = 0 ; i < N ; i++) {
			for (int j = 0 ; j < N ; j++) {
				int k = set [i] [j] ;
				float level ;
				if(k < CUTOFF) {
					level = (float) k / CUTOFF ;
				}
				else {
					level = 0 ;
				}
				Color c = new Color(0, level, 0) ; // Green
				img.setRGB(i, j, c.getRGB()) ;
			}
		}
		// Print file
		//ImageIO.write(img, "PNG", new File("ParellelMandelbrotWK15.png"));
	}
	int me ;
	public ParallelMandelbrot(int me) {
		this.me = me ;
	}
	public void run() {
		int begin, end;
		int b = N/P ; // block size
		begin = me * b ;
		end = begin + b ;
		
		for(int i = begin ; i < end ; i++) {
			for(int j = 0 ; j < N ; j++) {
				double cr = (4.0 * i - 2 * N) / N ;
				double ci = (4.0 * j - 2 * N) / N ;
				double zr = cr, zi = ci ;
				int k = 0 ;
				while (k < CUTOFF && zr * zr + zi * zi < 4.0) {
					// z = c + z * z
					double newr = cr + zr * zr - zi * zi ;
					double newi = ci + 2 * zr * zi ;
					zr = newr ;
					zi = newi ;
					k++ ;
				}
				set [i] [j] = k ;
			}
		}		
	}
}