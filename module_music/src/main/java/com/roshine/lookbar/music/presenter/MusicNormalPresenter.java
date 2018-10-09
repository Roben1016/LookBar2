package com.roshine.lookbar.music.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.commonlib.base.IBasePresenter;
import com.roshine.lookbar.commonlib.http.RetrofitClient;
import com.roshine.lookbar.commonlib.http.RetrofitHelper;
import com.roshine.lookbar.commonlib.http.RxSubUtil;
import com.roshine.lookbar.music.bean.MusicBean;
import com.roshine.lookbar.music.contract.MusicNormalContract;
import com.roshine.lookbar.music.contract.MusicService;

/**
 * @author Roshine
 * @date 2017/8/28 14:35
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MusicNormalPresenter extends IBasePresenter<MusicNormalContract.IMusicNormalView> implements MusicNormalContract.IMusicNormalPresenter{



    @Override
    public void getMusics(String tag, int start, int count) {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService(MusicService.class)
                .getMusicByTag(tag,start,count)
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<MusicBean>(compositeDisposable) {
                    @Override
                    protected void onSuccess(MusicBean musicBean) {
                        getView().loadSuccess(musicBean);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(MusicBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
