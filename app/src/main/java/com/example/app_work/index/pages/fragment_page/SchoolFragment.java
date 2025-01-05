package com.example.app_work.index.pages.fragment_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_work.R;

public class SchoolFragment extends Fragment {
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_school, container, false);
        webView = rootView.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用 JavaScript
        webSettings.setSupportZoom(true); // 启用缩放
        webSettings.setBuiltInZoomControls(true); // 显示缩放按钮
        webSettings.setDisplayZoomControls(false); // 隐藏默认缩放控件

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.tute.edu.cn/xxgk/xxjj.htm");
        return rootView;
    }
}
