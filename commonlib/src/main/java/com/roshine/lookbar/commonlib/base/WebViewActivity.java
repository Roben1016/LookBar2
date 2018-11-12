package com.roshine.lookbar.commonlib.base;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lookbar.commonlib.R;
import com.roshine.lookbar.commonlib.utils.StatusBarUtil;
import com.roshine.lookbar.commonlib.utils.ThemeColorUtil;


//首页点击轮播图 加载网页
public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    ImageView ivBack;
    TextView tvTitle;
    Toolbar tbBaseToolBar;
    WebView wvSite;
    private String url;
    private String title = "";
    private boolean hasTitle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorBar(this,getResources().getColor(ThemeColorUtil.getThemeColor()));
        getBundle();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.common_activity_webview;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tbBaseToolBar = findViewById(R.id.tb_base_tool_bar);
        wvSite = findViewById(R.id.webview);
        ivBack.setOnClickListener(this);
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
    }

    private void getBundle() {
        if (getIntent().hasExtra("url")) {
            url = getIntent().getExtras().getString("url");
            if (!url.contains("http://") && !url.contains("https://")) {
                url = "http://" + url;
            }
            if (getIntent().hasExtra("title")) {
                hasTitle = true;
                title = getIntent().getStringExtra("title");
            }
            initData();
        }
    }

    public void initData() {
        wvSite.getSettings().setJavaScriptEnabled(true);
        wvSite.getSettings().setDomStorageEnabled(true);
        wvSite.loadUrl(url);
        wvSite.getSettings().setUseWideViewPort(true);//设定支持viewport
        wvSite.getSettings().setLoadWithOverviewMode(true);
        wvSite.getSettings().setSupportZoom(true);
        wvSite.getSettings().setBuiltInZoomControls(true);
        wvSite.setDownloadListener(new MyWebViewDownLoadListener());
        wvSite.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
//                try {
                if (tvTitle != null) {
                    tvTitle.setText("页面加载中 ... " + progress + "%");
                    setProgress(progress * 100);
                    if (progress == 100 && tvTitle != null) {
                        if (!hasTitle) {
                            if (view.getTitle() != null) {
                                try {
                                    title = view.getTitle();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    title = WebViewActivity.this.getResources().getString(R.string.common_app_name);
                                }
                            }
                        }
                        tvTitle.setText(title);
                    }
                }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
        wvSite.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                // Otherwise allow the OS to handle things like tel, mailto, etc.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
//		        设置点击网页里面的链接还是在当前的webview里跳转  
//		        view.loadUrl(url);
                return true;//返回值 true表示外部操作  false表示webview内部操作
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //设置webview处理https请求
                handler.proceed();
            }

            public void onReceivedError(WebView view,
                                        int errorCode, String description, String failingUrl) {
                //加载页面报错时的处理
                toast("网页加载出错");
            }
        });
//        setZoomControlGone(wvSite);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            finish();
        }
    }

    private class MyWebViewDownLoadListener implements DownloadListener {
        //添加监听事件即可
        public void onDownloadStart(String url, String userAgent, String contentDisposition,
                                    String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();//
        }
        return super.onKeyDown(keyCode, event);
    }

    //实现放大缩小控件隐藏
//    public void setZoomControlGone(View view) {
//        Class classType;
//        Field field;
//        try {
//            classType = WebView.class;
//            field = classType.getDeclaredField("mZoomButtonsController");
//            field.setAccessible(true);
//            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view);
//            mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
//            try {
//                field.set(view, mZoomButtonsController);
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onDestroy() {
        if (wvSite != null && wvSite.getParent() != null) {
            try {
                ((ViewGroup) wvSite.getParent()).removeView(wvSite);
                wvSite.destroy();
                wvSite = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

}
