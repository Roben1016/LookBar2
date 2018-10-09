package com.roshine.lookbar.movie.contract;

import com.roshine.lookbar.commonlib.base.IBaseView;
import com.roshine.lookbar.movie.bean.MovieBean;

/**
 * @author Roshine
 * @date 2018/10/8 19:14
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MovieTop250Contract {
    //获取Top250 电影 契约
    interface ITop250Presenter extends IBaseView<MovieBean> {
        void getTop250Movie(int start, int count);
    }
    interface ITop250View extends IBaseView<MovieBean>{
    }
}
