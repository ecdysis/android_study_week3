package feeder.rss.thread;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
    스레드는 동시 수행이 가능한 작업단위이며 하나의 프로세스에 여러 스레드가 있을 수 있다.
    오랜 지연시간이 생기는 작업은 두가지 시나리오로 진행한다.
    1. 서비스: 백그라운드 작업을 서비스로 하고 이를 브로드 캐스팅해서 액티비티로 결과값 전달.
    2. 스레드: 동일 프로세스 내에 있기 때문에 작업결과를 바로 처리 가능 UI 객체는 직접 접근할 수 없으므로 핸들러(Handler)
                사용.
    * 데드락: 멀티 스레드 방식의 고질적인 문제인 리소스에 동시 접근시에 생기는 경우를 데드락이라고 하며 시스템이 비정상적으로
            동작할 가능성이 있다.
    ∴ 핸들러를 이용하여 이를 순서대로 처리하여 처리. 이 역할은 메인스레드의 핸들러가 담당.
     => 메인 스레드가 사전에 데드락을 방지하기 위해 다른 스레드에서의 접근을 허용하지 않음.

    # Activity
     - MainActivity: 메인
     - BasicThreadActivity: 기본적인 쓰레드 사용 소스.
     - HandlerActivity: 핸들러 기본적인 사용 소스.
     - RunnableActivity: runnable의 기본적인 사용소스.

 */
public class MainActivity extends AppCompatActivity {

    Button button2;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위젯에서 바로 이벤트 리스너 설정한 방법.
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), HandlerActivity.class);
                startActivity(intent2);
            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), RunnableActivity.class);
                startActivity(intent3);
            }
        });
    }

    // XML 상에서 onClick 속성을 이용한 방법.
    public void onBasicThreadClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), BasicThreadActivity.class);
        startActivity(intent);
    }

}
