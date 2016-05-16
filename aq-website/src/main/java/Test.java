import com.ming800.core.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by Administrator on 2016/2/2.
 */
public class Test {

    public static void main(String[] a) throws Exception {
//        xx();
//        gcd(65536, 1024);
//        gcdplus(67108864, 65536);
//        minSeq(99999);
//        maxProductSeq(10);
        byte b=00001;
        System.out.println(b);
    }

    private static void maxProductSeq(int size) {
//        int[] b = randomSeq(size);
        int[] b = {-8,0,-4,0,-9,2,-7,-7,8,5};
        for (int x : b) {
            System.out.print(x + ",");
        }

        long start = System.currentTimeMillis();
        int max = b[0];
        int sum = b[0];
        int leftPositive = b[0];
        int rightPositive = 1;
        boolean flag;
        if(b[0] < 0) {
            flag = false;
        }else{
            flag = true;
        }
        for (int i = 1; i < b.length; i++) {
            if (b[i] != 0) {
                if (flag && b[i] < 0) {
                    flag = false;
                }
                if(!flag){
                    rightPositive *= b[i];
                }

                if(b[i] < 0 && sum > 0){
                    leftPositive = sum;
                }
                sum *= b[i];
            } else if (b[i] == 0) {
                if (sum > 0) {
                    max = findMax(leftPositive, rightPositive, sum, max);
                } else if (sum < 0) {
                    max = findMax(leftPositive, rightPositive, sum, max);
                }
                sum = 1;
                leftPositive = 1;
                rightPositive = 1;
                if(b[i + 1] < 0) {
                    flag = false;
                }else{
                    flag = true;
                }
            }
        }
        max = findMax(leftPositive, rightPositive, max,sum);
        System.out.println("\nO(N)max:" + max + " cost:" + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        int sum1 = 1;
        int max1 = 1;
        for (int x = 0; x < b.length; x++) {
            for (int y = x; y < b.length; y++) {
                sum1 *= b[y];
                if (sum1 > max1) {
                    max1 = sum1;
                }
            }
            sum1 = 1;
        }
        max1 = findMax(max1, sum1);
        System.out.println("O(N*N)max1:" + max1 + " cost:" + (System.currentTimeMillis() - start) + "ms");

    }

    public static int findMax(int... nums) {
        int max = 0;
        boolean flag = true;
        for (int x : nums) {
            if (flag) {
                max = x;
                flag = false;
            }
            if (x > max) {
                max = x;
            }
        }
        return max;
    }

    private static void minSeq(int size) {
        int[] b = randomSeq(size);
        long start = System.currentTimeMillis();
//        int[] b = {4, -4, 0, -2, 0, 3, -1, 6, 9, -9};
//        int startIndex = 1;
//        int endIndex = 1;
        int min = b[0];
        int sum = b[0];
        int temp = b[0];
        for (int i = 1; i < b.length; i++) {
            if (b[i] <= 0) {
                if (sum > 0) {
                    sum = b[i];
                } else if (sum <= 0) {
                    sum += b[i];
                }
            } else if (b[i] > 0) {
                if (sum > 0) {
                    sum = b[i];
                } else if (sum <= 0) {
                    if (sum < temp) {
                        temp = sum;
                    }
                    if (sum + b[i] <= 0) {
                        sum += b[i];
                    } else if (sum + b[i] > 0) {
                        sum = 0;
                    }
                }
            }
        }
        min = findMin(temp, min, sum);
//        for (int x : b) {
//            System.out.print(x + ",");
//        }
//        System.out.println("start:" + startIndex + " end: " + endIndex + " min:" + min);
        System.out.println("\nO(N)min:" + min + " cost:" + (System.currentTimeMillis() - start) + "ms");
        start = System.currentTimeMillis();
        int sum1 = 0;
        int min1 = b[0];
        for (int x = 0; x < b.length; x++) {
            for (int y = x; y < b.length; y++) {
                sum1 += b[y];
                if (sum1 < min1) {
                    min1 = sum1;
                }
            }
            sum1 = 0;
        }
        min1 = findMin(min1, sum1);
        System.out.println("O(N*N)min1:" + min1 + " cost:" + (System.currentTimeMillis() - start) + "ms");
    }

    public static int findMin(int... nums) {
        int min = 0;
        boolean flag = true;
        for (int x : nums) {
            if (flag) {
                min = x;
                flag = false;
            }
            if (x < min) {
                min = x;
            }
        }
        return min;
    }

    public static int[] randomSeq(int size) {
        Random random = new Random();
        int[] b = new int[size];
        for (int a = 0; a < b.length; a++) {
            int x = random.nextInt(size);
            boolean y = random.nextBoolean();
            if (y) {
                x = -x;
            }
            b[a] = x;
        }
        return b;
    }

    private static int gcdplus(int a, int b) {
        int x;
        if (b == 1) {
            return 1;
        }
        if (a % 2 == 0 && b % 2 == 0) {
            x = 2 * gcdplus(a / 2, b / 2);
        } else if (a % 2 == 0 && b % 2 == 1) {
            x = gcdplus(a / 2, b);
        } else if (a % 2 == 1 && b % 2 == 0) {
            x = gcdplus(a, b / 2);
        } else {
            x = gcdplus((a + b) / 2, (a - b) / 2);
        }
        System.out.println(x);
        return x;
    }

    private static void gcd(int a, int b) {
        int temp;
        while (a % b != 0) {
            temp = b;
            b = a % b;
            a = temp;
        }
        System.out.println(b);
    }

    private static void xx() {
        long start = System.currentTimeMillis();
        int size = 10240000;
        Random random = new Random();
        boolean[] used = new boolean[size];
        int[] result = new int[size];
        int count = 0;
        while (count < size) {
            int ranNum = random.nextInt(size);
            if (!used[ranNum]) {
                used[ranNum] = true;
                result[count] = ranNum;
                count++;
            }
        }
        System.out.println("takes " + (System.currentTimeMillis() - start) + "ms");
    }



}
