package com.roshine.lookbar.book.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lookbar.book.R;
import com.roshine.lookbar.book.bean.Books;
import com.roshine.lookbar.book.contract.BookDetailContract;
import com.roshine.lookbar.book.presenter.BookDetailPresenter;
import com.roshine.lookbar.commonlib.base.MvpBaseActivity;
import com.roshine.lookbar.commonlib.base.WebViewActivity;
import com.roshine.lookbar.commonlib.imageloader.ImageLoaderManager;
import com.roshine.lookbar.commonlib.utils.DisplayUtil;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;
import com.roshine.lookbar.commonlib.wight.recyclerview.FullyLinearLayoutManager;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.commonlib.wight.recyclerview.normal.NormalRecyclertView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/26 11:45
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class BookDetailActivity extends MvpBaseActivity<BookDetailContract.IBookDetailView, BookDetailPresenter> implements BookDetailContract.IBookDetailView, View.OnClickListener {
    ImageView ivBack;
    TextView tvTitle;
    Toolbar tbBaseToolBar;
    ImageView ivBookPic;
    TextView tvBookName;
    TextView tvBookYear;
    TextView tvBookPublisher;
    TextView tvBookScore;
    TextView tvBookIntroduce;
    TextView tvAuthorIntroduce;
    NormalRecyclertView recyclerview;
    Button btnGetMore;
    TextView tvBookAuthor;
    TextView tvCatalogNull;
    NestedScrollView scrollView;
    private BookDetailPresenter presenter;
    private String id = "";
    private List<String> listData = new ArrayList<>();
    private SimpleRecyclertViewAdater<String> mAdapter;

    private Books currentData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            id = intent.getStringExtra("id");
            loadDatas();
        }
    }

    private void loadDatas() {
        showPageLoading();
        presenter.getBookDetail(id);
    }

    @Override
    protected void onReload() {
        super.onReload();
        loadDatas();
    }

    @Override
    public BookDetailPresenter getPresenter() {
        presenter = new BookDetailPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.book_activity_book_detail;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ivBack = findViewById(com.roshine.lookbar.commonlib.R.id.iv_back);
        tvTitle = findViewById(com.roshine.lookbar.commonlib.R.id.tv_title);
        tbBaseToolBar = findViewById(com.roshine.lookbar.commonlib.R.id.tb_base_tool_bar);
        ivBookPic = findViewById(R.id.iv_book_pic);
        tvBookName = findViewById(R.id.tv_book_name);
        tvBookYear = findViewById(R.id.tv_book_year);
        tvBookPublisher = findViewById(R.id.tv_book_publisher);
        tvBookScore = findViewById(R.id.tv_book_score);
        tvBookIntroduce = findViewById(R.id.tv_book_introduce);
        tvAuthorIntroduce = findViewById(R.id.tv_author_introduce);
        recyclerview = findViewById(R.id.recyclerview);
        btnGetMore = findViewById(R.id.btn_get_more);
        tvBookAuthor = findViewById(R.id.tv_book_author);
        tvCatalogNull = findViewById(R.id.tv_catalog_null);
        scrollView = findViewById(R.id.scrollview);
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        btnGetMore.setOnClickListener(this);
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        btnGetMore.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        initPageLayout(scrollView);
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
        mAdapter = new SimpleRecyclertViewAdater<String>(this, listData, R.layout.book_item_book_detail_catalog) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, String itemBean, int position) {
                holder.setText(R.id.tv_item_book_catalog, itemBean);
            }
        };
        DividerItemDecoration decoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        recyclerview.addItemDecoration(decoration);
        recyclerview.setAdapter(mAdapter);
    }

    @Nullable
    @Override
    public void loadSuccess(Books datas) {
        if (datas != null) {
            hidePageLoading();
            currentData = datas;
            setDatas(datas);
        } else {
            showPageEmpty();
        }

    }

    private void setDatas(Books datas) {
        tvTitle.setText(datas.getTitle());
        tvAuthorIntroduce.setText(datas.getAuthor_intro());
        tvBookIntroduce.setText(datas.getSummary());
        tvBookName.setText(datas.getTitle());
        tvBookPublisher.setText(datas.getPublisher());
        tvBookScore.setText(String.format(getResources().getString(R.string.book_score_text), datas.getRating().getAverage()));
        tvBookYear.setText(String.format(getResources().getString(R.string.book_publish_year), datas.getPubdate()));

        ViewGroup.LayoutParams params = ivBookPic.getLayoutParams();
        int ivHeight = DisplayUtil.dp2px(this, 150);
        params.height = ivHeight;
        double ivWidth = ivHeight * 300.0 / 444.0;
        params.width = (int) ivWidth;
        ivBookPic.setLayoutParams(params);
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(ivBookPic, datas.getImages().getLarge()));

        initAuthors(datas);

        initCatalogs(datas);
    }

    private void initAuthors(Books datas) {
        List<String> author = datas.getAuthor();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < author.size(); i++) {
            stringBuffer.append(author.get(i)).append("、");
        }
        tvBookAuthor.setText(stringBuffer.length() >= 1 ? stringBuffer.substring(0, stringBuffer.length() - 1) : stringBuffer.toString());
    }

    private void initCatalogs(Books datas) {
        String catalog = datas.getCatalog();
        if(catalog != null && catalog.length() > 0){
            tvCatalogNull.setVisibility(View.GONE);
            recyclerview.setVisibility(View.VISIBLE);
            if(catalog.contains("\r\n")){
                String[] split = catalog.split("\r\n");
                for (int i = 0; i < split.length; i++) {
                    listData.add(split[i]);
                }
            } else if(catalog.contains("\n")){
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
        }else{
            tvCatalogNull.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFail(String message) {
        showPageError();
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

