/**
 * 聊天室客户端
 * 使用：可运行多个客户端，每个客户端可以发送消息，当有客户端发送消息时所有客户端接收到消息。
 * 代码实现：
 * 用Socket实现IO流通信：输入ip及端口确认服务器,获取Socket输入输出流；
 * 用Swing实现界面：显示文本、输入文本、发送按钮，监听发送事件；
 * 启动一个线程接收服务端返回消息。
 */

package cn.gojay.chat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 聊天室客户端
 */
public class ChatClient {
    //服务端Socket默认配置
    private Socket socket;
    private String ip = "localhost";
    private int port = 1210;

    //Socket输入输出流
    private BufferedReader in;
    private PrintWriter out;

    //Swing组件
    private JFrame myFrame;
    private JPanel myPanel;
    private JTextArea myTextArea;
    private JScrollPane myScrollPane;
    private JTextField myTextField;
    private JButton myButton;

    /**
     * 主函数
     */
    public static void main(String[] args) {
        new ChatClient().start();
    }

    /**
     * 启动聊天室客户端
     */
    public void start() {
        //获取输入流读取键盘数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //输入服务端信息
        try {
            System.out.print("输入IP地址：");
            ip = reader.readLine();
            System.out.print("输入端口号：");
            port = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("读取失败！");
            System.exit(0);
        }

        //与服务端建立连接
        setSocket();
        //定义Swing
        setUI();
        //启动接收服务端返回消息的线程
        new Thread(new ReceiveThread()).start();
    }

    /**
     * 配置与服务端连接的Socket并获取IO流
     */
    public void setSocket() {
        try {
            //建立服务器Sockeet
            socket = new Socket(ip, port);

            //获取Socket输入输出流
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("连接服务器失败！");
        }
    }

    /**
     * 创建聊天室客户端的UI：显示文本、输入文本、发送按钮
     */
    public void setUI() {
        //创建组件
        myTextField = new JTextField(20);
        myButton = new JButton("发送");
        //监听鼠标发送事件
        myButton.addActionListener(new SendListener());
        myTextArea = new JTextArea(10, 30);
        myTextArea.setEditable(false);
        myScrollPane = new JScrollPane(myTextArea);

        //将组件加入到面板
        myPanel = new JPanel();
        myPanel.add(myScrollPane);
        myPanel.add(myTextField);
        myPanel.add(myButton);

        //创建并配置Frame
        myFrame = new JFrame("聊天室");
        myFrame.add(myPanel);
        myFrame.setSize(400, 300);
        myFrame.setDefaultCloseOperation(myFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }

    /**
     * 客户端发送数据的监听器
     */
    class SendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //读取输入数据，加入到Socket输出流并刷新
            String message = myTextField.getText().toString();
            out.println(message);
            out.flush();

            //将输入框清空并获取焦点
            myTextField.setText("");
            myTextField.requestFocus();
        }
    }

    /**
     * 接收服务端返回消息的线程
     */
    class ReceiveThread implements Runnable {
        @Override
        public void run() {
            //读取Socket输入流并加入到显示文本框
            String message = "";
            try {
                while ((message = in.readLine()) != null) {
                    myTextArea.append(message + "\n");
                }
            } catch (IOException e) {
                System.out.println("接收消息失败！");
            }
        }
    }
}
