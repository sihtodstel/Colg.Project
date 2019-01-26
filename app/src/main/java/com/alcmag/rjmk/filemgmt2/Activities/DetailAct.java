package com.alcmag.rjmk.filemgmt2.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alcmag.rjmk.filemgmt2.R;

public class DetailAct extends AppCompatActivity {
    private TextView wish;
    private TextView secwish;
    private TextView dateadded;
    private int wishid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        wish=findViewById(R.id.wishdet);
        secwish=findViewById(R.id.secwishdet);
        dateadded=findViewById(R.id.datedet);

        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            wish.setText(bundle.getString("name"));
            secwish.setText(bundle.getString("value"));
            dateadded.setText(bundle.getString("date"));
            wishid=bundle.getInt("id");


        }
    }
}
