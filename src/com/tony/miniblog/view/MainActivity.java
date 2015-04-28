package com.tony.miniblog.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tony.miniblog.R;
import com.tony.miniblog.adapter.BlogsAdapter;
import com.tony.miniblog.adapter.MainPageAdapter;
import com.tony.miniblog.listener.PersonalCenterOauthListener;
import com.tony.miniblog.utils.BlogConstants;
import com.tony.miniblog.utils.DataUtils;
import com.tony.miniblog.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tony on 2015/2/18 0018.
 */
public class MainActivity extends Activity implements ViewPager.OnPageChangeListener, DynamicListView.DynamicListViewListener {


    private TextView tv_hotBs, tv_friendBs, tv_relatedMe, tv_personalCenter;
    private ViewPager vp;
    private ImageView iv_cursor;
    private List<View> viewList;
    private DynamicListView vpcontent_hotBs;
    private ListView vpcontent_friendBs;
    private ListView vpcontent_relatedMe;
    private View vpcontent_personalCenter;

    public int screenWidth;// 屏幕的宽度
    public int imgWidth; // 图片宽度
    public int currentView = 0;// 当前视图
    public int viewOffset;// 动画图片偏移量
    public int toX;

    //define the log tag
    public static final String TAG = "Main";

    //personal center views defination
    private TextView tv_simpleTitle, tv_blogUrl;
    private ImageView iv_headIcon;

    //personal center oauth variables.
    private SsoHandler ssoHandler;
    private PCenterBroadCastReceiver pCenterBroadCastReceiver;

    //pcenter url
    //头像地址URL
    private String profile_image_url = null;

    //define the data structure of each page.
    /**
     * ****************test*********************
     */
    private ArrayList<String> hotBlogsData = null;
    private ArrayAdapter<String> dataAdapter = null;

    /**
     * *****************end*********************
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        if (viewList == null) {
            viewList = new ArrayList<View>(4);
        }
        initTXViews();
        initImageView();
    }

    /**
     * Initialize the blog items.
     */
    private void initListViews() {
        //Get blog data cached at local. Then when called refresh, update data list.
        hotBlogsData = new ArrayList<String>(50);
        for (int i = 0; i < 5; i++) {
            hotBlogsData.add("原始数据原始数据原始数据原始数据" + i);
        }

        dataAdapter = new ArrayAdapter<String>(this, R.layout.expandable_list_item_pg1, hotBlogsData);
        vpcontent_hotBs = new DynamicListView(this);
        vpcontent_hotBs.setAdapter(dataAdapter);
        vpcontent_hotBs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You choose item[" + (position - 1) + "]", Toast.LENGTH_SHORT).show();
            }
        });
        vpcontent_hotBs.setDoMoreWhenBottom(false);
        vpcontent_hotBs.setOnRefreshListener(this);
        vpcontent_hotBs.setOnMoreListener(this);
    }

    private void initImageView() {
        iv_cursor = (ImageView) this.findViewById(R.id.main_iv_cursor);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        screenWidth = metrics.widthPixels;
        imgWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor_title).getWidth();
        viewOffset = (screenWidth / 4 - imgWidth) / 2;
        Log.d("Main", "offset = " + viewOffset);

        Matrix cursor_matrix = new Matrix();
        cursor_matrix.postTranslate(viewOffset, 0);
        iv_cursor.setImageMatrix(cursor_matrix);
    }

    private void initTXViews() {

        //init textviews
        tv_hotBs = (TextView) this.findViewById(R.id.main_tab_hotblogs);
        tv_friendBs = (TextView) this.findViewById(R.id.main_tab_friendsblog);
        tv_relatedMe = (TextView) this.findViewById(R.id.main_tab_relatedme);
        tv_personalCenter = (TextView) this.findViewById(R.id.main_tab_myblogs);

        LayoutInflater inflater = LayoutInflater.from(this);
        //vpcontent_hotBs = (ListView) inflater.inflate(R.layout.layout_vpcontent_pg1, null).findViewById(R.id.lv_pg1_hotblogs);
        //init the data of first page.
        initListViews();

        vpcontent_friendBs = (ListView) inflater.inflate(R.layout.layout_vpcontent_pg2, null).findViewById(R.id.lv_pg2_friendBlogs);
        vpcontent_relatedMe = (ListView) inflater.inflate(R.layout.layout_vpcontent_pg3, null).findViewById(R.id.lv_pg3_relatedMeBlogs);
        vpcontent_personalCenter = (View) inflater.inflate(R.layout.layout_vpcontent_pg4, null);

        //init personal center textviews
        tv_simpleTitle = (TextView) vpcontent_personalCenter.findViewById(R.id.tv_pCenter_nickName);
        tv_blogUrl = (TextView) vpcontent_personalCenter.findViewById(R.id.tv_pCenter_blogAddress);
        iv_headIcon = (ImageView) vpcontent_personalCenter.findViewById(R.id.iv_pCenter_headIcon);

        //create ssoHandler, to implement sso authorization.
        AuthInfo authInfo = new AuthInfo(MainActivity.this, BlogConstants.APPInfo.APP_KEY, BlogConstants.APPInfo.REDIRECT_URL, BlogConstants.APPInfo.SCOPE);
        ssoHandler = new SsoHandler(MainActivity.this, authInfo);
        //finished

        //vpcontent_hotBs.setAdapter(new BlogsAdapter(MainActivity.this, DataUtils.getData(0)));
        vpcontent_friendBs.setAdapter(new BlogsAdapter(MainActivity.this, DataUtils.getData(1)));
        vpcontent_relatedMe.setAdapter(new BlogsAdapter(MainActivity.this, DataUtils.getData(2)));
        //vpcontent_personalCenter.setAdapter(new BlogsAdapter(MainActivity.this, DataUtils.getData(2)));
        vpcontent_personalCenter.findViewById(R.id.tv_headinfo_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "This function is developing! Please try later", Toast.LENGTH_SHORT).show();
            }
        });

        viewList.add(vpcontent_hotBs);
        viewList.add(vpcontent_friendBs);
        viewList.add(vpcontent_relatedMe);
        viewList.add(vpcontent_personalCenter);

        //init viewpager
        vp = (ViewPager) this.findViewById(R.id.main_vp);

        tv_hotBs.setOnClickListener(new MainPageTitleOnClickListener(BlogConstants.MainBlogTitles.hotBlog));
        tv_friendBs.setOnClickListener(new MainPageTitleOnClickListener(BlogConstants.MainBlogTitles.friendsBlogs));
        tv_relatedMe.setOnClickListener(new MainPageTitleOnClickListener(BlogConstants.MainBlogTitles.relatedMe));
        tv_personalCenter.setOnClickListener(new MainPageTitleOnClickListener(BlogConstants.MainBlogTitles.personalCenter));

        vp.setCurrentItem(0);
        vp.setOnPageChangeListener(this);
        vp.setAdapter(new MainPageAdapter(viewList));
    }


    class MainPageTitleOnClickListener implements View.OnClickListener {
        private int title_index = 0;

        public MainPageTitleOnClickListener(int index) {
            this.title_index = index;
        }

        @Override
        public void onClick(View v) {
            Log.d("Main_Title_Click", "you have clicked the " + title_index + " tab!");
            switch (title_index) {
                case BlogConstants.MainBlogTitles.hotBlog:
                    vp.setCurrentItem(0);
                    break;
                case BlogConstants.MainBlogTitles.friendsBlogs:
                    vp.setCurrentItem(1);
                    break;
                case BlogConstants.MainBlogTitles.relatedMe:
                    vp.setCurrentItem(2);
                    break;
                case BlogConstants.MainBlogTitles.personalCenter:
                    vp.setCurrentItem(3);
                    break;
            }
            currentView = title_index;
            //点击导航栏对应的页卡时显示不同的内容
            displayContentOfPages(title_index);
        }
    }

    //init title text color
    private void initTextColor() {
        tv_hotBs.setTextColor(getResources().getColor(android.R.color.white));
        tv_friendBs.setTextColor(getResources().getColor(android.R.color.white));
        tv_relatedMe.setTextColor(getResources().getColor(android.R.color.white));
        tv_personalCenter.setTextColor(getResources().getColor(android.R.color.white));
    }

    //change text color
    private void changeTextColor(int i) {
        switch (i) {
            case 0:
                tv_hotBs.setTextColor(getResources().getColor(R.color.purple));
                break;
            case 1:
                tv_friendBs.setTextColor(getResources().getColor(R.color.purple));
                break;
            case 2:
                tv_relatedMe.setTextColor(getResources().getColor(R.color.purple));
                break;
            case 3:
                tv_personalCenter.setTextColor(getResources().getColor(R.color.purple));
                break;

        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        initTextColor();
        this.currentView = i;
        Log.e("Main", "one = " + toX + " currentView = " + currentView);
        int one = viewOffset * 2 + imgWidth;
        Animation anim = new TranslateAnimation((one * currentView + 5), i * one, 0, 0);
        anim.setDuration(300);
        anim.setFillAfter(true);
        iv_cursor.startAnimation(anim);
        changeTextColor(i);
        Log.e("Main", "Animation has started....");
        //vp.setCurrentItem(i);

        //选择不用页卡显示不同内容
        Log.e("Main", "you have choosed the <" + i + "> page");
        displayContentOfPages(i);
    }

    /**
     * 根据选中的页卡来显示不同的内容
     *
     * @param i
     */
    private void displayContentOfPages(int i) {
        Log.e(TAG, "begin to display content of this page...");
        switch (i) {
            case 0://page1
                break;
            case 1://page2

                break;
            case 2://page3
                break;
            case 3://page4 personal center
                //check is authorized
                boolean isAuthorized = false;
                if(isAuthorized){
                    //get userinfo and set up.
                }
                getAuthorization();
                break;
        }

    }

    /**
     * 认证流程
     */
    protected void getAuthorization() {
        ssoHandler.authorize(new PersonalCenterOauthListener(MainActivity.this));
        IntentFilter filter = new IntentFilter();
        filter.addAction(BlogConstants.BroadCastActions.ACTION_NAME);
        pCenterBroadCastReceiver = new PCenterBroadCastReceiver();
        registerReceiver(pCenterBroadCastReceiver, filter);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != ssoHandler) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 广播接收器，获取后台从网络URL取得的用户信息
     */
    class PCenterBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Begin to receive the broadcast from request listener!");
            if (intent.getAction().equals(BlogConstants.BroadCastActions.ACTION_NAME)) {
                String screen_name = intent.getStringExtra("screen_name");
                String url = intent.getStringExtra("profile_url");
                Log.e(TAG, "You have got the screen_name = " + screen_name + "// url = " + url + "from request listenr!");
                tv_simpleTitle.setText(screen_name);
                tv_blogUrl.setText("http://blog.sina.com.cn/" + url);
                profile_image_url = intent.getStringExtra("avatar_large");
                Log.e(TAG, "avatar_large_url in Receiver --->" + profile_image_url);
                //获取到图片路径，开启线程下载图片
                if (null != profile_image_url && "" != profile_image_url) {
                    new ImageLoaderUtils(MainActivity.this, profile_image_url, handler).start();
                } else {
                    //如果头像地址无，则设置默认图片
                    iv_headIcon.setImageDrawable(getResources().getDrawable(R.drawable.head_none));
                }
            }
            //解绑广播接收器
            unregisterReceiver(pCenterBroadCastReceiver);
        }
    }


    /**
     * 用于更新个人中心头像图片
     */
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("MainActivity", "msg.what------>" + msg.what);
            //更新个人中心资料
            if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
                // 从SD卡种读取头像文件
                // String path = Environment.getExternalStorageDirectory()
                // + "/blog/images/blog_icon.jpg";
                // Bitmap bitmap = BitmapFactory.decodeFile(path);
                iv_headIcon.setImageBitmap(bitmap);
            }
            //更新热门微博列表
            if (msg.what == 2) {
                dataAdapter.notifyDataSetChanged();
                vpcontent_hotBs.doneRefresh();
                Toast.makeText(MainActivity.this, "New data [" + msg.arg1 + "] items!", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 21) {
                dataAdapter.notifyDataSetChanged();
                vpcontent_hotBs.doneMore();
            } else {
                super.handleMessage(msg);
            }
            //更新好友动态列表
            if (msg.what == 3) {

            }
            //更新与我相关列表
            if (msg.what == 4) {

            }
        }
    };

    @Override
    public boolean onRefreshOrMore(DynamicListView dynamicListView,
                                   boolean isRefresh) {
        if (isRefresh) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 刷新
                    ArrayList<String> temp = new ArrayList<String>();
                    for (int i = 0; i < 3; ++i) {
                        temp.add(0, new Date().toLocaleString());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    synchronized (hotBlogsData) {
                        hotBlogsData.addAll(0, temp);
                    }

                    Message message = new Message();
                    message.what = 2;
                    message.arg1 = temp.size();
                    handler.sendMessage(message);
                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @SuppressWarnings("deprecation")
                @Override
                public void run() {
                    // 加载更多
                    ArrayList<String> temp = new ArrayList<String>();
                    for (int i = 0; i < 3; ++i) {
                        temp.add(new Date().toLocaleString());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    synchronized (hotBlogsData) {
                        hotBlogsData.addAll(temp);
                    }
                    handler.sendEmptyMessage(21);
                }
            }).start();
        }
        return true;
    }

}
