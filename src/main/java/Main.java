import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Logs from your program will appear here!");
        int port = 6379;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);
        Socket clientSocket = null;
        int i = 1;

        while (true) {
            clientSocket = serverSocket.accept();

            Main main = new Main();
            main.handleClient(clientSocket, i);
            i++;
        }

    }

    private void handleClient(Socket socket, int i) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        System.out.println("current count is" + i);
        Runnable Task = () -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                OutputStream outputStream = socket.getOutputStream();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equalsIgnoreCase("PING")) {
                        System.out.println("Command I am getting:" + reader);
                        outputStream.write("+PONG\r\n".getBytes());
                    }
                }
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        executor.submit(Task);

    }
}
