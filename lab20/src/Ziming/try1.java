package Ziming;

public class try1 {

	public static void main(String[] args) {

		int size = 4;
		int process = 25;

		for (int i = 1; i <= process; i++) {

			System.out.println("process " + i + ": size " + (size - 1));
			if (size != 0) {
				size--;
				if (size == 0) {
					size = 4;
				}
			}
		}
	}

}
