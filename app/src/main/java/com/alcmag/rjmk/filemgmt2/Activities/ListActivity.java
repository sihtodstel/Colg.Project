package com.alcmag.rjmk.filemgmt2.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alcmag.rjmk.filemgmt2.Data.DBhandler;
import com.alcmag.rjmk.filemgmt2.Model.wishes;
import com.alcmag.rjmk.filemgmt2.R;
import com.alcmag.rjmk.filemgmt2.UI.RecViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecViewAdapter recViewAdapter;
    private List<wishes> wishesList;
    private List<wishes> listitems;
    private DBhandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        db= new DBhandler(this);
        recyclerView=(RecyclerView) findViewById(R.id.recviewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wishesList=new ArrayList<>();
        listitems= new ArrayList<>();
        // get items from databse
        wishesList = db.getallwishes();
        for (wishes c: wishesList) {

            wishes wishes=new wishes();
            wishes.setName(c.getName());
            wishes.setSecondwish("Second Wish: " + c.getSecondwish());
            wishes.setId(c.getId());
            wishes.setDate("Added on:" + c.getDate());
            listitems.add(wishes);

        }
        recViewAdapter = new RecViewAdapter(this, listitems);
        recyclerView.setAdapter(recViewAdapter);
        recViewAdapter.notifyDataSetChanged();

    }

}
