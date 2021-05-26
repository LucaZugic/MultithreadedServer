// TODO: change package name to match yours
package your.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class ClientConnection implements Runnable {
    private final Socket clientSocket;
    private boolean isConnected = true;
    DatabaseConnection databaseConnection;

    public ClientConnection(Socket socket, DatabaseConnection databaseConnection) {
        this.clientSocket = socket;
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void run() {
        /* TODO: Insert your code that handles client requests, passes these to the database and sends replies to the client here.
         * I have here used an example of what this could look like, using ObjectInputStream, for the custom class Request.
         *  Start of Example:
         */
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Request request;
            if (objectInputStream != null) {
                request = (Request) objectInputStream.readObject();
                /*
                 * In this example, we assume the request is a String made up of: action [space] value.
                 * As seen below, the example we use is action= add, value= Stacey. Which will add the name Stacey to our database.
                 */
                String[] clientRequest = request.getRequest().split("\\s+", 2);
                String reply = "";
                switch(clientRequest[0]) {
                    case "add":
                        databaseConnection.add(clientRequest[1]);
                        reply = "name-added-success"; // We return this String to the Client for confirmation
                        break;
                    default:
                        reply = ("invalid-request");
                        break;
                }
                Request serverResponse = new Request(reply);
                // Here we create an ObjectOutPutStream which sends an object of class Request through the socket, to the Client
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.writeObject(serverResponse);
                objectOutputStream.flush();
            } else {
                // Here we assume that if inputStream in the socket is null, the client must be disconected
                System.out.println("Client has disconnected!");
                clientSocket.close();
                isConnected = false;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        // End of example!
    }
    public boolean isConnected() {
        if (isConnected) { return true; }
        else { return false; }
    }
}
