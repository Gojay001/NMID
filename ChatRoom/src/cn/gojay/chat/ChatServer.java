/**
 * 聊天室服务端
 * 使用：接收客户端输入消息，并转发给所有客户端
 * 代码实现：
 * 创建ServerSocket对象始终监听端口；
 * 创建处理数据线程，通过IO流进行数据传输；
 * 用ArrayList存储连接到每个客户端的输出流，并用迭代器输出实现多客户端转发。
 */

package cn.gojay.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 聊天室服务端
 */
public class ChatServer {
    //连接每个客户端的Socket输出流
    private ArrayList<PrintWriter> allWriter = new ArrayList<>();

    //Socket输入输出流
    private BufferedReader in;
    private PrintWriter out;

    /**
     * 主函数
     */
    public static void main(String[] args) {
        new ChatServer().start();
    }

    /**
     * 启动聊天室服务端
     */
    public void start() {
        try {
            //创建ServerSocket对象
            ServerSocket server = new ServerSocket(1210);

            while (true) {
                //监听端口
                Socket client = server.accept();
                String ip = client.getInetAddress().getHostAddress();
                System.out.println(ip + "已经成功连接！");

                //获取输出流，并将输出流加入到ArrayList
                 out = new PrintWriter(client.getOutputStream(), true);
                allWriter.add(out);

                //启动处理数据线程
                new Thread(new HandlerThread(client)).start();
            }
        } catch (IOException e) {
            System.out.println("端口被占用");
            System.exit(0);
        }

    }

    /**
     * 处理数据的线程
     */
    class HandlerThread implements Runnable {
        Socket socket = null;

        /**
         * 传递客户端socket并获取输入流
         * @param socket 客户端socket
         */
        public HandlerThread(Socket socket) {
            this.socket = socket;
            //获取Socket输入输出流
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                System.out.println("客户端连接失败！");
            }
        }

        @Override
        public void run() {
            String message = "";
            //读取输入流并转发
            try {
                while ((message = in.readLine()) != null) {
                    sendMessage(message);
                }
            } catch (IOException e) {
                System.out.println("客户端断开连接！");
            }
        }
    }

    /**
     * 将客户端输入消息转发到每个客户端
     * @param message 客户端消息
     */
    public void sendMessage(String message) {
        for (PrintWriter printWriter : allWriter) {
            printWriter.println(message);
            printWriter.flush();
        }
    }
}

