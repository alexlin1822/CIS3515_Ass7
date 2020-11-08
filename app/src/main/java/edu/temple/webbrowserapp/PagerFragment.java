package edu.temple.webbrowserapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class PagerFragment extends Fragment implements PageViewerFragment.OnPageChangeURLListener {

    //interface
    private PagerFragment.OnChangeListener listener;

    public void addOnChangeListener(PagerFragment.OnChangeListener listener){
        this.listener = listener;}

    public interface  OnChangeListener{
        void OnPagerPageChangeURL(int position, String sURL);
        void OnPagerPageFinish(int position,String sTitle);
        void OnPagerChanged(int position,String sTitle,String sURL);
    }


    private ViewPager2 vp2Pager;
    private ViewPagerFragmentStateAdapter vpAdapter;
    ArrayList<String> arrgWebTitle;
    ArrayList<PageViewerFragment> arrgWeb;

    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
//        Bundle args = new Bundle();
//        args.putStringArrayList("WebContent",arrWeb);
////        args.putSerializable("WebContent", (Serializable) arrWeb);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pager, container, false);
        vp2Pager = view.findViewById(R.id.vp2Pager);

        if (savedInstanceState!=null){
            arrgWebTitle=savedInstanceState.getStringArrayList("arrgWebTitle");
        }
        else {
            arrgWeb = new ArrayList<>();
            arrgWeb.add(new PageViewerFragment());
            PageViewerFragment pvfCurrent = arrgWeb.get(arrgWeb.size()-1);
            pvfCurrent.addOnPageChangeURListener(this);

            arrgWebTitle=new ArrayList<>();
            arrgWebTitle.add("");
        }


        vpAdapter=new ViewPagerFragmentStateAdapter(this.getActivity(),arrgWeb);
        vp2Pager.setAdapter(vpAdapter);

        vp2Pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (listener!=null){
                    listener.OnPagerChanged(position,
                            arrgWeb.get(position).getWebTitle(),
                            arrgWeb.get(position).getUrl());
                }
            }
        });

        return  view;
    }

    @Override
    public void OnPageChangeURL(String sURL) {
        if (listener!=null){listener.OnPagerPageChangeURL(vp2Pager.getCurrentItem(),sURL);}
    }

    @Override
    public void OnPageFinish(String sTitle) {
        if (listener!=null){listener.OnPagerPageFinish(vp2Pager.getCurrentItem(),sTitle);}
        arrgWebTitle.set(vp2Pager.getCurrentItem(),sTitle);
    }



    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        ArrayList<PageViewerFragment> arrMyWeb;
        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<PageViewerFragment> arrWeb) {
            super(fragmentActivity);
            this.arrMyWeb=arrWeb;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return arrMyWeb.get(position);
        }
        @Override
        public int getItemCount() {
            return arrMyWeb.size();
        }
    }

    public ArrayList<String> getWebTitleList(){
        return arrgWebTitle;
    }

    //load a website from URL
    public void LoadPageFromURL(String sURL) {
        PageViewerFragment pvfCurrent;
        pvfCurrent = arrgWeb.get(vp2Pager.getCurrentItem());

        try {
            pvfCurrent.LoadPageFromURL(sURL);
        }
        catch(MalformedURLException q) {
            q.printStackTrace();
        }
    }

    //go back or next
    public void BackNext(int iBtn){
        PageViewerFragment pvfCurrent;
        pvfCurrent = arrgWeb.get(vp2Pager.getCurrentItem());
        pvfCurrent.BackNext(iBtn);
    }

    //Add a new WebView fragment
    public void AddFragment(){
        arrgWeb.add(new PageViewerFragment());
        PageViewerFragment pvfCurrent = arrgWeb.get(arrgWeb.size()-1);
        pvfCurrent.addOnPageChangeURListener(this);

        arrgWebTitle.add("");
        vpAdapter.notifyItemInserted(arrgWeb.size()- 1);
        vp2Pager.setCurrentItem(arrgWeb.size()-1);
    }

    //set current fragment
    public void setCurrentFragment(int position){
        vp2Pager.setCurrentItem(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putStringArrayList("arrgWebTitle",arrgWebTitle);


    }
}