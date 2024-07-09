package online;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Server {
    private static final int PORT = 12345; // Đặt cổng để server lắng nghe các kết nối từ client.
    private static Set<PrintWriter> clientWriters = new HashSet<>(); // Tạo tập hợp để lưu trữ các đối tượng PrintWriter, phục vụ việc gửi dữ liệu tới các client.
    private static List<String> gameState = new ArrayList<>(); // Lưu trữ trạng thái trò chơi

    public static void main(String[] args) throws IOException {
        System.out.println("Game server đang chạy...");
        ServerSocket listener = new ServerSocket(PORT); // Tạo ServerSocket để lắng nghe các kết nối từ client tại cổng đã định.
        try {
            while (true) {
                new Handler(listener.accept()).start(); // Chấp nhận kết nối từ client và khởi chạy một thread mới để xử lý kết nối này.
            }
        } finally {
            listener.close(); // Đóng ServerSocket khi không còn sử dụng.
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public Handler(Socket socket) {
            this.socket = socket; // Lưu socket kết nối từ client.
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Tạo BufferedReader để đọc dữ liệu từ client.
                out = new PrintWriter(socket.getOutputStream(), true); // Tạo PrintWriter để gửi dữ liệu tới client.

                synchronized (clientWriters) {
                    clientWriters.add(out); // Thêm PrintWriter vào tập hợp clientWriters để có thể gửi dữ liệu tới client này sau này.
                }
                synchronized (gameState) {
                    for (String state : gameState) {
                        out.println(state); // Gửi trạng thái trò chơi hiện tại cho client mới kết nối
                    }
                }
                while (true) {
                    String input = in.readLine(); // Đọc dữ liệu từ client.
                    if (input == null) {
                        return; // Nếu không nhận được dữ liệu, thoát khỏi vòng lặp.
                    }
                    synchronized (gameState) {
                        gameState.add(input); // Cập nhật trạng thái trò chơi
                    }
                    synchronized (clientWriters) {
                        for (PrintWriter writer : clientWriters) {
                            writer.println(input); // Gửi dữ liệu tới tất cả các client đã kết nối.
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e); // In ra lỗi nếu có.
            } finally {
                try {
                    socket.close(); // Đóng socket khi không còn sử dụng.
                } catch (IOException e) {
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out); // Loại bỏ PrintWriter khỏi tập hợp khi kết nối bị đóng.
                }
            }
        }
    }
}
