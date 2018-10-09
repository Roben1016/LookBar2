package com.roshine.lookbar.music.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lookbar.commonlib.base.MvpBaseActivity;
import com.roshine.lookbar.commonlib.base.WebViewActivity;
import com.roshine.lookbar.commonlib.imageloader.ImageLoaderManager;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;
import com.roshine.lookbar.commonlib.wight.recyclerview.FullyLinearLayoutManager;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.commonlib.wight.recyclerview.normal.NormalRecyclertView;
import com.roshine.lookbar.music.R;
import com.roshine.lookbar.music.bean.Musics;
import com.roshine.lookbar.music.contract.MusicDetailContract;
import com.roshine.lookbar.music.presenter.MusicDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/28 16:39
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MusicDetailActivity extends MvpBaseActivity<MusicDetailContract.IMusicDetailView, MusicDetailPresenter> implements MusicDetailContract.IMusicDetailView, View.OnClickListener {
    ImageView ivBack;
    TextView tvTitle;
    Toolbar tbBaseToolBar;
    ImageView ivMusicPic;
    TextView tvMusicName;
    TextView tvMusicRealName;
    TextView tvMusicAuthor;
    TextView tvMusicPublishDate;
    TextView tvMusicScore;
    TextView tvMusicIntroduce;
    NormalRecyclertView recyclerview;
    TextView tvCatalogNull;
    Button btnGetMore;
    private MusicDetailPresenter presenter;
    private String id = "";
    private SimpleRecyclertViewAdater<String> mAdapter;
    private List<String> listData = new ArrayList<>();
    private Musics currentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundle();
        initData();
    }

    private void getBundle() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("id")) {
                id = intent.getStringExtra("id");
            }
        }
    }

    private void initData() {
        presenter.getMusicDetailById(id);
    }

    @Override
    public MusicDetailPresenter getPresenter() {
        presenter = new MusicDetailPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.music_activity_music_detail;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ivBack = findViewById(com.roshine.lookbar.commonlib.R.id.iv_back);
        tvTitle = findViewById(com.roshine.lookbar.commonlib.R.id.tv_title);
        tbBaseToolBar = findViewById(com.roshine.lookbar.commonlib.R.id.tb_base_tool_bar);

        ivMusicPic = findViewById(R.id.iv_music_pic);
        tvMusicName = findViewById(R.id.tv_music_name);
        tvMusicRealName = findViewById(R.id.tv_music_real_name);
        tvMusicAuthor = findViewById(R.id.tv_music_author);
        tvMusicPublishDate = findViewById(R.id.tv_music_publish_date);
        tvMusicScore = findViewById(R.id.tv_music_score);
        tvMusicIntroduce = findViewById(R.id.tv_music_introduce);
        recyclerview = findViewById(R.id.recyclerview);
        tvCatalogNull = findViewById(R.id.tv_catalog_null);
        btnGetMore = findViewById(R.id.btn_get_more);

        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);

        ivBack.setOnClickListener(this);
        btnGetMore.setOnClickListener(this);
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        btnGetMore.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        initRecyclerView();
    }

    private void initRecyclerView() {
        //防止ScrollView和RecyclerView滑动冲突
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        mAdapter = new SimpleRecyclertViewAdater<String>(this, listData, R.layout.music_item_book_detail_catalog) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, String itemBean, int position) {
                holder.setText(R.id.tv_item_book_catalog, itemBean);
            }
        };
        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerview.addItemDecoration(decoration);
        recyclerview.setAdapter(mAdapter);
    }

    @Nullable
    @Override
    public void loadSuccess(Musics datas) {
        if (datas != null) {
            currentData = datas;
            setDatas(datas);
        }
    }

    private void setDatas(Musics datas) {
        tvTitle.setText(datas.getTitle());
        tvMusicIntroduce.setText(datas.getSummary());
        tvMusicName.setText(datas.getTitle());
        tvMusicPublishDate.setText(datas.getAttrs().getPubdate().get(0) == null ? "" : datas.getAttrs().getPubdate().get(0));
        tvMusicScore.setText(String.format(getResources().getString(R.string.music_score_text), datas.getRating().getAverage()));
        if (datas.getAlt_title() != null && !datas.getAlt_title().equals("")) {
            tvMusicRealName.setVisibility(View.VISIBLE);
            tvMusicRealName.setText(String.format(getResources().getString(R.string.music_real_name_text),datas.getAlt_title()));
        }
        List<String> singer = datas.getAttrs().getSinger();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < singer.size(); i++) {
            stringBuffer.append(singer.get(i)).append("　");
        }
        tvMusicAuthor.setText(stringBuffer.length()>=1?stringBuffer.substring(0,stringBuffer.length()-1):stringBuffer.toString());
//        tvBookYear.setText(String.format(getResources().getString(R.string.book_publish_year), datas.getPubdate()));
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(ivMusicPic, datas.getImage()));

        initCatalogs(datas);
    }

    private void initCatalogs(Musics datas) {
        String catalog = datas.getAttrs().getTracks().get(0);
        if (catalog != null && catalog.length() > 0) {
            tvCatalogNull.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
            if (catalog.contains("\r\n")) {
                String[] split = catalog.split("\r\n");
                for (int i = 0; i < split.length; i++) {
                    listData.add(split[i]);
                }
            } else if (catalog.contains("\n")) {
                String[] split = catalog.split("\n");
                for (int i = 0; i < split.length; i++) {
                    listData.add(split[i]);
                }
            } else {
                listData.add(catalog);
            }
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        } else {
            tvCatalogNull.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFail(String message) {
        toast(message);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == com.roshine.lookbar.commonlib.R.id.iv_back) {
            finishActivity();

        } else if (i == R.id.btn_get_more) {
            if (currentData != null) {
                String url = currentData.getAlt();
                String name = currentData.getTitle();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString("title", name);
                startActivity(WebViewActivity.class, bundle);
            }

        } else {
        }
    }
}
