package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.MalformedURLException;

public class PageViewerFragment extends Fragment {
    //var
    private WebView wbMain;
    private WebSettings webSettings;
    private int FID;

    //interface
    private PageViewerFragment.OnPageChangeURLListener listener;

    public void addOnPageChangeURListener(PageViewerFragment.OnPageChangeURLListener listener){
        this.listener = listener;}

    public interface  OnPageChangeURLListener{
        void OnPageChangeURL(String sURL);
        void OnPageFinish(String sTitle);
    }

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance(int param1) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putInt("FID", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myFragmentView =inflater.inflate(R.layout.fragment_page_viewer, container, false);

        if (getArguments()!=null) {
            FID = getArguments().getInt("FID");
        }


        wbMain=(WebView)myFragmentView.findViewById(R.id.wbMain);

        wbMain.addJavascriptInterface(this,"android");
        wbMain.setWebViewClient(webViewClient);

        webSettings=wbMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);

        if(savedInstanceState != null){
            wbMain.restoreState(savedInstanceState);
        }


        return  myFragmentView;
    }


    private WebViewClient webViewClient=new WebViewClient(){
        //finished
        @Override
        public void onPageFinished(WebView view, String url) {
            if (listener!=null){listener.OnPageFinish(view.getTitle());}
        }

        //Load page
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (listener!=null){listener.OnPageChangeURL(url);}
        }
    };

    public int getFID(){
        return FID;
    }

    public String getWebTitle(){
        String sRtn="";
        if (wbMain!=null)
            sRtn=wbMain.getTitle();
        return sRtn;
    }

    public String getUrl(){
        String sRtn="";
        if (wbMain!=null)
            sRtn=wbMain.getUrl();
        return sRtn;
    }

    public void LoadPageFromURL(String sURL) throws MalformedURLException {
        if (wbMain!=null)
            wbMain.loadUrl(sURL);
    }

    public void BackNext(int iBtn){
        if (iBtn==R.id.btnBack) {
            if (wbMain.canGoBack()) {
                wbMain.goBack();
            }
        }
        else if (iBtn==R.id.btnNext){
            if (wbMain.canGoForward()) {
                wbMain.goForward();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        wbMain.saveState(outState);
    }
}