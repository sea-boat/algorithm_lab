import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author seaboat
 * @date 2018-12-08
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>集合A有n(万级别)个子集，每个子集有m(万级别)个元素，元素都为正负数，找出任意相加为0的元素，将他们消掉，要求每次消掉的都是最大正数，且尽可能多的负元素。</p>
 */
public class SetSum {

	private static boolean flag[];
	private static int length = 0;
	private static List<Integer> longestArrays = new ArrayList<Integer>();
	private static int longestSize = 0;
	private static boolean isGet = false;
	private static long counter = 0;
	private static long total_counter = 0;
	private static long threshold = 200;
	private static long calculation_threshold = 100000000;
	private static long total_calculation_threshold = 100000000000L;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Random r = new Random();
		System.out.println("start time : " + start);
		int[] po_array = { 1000, 2000, 1500, 1200, 1800, 1100, 1300, 1400, 1600, 1700 };
		int[] ne_array = new int[500];
		for (int i = 0; i < ne_array.length; i++)
			ne_array[i] = -r.nextInt(100);
		//1、abs 
		for (int i = 0; i < ne_array.length; i++)
			ne_array[i] = -ne_array[i];
		//2、sort
		Arrays.sort(po_array);
		Arrays.sort(ne_array);

		for (int i = po_array.length - 1; i > 0; i--) {
			flag = new boolean[ne_array.length];
			isGet = false;
			length = 0;
			longestSize = 0;
			counter = 0;
			longestArrays.clear();
			doSum(ne_array, 0, po_array[i]);

			//not enough data, we break it directly.
			if (longestArrays.size() == 0)
				break;

			System.out.println(longestArrays);

			//clear the elements we have got.
			for (int value : longestArrays)
				ne_array = remove(ne_array, value);
			Arrays.sort(ne_array);
		}
		System.out.println("end time : " + System.currentTimeMillis());
		System.out.println("counter :" + total_counter);
		System.out.println("elapsed time : " + (System.currentTimeMillis() - start) + "ms");
	}

	static void doSum(int[] array, int index, int sum) {
		if (counter > calculation_threshold)
			return;
		if (total_counter > total_calculation_threshold)
			return;
		counter++;
		total_counter++;
		if (isGet)
			return;
		if (sum == 0) {
			if (longestSize < length) {
				longestArrays.clear();
				longestSize = length;
				for (int i = 0; i < array.length; ++i) {
					if (flag[i])
						longestArrays.add(array[i]);
				}
			}
			if (length > threshold) {
				System.out.println("we have got a satisfying answer!");
				isGet = true;
				return;
			}
		} else {
			if (index == array.length)
				return;
			else {
				flag[index] = true;
				length++;
				if (sum - array[index] >= 0)
					doSum(array, index + 1, sum - array[index]);
				flag[index] = false;
				length--;
				if (sum >= 0)
					doSum(array, index + 1, sum);
			}
		}
	}

	private static int[] remove(int[] arr, int num) {
		int[] tmp = new int[arr.length - 1];
		int idx = 0;
		boolean hasRemove = false;
		for (int i = 0; i < arr.length; i++) {
			if (!hasRemove && arr[i] == num) {
				hasRemove = true;
				continue;
			}
			tmp[idx++] = arr[i];
		}
		return tmp;
	}

}
