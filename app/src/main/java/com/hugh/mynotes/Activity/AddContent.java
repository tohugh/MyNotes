package com.hugh.mynotes.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hugh.mynotes.NotesDB;
import com.hugh.mynotes.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hugh on 2016/2/1.
 */
public class AddContent extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.btn_save)
    Button btnSave;
    @InjectView(R.id.btn_cancel)
    Button btnCancel;
    @InjectView(R.id.et_addcontent)
    EditText etAddcontent;
    @InjectView(R.id.et_addtitle)
    EditText etAddtitle;
    private NotesDB notesDB;
    private SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_addcontent);
        ButterKnife.inject(this);

        initOnclickListener();

        initToolBar();

        initDB(this);
    }

    /**
     * 初始化button监听器
     */
    private void initOnclickListener() {
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    /**
     * @param context 初始化数据库
     */
    private void initDB(Context context) {
        notesDB = new NotesDB(this);
        writableDatabase = notesDB.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                addDB();
                finish();
                break;

            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    /**
     * 把内容添加到数据库
     */
    private void addDB() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.TITLE, etAddtitle.getText().toString());
        cv.put(NotesDB.CONTENT, etAddcontent.getText().toString());
        cv.put(NotesDB.TIME, getTime());
        writableDatabase.insert(NotesDB.TABLE_NAME, null, cv);
    }

    /**
     * @return 时间的字符串
     */
    private String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date curDate = new Date();
        String str = simpleDateFormat.format(curDate);
        return str;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writableDatabase.close();
    }
}
