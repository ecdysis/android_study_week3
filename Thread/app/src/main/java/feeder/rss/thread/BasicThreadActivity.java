package feeder.rss.thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class BasicThreadActivity extends AppCompatActivity {

    private final static String TAG = "BasicThread";

    TextView textView;
    private int value;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_thread);

        // flag
        running = true;
        // 보여줄 값
        value   = 0;

        textView  = (TextView) findViewById(R.id.basicThreadTextView);

        findViewById(R.id.basicThreadShowBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("스레드에서 받은 값 : " + value);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Thread thread1 = new BackgroundThread();
        thread1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 액티비티가 멈추면 스레드 중지.
        running = false;
        value = 0;
    }

    class BackgroundThread extends Thread {
        public void run() {
            while(running) {
                try {
                    Thread.sleep(1000);
                    value++;
                } catch(Exception e) {
                    Log.e(TAG, "run: BackgroundThread", e);
                }
            }
        }
    }
}
