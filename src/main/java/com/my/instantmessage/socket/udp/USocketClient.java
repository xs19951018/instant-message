package com.my.instantmessage.socket.udp;

import java.io.IOException;
import java.net.*;

public class USocketClient {

    public static void main(String[] args) {

        try {
            String msg = "客户端发送消息";
            InetAddress addr = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(msg.getBytes(),
                    msg.getBytes().length, addr, 8000);

            //创建socket
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
