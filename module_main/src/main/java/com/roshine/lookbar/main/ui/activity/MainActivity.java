package com.roshine.lookbar.main.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jaeger.library.StatusBarUtil;
import com.roshine.lookbar.commonlib.base.BaseActivity;
import com.roshine.lookbar.commonlib.base.TabFragmentAdapter;
import com.roshine.lookbar.commonlib.utils.ActivityUtil;
import com.roshine.lookbar.commonlib.utils.Constants;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;
import com.roshine.lookbar.main.R;
import com.roshine.lookbar.main.ui.adapter.ThemeChooseAdapter;
import com.roshine.lookbar.main.ui.fragment.NormalFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    TabLayout mTabLayout;
    Toolbar mToolBar;
    ViewPager mViewPager;
    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;

    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> mListData = new ArrayList<>();
    private long firstTime ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(this,mDrawerLayout,getResources().getColor(ThemeColorUtil.getThemeColor()));
        com.roshine.lookbar.commonlib.utils.StatusBarUtil.setColorBar(MainActivity.this, getResources().getColor(ThemeColorUtil.getThemeColor()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        mTabLayout = findViewById(R.id.main_tab_layout);
        mToolBar = findViewById(R.id.main_tool_bar);
        mViewPager = findViewById(R.id.main_view_pager);
        mNavigationView = findViewById(R.id.id_navigationview);
        mDrawerLayout = findViewById(R.id.drawerlayout_home);

        mToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        initTabLayout();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar, R.string.main_open, R.string.main_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        mNavigationView.inflateHeaderView(R.layout.main_head_navigation);//设置菜单栏顶部图片
        mNavigationView.inflateMenu(R.menu.main_menu_navigation);//设置菜单栏条目
        mNavigationView.setItemTextColor(this.getResources().getColorStateList(ThemeColorUtil.getNavigationViewItemColor()));
        mNavigationView.setItemIconTintList(this.getResources().getColorStateList(ThemeColorUtil.getNavigationViewItemColor()));
        // 自己写的方法，设置NavigationView中menu的item被选中后要执行的操作
        onNavgationViewMenuItemSelected(mNavigationView);
    }

    private void initTabLayout() {
        mTitles.add("电影");
        mTitles.add("书籍");
        mTitles.add("音乐");
//        mTitles.add("笑话");
        //添加到View集合
        Fragment movieFragment = (Fragment) ARouter.getInstance().build("/movie/movie_home_fragment").navigation();
        Fragment bookFragment = (Fragment) ARouter.getInstance().build("/book/book_home_fragment").navigation();
        Fragment musicFragment = (Fragment) ARouter.getInstance().build("/music/music_home_fragment").navigation();
        if(movieFragment == null){
            movieFragment = NormalFragment.newInstance("电影");
        }
        if(bookFragment == null){
            bookFragment = NormalFragment.newInstance("书籍");
        }
        if(musicFragment == null){
            musicFragment = NormalFragment.newInstance("音乐");
        }
        mListData.add(movieFragment);
        mListData.add(bookFragment);
        mListData.add(musicFragment);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
//        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(ThemeColorUtil.getThemeColor(this)));
        TabFragmentAdapter mAdapter = new TabFragmentAdapter(getSupportFragmentManager(),mListData,mTitles);
//        MyPagerAdapter mAdapter = new MyPagerAdapter(mListData);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager
        mViewPager.setOffscreenPageLimit(mListData.size());
    }


    private void onNavgationViewMenuItemSelected(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(item -> {
            int i = item.getItemId();
            if (i == R.id.nav_menu_home) {//
                // startThActivity(AuthorInfoActivity.class);
                toast("主页");

            } else if (i == R.id.nav_menu_categories) {
                startActivity(AboutActivity.class);
//                    toast("关于");

            } else if (i == R.id.nav_menu_recommend) {//
                // startThActivity(RecommedActivity.class);
                toast("推荐");

            } else if (i == R.id.nav_menu_feedback) {
                toast("反馈");

            } else if (i == R.id.nav_menu_setting) {
                toast("设置");
                // startThActivityByIntent(new Intent(MainActivity.this, SettingActivity.class));

            } else if (i == R.id.nav_menu_theme) {
                toast("主题");
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_dialog_theme_color, null, false);
                final GridView gridView = (GridView) view.findViewById(R.id.theme_grid_view);
                ThemeChooseAdapter adapter = new ThemeChooseAdapter(MainActivity.this, Constants.ThemeColors.THEME_COLOR_LIST);
                gridView.setAdapter(adapter);
                gridView.setItemChecked(ThemeColorUtil.getThemeColorPosition(), true);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("主题选择")
                        .setView(view)
                        .setPositiveButton(this.getResources().getString(R.string.main_sure_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ThemeColorUtil.setThemeColorPosition(gridView.getCheckedItemPosition());
                                toast("您选择了" + getResources().getString(Constants.ThemeColors.THEME_COLOR_LIST[gridView.getCheckedItemPosition()].getColorName()) + "主题");
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        ActivityUtil.getInstance().refreshAllActivityTheme();
                                    }
                                }, 100);
                            }
                        })
                        .setNegativeButton(this.getResources().getString(R.string.main_cancle_text), null)
                        .show();


            }
            // Menu item点击后选中，并关闭Drawerlayout
            item.setChecked(true);
            return true;
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            long secondTime = System.currentTimeMillis();
            if(secondTime - firstTime > 2500){
                toast("再按一次退出APP");
                firstTime = System.currentTimeMillis();
                return true;
            }else{
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
