package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myFragmentView =inflater.inflate(R.layout.fragment_page_list, container, false);
        lstPage=(ListView) myFragmentView.findViewById(R.id.lstPage);
        lstgWebTitle=new ArrayList<>();


        if (getArguments()!=null) {
            ArrayList<String> arrTemp=new ArrayList<>();
            arrTemp = getArguments().getStringArrayList("WebTitle");

            for (int i=0;i<arrTemp.size();i++){
                if (lstgWebTitle.size()<=i){
                    lstgWebTitle.add(arrTemp.get(i));
                }
                else{
                    lstgWebTitle.set(i,arrTemp.get(i));
                }
            }
        }
        else{
            lstgWebTitle.add("");
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

    public void UpdateList(ArrayList<String> arrWebTitle){
            for (int i=0;i<arrWebTitle.size();i++){
                if (lstgWebTitle.size()<=i){
                    lstgWebTitle.add(arrWebTitle.get(i));
                }
                else{
                    lstgWebTitle.set(i,arrWebTitle.get(i));
                }
            }

        adpList.notifyDataSetChanged();

    }
}