package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,PageViewerFragment.OnPageChangeURLListener
{
    private PageControlFragment frControl;
    private PageViewerFragment frViewer;
    private FragmentManager fm;
    private String sgURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        SetViewsAndListener();

        if(savedInstanceState!=null){
            sgURL=savedInstanceState.get("CurrentURL").toString();
        }else {
            sgURL = "";
        }
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_browser);
        SetViewsAndListener();
//        if (!sgURL.equals("")){
//            frViewer.LoadPageFromURL(sgURL);
//        }



    }

    public void SetViewsAndListener(){
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        frControl = PageControlFragment.newInstance();
        frViewer = PageViewerFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frmControl,frControl,"frControl")
                .add(R.id.frmViewer,frViewer,"frViewer")
                .commit();
        frControl.addButtonClickListener(this);
        frViewer.addOnPageChangeURListener(this);
        fragmentTransaction.commit();

    }

    //ControlFragment Button Click
    @Override
    public void OnClick(int btnID) {
        switch (btnID) {
            case R.id.btnGo:{
                sgURL=frControl.getURL();
                frViewer.LoadPageFromURL(sgURL);
                break;
                }
            case R.id.btnBack:Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnNext:Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
                break;
        }
    }

    //ViewerFragment OnPageChange
    @Override
    public void OnPageChangeURL(String sURL) {
        sgURL=sURL;
        frControl.setURL(sURL);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("CurrentURL",sgURL);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sgURL=savedInstanceState.getString("CurrentURL");
    }

}