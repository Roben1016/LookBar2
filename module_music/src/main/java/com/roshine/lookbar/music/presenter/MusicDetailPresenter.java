package com.roshine.lookbar.music.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.commonlib.base.IBasePresenter;
import com.roshine.lookbar.commonlib.http.RetrofitClient;
import com.roshine.lookbar.commonlib.http.RetrofitHelper;
import com.roshine.lookbar.commonlib.http.RxSubUtil;
import com.roshine.lookbar.music.bean.Musics;
import com.roshine.lookbar.music.contract.MusicDetailContract;
import com.roshine.lookbar.music.contract.MusicService;

/**
 * @author Roshine
 * @date 2017/8/28 16:44
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MusicDetailPresenter extends IBasePresenter<MusicDetailContract.IMusicDetailView> implements MusicDetailContract.IMusicDetailPresenter{
    @Override
    public void getMusicDetailById(String id) {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService(MusicService.class)
                .getMusicDetail(id)
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<Musics>(compositeDisposable) {
                    @Override
                    protected void onSuccess(Musics musics) {
                        getView().loadSuccess(musics);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(Musics datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
