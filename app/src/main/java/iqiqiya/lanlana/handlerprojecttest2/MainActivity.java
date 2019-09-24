package iqiqiya.lanlana.handlerprojecttest2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    /**
     * 倒计时标记handle code.
     */

    public static final int COUNTDOWN_TIME_CODE = 100001;

    public static final int DELAY_MILLIS = 1000;
    public static final int MAX_COUNT = 10;
    private TextView mCountdownTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 得到控件
        mCountdownTimeTextView = findViewById(R.id.countdownTimeTextView);

        // 解决内存泄露的情况  重新创建一个静态的

        // 创建了一个handler.
        CountdownTimeHandler handler = new CountdownTimeHandler(this);

        // 创建message.
        Message message = Message.obtain();
        message.what = COUNTDOWN_TIME_CODE;
        message.arg1 = MAX_COUNT;

        // 第一次发送message.
        handler.sendMessageDelayed(message, DELAY_MILLIS);
        // 每隔一秒发出信息
    }

       // 通过弱引用  不用的时候  会自动销毁
        public static class CountdownTimeHandler extends Handler {

            final WeakReference<MainActivity> mWeakReference;
            static final int MIN_COUNT = 0;

            public CountdownTimeHandler(MainActivity activity) {
                mWeakReference = new WeakReference<>(activity);
            }

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                MainActivity activity = mWeakReference.get();

                switch (msg.what) {
                    case COUNTDOWN_TIME_CODE:

                        int value = msg.arg1;

                        activity.mCountdownTimeTextView.setText(String.valueOf(value--));

                        // 这里不能复用msg 会闪退  要重新从Message池里拿

                        // 循环进行消息控制
                        if (value >= MIN_COUNT){
                            Message message = Message.obtain();
                            message.what = COUNTDOWN_TIME_CODE;
                            message.arg1 = value;
                            sendMessageDelayed(message,DELAY_MILLIS);
                        }
                        break;
                }
            }
        }
    }
