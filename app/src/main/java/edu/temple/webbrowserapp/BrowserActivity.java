package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,
            BrowserControlFragment.OnNewButtonClickListener,
            PageListFragment.OnItemSelectedListener,
            PagerFragment.OnChangeListener
{

    //private PageViewerFragment frViewer;
    //////////////////////// New
    private PageControlFragment frPageControl;
    private BrowserControlFragment frBrowserCtrl;
    private PageListFragment frPageList;
    private PagerFragment frPager;
    private ArrayList<PageViewerFragment> arrViewer=new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        ArrayList<String> arrTest=new ArrayList<>();


        FragmentManager fm = getSupportFragmentManager();
        frPageControl = (PageControlFragment) fm.findFragmentById(R.id.frmPageCtrl);
        frBrowserCtrl=(BrowserControlFragment) fm.findFragmentById(R.id.frmBrowserCtrl);
        frPager=(PagerFragment) fm.findFragmentById(R.id.frmPageDisp);

        if(frPageControl == null){
            frPageControl = PageControlFragment.newInstance();
            fm.beginTransaction().add(R.id.frmPageCtrl,frPageControl).commit();
            frPageControl.addButtonClickListener(this);
        }

        if(frBrowserCtrl == null){
            frBrowserCtrl = BrowserControlFragment.newInstance();
            fm.beginTransaction().add(R.id.frmBrowserCtrl,frBrowserCtrl).commit();
            frBrowserCtrl.addNewButtonListener(this);
        }

        if(frPager == null){
            frPager = PagerFragment.newInstance();
            fm.beginTransaction().add(R.id.frmPageDisp,frPager).commit();
            frPager.addOnChangeListener(this);
        }


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //portrait


        } else {
            //landscape
            frPageList=(PageListFragment) fm.findFragmentById(R.id.frmPageList);

            if(frPageList == null){
                frPageList = PageListFragment.newInstance(arrTest);
                fm.beginTransaction().add(R.id.frmPageList,frPageList).commit();
                frPageList.addSelectListener(this);
            }
        }
    }

    //ControlFragment Button Click
    @Override
    public void OnClick(int btnID){
         //click go
        if (btnID==R.id.btnGo) {
            frPager.LoadPageFromURL(frPageControl.getURL());
        }else{
            frPager.BackNext(btnID);
        }
    }

    @Override
    public void OnPagerPageChangeURL(String sURL) {
        frPageControl.setURL(sURL);
    }

    @Override
    public void OnPagerPageFinish(String sTitle) {
        //Toast.makeText(getApplicationContext(),"OnPageFinish",Toast.LENGTH_LONG);
        getSupportActionBar().setTitle(sTitle);
        frPager.setCurrentWebTitle(sTitle);
        if (frPageList!=null) {
            frPageList.UpdateList(frPager.getWebTitleList());
        }
    }

    @Override
    public void OnNewButtonClick() {
        frPager.AddFragment();
    }

    @Override
    public void onItemSelected(int iID) {
        frPager.setCurrentFragment(iID);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putAll(outState);
    }


}