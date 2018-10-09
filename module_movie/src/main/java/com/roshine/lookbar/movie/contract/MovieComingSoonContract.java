package com.roshine.lookbar.movie.contract;

import com.roshine.lookbar.commonlib.base.IBaseView;
import com.roshine.lookbar.movie.bean.MovieBean;

/**
 * @author Roshine
 * @date 2018/10/8 18:32
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MovieComingSoonContract {
    //即将上映电影 契约
    interface IComingSoonPresenter extends IBaseView<MovieBean> {
        void getComingSoonMovie(int start,int count);
    }
    interface IComingSoonView extends IBaseView<MovieBean>{

    }
}
