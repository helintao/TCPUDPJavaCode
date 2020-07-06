package communication.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @anthor: Banana
 * @function:
 * @date: 2020/7/3
 */
public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8787);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            while (true) {
                byte[] bytes = new byte[1024];
                int len = inputStream.read(bytes);
                String string = new String(bytes, 0, len);
                if (string.equals("exit!")) break;
                System.out.println("客户端:" + string);
            }
            serverSocket.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("服务端停止接收信息");
        }
    }
}
