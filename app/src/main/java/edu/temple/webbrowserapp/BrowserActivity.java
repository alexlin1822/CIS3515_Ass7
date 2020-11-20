package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,
            BrowserControlFragment.OnNewButtonClickListener,
            PageListFragment.OnItemSelectedListener,
            PagerFragment.OnChangeListener
{
    ////////
    private FragmentManager fm;
    private PageControlFragment frPageControl;
    private BrowserControlFragment frBrowserCtrl;
    private PageListFragment frPageList;
    private PagerFragment frPager;
    private int igCurPagerID;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            igCurPagerID=savedInstanceState.getInt("igCurPagerID",0);
        }
        else{
            igCurPagerID=0;
        }

        setContentView(R.layout.activity_browser);
        fm = getSupportFragmentManager();

        Fragment tmpFragment;

        //BrowserCtrl -> Add New page Button
        if ((tmpFragment = fm.findFragmentById(R.id.frmBrowserCtrl)) instanceof BrowserControlFragment)
            frBrowserCtrl = (BrowserControlFragment) tmpFragment;
        else {
            frBrowserCtrl = new BrowserControlFragment();
            fm.beginTransaction()
                    .add(R.id.frmBrowserCtrl, frBrowserCtrl)
                    .commit();
        }
        frBrowserCtrl.addNewButtonListener(this);

        //Url text box, go, back , next Button
        if ((tmpFragment = fm.findFragmentById(R.id.frmPageCtrl)) instanceof PageControlFragment)
            frPageControl = (PageControlFragment) tmpFragment;
        else {
            frPageControl = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.frmPageCtrl, frPageControl)
                    .commit();
        }
        frPageControl.addButtonClickListener(this);

        //ViewPager and all webpager inside.
        if ((tmpFragment = fm.findFragmentById(R.id.frmViewPager)) instanceof PagerFragment)
            frPager = (PagerFragment) tmpFragment;
        else {
            frPager = new PagerFragment();
            fm.beginTransaction()
                    .add(R.id.frmViewPager, frPager)
                    .commit();

        }
        frPager.addOnChangeListener(this);

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            //landscape
            if ((tmpFragment = fm.findFragmentById(R.id.frmPageList)) instanceof PageListFragment) {
                frPageList = (PageListFragment) tmpFragment;
            }
            else {

                frPageList = PageListFragment.newInstance(frPager.getWebTitleList());
                fm.beginTransaction()
                        .add(R.id.frmPageList, frPageList)
                        .commit();
            }
            frPageList.addSelectListener(this);
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
    public void OnPagerPageChangeURL(int position, String sURL) {
        frPageControl.setURL(sURL);
    }

    @Override
    public void OnPagerPageFinish(int position,String sTitle) {
        if (position==frPager.getCurItemPosition()){
            getSupportActionBar().setTitle(frPager.getCurItemTitle());
            frPageControl.setURL(frPager.getCurItemURL());
        }
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            frPageList.UpdateList(frPager.getWebTitleList());
        }
    }

    @Override
    public void OnPagerChanged(int position,String sTitle,String sURL){
        igCurPagerID=frPager.getCurItemPosition();
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            frPageList.UpdateList(frPager.getWebTitleList());
        }
        frPageControl.setURL(frPager.getCurItemURL());
        getSupportActionBar().setTitle(frPager.getCurItemTitle());
    }

    //Add a new web page
    @Override
    public void OnNewButtonClick() {
        getSupportActionBar().setTitle("");
        frPager.AddFragment();
    }

    @Override
    public void onItemSelected(int iID) {
        frPager.setCurrentFragment(iID);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("igCurPagerID",igCurPagerID);
    }
}