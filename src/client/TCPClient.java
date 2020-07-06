package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @anthor: Banana
 * @function:
 * @date: 2020/7/3
 */
public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8787);
            OutputStream out = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入发送的消息:");
            while (true) {
                String string = scanner.nextLine();
                out.write(string.getBytes());
                if (string.equals("exit!")) break;
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("客户端停止发送信息");
        }
    }
}
