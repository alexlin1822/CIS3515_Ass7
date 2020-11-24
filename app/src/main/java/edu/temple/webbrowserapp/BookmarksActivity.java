package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {
    private DeletableAdapter adapter;
    private ArrayList<TBookmark> bkgBookmark;
    private ArrayList<String> arrgBookmartTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        ListView list_view = (ListView) findViewById(R.id.list_view);

        getSupportActionBar().setTitle("Bookmark List");
        bkgBookmark=LoadBookmark();
        arrgBookmartTitle = new ArrayList<String>();

        for (int i=0;i<bkgBookmark.size();i++){
            arrgBookmartTitle.add(bkgBookmark.get(i).getTitle());
        }

        adapter = new DeletableAdapter(this, arrgBookmartTitle);

        adapter.setAttentionClickListener(new DeletableAdapter.AttentionClickListener() {
            @Override
            public void DeleteItem(int iID) {
                bkgBookmark.remove(iID);
                SaveBookmark();
            }

            @Override
            public void OnBookmartClick(int index){
                BrowserActivity.ToBookmark(index);
                finish();
            }
        });

        list_view.setAdapter(adapter);

        //close form button
        Button btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private ArrayList<String> getBookmarkTitleList(){
        ArrayList<String> arrTitle=new ArrayList<>();
        for (int i=0;i<bkgBookmark.size();i++){
            arrTitle.add(bkgBookmark.get(i).getTitle());
        }
        return arrTitle;
    }
}