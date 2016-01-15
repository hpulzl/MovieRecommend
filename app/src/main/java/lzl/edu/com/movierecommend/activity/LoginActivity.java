package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.util.CheckValidate;
import lzl.edu.com.movierecommend.util.KeyboardUtil;
import lzl.edu.com.movierecommend.view.LoginEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG="LoginActivity";
    private TextView registerTextView;
    private TextInputLayout passInput;
    private TextInputLayout phoneInput;
    private LoginEditText phoneEt;
    private EditText passEt;
    private Button loginBtn;
    private CheckValidate checkValidate;
    private ImageView visibleIv;
    private String passNum;
    private String phoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolBar();
        initFindId();
    }

    private void initToolBar() {
        Toolbar loginToolbar = (Toolbar) findViewById(R.id.toolBar);
        loginToolbar.setTitle("登录");
        setSupportActionBar(loginToolbar);
    }

    private void initFindId() {
         registerTextView = (TextView) findViewById(R.id.registerTextView);
         phoneInput = (TextInputLayout) findViewById(R.id.phoneInput);
         passInput = (TextInputLayout) findViewById(R.id.passwordInput);
         passEt = (EditText) findViewById(R.id.passwordTextView);
         phoneEt = (LoginEditText) findViewById(R.id.phoneTextView);
         loginBtn  = (Button) findViewById(R.id.loginBtn);
         visibleIv= (ImageView) findViewById(R.id.visibleIv);
        //可见的
        visibleIv.setTag(true);
        visibleIv.setOnClickListener(this);
         registerTextView.setOnClickListener(this);
         loginBtn.setOnClickListener(this);
         Log.i(TAG,phoneEt.getText().toString());
    }

    @Override
    public void onClick(View v) {
        Intent mIntent = new Intent();
        switch (v.getId()){
            case R.id.registerTextView:
                mIntent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(mIntent);
                break;
            case R.id.loginBtn:
                //隐藏键盘
                hideKeyboard();
                //登录操作
                userLogin();
                break;
            case R.id.visibleIv:
                Log.i(TAG,(boolean)visibleIv.getTag()+"");
                if((boolean)visibleIv.getTag()){ //如果为true,再点击
                    setPassImageChange(false);

                }else{
                    setPassImageChange(true);
                }
                break;
            default:break;
        }
    }
    private void setPassImageChange(boolean flag){
        if(flag){
            //设置密码隐藏的
            passEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            visibleIv.setImageResource(R.mipmap.ic_invisible);
        }else{
            //密码可见的
            passEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            visibleIv.setImageResource(R.mipmap.ic_visible);
        }
        visibleIv.setTag(flag);
        passEt.setSelection(passEt.getText().length());
    }
    private void hideKeyboard() {
       new KeyboardUtil(this).hideKeyboard();
    }

    private void userLogin() {
        checkValidate = new CheckValidate();
        String phoneInfo =  checkPhone();
        String passInfo = checkPass();
        if(phoneInfo !=null){
            phoneInput.setError(phoneInfo);
        }else if(passInfo !=null){
            passInput.setError(phoneInfo);
        }else{
            phoneInput.setErrorEnabled(false);
            passInput.setErrorEnabled(false);
            doLogin();
        }
    }

    private void doLogin() {
        //与网络连接，返回正常成功或失败
    }

    private String checkPass() {
         passNum = passEt.getText().toString();
        return checkValidate.checkPass(passNum);
    }

    private String checkPhone() {
        phoneNum = phoneEt.getText().toString();
        return  checkValidate.checkPhone(phoneNum);
    }
}
