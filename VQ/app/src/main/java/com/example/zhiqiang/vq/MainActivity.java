package com.example.zhiqiang.vq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zhiqiang.vq.Adapter.MyFragmentPagerAdapter;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.jzvd.JZVideoPlayer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private LinearLayout account;
    private ImageView image;
    private TextView name;
    private View headerView;

    private RadioGroup rg_tab_bar;
    private RadioButton first;
    private RadioButton second;
    private RadioButton thrid;

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mAdapter;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;

    private Tencent mTencent;
    private BaseUiListener listener;
    private UserInfo mUserInfo;
    private String qqGroupKey;
    private Bitmap bitmap;
    public static int MODE = MODE_PRIVATE;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String PREFERENCE_NAME = "login";
    private static final String TAG = "QQ";
    private static final String APP_ID = "1106619429";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadView();

        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
        qqGroupKey = "sG1Wk4vHjbaA0N1v_3ljbymVWVW9uYke";
        loginByToken();//由记录的token信息登录，不必拉起

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindView();
        first.setChecked(true);
    }

    public void bindView() {
        account = (LinearLayout) headerView.findViewById(R.id.account);
        image = (ImageView) headerView.findViewById(R.id.imageView);
        name = (TextView) headerView.findViewById(R.id.name);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        first = (RadioButton) findViewById(R.id.first);
        second = (RadioButton) findViewById(R.id.second);
        thrid = (RadioButton) findViewById(R.id.thrid);
        rg_tab_bar.setOnCheckedChangeListener(this);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
    }

    /*底部导航栏*/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageSelected(int position) {}
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (mViewPager.getCurrentItem()) {
                case PAGE_ONE:
                    first.setChecked(true);
//                    Log.e("滑动", "1");
                    break;
                case PAGE_TWO:
                    second.setChecked(true);
//                    Log.e("滑动", "2");
                    break;
                case PAGE_THREE:
                    thrid.setChecked(true);
//                    Log.e("滑动", "3");
                    break;
            }
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.first:
                mViewPager.setCurrentItem(PAGE_ONE);
//                Log.e("选中", "1");
                break;
            case R.id.second:
                mViewPager.setCurrentItem(PAGE_TWO);
//                Log.e("选中", "2");
                break;
            case R.id.thrid:
                mViewPager.setCurrentItem(PAGE_THREE);
//                Log.e("选中", "3");
                break;
        }
    }
    /*底部导航栏*/

    /*侧滑栏*/
    public void loadView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Bundle params = new Bundle();
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, "(泡妞最常见的20个方法)你知道几个?");
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://img5.imgtn.bdimg.com/it/u=2055785157,409777436&fm=27&gp=0.jpg");
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "PUA把妹达人分享形象建设、提升魅力,告诉你怎么和女生、男生聊天,让你掌握和女孩子的约会技巧,让你喜欢的人");
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://img5.imgtn.bdimg.com/it/u=2055785157,409777436&fm=27&gp=0.jpg");
//            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,"/storage/emulated/0/Tencent/QQ_images/1516682823759.jpeg");
//            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试");
//            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
//            params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
//            params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "要分享的摘要");
//            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://www.qq.com/news/1.html");
//            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
//            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, "http://t.cn/RQEGMC4");

            mTencent.shareToQQ(MainActivity.this, params, new BaseUiListener());
        }else if (id == R.id.nav_people) {
            joinQQGroup(qqGroupKey);
        } else if (id == R.id.nav_setting) {
            logout();
            updateLoginState();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*侧滑栏*/

    /*QQ登录部分*/
    public void login() {
        listener = new BaseUiListener();
        if (!mTencent.isSessionValid()) {
            Log.e("QQ", "登录");
            mTencent.login(this, "all", listener);
        }
    }
    public void loginByToken() {
        sharedPreferences = getSharedPreferences (PREFERENCE_NAME, MODE);
        editor = sharedPreferences.edit();
        String openID = sharedPreferences.getString("openID", "");
        String accessToken = sharedPreferences.getString("access_token", "");
        String expires = sharedPreferences.getString("expires", "");
        if (!openID.equals("") && !accessToken.equals("") && !expires.equals("")) {
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(accessToken, expires);
            login();
            getUserInfo();
        }
    }
    public void getUserInfo() {
        QQToken qqToken = mTencent.getQQToken();
        mUserInfo = new UserInfo(getApplicationContext(), qqToken);
        mUserInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object response) {
                final JSONObject jsonObject = (JSONObject) response;
                try {
                    String nickname = jsonObject.getString("nickname");
                    name.setText(nickname);
                    final Handler handler =  new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what == 0) {
                                image.setImageBitmap(bitmap);
                            }
                        }
                    };
                    new Thread() {
                        @Override
                        public void run() {
                            // 返回的arg0 包含QQ个人所有的信息
                            // 获取头像
                            if (jsonObject.has("figureurl")) {
                                try {
                                    bitmap = createCircleImage(getbitmap(jsonObject.getString("figureurl_qq_2")));
                                    Message msg = new Message();
                                    msg.what = 0;
                                    handler.sendMessage(msg);;
                                    // 根据网址加载网络图片
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }.start();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "登录成功" + response.toString());
            }

            @Override
            public void onError(UiError uiError) {
                Log.e(TAG, "登录失败" + uiError.toString());
            }
            @Override
            public void onCancel() {
                Log.e(TAG, "登录取消");
            }
        });
    }
    public static Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        Bitmap bm = null;
        try {
            URL myFileUrl = new URL(imageUri);
            Log.e("url", "url=" + imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bm = BitmapFactory.decodeStream(is);
            is.close();
            // 加载网络图片
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
    public void logout() {
        editor.putString("openID", "").commit();
        editor.putString("access_token", "").commit();
        editor.putString("expires", "").commit();
        mTencent.logout(this);
    }
    public void updateLoginState() {
        name.setText("立即登录");
        image.setImageResource(R.mipmap.user);
    }
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            JSONObject obj = (JSONObject) o;
            String openID = null;
            try {
                openID = obj.getString("openid");
                mTencent.setOpenId(openID);
                editor.putString("openID", openID).commit();
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setAccessToken(accessToken, expires);
                editor.putString("access_token", accessToken).commit();
                editor.putString("expires", expires).commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getUserInfo();
        }
        @Override
        public void onError(UiError e) {
            Log.e("onError:", "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }
        @Override
        public void onCancel() {
            Log.e("cancel ", "取消");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, listener);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    /*QQ登录部分*/
}