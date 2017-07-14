/**
 * Created by Gojay on 2017/7/14.
 *  运算符&流程控制的操作练习：九九乘法表
 */
public class week_1 {
    public static void main(String[] args) {
        for (int row = 1;row <= 9;row++) {
            for (int col = 1;col <= row;col++) {
                System.out.print(row + "*" + col + "=" + row*col + "\t");
            }
            System.out.println();
        }
    }
}
