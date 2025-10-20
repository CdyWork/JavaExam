
package TwoJavaExam;

public class ThreeQusetion {
	private int start;
	private int end;

	public ThreeQusetion(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int Factorial(int n) {
		if (n <= 1) return 1;
		return n * Factorial(n - 1);
	}

	public int SumFactorials() {
		int sum = 0;
		for (int i = start; i <= end; i++) {
			sum += Factorial(i);
		}
		return sum;
	}

	public static void main(String[] args) {
		ThreeQusetion tq = new ThreeQusetion(1, 10);
		int sum = tq.SumFactorials();
		System.out.println("1! + 2! + ... + 10! 的和为：" + sum);
	}
}
