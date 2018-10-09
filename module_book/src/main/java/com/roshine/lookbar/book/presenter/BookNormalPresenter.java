package com.roshine.lookbar.book.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.book.bean.BookBean;
import com.roshine.lookbar.book.contract.BookNormalContract;
import com.roshine.lookbar.book.contract.BookService;
import com.roshine.lookbar.commonlib.base.IBasePresenter;
import com.roshine.lookbar.commonlib.http.RetrofitClient;
import com.roshine.lookbar.commonlib.http.RetrofitHelper;
import com.roshine.lookbar.commonlib.http.RxSubUtil;

/**
 * @author Roshine
 * @date 2017/8/25 22:51
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class BookNormalPresenter extends IBasePresenter<BookNormalContract.IBookNormalView> implements BookNormalContract.IBookNormalPresenter{


    @Nullable
    @Override
    public void loadSuccess(BookBean datas) {

    }

    @Override
    public void loadFail(String message) {

    }

    @Override
    public void getBooks(String tag, int start, int count) {
        compositeDisposable.add(RetrofitClient.getInstance()
        .getApiService(BookService.class)
        .getBookByTag(tag,start,count)
        .compose(RetrofitHelper.<BookBean>handleResult())
        .subscribeWith(new RxSubUtil<BookBean>(compositeDisposable) {
            @Override
            protected void onSuccess(BookBean bookBean) {
                getView().loadSuccess(bookBean);
            }

            @Override
            protected void onFail(String errorMsg) {
                getView().loadFail(errorMsg);
            }
        }));
    }
}
