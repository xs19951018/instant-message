package com.my.instantmessage.socket.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {

        Socket socket = null;
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        String msg = null;

        OutputStream os = null;
        PrintWriter pw = null;
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true){
                socket = serverSocket.accept();
                is = socket.getInputStream();
                reader = new InputStreamReader(is);
                br = new BufferedReader(reader);
                while ((msg = br.readLine()) != null){
                    System.out.println("[服务器]:"+msg);
                }

                //连接成功,返回提示消息
                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                pw.write("服务器返回消息");
                pw.flush();

                socket.shutdownOutput();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
