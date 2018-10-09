package com.roshine.lookbar.book.presenter;

import android.support.annotation.Nullable;

import com.roshine.lookbar.book.bean.Books;
import com.roshine.lookbar.book.contract.BookDetailContract;
import com.roshine.lookbar.book.contract.BookService;
import com.roshine.lookbar.commonlib.base.IBasePresenter;
import com.roshine.lookbar.commonlib.http.RetrofitClient;
import com.roshine.lookbar.commonlib.http.RetrofitHelper;
import com.roshine.lookbar.commonlib.http.RxSubUtil;

/**
 * @author Roshine
 * @date 2017/8/26 11:46
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class BookDetailPresenter extends IBasePresenter<BookDetailContract.IBookDetailView> implements BookDetailContract.IBookDetailPresenter{

    @Override
    public void getBookDetail(String id) {
        compositeDisposable.add(RetrofitClient.getInstance()
                .getApiService(BookService.class)
                .getBookDetail(id)
                .compose(RetrofitHelper.handleResult())
                .subscribeWith(new RxSubUtil<Books>(compositeDisposable) {
                    @Override
                    protected void onSuccess(Books books) {
                        getView().loadSuccess(books);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(Books datas) {

    }

    @Override
    public void loadFail(String message) {

    }
}
