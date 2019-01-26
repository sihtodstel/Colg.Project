package com.alcmag.rjmk.filemgmt2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alcmag.rjmk.filemgmt2.Data.DBhandler;
import com.alcmag.rjmk.filemgmt2.Model.wishes;
import com.alcmag.rjmk.filemgmt2.R;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private EditText wish1;
    private EditText wish2;
    private Button saveit;
    private DBhandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= new DBhandler(this);
        bypassAct();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  //              Snackbar.make(view, "GG BOYS", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createDialog();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void createDialog(){
        dialogbuilder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.popup,null);
        wish1= (EditText)  view.findViewById(R.id.firsthint);
        wish2= (EditText) view.findViewById(R.id.secondhint);
        saveit= (Button) view.findViewById(R.id.savebut);
        dialogbuilder.setView(view);
        dialog = dialogbuilder.create();
        dialog.show();
       saveit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!wish1.getText().toString().isEmpty() &&
                        !wish2.getText().toString().isEmpty()) {
                    savefirstwishdb(v);
                }
            }
        });


    }

    private void savefirstwishdb(View v) {

        wishes wishes=new wishes();
        String newwish= wish1.getText().toString();
        String secondwish=wish2.getText().toString();
        wishes.setName(newwish);
        wishes.setSecondwish(secondwish);
        //SAve to DB
            db.addwish(wishes);
            Snackbar.make(v,"Wish saved",Snackbar.LENGTH_LONG).show();
        //Log.d("Wish added ID:", String.valueOf(db.getwishcount()));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              dialog.dismiss();
              //start another activity
                startActivity(new Intent(MainActivity.this,ListActivity.class));

            }
        },1000);


    }
    public void bypassAct(){
        if(db.getwishcount()  > 0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }

}
