package cn.gojay.controller;

import cn.gojay.model.CountModel;
import cn.gojay.view.GameView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * 游戏控制器
 * 进行游戏功能实现（开始，退出，重来，排行）
 */
public class GameController {

    /**
     * 开始游戏
     */
    public static void begin() {
        System.out.println("-------------------------");
        System.out.println("游戏开始！");
        //实例化计数实体类
        CountModel countModel = new CountModel();
        //生成伪随机数
        //System.out.println(countModel.getResultNumber());

        boolean flag = false;
        do {
            System.out.print("请输入猜测数字：");
            int guess = new Scanner(System.in).nextInt();
            countModel.setGuessCount(countModel.getGuessCount() + 1);//计数器加1

            if (guess < countModel.getResultNumber()) {
                System.out.println("你猜的数字比答案小，请继续猜测！");
            } else if (guess > countModel.getResultNumber()) {
                System.out.println("你猜的数字比答案大，请继续猜测！");
            } else {
                flag = true;
                System.out.println("恭喜你猜中了答案：" + guess);
            }
        } while (!flag);
        System.out.println("你猜中所用次数为：：" + countModel.getGuessCount());
        //将猜测次数写入文件中
        FileController.saveToFile(countModel.getGuessCount());
        //返回主菜单
        System.out.println();
        System.out.println("游戏结束！是否需要再来一次？");
        GameView.showView();
    }

    /**
     * 退出游戏
     */
    public static void quit() {
        System.out.println("-------------------------");
        System.out.println("你已退出游戏！");
        System.exit(2);
    }

    /**
     * 游戏排行
     */
    public static void rank() {
        //读取文件数据
        ArrayList<Integer> allCount = FileController.readOfFile();

        //将数据升序排序输出
        Collections.sort(allCount);
        System.out.println("-------------------------");
        System.out.println("\t\t排行榜");
        for (int i = 0;i < allCount.size();i++) {
            System.out.println("第" + (i+1) + "名：\t" + allCount.get(i) + "次");
        }
        //返回主菜单
        System.out.println();
        GameView.showView();
    }
}
