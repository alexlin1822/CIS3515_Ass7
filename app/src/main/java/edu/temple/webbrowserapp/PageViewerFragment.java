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
    private View myFragmentView;

    //interface
    private PageViewerFragment.OnPageChangeURLListener listener;

    public void addOnPageChangeURListener(PageViewerFragment.OnPageChangeURLListener listener){
        this.listener = listener;}

    public interface  OnPageChangeURLListener{
        void OnPageChangeURL(String sURL);
    }

    private String sgURL;

    public PageViewerFragment() {
        // Required empty public constructor
    }

    public static PageViewerFragment newInstance(String param1) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putString("sgURL", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            sgURL = getArguments().getString("sgURL");
        }
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //final View myFragmentView =inflater.inflate(R.layout.fragment_page_viewer, container, false);

        if(myFragmentView==null){
            myFragmentView=inflater.inflate(R.layout.fragment_page_viewer, null);
        }

        ViewGroup parent = (ViewGroup) myFragmentView.getParent();
        if (parent != null) {
            parent.removeView(myFragmentView);
        }

        wbMain=(WebView)myFragmentView.findViewById(R.id.wbMain);

        wbMain.addJavascriptInterface(this,"android");
        wbMain.setWebViewClient(webViewClient);

        webSettings=wbMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        if (!sgURL.equals(""))
            wbMain.loadUrl(sgURL);
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
            if (listener!=null){listener.OnPageChangeURL(url);}
        }
    };

    public void LoadPageFromURL(String sURL){
        weburl=sURL;
        if (wbMain!=null)
        wbMain.loadUrl(weburl);
    }

    public void BackNext(int iBtn){
        switch (iBtn){
            case R.id.btnBack:{
                wbMain.goBack();
            }
                break;

            case R.id.btnNext:{
                wbMain.goForward();
            }
                break;
        }

    }




}