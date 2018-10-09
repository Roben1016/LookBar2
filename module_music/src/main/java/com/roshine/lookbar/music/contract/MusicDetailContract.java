package com.roshine.lookbar.music.contract;

import com.roshine.lookbar.commonlib.base.IBaseView;
import com.roshine.lookbar.music.bean.Musics;

/**
 * @author Roshine
 * @date 2018/10/9 11:20
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public interface MusicDetailContract {
    //音乐详情 契约
    interface IMusicDetailPresenter extends IBaseView<Musics>{
        void getMusicDetailById(String id);
    }
    interface IMusicDetailView extends IBaseView<Musics> {}
}
