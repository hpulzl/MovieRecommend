package lzl.edu.com.movierecommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.sms.BmobSMS;
import lzl.edu.com.movierecommend.R;
import lzl.edu.com.movierecommend.activity.fragment.CollectionsFragment;
import lzl.edu.com.movierecommend.activity.fragment.CommentFragment;
import lzl.edu.com.movierecommend.activity.fragment.FriendsFragment;
import lzl.edu.com.movierecommend.activity.fragment.MessagesFragment;
import lzl.edu.com.movierecommend.activity.fragment.MoviePagerFragment;
import lzl.edu.com.movierecommend.activity.fragment.SearchFragment;
import lzl.edu.com.movierecommend.activity.fragment.SettingFragment;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG="NavigationDrawerActivity";
    //主Fragement
    private FragmentManager fragmentManager;

    private CollectionsFragment collectionsFragment;  //收藏
    private CommentFragment commentFragment;   //微评
    private FriendsFragment friendsFragment;   //好友
    private MessagesFragment messagesFragment; //消息
    private SettingFragment settingFragment;  //设置
    private MoviePagerFragment moviePagerFragment;  //首页
    private SearchFragment searchFragment;  //搜索

    private boolean isChangeMenu = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nativation_drawer);
        //Bmob短信验证
        BmobSMS.initialize(this,"6ec3245380348b167e542544c487e6f8");
        initView();
        selectItemFragment(R.id.nav_gallery);
    }
    private void initView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //实例化
        fragmentManager = getSupportFragmentManager();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_nativation_drawer);
        ImageView logoImageView = (ImageView) headerView.findViewById(R.id.logo_imageView);

        final Intent mIntent = new Intent();
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.setClass(NavigationDrawerActivity.this,LoginActivity.class);
                startActivity(mIntent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(isChangeMenu){
            getMenuInflater().inflate(R.menu.add_fridends,menu);
        }else {
            getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        //选择Fragment
        selectItemFragment(item.getItemId());
        return true;
    }
    private void selectItemFragment(int id){
        // Handle navigation view item clicks here.
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (id){
            case R.id.nav_camera:
                //搜做Fragment
                if( searchFragment == null){
                    searchFragment = new SearchFragment();
                    transaction.add(R.id.mFragment,searchFragment);
                }else{
                    transaction.show(searchFragment);
                }
                isChangeMenu = false;
                break;
            case R.id.nav_gallery:
                //首页Fragment
                if(moviePagerFragment == null){
                    moviePagerFragment = new MoviePagerFragment();
                    transaction.add(R.id.mFragment,moviePagerFragment);
                }else{
                    transaction.show(moviePagerFragment);
                }
                isChangeMenu = false;

                break;
            case R.id.nav_slideshow:
                //微评Fragment
                if(commentFragment == null){
                    commentFragment = new CommentFragment();
                    transaction.add(R.id.mFragment,commentFragment);
                }else{
                    transaction.show(commentFragment);
                }
                isChangeMenu = false;

                break;
            case R.id.nav_manage:
                //好友Fragment
                if(friendsFragment == null){
                    friendsFragment = new FriendsFragment();
                    transaction.add(R.id.mFragment,friendsFragment);
                }else{
                    transaction.show(friendsFragment);
                }
                isChangeMenu = true;
                break;
            case R.id.nav_share:
                //消息Fragment
                if(messagesFragment == null){
                    messagesFragment = new MessagesFragment();
                    transaction.add(R.id.mFragment,messagesFragment);
                }else{
                    transaction.show(messagesFragment);
                }
                isChangeMenu = false;

                break;
            case R.id.nav_send:
                //收藏Fragment
                if(collectionsFragment == null){
                    collectionsFragment = new CollectionsFragment();
                    transaction.add(R.id.mFragment,collectionsFragment);
                }else{
                    transaction.show(collectionsFragment);
                }
                isChangeMenu = false;

                break;
            case R.id.nav_set:
                //设置Fragment
                if(settingFragment == null){
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.mFragment,settingFragment);
                }else{
                    transaction.show(settingFragment);
                }
                isChangeMenu = false;
                break;
            default:
                break;
        }
        //通知更改menuItem
        invalidateOptionsMenu();
        //提交
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ab_search:
                Toast.makeText(this,"搜索",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_login:
                Toast.makeText(this,"登录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                Toast.makeText(this,"退出",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_add:
                Toast.makeText(this,"添加好友",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 因此Fragment，避免冲突
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction){
        if(searchFragment != null){
            transaction.hide(searchFragment);
        }
        if(collectionsFragment != null){
            transaction.hide(collectionsFragment);
        }
        if(moviePagerFragment!=null){
         transaction.hide(moviePagerFragment);
        }
        if(commentFragment != null){
            transaction.hide(commentFragment);
        }
        if(friendsFragment != null){
            transaction.hide(friendsFragment);
        }
        if(messagesFragment != null){
            transaction.hide(messagesFragment);
        }
        if(settingFragment != null){
            transaction.hide(settingFragment);
        }
    }
}
