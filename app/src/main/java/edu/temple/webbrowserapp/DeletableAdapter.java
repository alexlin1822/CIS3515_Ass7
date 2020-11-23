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
import android.widget.Toast;

import edu.temple.webbrowserapp.R;


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
    }

    public DeletableAdapter(Context context,ArrayList<String> text){
        this.context = context;
        this.text=text;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return text.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return text.get(position);
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final int index=position;
        View view=convertView;
        if(view==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.row_simple_list_item_2, null);
        }
        final TextView textView=(TextView)view.findViewById(R.id.simple_item_1);
        textView.setText(text.get(position));
        final ImageView imageView=(ImageView)view.findViewById(R.id.simple_item_2);
        imageView.setBackgroundResource(android.R.drawable.ic_delete);
        imageView.setTag(position);
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                text.remove(index);
                mAttentionClickListener.DeleteItem(index);
                notifyDataSetChanged();
                Toast.makeText(context, textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        /////
        return view;
    }



}

