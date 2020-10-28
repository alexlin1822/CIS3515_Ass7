package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.os.Bundle;

import java.net.MalformedURLException;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,PageViewerFragment.OnPageChangeURLListener
{
    private PageControlFragment frControl;
    private PageViewerFragment frViewer;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        FragmentManager fm = getSupportFragmentManager();
        frControl = (PageControlFragment) fm.findFragmentById(R.id.frmControl);
        frViewer = (PageViewerFragment) fm.findFragmentById(R.id.frmViewer);

        if(frControl == null){
            frControl = PageControlFragment.newInstance();
            fm.beginTransaction().add(R.id.frmControl,frControl).commit();
            frControl.addButtonClickListener(this);
        }
        if(frViewer == null){
            frViewer = PageViewerFragment.newInstance("");
            fm.beginTransaction().add(R.id.frmViewer,frViewer).commit();
            frViewer.addOnPageChangeURListener(this);
        }
    }

    //ControlFragment Button Click
    @Override
    public void OnClick(int btnID){
        if (btnID==R.id.btnGo) {
            LoadWeb(frControl.getURL());
        }else{
            frViewer.BackNext(btnID);
        }
    }

    //try to load the page
    public void LoadWeb(String sURL) {
        try {
            frViewer.LoadPageFromURL(sURL);
        }
        catch(MalformedURLException q) {
            q.printStackTrace();
        }
    }

    //ViewerFragment OnPageChange
    @Override
    public void OnPageChangeURL(String sURL) {
        frControl.setURL(sURL);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(outState);
    }



}