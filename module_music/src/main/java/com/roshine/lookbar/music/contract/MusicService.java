package com.roshine.lookbar.music.contract;

import com.roshine.lookbar.music.bean.MusicBean;
import com.roshine.lookbar.music.bean.Musics;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Roshine
 * @date 2018/10/9 11:28
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MusicService {
    /**
     * 根据tag获取music
     * @param tag
     * @return
     */

    @GET("v2/music/search")
    Flowable<MusicBean> getMusicByTag(@Query("tag")String tag, @Query("start")int start, @Query("count")int count);

    @GET("v2/music/{id}")
    Flowable<Musics> getMusicDetail(@Path("id") String id);
}
