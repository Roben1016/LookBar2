package com.roshine.lookbar.main.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.roshine.lookbar.commonlib.base.BaseFragment;
import com.roshine.lookbar.main.R;

/**
 * @author Roshine
 * @date 2018/10/7 18:51
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class NormalFragment extends BaseFragment {
    private TextView tvContent;
    private String contentType;

    public static Fragment newInstance(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("contentType",content);
        NormalFragment fragment = new NormalFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_normal;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        contentType = arguments.getString("contentType");
    }

    @Override
    protected void initViewData(View view,Bundle savedInstanceState) {
        tvContent = findViewById(R.id.tv_content);
        tvContent.setText(contentType);
    }
}
