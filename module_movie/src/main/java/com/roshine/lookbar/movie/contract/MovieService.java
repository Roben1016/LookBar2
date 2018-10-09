package com.roshine.lookbar.movie.contract;

import com.roshine.lookbar.movie.bean.MovieBean;
import com.roshine.lookbar.movie.bean.MovieDetailBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Roshine
 * @date 2018/10/8 18:47
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MovieService {
    /**
     * 热映中
     * @return
     */
    @GET("v2/movie/in_theaters")
    Flowable<MovieBean> getLiveMovie();


    /**
     * 即将上映
     * @return
     */
    @GET("/v2/movie/coming_soon")
    Flowable<MovieBean> getComingSoonMovie(@Query("start") int start, @Query("count") int count);

    /**
     * 获取top250
     * @param start
     * @param count
     * @return
     */
    @GET("v2/movie/top250")
    Flowable<MovieBean> getTop250(@Query("start") int start, @Query("count") int count);

    /**
     * 获取电影详情
     * @param id
     * @return
     */
    @GET("v2/movie/subject/{id}")
    Flowable<MovieDetailBean> getMovieDetail(@Path("id") String id);
}
