package com.my.instantmessage.socket.tcpThread;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private Socket server;

    public ServerThread(Socket socket){
        this.server = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        BufferedReader br = null;
        String msg = null;

        OutputStream os = null;
        PrintWriter pw = null;

        try {
            is = server.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((msg = br.readLine()) != null){
                System.out.println("[服务器]:"+msg);
            }

            //连接成功,返回提示消息
            os = server.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("服务器返回消息");
            pw.flush();
            server.shutdownOutput();

            pw.close();
            os.close();
            br.close();
            is.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
