package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

//        Button saveDate = (Button) findViewById(R.id.button_save_date);
//        saveDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //使用编辑器去更改preference的信息
//                SharedPreferences pref = getSharedPreferences("userInfo" , MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putInt("ID" , 1);
//                editor.putString("Name", "Join");
//                editor.putString("Age", "28");
//                //提交编辑好的数据
//                editor.apply();
//            }
//        });

    }
}