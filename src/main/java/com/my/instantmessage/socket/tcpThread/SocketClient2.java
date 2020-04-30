package com.my.instantmessage.socket.tcpThread;

import java.io.*;
import java.net.Socket;

public class SocketClient2 {

    public static void main(String[] args) {

        try {
            // 和服务器创建连接
            Socket socket = new Socket("localhost",8000);

            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("客户端发送信息2");
            pw.flush();

            socket.shutdownOutput();

            // 从服务器接收的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while((info = br.readLine())!=null){
                System.out.println("[客户端2]："+info);
            }

            br.close();
            is.close();
            os.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
