package Matrix;

import java.io.IOException;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class runHere {

	public static void main(String[] args) throws BiffException, WriteException, IOException {

		// input your value here!
		int Start =200;
		int Steps = 200;
		int stopAt = 2000;
		
		Demo calc = new Demo(Start, Steps, stopAt);		
	}
}
