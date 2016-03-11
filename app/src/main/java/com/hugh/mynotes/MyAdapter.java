package com.hugh.mynotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.mynotes.Activity.SelectAct;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hugh on 2016/2/1.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TextViewHolder> {


    private SQLiteDatabase writableDatabase;
    private NotesDB notesDB;
    private Cursor cursor;
    private Context context;
    private LayoutInflater mLayoutInflater;



    public MyAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        mLayoutInflater = LayoutInflater.from(context);

        notesDB = new NotesDB(context);
        writableDatabase = notesDB.getWritableDatabase();

    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.tvTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
        holder.tvContent.setText(cursor.getString(cursor.getColumnIndex("content")));
        holder.tvTime.setText(cursor.getString(cursor.getColumnIndex("time")));
        WatchDetail(holder);

    }

    // TODO: 2016/2/9 长按删除

    private void DeleteNotes(final TextViewHolder holder) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                cursor.moveToPosition(holder.getLayoutPosition());
                writableDatabase.delete(NotesDB.TABLE_NAME,
                        "_id=" + cursor.getInt(cursor.getColumnIndex(NotesDB.ID)), null);
                notifyItemRemoved(holder.getLayoutPosition());
                return false;
            }
        });

    }

    private void WatchDetail(final TextViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPosition(holder.getLayoutPosition());
                Intent i = new Intent(context, SelectAct.class);
                i.putExtra(NotesDB.ID,
                        cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.TITLE, cursor.getString(cursor
                        .getColumnIndex(NotesDB.TITLE)));
                i.putExtra(NotesDB.CONTENT, cursor.getString(cursor
                        .getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,
                        cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                context.startActivity(i);

            }
        });
    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.tv_time)
        TextView tvTime;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);


        }


    }


}
