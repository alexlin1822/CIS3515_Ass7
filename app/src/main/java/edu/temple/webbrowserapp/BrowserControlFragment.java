package edu.temple.webbrowserapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BrowserControlFragment extends Fragment {
    private ImageButton btnNew;
    private ImageButton btnBookmark;
    private ImageButton btnSave;

    //interface
    public void addNewButtonListener(BrowserControlFragment.OnNewButtonClickListener listener){this.listener = listener;}

    public interface OnNewButtonClickListener{
        void OnNewButtonClick();
        void OnBookmark();
        void OnSave();
    }

    private BrowserControlFragment.OnNewButtonClickListener listener;

    public BrowserControlFragment() {
        // Required empty public constructor
    }

    public static BrowserControlFragment newInstance() {
        return new BrowserControlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myFragmentView =
                inflater.inflate(R.layout.fragment_browser_control, container, false);

        btnNew=myFragmentView.findViewById(R.id.btnAddPage);
        btnNew.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnNewButtonClick();
            }
        });

        btnBookmark=myFragmentView.findViewById(R.id.btnBookmark);
        btnBookmark.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnBookmark();
            }
        });

        btnSave=myFragmentView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnSave();
            }
        });


        return myFragmentView;
    }
}