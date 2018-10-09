package com.roshine.lookbar.movie.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.commonlib.base.IBasePresenter;
import com.roshine.lookbar.commonlib.http.RetrofitClient;
import com.roshine.lookbar.commonlib.http.RetrofitHelper;
import com.roshine.lookbar.commonlib.http.RxSubUtil;
import com.roshine.lookbar.movie.bean.MovieDetailBean;
import com.roshine.lookbar.movie.contract.MovieDetailContract;
import com.roshine.lookbar.movie.contract.MovieService;

/**
 * @author Roshine
 * @date 2017/8/25 17:17
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieDetailPresenter extends IBasePresenter<MovieDetailContract.IMovieDetailView> implements MovieDetailContract.IMovieDetailPresenter {
    @Override
    public void getMovieDetail(String id) {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService(MovieService.class)
                .getMovieDetail(id)
                .compose(RetrofitHelper.<MovieDetailBean>handleResult())
                .subscribeWith(new RxSubUtil<MovieDetailBean>(compositeDisposable) {
                    @Override
                    protected void onSuccess(MovieDetailBean movieDetailBean) {
                        getView().loadSuccess(movieDetailBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(MovieDetailBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
