package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity
        implements PageControlFragment.OnClickListener,
            BrowserControlFragment.OnNewButtonClickListener,
            PageListFragment.OnItemSelectedListener,
            PagerFragment.OnChangeListener
{
    private final int REQUEST_CODE=111;
    private FragmentManager fm;
    private PageControlFragment frPageControl;
    private BrowserControlFragment frBrowserCtrl;
    private PageListFragment frPageList;
    private PagerFragment frPager;
    private int igCurPagerID;
    private ArrayList<TBookmark> bkgBookmark;
    private static int igClickID=-1;

    public static void ToBookmark(int iClick){
        igClickID=iClick;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        if (savedInstanceState!=null){
            igCurPagerID=savedInstanceState.getInt("igCurPagerID",0);
        }
        else{
            igCurPagerID=0;
        }

        bkgBookmark=LoadBookmark();
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
    public void OnBookmark(){
        Intent MyInform=new Intent (BrowserActivity.this,BookmarksActivity.class);
        MyInform.putExtra("myID",1);
        startActivityForResult(MyInform,REQUEST_CODE);
    }

    //save a new bookmark
    @Override
    public void OnSave(){
        String sURL= frPager.getCurItemURL();
        String sTitle=frPager.getCurItemTitle();

        if ((sURL!=null) && (sTitle!=null)){
            Log.v("KKK","canSave");
            for (int i=0;i<bkgBookmark.size();i++){
                String sTmp=bkgBookmark.get(i).getURL();
                if (sTmp.equals(sURL)){
                    Toast.makeText(getApplicationContext(),"Bookmark already exist.",Toast.LENGTH_LONG).show();
                    return;
                }
            }

            TBookmark bkTmp=new TBookmark();
            bkTmp.setVal(bkgBookmark.size(),sTitle,sURL);
            bkgBookmark.add(bkTmp);
            SaveBookmark();
            Toast.makeText(getApplicationContext(),"Bookmark save success.",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"   No Title or URL \nBookmark not save",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(int iID) {
        frPager.setCurrentFragment(iID);
    }

    //load the bookmark
    private ArrayList<TBookmark> LoadBookmark(){
        ArrayList<TBookmark> arrTemp=new ArrayList<>();

        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);

        int itotalBookmark=pref.getInt("TotalBookmark" , 0);

        for (int i=0; i<itotalBookmark;i++){
            TBookmark bkTmp=new TBookmark();
            int iTmpID=pref.getInt("B_ID_"+i,-1);
            String iTmpTitle=pref.getString("B_Title_"+i,"");
            String iTmpURL=pref.getString("B_URL_"+i,"");
            bkTmp.setVal(iTmpID,iTmpTitle,iTmpURL);
            arrTemp.add(bkTmp);
        }
        return arrTemp;
    }

    //save the bookmark
    private int SaveBookmark(){
        //SharedPreferences pref = getSharedPreferences("MyAppInfo" , MODE_MULTI_PROCESS);
        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("TotalBookmark" ,bkgBookmark.size());

        for (int i=0; i<bkgBookmark.size();i++){
            editor.putInt("B_ID_"+i,bkgBookmark.get(i).getID());
            editor.putString("B_Title_"+i,bkgBookmark.get(i).getTitle());
            editor.putString("B_URL_"+i,bkgBookmark.get(i).getURL());
        }

        editor.apply();
        return 0;
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        bkgBookmark=LoadBookmark();
        if (hasFocus && (igClickID>=0)){
            //Log.v("KKK","onWindowFocusChanged="+Integer.toString(igClickID));
            frPager.LoadPageFromURL(bkgBookmark.get(igClickID).getURL());
            igClickID=-1;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("igCurPagerID",igCurPagerID);
    }
}