package com.roshine.lookbar.main.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.roshine.lookbar.commonlib.base.BaseActivity;
import com.roshine.lookbar.commonlib.utils.Utils;
import com.roshine.lookbar.main.R;

/**
 * @author Roshine
 * @date 2017/8/28 21:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 关于
 */
public class AboutActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    ImageView ivMeIcon;
    TextView tvMyName;
    TextView tvLike;
//    @BindView(R.id.iv_back)
//    ImageView ivBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
//    @BindView(R.id.rl_tool_bar)
//    RelativeLayout rlToolBar;
    private int mMaxScrollSize;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setColorBar(this,getResources().getColor(ThemeColorUtil.getThemeColor()));
        Utils.KITKAT(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_about;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar_layout);
        ivMeIcon = findViewById(R.id.iv_me_icon);
        tvMyName = findViewById(R.id.tv_my_name);
        tvLike = findViewById(R.id.tv_my_title);

//        tvTitle.setVisibility(View.VISIBLE);
//        ivBack.setVisibility(View.VISIBLE);
//        tvTitle.setText("关于");
//        rlToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
//        rlToolBar.setAlpha(0);
        toolbar.setNavigationIcon(getResources().getDrawable(com.roshine.lookbar.commonres.R.drawable.res_iv_back));
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0) {
            mMaxScrollSize = appBarLayout.getTotalScrollRange();
        }
        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / mMaxScrollSize;
        float alpha = (float) (1 - currentScrollPercentage / 100.0);
        tvMyName.setAlpha(alpha);
        ivMeIcon.setAlpha(alpha);
        tvLike.setAlpha(alpha);
//        collapsingToolbarLayout.setAlpha(alpha);
//        rlToolBar.setAlpha(1 - alpha);
//        LogUtil.showD("Roshine","alpha:"+alpha);
//        attentionNumTv.setAlpha(alpha);
//        detailTv.setAlpha(alpha);
    }
}
