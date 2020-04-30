package com.my.instantmessage.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class USocketServer {

    public static void main(String[] args) {

        try {
            byte[] bytes = new byte[1024];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

            //创建socket服务端
            DatagramSocket server = new DatagramSocket(8000);

            while (true){
                server.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getData().length);
                System.out.println(msg);
            }

            //server.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
