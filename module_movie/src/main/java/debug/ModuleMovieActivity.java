package debug;

import android.os.Bundle;
import android.widget.TextView;

import com.roshine.lookbar.commonlib.base.BaseToolBarActivity;
import com.roshine.lookbar.movie.R;
import com.roshine.lookbar.movie.ui.fragment.HomeMovieFragment;

/**
 * @author Roshine
 * @date 2018/10/7 17:50
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class ModuleMovieActivity extends BaseToolBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.movie_module_activity;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ((TextView)findViewById(com.roshine.lookbar.commonlib.R.id.tv_title)).setText(getResources().getString(R.string.movie_text));
        getSupportFragmentManager()    //
                .beginTransaction()
                // 此处的R.id.fragment_container是要盛放fragment的父容器
                .add(R.id.fragment_container,HomeMovieFragment.newInstance())
                .commitAllowingStateLoss();

        }
}
