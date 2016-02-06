package vlasova.school.by.schoolchat;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private static final int SLEEP_TIME = 1000;
    static LinearLayout ll;
    static ClientThread clientThread;
    static ArrayList<String> mes = new ArrayList<>();
    static ScrollView sv;
    static ConnectivityManager cm;
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll = (LinearLayout) findViewById(R.id.linear);
        sv = (ScrollView) findViewById(R.id.scrollView);
        clientThread = new ClientThread();
        cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        context = this;
        final Thread thread = new Thread(clientThread);
        thread.start();
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new GetMes().execute();
    }

    public static void add(String s) {
        TextView textView = new TextView(context);
        textView.setText(s);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.mes);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 0, 10);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(lp);
        ll.addView(textView);
        sv.fullScroll(ScrollView.FOCUS_DOWN);
    }

    public static boolean isOnline() {
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
