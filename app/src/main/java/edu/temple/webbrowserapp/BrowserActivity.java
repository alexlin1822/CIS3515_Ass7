package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,
            BrowserControlFragment.OnNewButtonClickListener,
            PageListFragment.OnItemSelectedListener,
            PagerFragment.OnChangeListener
{
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

        Log.v("AAA","igCurPagerID="+Integer.toString(igCurPagerID));

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

        Log.v("TAG","Load frPager");
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

//        frPager=(PagerFragment) fm.findFragmentById(R.id.frmPageDisp);
//        if(frPager == null){
//            frPager = PagerFragment.newInstance();
//            fm.beginTransaction().add(R.id.frmPageDisp,frPager).commit();
//            frPager.addOnChangeListener(this);
//        }

//        if ((tmpFragment = fm.findFragmentById(R.id.frmPageDisp)) instanceof PagerFragment)
//            frPager = (PagerFragment) tmpFragment;
//        else {
//            frPager = new PagerFragment();
//            fm.beginTransaction()
//                    .add(R.id.frmPageDisp, frPager)
//                    .commit();
//
//        }
//        frPager.addOnChangeListener(this);


        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
            //landscape

            if ((tmpFragment = fm.findFragmentById(R.id.frmPageList)) instanceof PageListFragment) {
//                frPageList = PageListFragment.newInstance(frPager.getWebTitleList());
////                fm.beginTransaction()
////                        .replace(R.id.frmPageList, frPageList)
////                        .commit();
                frPageList = (PageListFragment) tmpFragment;
            }
            else {

                frPageList = PageListFragment.newInstance(frPager.getWebTitleList());
//                arrList.add("test");         //for test
//                frPageList = PageListFragment.newInstance(arrList);         //for test

                fm.beginTransaction()
                        .add(R.id.frmPageList, frPageList)
                        .commit();
            }


//            frPageList = PageListFragment.newInstance(frPager.getWebTitleList());
//            fm.beginTransaction().replace(R.id.frmPageList,frPageList).commit();
            frPageList.addSelectListener(this);
        }


//        if (isgLandscape) {
//            Toast.makeText(getApplicationContext(), "start,is Land", Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(getApplicationContext(), "start, is P", Toast.LENGTH_LONG).show();
//        }
/*
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

        isLandscape=(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT);

        if (isLandscape){
            //landscape
           // Toast.makeText(getApplicationContext(),"isLandscape",Toast.LENGTH_LONG).show();

//            frPageList=(PageListFragment) fm.findFragmentById(R.id.frmPageList);
//
//
//            if(frPageList == null){
//                frPageList = PageListFragment.newInstance(frPager.getWebTitleList());
//                fm.beginTransaction().add(R.id.frmPageList,frPageList).commit();
//                frPageList.addSelectListener(this);
//            }
            frPageList = PageListFragment.newInstance(frPager.getWebTitleList());
            fm.beginTransaction().replace(R.id.frmPageList,frPageList).commit();
            frPageList.addSelectListener(this);
        }*/


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
        Log.v("TAG","Finish sTitle= "+sTitle);
        //igCurPagerID=frPager.getCurItemPosition();
        if (position==frPager.getCurItemPosition()){
            getSupportActionBar().setTitle(frPager.getCurItemTitle());
            frPageControl.setURL(frPager.getCurItemURL());
        }
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            //frPageList.UpdateList(position,sTitle);
            //Toast.makeText(getApplicationContext(),"OnPageFinish",Toast.LENGTH_LONG).show();
//            if (frPageList!=null)
            frPageList.UpdateList(frPager.getWebTitleList());
        }
    }

    @Override
    public void OnPagerChanged(int position,String sTitle,String sURL){
//        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
//            Toast.makeText(getApplicationContext(), "HAHA,is Land", Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(getApplicationContext(), "HAHA, is P", Toast.LENGTH_LONG).show();
//        }
        igCurPagerID=frPager.getCurItemPosition();
        Log.v("AAA","igCurPagerID="+Integer.toString(igCurPagerID));
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
 //           frPageList.UpdateList(position,sTitle);
//            if (frPageList!=null)
            frPageList.UpdateList(frPager.getWebTitleList());
        }
        Log.v("TAG","On Pager Change - sTitle ="+sTitle);
        frPageControl.setURL(frPager.getCurItemURL());
        getSupportActionBar().setTitle(frPager.getCurItemTitle());


//        ActionBar actionBar =getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//        actionBar.setTitle(sTitle);
//        actionBar.show();

        //setSupportActionBar();

        //Toast.makeText(getApplicationContext(),actionBar.getTitle(),Toast.LENGTH_LONG).show();

        //getSupportActionBar().setTitle(sTitle);

    }

    //Add a new web page
    @Override
    public void OnNewButtonClick() {
        Log.v("AAA","NewButton");
        getSupportActionBar().setTitle("");
        frPager.AddFragment();

//        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){
//            frPageList.AddAbankTitle();
//            Log.v("AAA","frPageList.AddAbankTitle();");
//        }
    }

    @Override
    public void onItemSelected(int iID) {
        frPager.setCurrentFragment(iID);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("igCurPagerID",igCurPagerID);
        //outState.putAll(outState);
    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//    }


}