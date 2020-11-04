package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PagerFragment extends Fragment {

    private ViewPager2 vp2Pager;

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

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pager, container, false);
        vp2Pager = view.findViewById(R.id.vp2Pager);


        arrgWeb = new ArrayList<>();
        arrgWeb.add(new PageViewerFragment());
        arrgWeb.add(new PageViewerFragment());
        arrgWeb.add(new PageViewerFragment());

        vp2Pager.setAdapter(new ViewPagerFragmentStateAdapter(this.getActivity(),arrgWeb));
        return  view;
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
            //return PageViewerFragment.newInstance(Integer.toString(position) );
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

    public void BackNext(int iBtn){
        PageViewerFragment pvfCurrent;
        pvfCurrent = arrgWeb.get(vp2Pager.getCurrentItem());
        pvfCurrent.BackNext(iBtn);
    }

    public void AddFragment(){
        arrgWeb.add(new PageViewerFragment());
        vp2Pager.setCurrentItem(arrgWeb.size()-1);

    }
}