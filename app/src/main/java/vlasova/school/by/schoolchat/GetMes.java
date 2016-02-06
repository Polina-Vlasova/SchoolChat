package vlasova.school.by.schoolchat;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;

class GetMes extends AsyncTask {
    String text;

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            text = MainActivity.clientThread.getStringForce();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if(text != null) {
            MainActivity.mes.add(text);
            MainActivity.add(text);
        }
        new GetMes().execute();
    }


}