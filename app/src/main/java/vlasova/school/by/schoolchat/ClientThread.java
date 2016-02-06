package vlasova.school.by.schoolchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientThread implements Runnable {
    private Socket socket;
    private BufferedReader reader;

    private static final int SERVER_PORT = 4444;
    private static final String SERVER_IP = "46.101.96.234";

    @Override
    public void run() {
        boolean finish = true;
        try {
            while (!MainActivity.isOnline()) ;
            socket = new Socket(SERVER_IP, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (finish) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            socket.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    public String getString() throws IOException {
        if (reader.ready())
            return reader.readLine();
        return "No data";
    }

    public String getStringForce() throws IOException {
        return reader.readLine();
    }


}