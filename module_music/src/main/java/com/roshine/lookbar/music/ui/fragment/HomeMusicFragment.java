package com.roshine.lookbar.music.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.roshine.lookbar.commonlib.base.BasePageFragment;
import com.roshine.lookbar.commonlib.base.TabFragmentAdapter;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;
import com.roshine.lookbar.music.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/24 17:00
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
@Route(path = "/music/music_home_fragment")
public class HomeMusicFragment extends BasePageFragment {

    TabLayout tablayout;
    ViewPager viewpager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.music_fragment_home_movie;
    }

    @Override
    protected void initViewData(View view,Bundle savedInstanceState) {
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        initViewPager();
    }

    private void initViewPager() {
        String[] tabTitle = getActivity().getResources().getStringArray(R.array.music_tab);
        for (int i = 0; i < tabTitle.length; i++) {
            titles.add(tabTitle[i]);
            fragments.add(MusicNormalFragment.newInstanse(titles.get(i)));
        }
        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager(),fragments,titles);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(2);

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setTabMode(TabLayout.MODE_FIXED);
        tablayout.setSelectedTabIndicatorColor(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
//        tablayout.setTabTextColors(getResources().getColor(R.color.text_gray_6),ThemeUtils.getThemeColor());
        tablayout.setTabTextColors(getResources().getColor(com.roshine.lookbar.commonlib.R.color.common_gray_lighter),getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        tablayout.setupWithViewPager(viewpager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
        tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onPageStart() {

    }

    @Override
    public void onPageEnd() {

    }

    @Override
    public void loadNetData() {

    }

    public static Fragment newInstance() {
        return new HomeMusicFragment();
    }
}
