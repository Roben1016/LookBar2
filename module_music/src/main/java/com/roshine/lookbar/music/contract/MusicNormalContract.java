package com.roshine.lookbar.music.contract;

import com.roshine.lookbar.commonlib.base.IBaseView;
import com.roshine.lookbar.music.bean.MusicBean;

/**
 * @author Roshine
 * @date 2018/10/9 11:19
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MusicNormalContract {
    //音乐列表 契约
    interface IMusicNormalPresenter extends IBaseView<MusicBean> {
        void getMusics(String tag,int start,int count);
    }
    interface IMusicNormalView extends IBaseView<MusicBean>{}
}
