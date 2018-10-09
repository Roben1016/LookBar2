package com.roshine.lookbar.movie.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.commonlib.base.IBasePresenter;
import com.roshine.lookbar.commonlib.http.RetrofitClient;
import com.roshine.lookbar.commonlib.http.RetrofitHelper;
import com.roshine.lookbar.commonlib.http.RxSubUtil;
import com.roshine.lookbar.movie.bean.MovieBean;
import com.roshine.lookbar.movie.contract.MovieLivingContract;
import com.roshine.lookbar.movie.contract.MovieService;

/**
 * @author Roshine
 * @date 2017/8/24 21:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MovieLivingPresenter extends IBasePresenter<MovieLivingContract.ILivingMovieView> implements MovieLivingContract.ILivingMoviePresenter {
    @Nullable
    @Override
    public void loadSuccess(MovieBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void getLivingMovie() {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService(MovieService.class)
                .getLiveMovie()
                .compose(RetrofitHelper.<MovieBean>handleResult())
                .subscribeWith(new RxSubUtil<MovieBean>(compositeDisposable) {
                    @Override
                    protected void onSuccess(MovieBean movieBean) {
                        getView().loadSuccess(movieBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }
}
