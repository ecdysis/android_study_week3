package feeder.rss.thread;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
/*
    앞서 Handler에서 메시지를 전송하고 순서대로 사용하는 방법으로 개발시에 코드가 복잡하게 보이는 단점이 있음.
     이에 대한 대안으로 핸들러 클래스 내에 Runnable 객체를 실행시키는 방법을 제공함
     post()메소드를 이용해 runnable 객체를 핸들러에 전달해주면 이 객체에 정의 된 run()메소드 내의 코드들을
     메인 스레드에서 실행하게 해줌.
*/
public class RunnableActivity extends AppCompatActivity {

    Handler          handler;
    ProgressRunnable runnable;

    ProgressBar progressBar;
    TextView    textView;

    boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2 );
        textView    = (TextView)    findViewById(R.id.textView2    );

        handler = new Handler();
        runnable = new ProgressRunnable();

        isRunning = true;
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
                        // 이부분
                        handler.post(runnable);
                        //
                    }
                }catch(Exception e) {

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

    public class ProgressRunnable implements Runnable {
        @Override
        public void run() {
            progressBar.incrementProgressBy(5);

            if(progressBar.getProgress() == progressBar.getMax()) {
                textView.setText("Done");
            } else {
                textView.setText("Runnable working ..." + progressBar.getProgress());
            }
        }
    }
}
