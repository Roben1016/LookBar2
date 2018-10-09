package debug;

import android.os.Bundle;
import android.widget.TextView;

import com.roshine.lookbar.book.R;
import com.roshine.lookbar.book.ui.fragment.HomeBookFragmen;
import com.roshine.lookbar.commonlib.base.BaseToolBarActivity;

/**
 * @author Roshine
 * @date 2018/10/7 17:59
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class ModuleBookActivity extends BaseToolBarActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.book_module_activity;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ((TextView)findViewById(com.roshine.lookbar.commonlib.R.id.tv_title)).setText(getResources().getString(R.string.book_text));

        getSupportFragmentManager()    //
                .beginTransaction()
                // 此处的R.id.fragment_container是要盛放fragment的父容器
                .add(R.id.fragment_container,HomeBookFragmen.newInstance())
                .commitAllowingStateLoss();
    }
}
