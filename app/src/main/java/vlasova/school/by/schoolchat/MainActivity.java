package vlasova.school.by.schoolchat;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private static final int SLEEP_TIME = 1000;
    static LinearLayout ll;
    static ClientThread clientThread;
    static ArrayList<String> mes = new ArrayList<>();
    static ScrollView sv;
    static ConnectivityManager cm;
    static Context context;
    static EditText sendMessage;
    static TextView isRun;
    static String myId;
    static TextView myid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = (LinearLayout) findViewById(R.id.linear);
        sv = (ScrollView) findViewById(R.id.scrollView);
        clientThread = new ClientThread();
        final Thread thread = new Thread(clientThread);
        cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        context = this;
        sendMessage = (EditText) findViewById(R.id.message);
        isRun = (TextView) findViewById(R.id.isOnline);
        myid = (TextView) findViewById(R.id.myId);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientThread.sendMessage();
                sendMessage.setText("");
            }
        });
        if (isOnline() && isRun.getText().equals("Offline")) {
            isRun.setText("Online");
            thread.start();
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new GetMes().execute();
        }
    }

    public static void add(String s) {
        String id = "";
        if (mes.size() == 1) {
            myId = mes.get(0).substring(6);
            myid.setText("Id: " + myId);
        } else if (!s.equals("")) {
            id = s.substring(0, s.indexOf(':'));
        }
        TextView textView = new TextView(context);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(lp);
        if (!id.equals(myId)) {
            textView.setText(s);
            lp.setMargins(5, 0, 25, 10);
            lp.gravity = Gravity.LEFT;
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setBackgroundResource(R.drawable.mes);
            Log.d("wtf", 0 + "/");
        } else {
            textView.setText(s.substring(myId.length() + 2));
            lp.setMargins(25, 0, 5, 10);
            lp.gravity = Gravity.RIGHT;
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setBackgroundResource(R.drawable.my_mes);
            Log.d("wtf", 1 + "/");
        }
        ll.addView(textView);
        sv.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public static boolean isOnline() {
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
