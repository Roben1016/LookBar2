package com.roshine.lookbar.book.contract;

import com.roshine.lookbar.book.bean.Books;
import com.roshine.lookbar.commonlib.base.IBaseView;

/**
 * @author Roshine
 * @date 2018/10/9 10:45
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface BookDetailContract {
    //书籍详情 契约
    interface IBookDetailPresenter extends IBaseView<Books>{
        void getBookDetail(String id);
    }
    interface IBookDetailView extends IBaseView<Books> {}
}
