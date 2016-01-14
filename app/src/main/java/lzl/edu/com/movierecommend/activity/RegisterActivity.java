package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import lzl.edu.com.movierecommend.R;

public class RegisterActivity extends AppCompatActivity {
    private Intent mIntent;
    private static final String TAG="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        initToolbar();
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
}
