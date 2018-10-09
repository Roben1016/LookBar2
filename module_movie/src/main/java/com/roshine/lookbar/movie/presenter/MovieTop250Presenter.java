package com.roshine.lookbar.movie.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.commonlib.base.IBasePresenter;
import com.roshine.lookbar.commonlib.http.RetrofitClient;
import com.roshine.lookbar.commonlib.http.RetrofitHelper;
import com.roshine.lookbar.commonlib.http.RxSubUtil;
import com.roshine.lookbar.movie.bean.MovieBean;
import com.roshine.lookbar.movie.contract.MovieService;
import com.roshine.lookbar.movie.contract.MovieTop250Contract;

/**
 * @author Roshine
 * @date 2017/8/24 15:26
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieTop250Presenter extends IBasePresenter<MovieTop250Contract.ITop250View> implements MovieTop250Contract.ITop250Presenter {

    public MovieTop250Presenter() {
    }

    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {
        getView().loadSuccess(datas);
    }

    @Override
    public void loadFail(String message) {
        getView().loadFail(message);
    }

    @Override
    public void getTop250Movie(int start, int count) {
        compositeDisposable.add(
                RetrofitClient
                        .getInstance()
                        .getApiService(MovieService.class)
                        .getTop250(start, count)
                        .compose(RetrofitHelper.<MovieBean>handleResult())
                        .subscribeWith(new RxSubUtil<MovieBean>(compositeDisposable) {
                            @Override
                            protected void onSuccess(MovieBean movieTop250Bean) {
                                loadSuccess(movieTop250Bean);
                            }

                            @Override
                            protected void onFail(String errorMsg) {
                                loadFail(errorMsg);
                            }
                        }));
    }
}
