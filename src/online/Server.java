package online;

import gui.MyFrame;
import gui.PanelGame2;

import java.io.*;
import java.net.*;

public class Server extends Thread{

    private DatagramSocket socket;
    private OnlineGame onlineGame;

    public Server(OnlineGame onlineGame, String ipAddress) {
        this.onlineGame = onlineGame;
        try {
            this.socket = new DatagramSocket(1331);
        }catch (SocketException e){
            e.printStackTrace();
        }
    }
    public void run(){
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            try {
                socket.receive(packet);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            String message = new String(packet.getData());
            if(message.equalsIgnoreCase("ping")) {
                System.out.println("Client > " + message);
                sendData("pong".getBytes(), packet.getAddress(),packet.getPort());
            }
        }
    }
    public void sendData(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet = new DatagramPacket(data,data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
