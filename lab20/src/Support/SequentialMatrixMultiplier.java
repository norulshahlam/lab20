package Matrix.Support;

import java.util.Objects;

import Matrix.Matrix;
import Matrix.MatrixMultiplier;

public final class SequentialMatrixMultiplier implements MatrixMultiplier {

	@Override
	public Matrix multiply(Matrix left, Matrix right) {
		Objects.requireNonNull(left, "the left matrix is null");
		Objects.requireNonNull(right, "the right matrix is null");
		checkDimensions(left,right);
		
		Matrix result = new Matrix (left.getRows(), right.getColumns());
		
		for(int y =0; y<left.getRows(); y++) {
			for(int x =0; x<right.getColumns(); x++) {
				double sum = 0.0;
				
				for(int i=0; i<left.getColumns(); i++) {
					sum+= left.get(i, y) * right.get(x, i);
				}
				result.set(x, y, sum);
			}
		}
		return result;
	}
	private void checkDimensions(Matrix left, Matrix right) {
		if(left.getColumns() != right.getRows()) {
			throw new IllegalArgumentException(
					"Trying to multiply non-compatible matrices. Columns of "+
					"left matrix: " +left.getColumns()+ ". Rows of " +
					"right matrix: " +right.getRows());
			
		}
	}
	
	
	
}
