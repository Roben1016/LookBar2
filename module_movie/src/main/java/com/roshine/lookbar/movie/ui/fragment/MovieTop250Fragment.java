package com.roshine.lookbar.movie.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.roshine.lookbar.commonlib.base.MvpBaseFragment;
import com.roshine.lookbar.commonlib.imageloader.ImageLoaderManager;
import com.roshine.lookbar.commonlib.utils.DisplayUtil;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lookbar.commonlib.wight.recyclerview.base.ViewHolder;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnLoadMoreListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.interfaces.OnRefreshListener;
import com.roshine.lookbar.commonlib.wight.recyclerview.refreshandload.LSAutoRecyclertView;
import com.roshine.lookbar.commonlib.wight.recyclerview.refreshandload.SwipeRecyclertView;
import com.roshine.lookbar.movie.R;
import com.roshine.lookbar.movie.bean.MovieBean;
import com.roshine.lookbar.movie.bean.Subjects;
import com.roshine.lookbar.movie.contract.MovieTop250Contract;
import com.roshine.lookbar.movie.presenter.MovieTop250Presenter;
import com.roshine.lookbar.movie.ui.activity.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Roshine
 * @date 2017/8/24 14:42
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc Top250 电影列表
 */
public class MovieTop250Fragment extends MvpBaseFragment<MovieTop250Contract.ITop250View,MovieTop250Presenter> implements MovieTop250Contract.ITop250View, OnRefreshListener, OnLoadMoreListener, OnItemClickListener {

    SwipeRecyclertView swipRecyclerView;

    private MovieTop250Presenter movieTop250Presenter;

    private int start = 0;
    private int count = 18;

    private List<Subjects> listData = new ArrayList<>();

    private LSAutoRecyclertView recyclertView;
    private SimpleRecyclertViewAdater<Subjects> mAdapter;
    private int currentLoadIndex;

    @Override
    protected int getLayoutId() {
        return R.layout.movie_fragment_top250_movie;
    }

    @Override
    public MovieTop250Presenter getPresenter() {
        movieTop250Presenter = new MovieTop250Presenter();
        return movieTop250Presenter;
    }

    @Override
    protected void initViewData(View view,Bundle savedInstanceState) {
        swipRecyclerView = findViewById(R.id.swip_recycler_view);
        swipRecyclerView.setColorSchemeColors(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
        swipRecyclerView.setLoadMoreProgressBarDrawbale(getActivity().getResources().getDrawable(ThemeColorUtil.getThemeDrawable()));
        swipRecyclerView.setOnRefreshListener(this);
        swipRecyclerView.setOnloadMoreListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclertView = swipRecyclerView.getRecyclertView();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3, GridLayoutManager.VERTICAL,false);
        recyclertView.setLayoutManager(gridLayoutManager);
        mAdapter = new SimpleRecyclertViewAdater<Subjects>(getActivity(),listData,R.layout.movie_item_top_250_movie_layout) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, Subjects itemBean, int position) {
                holder.setText(R.id.tv_item_movie_rank,String.format(mContext.getResources().getString(R.string.movie_rank_text),position+1));
                holder.setText(R.id.tv_item_movie_name,itemBean.getTitle());

                ImageView imageView = holder.getView(R.id.iv_item_movie_pic);
                ViewGroup.LayoutParams params=imageView.getLayoutParams();
                int ivWidth=(screenWidth- DisplayUtil.dp2px(getActivity(),40))/3;
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
    public void loadNetData() {
        swipRecyclerView.setRefreshing(true);
        getListData(start,count);
    }

    private void getListData(int start,int count) {
        movieTop250Presenter.getTop250Movie(start,count);
    }

    public static MovieTop250Fragment newInstanse() {
        return new MovieTop250Fragment();
    }

    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {
        swipRecyclerView.setRefreshing(false);
        if(datas != null && datas.getSubjects()!= null ){
            if(datas.getSubjects().size() < count){
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
            }else{
                swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_SUC);
            }
            if(start == 0){
                listData.clear();
            }
            listData.addAll(datas.getSubjects());
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }else{
            swipRecyclerView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
        }
    }

    @Override
    public void loadFail(String message) {
//        LogUtil.showD("Roshine","失败："+message);
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

    @Override
    public void OnItemClick(int position, ViewHolder holder) {
        if(listData != null && position < listData.size()){
            String id = listData.get(position).getId();
            Pair<View, String> imagePair = Pair.create(holder.getView(R.id.iv_item_movie_pic), getString(R.string.movie_transition_name_image));
            Pair<View, String> textPair = Pair.create(holder.getView(R.id.tv_item_movie_name), getString(R.string.movie_transition_name_text));
            Pair<View, String> cardPair = Pair.create(holder.getView(R.id.card_view), getString(R.string.movie_transition_name_card));
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair, textPair,cardPair);
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            startActivityWithTransname(MovieDetailActivity.class,bundle,compat);
        }
    }
}
