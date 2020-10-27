package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class PageControlFragment extends Fragment {
    private ImageButton[] btn = new ImageButton[3];
    private EditText txtURL;
    private int[] btn_id = {R.id.btnGo, R.id.btnBack, R.id.btnNext};

    //interface
    public void addButtonClickListener(OnClickListener listener){this.listener = listener;}

    public interface OnClickListener{
        void OnClick(int btnID);
    }

    private OnClickListener listener;

    public PageControlFragment() {
        // Required empty public constructor
    }

    public static PageControlFragment newInstance() {
        return new PageControlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init EditText and 3 Buttons
        txtURL=(EditText)getActivity().findViewById(R.id.txtURL);

        for(int i = 0; i < btn.length; i++){
            btn[i] = (ImageButton) getActivity().findViewById(btn_id[i]);
            btn[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.OnClick(view.getId());
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myFragmentView =inflater.inflate(R.layout.fragment_page_control, container, false);

        return  myFragmentView;
    }

    public void setURL(String sURL){
        txtURL.setText(sURL);
    }

    public String getURL(){
        String sTmp=txtURL.getText().toString();
        if (sTmp.length()==0){
            sTmp="";
        }
        else if (sTmp.length()<8){
            sTmp="https://"+sTmp;
        }
        else if (!sTmp.substring(0,8).toLowerCase().equals("https://")){
            sTmp="https://"+sTmp;
        }
        txtURL.setText(sTmp);
        return sTmp;
    }
}


