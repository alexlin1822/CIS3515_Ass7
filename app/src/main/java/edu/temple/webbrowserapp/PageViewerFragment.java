package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PageViewerFragment extends Fragment {
    private WebView wbMain;
    private WebSettings webSettings;
    private String weburl;

    //interface
    private PageViewerFragment.OnPageChangeURLListener listener;

    public void addOnPageChangeURListener(PageViewerFragment.OnPageChangeURLListener listener){
        this.listener = listener;}

    public interface  OnPageChangeURLListener{
        void OnPageChangeURL(String sURL);
    }

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance() {
        return new PageViewerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myFragmentView =inflater.inflate(R.layout.fragment_page_viewer, container, false);

        weburl="https://www.google.com";
        wbMain=(WebView)myFragmentView.findViewById(R.id.wbMain);
        wbMain.loadUrl(weburl);
        wbMain.addJavascriptInterface(this,"android");
        wbMain.setWebViewClient(webViewClient);

        webSettings=wbMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        return  myFragmentView;
    }

    private WebViewClient webViewClient=new WebViewClient(){
        //finished
        @Override
        public void onPageFinished(WebView view, String url) {

        }

        //Load page
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (listener!=null){
                listener.OnPageChangeURL(url);}
        }

//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            Log.i("ansen","拦截url:"+url);
//            if(url.equals("http://www.google.com/")){
//                //Toast.makeText(MainActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
//                return true;//表示我已经处理过了
//            }
//            return super.shouldOverrideUrlLoading(view, url);
//        }

    };


}