package Matrix;

import java.util.Random;

import Matrix.Support.ParallelMatrixMultiplier;
import Matrix.Support.SequentialMatrixMultiplier;
import java.io.File;
import java.io.IOException;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.BooleanCell;
import jxl.format.CellFormat;
import jxl.write.biff.BooleanRecord;

public class Demo {

	private int Steps;
	private int stopAt;
	private int Start;


	public Demo(int Start, int Steps, int stopAt) throws BiffException, IOException, WriteException {

		{

			this.Steps = Steps;
			this.Start = Start;
			this.stopAt = stopAt;

			/*int diffSteps = 0;
			int diffStart = 0;
			if (Steps > Start) {
				diffSteps = Steps - Start;
			}
			if (Start > Steps) {
				diffStart = Start - Steps;s
			}*/

			// excel initiate
			WritableWorkbook wworkbook;
			wworkbook = Workbook.createWorkbook(new File("result.xls"));
			WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);

			// row and column labels
			Label columnName0 = new Label(2, 1, "Matrix Size"); // row, col.
			wsheet.addCell(columnName0);
			Label columnName1 = new Label(3, 1, "Sequential"); // row, col.
			wsheet.addCell(columnName1);
			Label columnName2 = new Label(4, 1, "Parallel");
			wsheet.addCell(columnName2);
			Label columnName3 = new Label(5, 1, "speed-up");
			wsheet.addCell(columnName3);
			Label columnName4 = new Label(6, 1, "same result?");
			wsheet.addCell(columnName4);

			Label columnName5 = new Label(8, 1, "Start at");
			wsheet.addCell(columnName5);
			Label columnName6 = new Label(9, 1, "Increase by");
			wsheet.addCell(columnName6);
			Label columnName7 = new Label(10, 1, "Stop at");
			wsheet.addCell(columnName7);

			Number starts = new Number(8, 2, Start);// col,row
			wsheet.addCell(starts);
			Number steps = new Number(9, 2, Steps);// col,row
			wsheet.addCell(steps);
			Number stop = new Number(10, 2, stopAt);// col,row
			wsheet.addCell(stop);

			// format excel val to 2 dec plc
			WritableCellFormat format = new WritableCellFormat(new NumberFormat("0.###"));

			Random random = new Random();
			int r = 3;
			for (int i = Start; i <= stopAt; i += Steps) {

				Number matrixSize = new Number(2, r/*((i + diffSteps) / (Steps + diffStart)) + 2*/, i);// col,row
				wsheet.addCell(matrixSize);

				Matrix m1 = getRandomMatrix(i, i, random);
				Matrix m2 = getRandomMatrix(i, i, random);
				Matrix m1b = new Matrix(m1);
				Matrix m2b = new Matrix(m2);

				double start = System.currentTimeMillis();
				Matrix result1 = new SequentialMatrixMultiplier().multiply(m1, m2);
				double end = System.currentTimeMillis();
				double resultTime1 = (end - start);
				System.out.println("Sequential " + i + " x " + i + " : " + resultTime1 + "ms");

				Number sequentialTime = new Number(3, r /*((i + diffSteps) / (Steps + diffStart)) + 2*/, resultTime1);// col,row
				wsheet.addCell(sequentialTime);

				start = System.currentTimeMillis();
				Matrix result1b = new ParallelMatrixMultiplier().multiply(m1b, m2b);
				end = System.currentTimeMillis();
				double resultTime2 = (end - start);
					
				System.out.println("Parallel " + i + " x " + i + " : " + resultTime2 + "ms");
				double speedup = resultTime1 / resultTime2;
				
				Number parallelTime = new Number(4, r /*((i + diffSteps) / (Steps + diffStart)) + 2*/, resultTime2);// col,row
				wsheet.addCell(parallelTime);

				

				if (resultTime1 == 0 || resultTime2 == 0) { // make sure wont give rubbish value if number/0
					speedup = 0;
				}

				Number parallelSpeedup = new Number(5, r /*((i + diffSteps) / (Steps + diffStart)) + 2*/, speedup, format);// col,row
				wsheet.addCell(parallelSpeedup);

				String isResultSame = "no";
				if (result1.equals(result1b)) {
					isResultSame = "yes";
				}

				Label isResultSsame = new Label(6, r /*((i + diffSteps) / (Steps + diffStart)) + 2*/, isResultSame);// col,row
				wsheet.addCell(isResultSsame);

				System.out.println("Same results: " + result1.equals(result1b)+ "\n");
				r++;
				
				if(i == stopAt) {
					continue;
				}
				if(i + Steps > stopAt ) {
					i = stopAt - Steps;
				}
			}
			wworkbook.write();
			wworkbook.close();
			System.out.println("end");
			
			
		}
	}

	private static Matrix getRandomMatrix(int rows, int cols, Random random) {
		Matrix m = new Matrix(rows, cols);

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				m.set(x, y, random.nextDouble());
			}
		}
		return m;
	}
}
