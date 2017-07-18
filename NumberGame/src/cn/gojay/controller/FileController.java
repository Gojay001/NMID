package cn.gojay.controller;

import java.io.*;
import java.util.ArrayList;

/**
 * 文件控制类
 * 实现写入文件及读取文件
 */
public class FileController {
    //文件路径
    private static String filePath = "src\\count.txt";

    /**
     * 将数据写入文件
     * @param guessCount
     */
    public static void saveToFile(int guessCount) {
        try {
            File file = new File(filePath);
            //文件不存在则创建
            if (!file.exists() || !file.isFile()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //将数据写入文件
            bufferedWriter.write(Integer.toString(guessCount));
            bufferedWriter.newLine();

            //清除缓冲区并关闭BufferedWriter
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("保存文件失败！");
            e.printStackTrace();
        }
    }

    /**
     * 读取文件数据，返回ArrayList<Integer>
     * @return allCount
     */
    public static ArrayList<Integer> readOfFile() {
        ArrayList<Integer> allCount = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("文件不存在");
            return null;
        }

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String temp = "";

            //读取文件数据
            while((temp = bufferedReader.readLine()) != null) {
                allCount.add(Integer.parseInt(temp));
            }

            //关闭FileReader,BufferedReader
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("文件读取失败！");
            e.printStackTrace();
        }

        return allCount;
    }
}
