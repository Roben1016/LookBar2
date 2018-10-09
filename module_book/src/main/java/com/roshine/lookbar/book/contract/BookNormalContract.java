package com.roshine.lookbar.book.contract;

import com.roshine.lookbar.book.bean.BookBean;
import com.roshine.lookbar.commonlib.base.IBaseView;

/**
 * @author Roshine
 * @date 2018/10/9 10:35
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface BookNormalContract {
    //图书广场 契约
    interface IBookNormalPresenter extends IBaseView<BookBean>{
        void getBooks(String tag,int start,int count);
    }
    interface IBookNormalView extends IBaseView<BookBean> {
    }
}
