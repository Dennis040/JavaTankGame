package online;

import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    private static final int PORT = 12345;
    private static Set<Socket> clientSockets = Collections.synchronizedSet(new HashSet<>());
    private static final int MAX_CLIENTS = 2;

    public static void main(String[] args) {
        //tạo server socket va lang nghe
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            //chap nhan ket noi tu client
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);


                synchronized (clientSockets) {
                    if (clientSockets.size() < MAX_CLIENTS) {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("WAIT");
                        clientSockets.add(clientSocket);
                    } else if (clientSockets.size() == MAX_CLIENTS) {
                        for (Socket s : clientSockets) {
                            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                            out.println("START");
                        }
                    } else if (clientSockets.size() > MAX_CLIENTS) {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("Full");
                        clientSocket.close();
                    }
                }
                System.out.println(clientSockets.size());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                Scanner sc = new Scanner(System.in);
                while (true) {
                    //Nhan tin nhan
                    String message;
                    message = in.readLine();
                    System.out.println("Client: " + message);
                    //Gui tin nhan
                    System.out.print("Server: ");
                    message = sc.nextLine();
                    out.println(message);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

