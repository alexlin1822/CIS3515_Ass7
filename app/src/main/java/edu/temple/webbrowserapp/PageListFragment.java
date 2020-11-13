package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PageListFragment extends Fragment {
    public void addSelectListener(OnItemSelectedListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectedListener{
        void onItemSelected(int iID);
    }

    private OnItemSelectedListener listener;

    private ArrayList<String> lstgWebTitle;
    private ListView lstPage;
    private ArrayAdapter adpList;

    public PageListFragment() {
        // Required empty public constructor
    }


    public static PageListFragment newInstance(ArrayList<String> lstWebTitle) {
        PageListFragment fragment = new PageListFragment();

        Bundle args = new Bundle();
        args.putStringArrayList("WebTitle",lstWebTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myFragmentView =inflater.inflate(R.layout.fragment_page_list, container, false);
        lstPage=(ListView) myFragmentView.findViewById(R.id.lstPage);
        lstgWebTitle=new ArrayList<>();
        ArrayList<String> arrTemp=new ArrayList<>();

//        if (savedInstanceState!=null){
//            //lstgWebTitle=savedInstanceState.getStringArrayList("lstgWebTitle");
//            //Toast.makeText(getContext(),"savedInstanceState!=null",Toast.LENGTH_LONG).show();
//        }
//        else {
//                lstgWebTitle = getArguments().getStringArrayList("WebTitle");
//            Toast.makeText(getContext(),"getArguments()",Toast.LENGTH_LONG).show();
//                //lstgWebTitle = new ArrayList<>();
//                //lstgWebTitle.add("");
//        }


        if (getArguments()!=null) {
            arrTemp = getArguments().getStringArrayList("WebTitle");

            for (int i=0;i<arrTemp.size();i++){
                if (lstgWebTitle.size()<=i){
                    lstgWebTitle.add(arrTemp.get(i));
                }
                else{
                    lstgWebTitle.set(i,arrTemp.get(i));
                }
            }
            Log.v("TAG","getArguments()");
        }

        if (lstgWebTitle==null) {
            lstgWebTitle = new ArrayList<>();
            lstgWebTitle.add("");
            Log.v("TAG","newArray");

        }

        adpList=new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_1,lstgWebTitle);
        lstPage.setAdapter(adpList);
        lstPage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener!=null){listener.onItemSelected(position);}
            }
        });

        return  myFragmentView;
    }

//    public void UpdateList(int position, String sTitle){
//        Log.v("AAA","position="+Integer.toString(position));
//        Log.v("AAA","B lstgWebTitle.size()="+Integer.toString(lstgWebTitle.size()));
//        while (position>=lstgWebTitle.size())
//            lstgWebTitle.add("");
//        Log.v("AAA","F lstgWebTitle.size()="+Integer.toString(lstgWebTitle.size()));
//        lstgWebTitle.set(position,sTitle);
//        adpList.notifyDataSetChanged();
//    }

    public void AddAbankTitle(){
        //lstgWebTitle.add("");
        Log.v("AAA","AddBank lstgWebTitle="+Integer.toString(lstgWebTitle.size()));
        adpList.notifyDataSetChanged();

    }

    public void notifyDataChanged(){
        //adpList.notifyDataSetChanged();
    }

    public void UpdateList(ArrayList<String> arrWebTitle){
            for (int i=0;i<arrWebTitle.size();i++){
                if (lstgWebTitle.size()<=i){
                    lstgWebTitle.add(arrWebTitle.get(i));
                }
                else{
                    lstgWebTitle.set(i,arrWebTitle.get(i));
                }
            }

        Log.v("AAA","AddBank lstgWebTitle="+Integer.toString(lstgWebTitle.size()));
        Log.v("AAA","AddBank income arrWebTitle="+Integer.toString(arrWebTitle.size()));
        adpList.notifyDataSetChanged();
//        if (!arrWebTitle.isEmpty()) {
//            //
//            lstgWebTitle.clear();
//
//
//            while (arrWebTitle.size()>lstgWebTitle.size())
//                lstgWebTitle.add("");
//            for (int i=0;i<arrWebTitle.size();i++){
//                    lstgWebTitle.add(arrWebTitle.get(i));
//            }
//            //lstgWebTitle=lstWebTitle;
//
//        }
//
//        String sTmp="ArrWeb-- ";
//        for (int i=0;i<arrWebTitle.size();i++){
//            sTmp=sTmp+" --- "+arrWebTitle.get(i);
//        }
//
//        //Toast.makeText(getContext(),"P - "+Integer.toString(position)+" FID - "+Integer.toString(arrgWeb.get(position).getFID()),Toast.LENGTH_LONG).show();
//            //Toast.makeText(getContext(),sTmp,Toast.LENGTH_LONG).show();
        //adpList.notifyDataSetChanged();
        //lstPage.notifyAll();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //outState.putStringArrayList("lstgWebTitle", lstgWebTitle);
        //super.onSaveInstanceState(outState);
        //outState.putAll(outState);
    }
}