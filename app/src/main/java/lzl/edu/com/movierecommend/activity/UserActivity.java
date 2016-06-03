package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import lzl.edu.com.movierecommend.util.ToastUtil;
import shareprefrence.OperateShareprefrence;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView userNameTv;
    private TextView userPhoneTv;
    private TextView sexTv;
    private TextView commentNumTV;
    private TextView birthdayTV;
    private TextView fansTV;
    private Button changeAccountBtn;
    private Toolbar userToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initToolbar();
        initFindView();
        initUserInfo();
    }

    /**
     * 从服务器端获取用户信息
     */
    private void initUserInfo() {
        String url = URLAddress.getURLPath("UserDetailSer");
        Map<String,String> userMap = new HashMap<>();
        userMap.put("userid", OperateShareprefrence.loadShareprefrence(this).getUserid());
        final JSONObject jsonObj = new JSONObject(userMap);
        VolleyUtil.sendJsonObjectRequest(this, url, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Message msg = new Message();
                msg.obj = jsonObject;
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtil.analysisException(UserActivity.this,"用户信息解析异常"+volleyError.getMessage());
            }
        });
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONObject json = (JSONObject) msg.obj;
            try {
                userNameTv.setText(json.getString("name"));
                userPhoneTv.setText(json.getString("phone"));
                sexTv.setText(json.getInt("sex") == 1 ? "男":"女");
                commentNumTV.setText(json.getInt("commentNum")+"");
                birthdayTV.setText(json.getString("birthday"));
                fansTV.setText(json.getInt("fans")+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    private void initFindView() {
        userNameTv  = (TextView) findViewById(R.id.tv_name);
        userPhoneTv = (TextView) findViewById(R.id.tv_phone);
        sexTv = (TextView) findViewById(R.id.tv_sex);
        commentNumTV = (TextView) findViewById(R.id.tv_commentNum);
        birthdayTV = (TextView) findViewById(R.id.tv_birthday);
        fansTV = (TextView) findViewById(R.id.tv_fans);
        changeAccountBtn = (Button) findViewById(R.id.btn_change_account);

        changeAccountBtn.setOnClickListener(this);
    }

    private void initToolbar() {
        userToolbar = (Toolbar) findViewById(R.id.toolBar);
        userToolbar.setTitle("个人中心");
        setSupportActionBar(userToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userToolbar.setOnClickListener(this);
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
            case R.id.btn_change_account:
                //切换账号
                OperateShareprefrence.deleteShareprefrence(this);
                Intent mIntent = new Intent(this,LoginActivity.class);
                startActivity(mIntent);
                finish();
            break;
        }
    }
}
