package online;

import gui.MyFrame;
import gui.PanelGame2;

import java.io.*;
import java.net.*;

public class GameClient2 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


    public static void main(String[] args) {
        try {
            //ket noi server
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("SUCCESSFUL");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

//            Scanner sc = new Scanner(System.in);
            String message;
//            while (true){
//                //Gui tin nhan
//                System.out.print("Client: ");
//                message = sc.nextLine();
//                out.println(message);
            //Nhan tin nhan
            message = in.readLine();
            System.out.println("Server: " + message);
//            }
            if ("START".equals(message)) {
                start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void start() {
        MyFrame onlineGame = new MyFrame();
    }

}
