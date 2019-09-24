package iqiqiya.lanlana.handlerprojecttest2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int COUNTDOWN_TIME_CODE = 100001;
    public static final int DELAY_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView countdownTimeTextView = findViewById(R.id.countdownTimeTextView);

        // 解决内存泄露的情况
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg != null && msg.what == COUNTDOWN_TIME_CODE) {
                    countdownTimeTextView.setText(String.valueOf(9));
                }
            }
        };

        Message message = Message.obtain();
        message.what = COUNTDOWN_TIME_CODE;

        handler.sendMessageDelayed(message, DELAY_MILLIS);
        // 每隔一秒发出信息

    }
}
