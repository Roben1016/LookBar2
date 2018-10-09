package com.roshine.lookbar.book.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.roshine.lookbar.book.R;
import com.roshine.lookbar.book.ui.activity.BookTagActivity;
import com.roshine.lookbar.book.ui.adapter.TabFragmentAdapterV2;
import com.roshine.lookbar.commonlib.base.BasePageFragment;
import com.roshine.lookbar.commonlib.utils.Constants;
import com.roshine.lookbar.commonlib.utils.SPUtil;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/24 16:59
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
@Route(path = "/book/book_home_fragment")
public class HomeBookFragmen extends BasePageFragment implements View.OnClickListener {

    private static final int GET_TAG_REQUEST_CODE = 100;
    TabLayout tablayout;
    ViewPager viewpager;
    FloatingActionButton floatingActionButton;

    private List<String> listContainerTags = new ArrayList<>();
    private List<String> listUnContainerTags = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    private TabFragmentAdapterV2 adapter;
    private TextView textView;
    private AlertDialog show;

    @Override
    protected int getLayoutId() {
        return R.layout.book_fragment_home_book;
    }

    @Override
    protected void initViewData(View view, Bundle savedInstanceState) {
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.setBackgroundTintList(
                getActivity().getResources().getColorStateList(
                        ThemeColorUtil.getNavigationViewItemColor()));
        checkDatas();
    }

    private void checkDatas() {
        if ((int) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, 0) == 0) {
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer1 = new StringBuffer();
            String[] defaultContainerTags = Constants.Tag.DEFAULT_CONTAINER_TAGS;
            String[] defaultUncontainerTags = Constants.Tag.DEFAULT_UNCONTAINER_TAGS;
            for (int i = 0; i < defaultContainerTags.length; i++) {
                listContainerTags.add(defaultContainerTags[i]);
                stringBuffer.append(defaultContainerTags[i]).append(",");
            }
            for (int i = 0; i < defaultUncontainerTags.length; i++) {
                listUnContainerTags.add(defaultUncontainerTags[i]);
                stringBuffer1.append(defaultUncontainerTags[i]).append(",");
            }
            SPUtil.setParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, stringBuffer.subSequence(0, stringBuffer.length() - 1));
            SPUtil.setParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS, stringBuffer1.subSequence(0, stringBuffer1.length() - 1));
            SPUtil.setParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, defaultContainerTags.length);
            SPUtil.setParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS_COUNT, defaultUncontainerTags.length);
        } else if ((int) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, 0) == 1) {
            String param = (String) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, "");
            listContainerTags.add(param);
        } else {
            String params = (String) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, "");
            String[] strings = params.split(",");
            for (int i = 0; i < strings.length; i++) {
                listContainerTags.add(strings[i]);
            }
        }
        initTabLayout();
    }

    private void initTabLayout() {
        adapter = new TabFragmentAdapterV2(getChildFragmentManager(), listContainerTags);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(listContainerTags.size());

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout.setSelectedTabIndicatorColor(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        tablayout.setTabTextColors(getResources().getColor(com.roshine.lookbar.commonlib.R.color.common_gray_lighter), getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        tablayout.setupWithViewPager(viewpager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
//        tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_TAG_REQUEST_CODE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (data != null) {
                    if (listContainerTags != null && fragments != null) {
                        listContainerTags.clear();
                        fragments.clear();
                        listContainerTags.addAll((List<String>) data.getSerializableExtra("containerTags"));
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    private void initFragments() {
        for (int i = 0; i < listContainerTags.size(); i++) {
            fragments.add(BookNormalFragment.newInstanse(listContainerTags.get(i)));
        }
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
        return new HomeBookFragmen();
    }

//    @OnClick(R.id.floatingActionButton)
//    public void onViewClicked() {
//        startActivityForResult(BookTagActivity.class,null,GET_TAG_REQUEST_CODE);
////        startActivity(TestAidlActivity.class);
//
////        if (show == null) {
////            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogThemeV7);
////            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_seekbar, null);
////            SeekBar seekBar = (SeekBar) inflate.findViewById(R.id.seekbar);
////            seekBar.setOnSeekBarChangeListener(this);
////            textView = (TextView) inflate.findViewById(R.id.tv_progress);
////            builder.setView(inflate);
////            show = builder.create();
////            Window window = show.getWindow();
////            window.setGravity(Gravity.BOTTOM);
////            //为Window设置动画
////            window.setWindowAnimations(R.style.CustomDialog);
////        }
////        //显示Dialog
////        show.show();
////        WindowManager.LayoutParams params = show.getWindow().getAttributes();
////        params.width = 300;
////        params.height = 200;
////        show.getWindow().setAttributes(params);
//
//    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.floatingActionButton) {
            startActivityForResult(BookTagActivity.class, null, GET_TAG_REQUEST_CODE);

        } else {
        }
    }

//    @Override
//    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        textView.setText("进度：" + progress);
//    }
//
//    @Override
//    public void onStartTrackingTouch(SeekBar seekBar) {
//
//    }
//
//    @Override
//    public void onStopTrackingTouch(SeekBar seekBar) {
//
//    }
}
