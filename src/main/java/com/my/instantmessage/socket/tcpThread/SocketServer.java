package com.my.instantmessage.socket.tcpThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {

        Socket socket = null;

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true){
                socket = serverSocket.accept();
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
