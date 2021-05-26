// TODO: change package name to match yours
package your.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable{
    ClientConnection clientConnection;
    public Server(boolean serverOn) {
        boolean runServer = serverOn;
        ServerSocket serverSocket = null;
        try {
            // TODO: Make sure to set your socket to open at the right port.
            serverSocket = new ServerSocket(44444);
        } catch (IOException e) {
            System.out.println("Server Socket could not be created at port 44444");
            e.printStackTrace();
        }
        while(runServer) {
            try {
                assert serverSocket != null;
                Socket clientSocket = serverSocket.accept();
                clientConnection = new ClientConnection(clientSocket, new DatabaseConnection());
            } catch (IOException e) {
                runServer = false;
                e.printStackTrace();
            }
            new Thread(this).start();
        }
    }
    public static void main(String[] args) throws IOException {
        boolean serverOn = true;
        new Server(serverOn);
    }
    @Override
    public void run() {
        while(clientConnection.isConnected()){
            clientConnection.run();
        }
    }
}
