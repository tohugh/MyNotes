package com.hugh.mynotes.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.mynotes.MyAdapter;
import com.hugh.mynotes.NotesDB;
import com.hugh.mynotes.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by hugh on 2016/2/3.
 */
public class NotesFragment extends Fragment {

    private NotesDB notesDB;
    private SQLiteDatabase readableDatabase;
    private Cursor cursor;
    private MyAdapter myAdapter;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initDB();

        View view = inflater.inflate(R.layout.fragmnt_notes, null);
        ButterKnife.inject(this, view);
        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        selectDB();
    }

    /**
     * 查询数据库
     */
    private void selectDB() {
        cursor = readableDatabase.query(NotesDB.TABLE_NAME, null, null, null, null,
                null, null);
        myAdapter = new MyAdapter(getActivity(), cursor);
        recyclerView.setAdapter(myAdapter);
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        notesDB = new NotesDB(getActivity());
        readableDatabase = notesDB.getReadableDatabase();
    }

    /**
     * 初始化recyclerview
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
