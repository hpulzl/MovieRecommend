package lzl.edu.com.movierecommend.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.util.CheckValidate;
import lzl.edu.com.movierecommend.util.KeyboardUtil;
import lzl.edu.com.movierecommend.util.MyCountTimer;
import lzl.edu.com.movierecommend.util.ToastUtil;
import lzl.edu.com.movierecommend.view.LoginEditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG="RegisterActivity";

    private TextInputLayout phoneRInput,passRInput,nickNameInput,checkCodeRInput;
    private LoginEditText phoneEt,nickNameEt;
    private EditText checkCodeREt,passEt;
    private ImageView visibleRIv;
    private MyCountTimer timer;
    private Button checkCodeBtn,registerBtn;
    private CheckValidate checkValidate;
    private boolean isSendSuccess = false;
    private String phoneNum;
    private String code;
    private String passNum;
    private String nickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar();
        findById();
    }

    private void findById() {
        checkValidate = new CheckValidate();
        phoneRInput = (TextInputLayout) findViewById(R.id.phoneRInput);
        passRInput = (TextInputLayout) findViewById(R.id.passRInput);
        nickNameInput = (TextInputLayout) findViewById(R.id.nickNameInput);
        checkCodeRInput = (TextInputLayout) findViewById(R.id.checkCodeRInput);
        phoneEt = (LoginEditText) findViewById(R.id.phoneEt);
        nickNameEt = (LoginEditText) findViewById(R.id.nickNameEt);
        passEt = (EditText) findViewById(R.id.passwordEt);
        checkCodeREt = (EditText) findViewById(R.id.checkCodeEt);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        checkCodeBtn = (Button) findViewById(R.id.checkCodeBtn);
        visibleRIv = (ImageView) findViewById(R.id.visibleRIv);

        visibleRIv.setTag(true);
        visibleRIv.setOnClickListener(this);
        checkCodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    private void initToolbar() {
            Toolbar loginToolbar = (Toolbar) findViewById(R.id.toolBar);
            loginToolbar.setTitle("注册");
            setSupportActionBar(loginToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerBtn:
                hideKeyboard();
                userRegister();
                break;
            case R.id.checkCodeBtn:  //点击获取验证码，是去获取验证码的。不做校验，点击注册按钮，才去校验验证码
                hideKeyboard();
                sendMessageToRegister();
                break;
            case R.id.visibleRIv:
                if((boolean)visibleRIv.getTag()){ //如果为true,再点击
                    setPassImageChange(false);

                }else{
                    setPassImageChange(true);
                }
                break;
        }
    }
    private void setPassImageChange(boolean flag){
        if(flag){
            //设置密码隐藏的
            passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            visibleRIv.setImageResource(R.mipmap.ic_invisible);
        }else{
            //密码可见的
            passEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            visibleRIv.setImageResource(R.mipmap.ic_visible);
        }
        visibleRIv.setTag(flag);
        passEt.setSelection(passEt.getText().length());
    }
    private void hideKeyboard() {
        new KeyboardUtil(this).hideKeyboard();
    }

    /**
     * 无论是获取验证码还是点击注册
     * 都需要进行 用户名、密码、电话号的验证
     * @return
     */
    private boolean userValidate(){
        String phoneInfo =  checkPhone();
        String passInfo = checkPass();
        String nickInfo = checkNickName();
        if(nickInfo!=null) {
            nickNameInput.setError(nickInfo);
            return false;
        }
        if(phoneInfo!=null){
            phoneRInput.setError(phoneInfo);
            return false;
        }
        if(passInfo!=null){
            passRInput.setError(passInfo);
            return false;
        }
        return true;
    }
    private void userRegister() {
        if(userValidate()) {  //如果验证成功的话执行验证码的验证
            passRInput.setErrorEnabled(false);
            phoneRInput.setErrorEnabled(false);
            nickNameInput.setErrorEnabled(false);
            CodeValidate();
            if (isSendSuccess) {  //短信验证成功，再执行注册逻辑
                doRegister();
            }
        }
    }
    /**
     * 进行短信的验证
     */
    private void CodeValidate() {
        String codeInfo = checkCode();
        if(codeInfo!=null){
            checkCodeRInput.setError(codeInfo);
        }else {
            checkCodeRInput.setErrorEnabled(false);
            if(timer!=null) {
                BmobSMS.verifySmsCode(this, phoneNum, code, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtil.toast(RegisterActivity.this, "短信验证成功");
                            isSendSuccess = true;
                        } else {
                            ToastUtil.toast(RegisterActivity.this, "验证码输入错误");
                            isSendSuccess = false;
                            timer.onFinish();
                            timer.cancel();
                        }
                    }
                });
            }else{
                ToastUtil.toast(RegisterActivity.this, "您尚未获取验证码");
            }
        }
    }
    private void doRegister() {
        //进行注册...
    }

    private String checkNickName() {
        nickName = nickNameEt.getText().toString();
        return checkValidate.checkNickName(nickName);
    }

    private String checkPass() {
        passNum = passEt.getText().toString();
        return checkValidate.checkPass(passNum);
    }

    private String checkPhone() {
        phoneNum = phoneEt.getText().toString();
        return checkValidate.checkPhone(phoneNum);
    }
    private String checkCode(){
        code = checkCodeREt.getText().toString();
        return checkValidate.checkCode(code);
    }
    public void sendMessageToRegister() {
        if(userValidate()) {
            timer = new MyCountTimer(60000, 1000);
            timer.setmBtn(checkCodeBtn);
            timer.start();
            BmobSMS.requestSMSCode(this, phoneNum, "Message", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                        ToastUtil.toast(RegisterActivity.this, "验证码发送成功");
                    } else {
                        ToastUtil.toast(RegisterActivity.this, "请十分钟之后重新发送");
                        timer.onFinish();
                        timer.cancel();
                    }
                }
            });
        }
    }
}
