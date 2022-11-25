package by.nika_doroshkevich.main;

import by.nika_doroshkevich.presentation.PresentationProvider;
import by.nika_doroshkevich.presentation.UserInterface;
import by.nika_doroshkevich.presentation.View;

import java.io.*;
import java.net.Socket;

public class Client {

    private static Client instance;
    private static final String HOST = "localhost";
    private static final int PORT = 8089;

    private Client() {
    }

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void startClient() {
        View view;
        UserInterface userInterface;

        view = PresentationProvider.getInstance().getVIEW();
        userInterface = PresentationProvider.getInstance().getUSER_INTERFACE();

        while (true) {
            try (Socket socket = new Socket(HOST, PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true)) {
                String line = userInterface.adminMenu();
                out.println(line);
                String response = in.readLine();
                view.print(response);
            } catch (IOException exception) {
                view.print("Server is not available now!\nPlease, start server!");
                break;
            }
        }
    }
}
