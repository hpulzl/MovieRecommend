package lzl.edu.com.movierecommend.util;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

/**
 * Created by admin on 2016/1/15.
 */
public class MyCountTimer extends CountDownTimer{
    Button mBtn;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    public void setmBtn(Button btn){
        this.mBtn = btn;
    }
    @Override
    public void onTick(long millisUntilFinished) {
        mBtn.setText((millisUntilFinished / 1000)+"秒后重新发送");
        mBtn.setEnabled(false);
    }

    @Override
    public void onFinish() {
        mBtn.setText("获取验证码");
        mBtn.setEnabled(true);
    }
}
