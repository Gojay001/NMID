package cn.gojay.view;

import cn.gojay.controller.GameController;

import java.util.Scanner;

/**
 * 主菜单页面
 * 进行功能选择显示
 */
public class GameView {
    public static void main(String[] args) {
        showView();
    }

    /**
     * 主菜单页面
     */
    public static void showView() {
        System.out.println("-------------------------");
        System.out.println("1、开始游戏");
        System.out.println("2、退出游戏");
        System.out.println("3、排行榜");
        System.out.println("-------------------------");
        System.out.print("请选择，输入正确选项（1-4）：");

        do {
            String choice = new Scanner(System.in).next();
            String regex = "[1-3]";
            //正则表达式判断选项输入是否正确
            if (choice.matches(regex)) {
                int choose = Integer.parseInt(choice);
               switch (choose) {
                   case 1:
                       GameController.begin();//开始游戏
                       break;
                   case 2:
                       GameController.quit();//退出游戏
                       break;
                   case 3:
                       GameController.rank();//排行榜
                       break;
                   default:
                       break;
               }
            }
            System.out.println("输入错误，请重新输入！");
            System.out.print("请重新选择：");
        } while (true);
    }
}
