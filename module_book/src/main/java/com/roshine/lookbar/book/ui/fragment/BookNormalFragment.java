package com.roshine.lookbar.book.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roshine.lookbar.book.R;
import com.roshine.lookbar.book.bean.BookBean;
import com.roshine.lookbar.book.bean.Books;
import com.roshine.lookbar.book.contract.BookNormalContract;
import com.roshine.lookbar.book.presenter.BookNormalPresenter;
import com.roshine.lookbar.book.ui.activity.BookDetailActivity;
import com.roshine.lookbar.commonlib.base.MvpBaseFragment;
import com.roshine.lookbar.commonlib.imageloader.ImageLoaderManager;
import com.roshine.lookbar.commonlib.utils.DisplayUtil;
import com.roshine.lookbar.commonlib.utils.LogUtil;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnLoadMoreListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnRefreshListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.refreshandload.LSAutoRecyclertView;
import com.roshine.lookbar.commonlib.wight.recyclerview.refreshandload.SwipeRecyclertView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/25 22:37
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class BookNormalFragment extends MvpBaseFragment<BookNormalContract.IBookNormalView,BookNormalPresenter> implements OnItemClickListener,BookNormalContract.IBookNormalView, OnRefreshListener, OnLoadMoreListener {
    SwipeRecyclertView swipRecyclerView;
    private String tag = "";
    private BookNormalPresenter presenter;
    private LSAutoRecyclertView recyclertView;
    private List<Books> listData = new ArrayList<>();
    private SimpleRecyclertViewAdater<Books> mAdapter;
    private int start = 0;
    private int count = 18;
    private int currentLoadIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        tag = bundle.getString("tag");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.book_fragment_book_normal;
    }

    @Override
    protected void initViewData(View view,Bundle savedInstanceState) {
        swipRecyclerView = findViewById(R.id.swip_recycler_view);

        swipRecyclerView.setColorSchemeColors(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        swipRecyclerView.setLoadMoreProgressBarDrawbale(getActivity().getResources().getDrawable(ThemeColorUtil.getThemeDrawable()));
        swipRecyclerView.setOnRefreshListener(this);
        swipRecyclerView.setOnloadMoreListener(this);
        recyclertView = swipRecyclerView.getRecyclertView();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3, GridLayoutManager.VERTICAL,false);
        recyclertView.setLayoutManager(gridLayoutManager);
        mAdapter = new SimpleRecyclertViewAdater<Books>(getActivity(),listData,R.layout.book_item_book_normal_layout) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, Books itemBean, int position) {
                holder.setText(R.id.tv_item_book_score,String.format(mContext.getResources().getString(R.string.book_score_text_v2),itemBean.getRating().getAverage()));
                holder.setText(R.id.tv_item_book_name,itemBean.getTitle());

                ImageView imageView = holder.getView(R.id.iv_item_book_pic);
                ViewGroup.LayoutParams params=imageView.getLayoutParams();
                int ivWidth=(screenWidth- DisplayUtil.dp2px(getActivity(),36))/3;
                params.width=ivWidth;
                double height=(444.0/300.0)*ivWidth;
                params.height=(int)height;
                imageView.setLayoutParams(params);
                ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(imageView,itemBean.getImages().getLarge()));
            }
        };
        recyclertView.setAdapter(mAdapter);
        recyclertView.setOnItemClick(this);
    }

    @Override
    public BookNormalPresenter getPresenter() {
        presenter = new BookNormalPresenter();
        return presenter;
    }

    @Override
    public void onPageStart() {
        LogUtil.showD("Roshine","当前页："+tag);
    }

    @Override
    public void onPageEnd() {

    }

    @Override
    public void loadNetData() {
        swipRecyclerView.setRefreshing(true);
        getListData(start,count);
    }

    private void getListData(int start, int count) {
        presenter.getBooks(tag,start,count);
    }

    public static Fragment newInstanse(String tag) {
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        BookNormalFragment fragment = new BookNormalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void OnItemClick(int position, ViewHolder holder) {
        if(listData != null && position < listData.size()){
            Pair<View, String> imagePair = Pair.create(holder.getView(R.id.iv_item_book_pic), getString(R.string.book_transition_name_image));
            Pair<View, String> textPair = Pair.create(holder.getView(R.id.tv_item_book_name), getString(R.string.book_transition_name_text));
            Pair<View, String> scorePair = Pair.create(holder.getView(R.id.tv_item_book_score), getString(R.string.book_transition_name_score));
            Pair<View, String> cardPair = Pair.create(holder.getView(R.id.card_view), getString(R.string.book_transition_name_card));
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair, textPair,scorePair,cardPair);
            Bundle bundle = new Bundle();
            bundle.putString("id",listData.get(position).getId());
            startActivityWithTransname(BookDetailActivity.class,bundle,compat);
        }

    }

    @Nullable
    @Override
    public void loadSuccess(BookBean datas) {
        swipRecyclerView.setRefreshing(false);
        if(datas != null && datas.getBooks()!= null ){
            if(datas.getBooks().size() < count){
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
            }else{
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_SUC);
            }
            if(start == 0){
                listData.clear();
            }
            listData.addAll(datas.getBooks());
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }else{
            swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
        }
    }

    @Override
    public void loadFail(String message) {
        swipRecyclerView.setRefreshing(false);
        swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_FAIL);
        toast(message);
    }

    @Override
    public void onRefresh() {
        start = 0;
        getListData(start,count);
    }

    @Override
    public void onLoadMore() {
        ++start;
        currentLoadIndex = start;
        getListData(count * start,count);
    }

    @Override
    public void onReLoadMore() {
        getListData(count * currentLoadIndex,count);
    }
}
