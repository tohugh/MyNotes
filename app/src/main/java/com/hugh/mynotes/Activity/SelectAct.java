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

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hugh on 2016/2/2.
 */
public class SelectAct extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.et_editcontent)
    EditText etEditcontent;
    @InjectView(R.id.btn_save)
    Button btnSave;
    @InjectView(R.id.btn_delete)
    Button btnDelete;
    @InjectView(R.id.et_edittitle)
    EditText etEdittitle;
    private NotesDB notesDB;
    private SQLiteDatabase writableDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_selectact);
        ButterKnife.inject(this);

        etEdittitle.setText(getIntent().getStringExtra(NotesDB.TITLE));
        etEditcontent.setText(getIntent().getStringExtra(NotesDB.CONTENT));

        initToolBar();

        initDB(this);

        initOnclickListener();

    }

    /**
     * 初始化button监听器
     */
    private void initOnclickListener() {
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }


    /**
     * @param context 初始化数据库
     */
    private void initDB(Context context) {
        notesDB = new NotesDB(this);
        writableDatabase = notesDB.getWritableDatabase();

    }


    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                deleteDB();
                finish();
                break;

            case R.id.btn_save:
                updateDB();
                finish();
                break;
        }
    }

    /**
     * 更新数据库
     */
    private void updateDB() {
        ContentValues cv = new ContentValues();
        cv.put(NotesDB.TITLE, etEdittitle.getText().toString());
        cv.put(NotesDB.CONTENT, etEditcontent.getText().toString());
        writableDatabase.update(NotesDB.TABLE_NAME, cv, "_id=" + getIntent().getIntExtra(NotesDB.ID, 0), null);
    }

    /**
     * 删除数据库
     */
    private void deleteDB() {
        writableDatabase.delete(NotesDB.TABLE_NAME,
                "_id=" + getIntent().getIntExtra(NotesDB.ID, 0), null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writableDatabase.close();
    }
}
