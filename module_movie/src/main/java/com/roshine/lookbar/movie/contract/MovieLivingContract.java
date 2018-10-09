package com.roshine.lookbar.movie.contract;

import com.roshine.lookbar.commonlib.base.IBaseView;
import com.roshine.lookbar.movie.bean.MovieBean;

/**
 * @author Roshine
 * @date 2018/10/8 19:00
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MovieLivingContract {

    //正在热映电影 契约

    interface ILivingMoviePresenter extends IBaseView<MovieBean> {
        void getLivingMovie();
    }
    interface  ILivingMovieView extends IBaseView<MovieBean>{
    }
}
