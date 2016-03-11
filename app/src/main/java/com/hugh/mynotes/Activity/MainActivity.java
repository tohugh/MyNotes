package com.hugh.mynotes.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hugh.mynotes.Fragment.AboutFragment;
import com.hugh.mynotes.Fragment.NotesFragment;
import com.hugh.mynotes.MyAdapter;
import com.hugh.mynotes.NotesDB;
import com.hugh.mynotes.R;



public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {


    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private NotesDB notesDB;
    private SQLiteDatabase readableDatabase;
    private Cursor cursor;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initDrawerLayout();

        initNavigationView();




    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        notesDB = new NotesDB(this);
        readableDatabase = notesDB.getReadableDatabase();
    }



    /**
     * 设置navigationview
     */
    private void initNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_item_note:
                        switchToNotes();
                        break;
                    case R.id.navigation_item_about:
                        switchToAbout();
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     *跳转到notes页面
     */
    private void switchToNotes() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new
                NotesFragment()).commit();
        mToolbar.setTitle(R.string.navigation_note);
    }

    /**
     *跳转到about页面
     */
    private void switchToAbout() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new
                AboutFragment()).commit();
        mToolbar.setTitle(R.string.navigation_about);
    }


    /**
     * 初始化DrawerLayout
     */
    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        /*抽屉指示器/启示与链接DrawerLayout的状态同步。*/
        mDrawerToggle.syncState();

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mToolbar.setTitleTextColor(getResources().getColor(R.color.white));//标题颜色
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //mToolbar.setTitle("MyNotes");
        //toolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.titlebar_edit:
                startActivity(new Intent(MainActivity.this, AddContent.class));
                break;

            /*case R.id.titlebar_more:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;*/

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB();
        switchToNotes();//默认是笔记页面
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            exitApp();
        }
    }


    private long exitTime = 0;
    /**
     * 退出app
     */
    private void exitApp() {
        if ((System.currentTimeMillis() - exitTime) >2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else{
            finish();
        }
    }


}
