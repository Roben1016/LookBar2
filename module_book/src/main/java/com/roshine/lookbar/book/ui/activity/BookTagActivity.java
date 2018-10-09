package com.roshine.lookbar.book.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roshine.lookbar.book.R;
import com.roshine.lookbar.commonlib.base.BaseToolBarActivity;
import com.roshine.lookbar.commonlib.utils.Constants;
import com.roshine.lookbar.commonlib.utils.SPUtil;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;
import com.roshine.lookbar.commonlib.wight.DragAdapter;
import com.roshine.lookbar.commonlib.wight.DragGrid;
import com.roshine.lookbar.commonlib.wight.OtherAdapter;
import com.roshine.lookbar.commonlib.wight.OtherGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/26 17:15
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 选择标签界面
 *
 * http://blog.csdn.net/vipzjyno1/article/details/25005851
 */
public class BookTagActivity extends BaseToolBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {


    ImageView ivBack;
    TextView tvTitle;
    TextView tvTitleRight;
    Toolbar tbBaseToolBar;
    DragGrid userGridView;
    OtherGridView otherGridView;

    /** 用户栏目对应的适配器，可以拖动 */
    DragAdapter userAdapter;
    /** 其它栏目对应的适配器 */
    OtherAdapter otherAdapter;
    /** 其它栏目列表 */
    List<String> otherChannelList = new ArrayList<>();
    /** 用户栏目列表 */
    List<String> userChannelList = new ArrayList<>();
    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
    boolean isMove = false;

    private int notMovePosition;

    private int forceChannelNum=0;//强制显示的频道数目，用来使频道不可拖动

    /**
     * 默认的用户选择频道列表
     * */
    public static List<String> defaultUserChannels = new ArrayList<>();
    /**
     * 默认的其他频道列表
     * */
    public static List<String> defaultOtherChannels = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.book_activity_book_tag;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ivBack = findViewById(com.roshine.lookbar.commonlib.R.id.iv_back);
        tvTitle = findViewById(com.roshine.lookbar.commonlib.R.id.tv_title);
        tvTitleRight = findViewById(com.roshine.lookbar.commonlib.R.id.tv_title_right);
        tbBaseToolBar = findViewById(com.roshine.lookbar.commonlib.R.id.tb_base_tool_bar);
        userGridView = findViewById(R.id.userGridView);
        otherGridView = findViewById(R.id.otherGridView);

        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitleRight.setVisibility(View.VISIBLE);

        ivBack.setOnClickListener(this);
        tvTitleRight.setOnClickListener(this);
        tvTitleRight.setText(getResources().getString(R.string.book_save_tag_text));
        tvTitle.setText(getResources().getString(R.string.book_set_book_tag));
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));

        userGridView.setForceChannelNum(forceChannelNum);
        userAdapter = new DragAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(this.otherAdapter);
        //设置GRIDVIEW的ITEM的点击监听
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGridTagData();
    }

    private void setGridTagData() {
        if ((int) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, 0) == 0
                && (int)SPUtil.getParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS_COUNT,0) == 0) {
            String[] defaultContainerTags = Constants.Tag.DEFAULT_CONTAINER_TAGS;
            String[] defaultUncontainerTags = Constants.Tag.DEFAULT_UNCONTAINER_TAGS;
            for (int i = 0; i < defaultContainerTags.length; i++) {
                userChannelList.add(defaultContainerTags[i]);
//                stringBuffer.append(defaultContainerTags[i]).append(",");
            }
            for (int i = 0; i < defaultUncontainerTags.length; i++) {
                otherChannelList.add(defaultUncontainerTags[i]);
//                stringBuffer1.append(defaultUncontainerTags[i]).append(",");
            }
        } else {
            String containerTag = (String) SPUtil.getParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, "");
            String[] containerTags = containerTag.split(",");
            for (int i = 0; i < containerTags.length; i++) {
                userChannelList.add(containerTags[i]);
            }
            if((int)SPUtil.getParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS_COUNT,0) == 0){

            }else if((int)SPUtil.getParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS_COUNT,0) == 1){
                otherChannelList.add((String) SPUtil.getParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS,""));
            }else {
                String uncontainerTag = (String) SPUtil.getParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS, "");
                String[] uncontainerTags = uncontainerTag.split(",");
                for (int i = 0; i < uncontainerTags.length; i++) {
                    otherChannelList.add(uncontainerTags[i]);
                }
            }
        }

        if (userAdapter != null) {
            userAdapter.notifyDataSetChanged();
        }
        if(otherAdapter != null){
            otherAdapter.notifyDataSetChanged();
        }
    }

    private void saveTags() {
        List<String> channnelLst = userAdapter.getChannnelLst();
        List<String> otherChannelLst = otherAdapter.getChannnelLst();

        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        for (int i = 0; i < channnelLst.size(); i++) {
            stringBuffer.append(channnelLst.get(i)).append(",");
        }
        for (int i = 0; i < otherChannelLst.size(); i++) {
            stringBuffer1.append(otherChannelLst.get(i)).append(",");
        }
        SPUtil.setParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS, stringBuffer.substring(0, stringBuffer.length() - 1));
        SPUtil.setParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS, stringBuffer1.substring(0, stringBuffer1.length() - 1));
        SPUtil.setParam(Constants.SharedPreferancesKeys.CONTAINER_TAGS_COUNT, channnelLst.size());
        SPUtil.setParam(Constants.SharedPreferancesKeys.UNCONTAINER_TAGS_COUNT, otherChannelLst.size());
        Intent intent = new Intent();
        intent.putExtra("containerTags",(Serializable)channnelLst);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if(isMove){
            return;
        }
        int i = parent.getId();
        if (i == R.id.userGridView) {//position为 0，1 的不可以进行任何操作
//			if (position != 0) {
//                if(forceChannelNum != 0 && position>forceChannelNum-1){
            final ImageView moveImageView = getView(view);
            if (moveImageView != null) {
                TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                final int[] startLocation = new int[2];
                newTextView.getLocationInWindow(startLocation);
                final String channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                otherAdapter.setVisible(false);
                //添加到最后一个
                otherAdapter.addItem(channel);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        try {
                            int[] endLocation = new int[2];
                            //获取终点的坐标
                            otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                            MoveAnim(moveImageView, startLocation, endLocation, channel, userGridView);
                            userAdapter.setRemove(position);
                        } catch (Exception localException) {
                        }
                    }
                }, 50L);
            }
//                }

        } else if (i == R.id.otherGridView) {
            final ImageView moveImageView2 = getView(view);
            if (moveImageView2 != null) {
                TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                final int[] startLocation = new int[2];
                newTextView.getLocationInWindow(startLocation);
                final String channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                userAdapter.setVisible(false);
                //添加到最后一个
                userAdapter.addItem(channel);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        try {
                            int[] endLocation = new int[2];
                            //获取终点的坐标
                            userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                            MoveAnim(moveImageView2, startLocation, endLocation, channel, otherGridView);
                            otherAdapter.setRemove(position);
                        } catch (Exception localException) {
                        }
                    }
                }, 50L);
            }

        } else {
        }
    }

    /**
     * 获取点击的Item的对应View，
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    /**
     * 点击ITEM移动动画
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final String moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                }else{
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == com.roshine.lookbar.commonlib.R.id.iv_back) {
            finishActivity();

        } else if (i == com.roshine.lookbar.commonlib.R.id.tv_title_right) {
            saveTags();

        } else {
        }
    }
}
