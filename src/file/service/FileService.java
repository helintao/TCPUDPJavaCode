package file.service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @anthor: Banana
 * @function:
 * @date: 2020/7/6
 */
public class FileService {
    static boolean isEnable;
    private static ServerSocket server;
    static int port = 0;

    public static void main(String[] args) {
        final String path = "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (server != null) {
                    while (true) {
                        ReceiveFile(path);
                    }
                }
            }
        }).start();
    }

    public static String ReceiveFile(String path) {
        try {
            Socket socket = server.accept();
            System.out.println("客户端" + socket.getInetAddress() + "已连接");
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];//数据存储
            long donelen = 0;//传输完成的数据长度
            long filelen = 0;//文件长度
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //以客户端的IP地址作为存储路径
            String fileDir = path + "\\" + socket.getInetAddress().toString().substring(1, socket.getInetAddress().toString().length());
            File file = new File(fileDir);
            //判断文件夹是否存在，不存在则创建
            if (!file.exists()) {
                file.mkdir();
            }
            //读取文件名
            String fileName = inputStream.readUTF();
            //设置文件路径
            String filePath = fileDir + "\\" + fileName;
            file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
            }

            DataOutputStream fileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

            filelen = inputStream.readLong();//文件长度
            System.out.println("文件的长度为:" + filelen + "\n");
            System.out.println("开始接收文件!" + "\n");
            DataOutputStream ack = new DataOutputStream(socket.getOutputStream());
            while (true) {
                int read = 0;
                if (inputStream != null) {
                    read = inputStream.read(buf);
                    ack.writeUTF("OK");//接收到数据以后给client一个回复
                }
                if (read == -1) {
                    break;
                }
                donelen += read;
                System.out.println("文件接收了" + (donelen * 100 / filelen)
                        + "%\n");
                fileOut.write(buf, 0, read);
            }
            if (donelen == filelen)
                System.out.println("接收完成，文件存为" + file + "\n");
            else {
                System.out.printf("IP:%s发来的%s传输过程中失去连接\n", socket.getInetAddress(), fileName);
                file.delete();
            }
            ack.close();
            inputStream.close();
            fileOut.close();
            return fileDir + "接受完成";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "接收失败";
    }
}
