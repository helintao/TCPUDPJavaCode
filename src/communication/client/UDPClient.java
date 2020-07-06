package communication.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @anthor: Banana
 * @function:
 * @date: 2020/7/3
 */
public class UDPClient {
    public static void main(String[] args) {

        //客户端接收信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatagramSocket datagramSocket = new DatagramSocket(8887);
                    while (true) {
                        byte[] bytes = new byte[1024];
                        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                        datagramSocket.receive(datagramPacket);
                        byte[] data = datagramPacket.getData();
                        String string = new String(data, 0, datagramPacket.getLength());
                        if (string.equals("exit!")) break;
                        String ip = datagramPacket.getAddress().getHostAddress();
                        System.out.println(ip + "发来消息：" + string);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("客户端停止接收消息");
                }
            }
        }).start();

        //客户端发送消息
        try {
            System.out.println("客户端启动");
            DatagramSocket socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            System.out.println("输入你要发送的字符串");
            while (true) {
                String data = scanner.nextLine();
                byte[] str = data.getBytes();
                InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
                DatagramPacket datagramPacket = new DatagramPacket(str, str.length, inetAddress, 8888);
                socket.send(datagramPacket);
                if (data.equals("exit!")) break;
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            System.out.println("客户端停止发送消息");
        }
    }
}
