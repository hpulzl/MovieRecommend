package lzl.edu.com.movierecommend.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.http.URLAddress;
import lzl.edu.com.movierecommend.http.volleyutil.VolleyUtil;
import lzl.edu.com.movierecommend.util.CheckValidate;
import lzl.edu.com.movierecommend.util.KeyboardUtil;
import lzl.edu.com.movierecommend.util.ToastUtil;
import lzl.edu.com.movierecommend.view.LoginEditText;
import shareprefrence.OperateShareprefrence;

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
    //登录时的缓冲条
    private ProgressDialog progressBar ;
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
            passInput.setError(passInfo);
        }else{
            phoneInput.setErrorEnabled(false);
            passInput.setErrorEnabled(false);
            doLogin();
        }
    }

    private void doLogin() {
        progressBar = new ProgressDialog(LoginActivity.this);
        progressBar.setMessage("登录中...");
        progressBar.setCancelable(false);
        progressBar.show();
        //与网络连接，返回正常成功或失败
        String url = URLAddress.getURLPath("UserLoginSer");
        Map<String,String> userMap = new HashMap<>();
        userMap.put("userphone",phoneNum);
        userMap.put("password",passNum);
        JSONObject obj = new JSONObject(userMap);
        //用JsonObject去请求网络。也就是Post请求
        VolleyUtil.sendJsonObjectRequest(this, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Message msg = new Message();
                msg.obj = jsonObject;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.toast(LoginActivity.this,"登录异常"+volleyError.getMessage());
                progressBar.dismiss();
            }
        });
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
          JSONObject jsonObject = (JSONObject) msg.obj;
            try {
                if(!jsonObject.getBoolean("result")){ //登录失败
                    ToastUtil.toast(LoginActivity.this,"用户名或密码错误");
                }else{
                    //登录成功，准备存储信息到Shareprefrence中
                    OperateShareprefrence.storeShareprefrence(LoginActivity.this,jsonObject.getString("userid")
                            ,jsonObject.getString("phone"),jsonObject.getString("username"));
                    //跳转至主页面
                    Intent mIntent = new Intent(LoginActivity.this,NavigationDrawerActivity.class);
                    startActivityForResult(mIntent,0);
                    finish();
                }
                progressBar.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private String checkPass() {
         passNum = passEt.getText().toString();
        return checkValidate.checkPass(passNum);
    }

    private String checkPhone() {
        phoneNum = phoneEt.getText().toString();
        return  checkValidate.checkPhone(phoneNum);
    }
}
