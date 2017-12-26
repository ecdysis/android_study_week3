package feeder.rss.thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

/*

 앱을 실행 할 때 프로세스가 만들어지면 그안에 메인스레드 (UI 스레드 가 함께 만들어짐)
 액티비티 브로드캐스트 수신자 등이 실행 되면서 윈도우를 관리하기 위한 메시지 큐를 실행하게 됨
 이 메시지 큐를 핸들러를 사용하여 스레드간의 메시지를 주고 받는다.

  대략적인 순서
  1. 안드로이드에서 제공하는 handler 클래스를 상속하는 클래스 생성.
  2. 메시지 큐에 obtainMessage를 이용하여 공간을 만든다
  3. 메시지 데이터를 넣기 위해 Bundle 객체를 사용.
  4. 번들에 값 집어넣고 번들을 메시지에 집어 넣음
  5. 메시지 큐로 보냄.
  6. 핸들러에서 메시지 받음.
*/
public class HandlerActivity extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";

    // Handler 클래스를 상속하는 클래스의 인스턴스
    ProgressHandler handler;
    ProgressBar     progressBar;
    TextView        textView;

    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        isRunning = true;

        handler = new ProgressHandler();
        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setProgress(0);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0; i< 20 && isRunning; i++) {
                        Thread.sleep(1000);
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                }catch (Exception e) {
                    Log.e(TAG, "run: onStart()", e);
                }
            }
        });

        isRunning = true;
        thread1.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }

    public class ProgressHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            progressBar.incrementProgressBy(5); // 5씩 증가

            if(progressBar.getProgress() == progressBar.getMax()) {
                // 꽉차면 done
                textView.setText("Done");

            } else {
                textView.setText("working... " + progressBar.getProgress());
            }
        }
    }
}
