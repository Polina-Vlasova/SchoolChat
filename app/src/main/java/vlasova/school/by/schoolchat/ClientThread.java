package vlasova.school.by.schoolchat;

import android.text.Editable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            socket.close();
        } catch (Exception e) {
        }
        MainActivity.isRun.setText("Offline");
    }

    public void sendMessage() {
        try {
            final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            if (!MainActivity.sendMessage.getText().equals("")) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        out.println(MainActivity.sendMessage.getText());
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
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