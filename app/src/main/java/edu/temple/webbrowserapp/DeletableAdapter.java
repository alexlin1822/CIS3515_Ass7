package edu.temple.webbrowserapp;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeletableAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<String> text;
    private AttentionClickListener mAttentionClickListener;

    //interface
    public void setAttentionClickListener(AttentionClickListener attentionClickListener) {
        mAttentionClickListener = attentionClickListener;
    }

    public interface AttentionClickListener {
        void DeleteItem(int iID);
        void OnBookmartClick(int iID);
    }

    public DeletableAdapter(Context context,ArrayList<String> text){
        this.context = context;
        this.text=text;
    }
    @Override
    public int getCount() {
        return text.size();
    }
    @Override
    public Object getItem(int position) {
        return text.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int index=position;
        View view=convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.bookmark_adapter, null);
        }
        final TextView txtTitle=(TextView)view.findViewById(R.id.txtListTitle);
        txtTitle.setText(text.get(position));
        txtTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttentionClickListener.OnBookmartClick(index);
            }
        });
        final ImageView btnDelete=(ImageView)view.findViewById(R.id.btnDelete);
        btnDelete.setTag(position);
        btnDelete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                text.remove(index);
                mAttentionClickListener.DeleteItem(index);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}

