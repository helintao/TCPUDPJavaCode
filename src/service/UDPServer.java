package service;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @anthor: Banana
 * @function:
 * @date: 2020/7/3
 */
public class UDPServer {
    public static void main(String[] args) {

        //服务端接收消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("服务端启动");
                    DatagramSocket socket = new DatagramSocket(8888);
                    while (true) {
                        byte[] bytes = new byte[1024];
                        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                        socket.receive(datagramPacket);
                        byte[] data = datagramPacket.getData();
                        String string = new String(data, 0, datagramPacket.getLength());
                        if (string.equals("exit!")) break;
                        System.out.println(string);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("服务器异常");
                } finally {
                    System.out.println("服务器关闭");
                }
            }
        }).start();

        //服务端发送消息
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            System.out.println("服务端：请输入发送的信息");
            while (true) {
                String data = scanner.nextLine();
                byte[] bytes = data.getBytes();
                InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, inetAddress, 8887);
                datagramSocket.send(datagramPacket);
                if (data.equals("exit!")) break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("服务端停止发送消息");
        }
    }
}
