package com.roshine.lookbar.movie.contract;

import com.roshine.lookbar.commonlib.base.IBaseView;
import com.roshine.lookbar.movie.bean.MovieDetailBean;

/**
 * @author Roshine
 * @date 2018/10/8 19:23
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MovieDetailContract {

    //电影详情 契约
    interface IMovieDetailPresenter extends IBaseView<MovieDetailBean> {
        void getMovieDetail(String id);
    }
    interface IMovieDetailView extends IBaseView<MovieDetailBean>{

    }
}
