import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Logs from your program will appear here!");

        int port = 6379;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setReuseAddress(true);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream outputStream = clientSocket.getOutputStream();

            String line;
            while ((line = reader.readLine()) != null) {
                if(line.equalsIgnoreCase("PING")){
                    outputStream.write("+PONG\r\n".getBytes());
                }
            }
            clientSocket.close();
        }
    }
}
