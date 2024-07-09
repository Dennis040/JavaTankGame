package online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


public class OnlineGame {
    private static final String SERVER_ADDRESS = "localhost"; // Địa chỉ của server.
    private static final int SERVER_PORT = 12345; // Cổng của server.
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private List<String> gameUpdates = new ArrayList<>(); // Danh sách lưu trữ các cập nhật trạng thái từ server
    private ConcurrentLinkedQueue<String> serverUpdates = new ConcurrentLinkedQueue<>();

    public OnlineGame() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT); // Kết nối tới server qua địa chỉ và cổng đã định.
            out = new PrintWriter(socket.getOutputStream(), true); // Tạo PrintWriter để gửi dữ liệu tới server.
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Tạo BufferedReader để đọc dữ liệu từ server.

            new Thread(new IncomingReader()).start(); // Khởi chạy một thread mới để lắng nghe dữ liệu từ server.
        } catch (IOException e) {
            e.printStackTrace(); // In ra lỗi nếu có.
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message); // Gửi thông điệp tới server nếu PrintWriter đã được tạo.
        }
    }

    public List<String> getGameUpdates() {
        return gameUpdates;
    }

    //    public ConcurrentLinkedQueue<String> getGameUpdates() {
//        return serverUpdates;
//    }
    private class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    synchronized (gameUpdates) {
                        gameUpdates.add(message); // Thêm thông điệp vào danh sách cập nhật trạng thái
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // In ra lỗi nếu có.
            }
        }
    }
}
