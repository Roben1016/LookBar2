package com.roshine.lookbar.book.contract;

import com.roshine.lookbar.book.bean.BookBean;
import com.roshine.lookbar.book.bean.Books;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Roshine
 * @date 2018/10/9 10:43
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface BookService {
    /**
     * 根据tag获取图书
     * @param tag
     * @return
     */

    @GET("v2/book/search")
    Flowable<BookBean> getBookByTag(@Query("tag")String tag, @Query("start")int start, @Query("count")int count);

    /**
     * 获取书籍详情
     * @param id
     * @return
     */
    @GET("v2/book/{id}")
    Flowable<Books> getBookDetail(@Path("id") String id);
}
