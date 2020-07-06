package file.client;

import java.io.*;
import java.net.Socket;

/**
 * @anthor: Banana
 * @function:
 * @date: 2020/7/6
 */
public class FileClient {
    public static void main(String arg[]){
        final String path="";//文件路径
        final String fileName="";//文件名
        final String ipAddress="";//IP地址
        final int port = 0;//端口号
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(FileSend(path,fileName,ipAddress,port));
            }
        }).start();
    }

    public static String FileSend(String path,String fileName,String ipAddress,int port){
        try {
            Socket socket = new Socket(ipAddress,port);
            int bufferSize = 8192;
            byte[] buf = new byte[bufferSize];
            //文件传输
            File file = new File(path+fileName);
            System.out.println("文件长度:"+file.length());
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(path+fileName));
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream getACK = new DataInputStream(socket.getInputStream());
            //传输文件名
            dataOutputStream.writeUTF(file.getName());
            dataOutputStream.flush();
            //传输文件长度
            dataOutputStream.writeLong(file.length());
            dataOutputStream.flush();
            int readSize = 0;
            while (true){
                if(dataInputStream!=null){
                    readSize=dataInputStream.read(buf);
                }
                if(readSize==-1){
                    break;
                }
                dataOutputStream.write(buf,0,readSize);

                if(!getACK.readUTF().equals("OK")){
                    System.out.println("服务器:"+ipAddress+":"+port+"失去连接!");
                    break;
                }
            }

            dataOutputStream.flush();
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
            getACK.close();
            System.out.println("文件传输完毕");
            return fileName+"发送完成";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "发送失败";
    }
}
