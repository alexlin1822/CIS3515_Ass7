package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,PageViewerFragment.OnPageChangeURLListener
{
    private PageControlFragment frControl;
    private PageViewerFragment frViewer;
    private FragmentManager fm;
    private String sgURL,myStat;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        if (savedInstanceState!=null) {
            myStat = savedInstanceState.getString("myStat");
            sgURL=savedInstanceState.getString("CurrentURL");
        }
        else {
            myStat="0";
            sgURL="";
        }

        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        frControl = PageControlFragment.newInstance();
        frViewer = PageViewerFragment.newInstance(sgURL);

        if (myStat.equals("0")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frmControl, frControl, "frControl")
                    .add(R.id.frmViewer, frViewer, "frViewer")
                    .commit();
        }
        else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frmControl, frControl, "frControl")
                    .replace(R.id.frmViewer, frViewer, "frViewer")
                    .commit();
        }
        frControl.addButtonClickListener(this);
        frViewer.addOnPageChangeURListener(this);
        fragmentTransaction.commit();

    }

    //ControlFragment Button Click
    @Override
    public void OnClick(int btnID) {
        if (btnID==R.id.btnGo) {
            sgURL = frControl.getURL();
            frViewer.LoadPageFromURL(sgURL);
        }else{
            frViewer.BackNext(btnID);
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
        outState.putString("myStat","1");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sgURL=savedInstanceState.getString("CurrentURL");
        myStat=savedInstanceState.getString("myStat");
    }

}