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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        SetViewsAndListener();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_browser);
        SetViewsAndListener();

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

    @Override
    public void OnClick(int btnID) {
        switch (btnID) {
            case R.id.btnGo:Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnBack:Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnNext:Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
                break;

        }

    }

    @Override
    public void OnPageChangeURL(String sURL) {
        frControl.setURL(sURL);
    }

}