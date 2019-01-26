package com.alcmag.rjmk.filemgmt2.UI;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alcmag.rjmk.filemgmt2.Activities.DetailAct;
import com.alcmag.rjmk.filemgmt2.Activities.MainActivity;
import com.alcmag.rjmk.filemgmt2.Data.DBhandler;
import com.alcmag.rjmk.filemgmt2.Model.wishes;
import com.alcmag.rjmk.filemgmt2.R;

import java.util.List;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ViewHolder> {
    private Context context;
    private List<wishes> wisheditems;
    private AlertDialog.Builder alertdbuiler;
     private AlertDialog dialo;
     private LayoutInflater inflater;


    public RecViewAdapter(Context context, List<wishes> wisheditems) {
        this.context = context;
        this.wisheditems = wisheditems;
    }

    @NonNull
    @Override
    public RecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewAdapter.ViewHolder holder, int position) {

        wishes wishes= wisheditems.get(position);

         holder.wishname.setText(wishes.getName());
         holder.valueofwish.setText(wishes.getSecondwish());
         holder.dateadded.setText(wishes.getDate());
    }

    @Override
    public int getItemCount() {
        return wisheditems.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView wishname;
        public TextView valueofwish;
        public TextView dateadded;
        public Button editbutton;
        public Button deltbut;
        public int id;

        public ViewHolder(View view, Context ctx) {
            super(view);
            context = ctx;
            wishname = view.findViewById(R.id.rowtsxt);
            valueofwish = view.findViewById(R.id.row2);
            dateadded = view.findViewById(R.id.date);
            editbutton = view.findViewById(R.id.editwish);
            deltbut = view.findViewById(R.id.deletewish);
            editbutton.setOnClickListener(this);
            deltbut.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //go to next screen/detailsact

                    int position = getAdapterPosition();
                    wishes wishes=wisheditems.get(position);
                    Intent intent= new Intent(context, DetailAct.class);
                    intent.putExtra("name",wishes.getName());
                    intent.putExtra("value",wishes.getSecondwish());
                    intent.putExtra("date",wishes.getDate());
                    intent.putExtra("id",wishes.getId());
                    context.startActivity(intent);

                }
            });

        }


        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.editwish:
                   int position=getAdapterPosition();
                   wishes wishes=wisheditems.get(position);
                   edititem(wishes);

                break;
                case R.id.deletewish:
                     position=getAdapterPosition();
                     wishes=wisheditems.get(position);
                    deleteitem(wishes.getId());
                    break;
            }

        }
        public void deleteitem(final int id){
            //create an alertdialog

            alertdbuiler=new AlertDialog.Builder(context);
            inflater=   LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.conf_dialog,null);

            Button noBut= view.findViewById(R.id.nobutton);
            Button yesBut= view.findViewById(R.id.yesbutton);
            alertdbuiler.setView(view);
            dialo=alertdbuiler.create();
            dialo.show();

            noBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialo.dismiss();

                }
            });
            yesBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete item
                    DBhandler db=new DBhandler(context);
                    db.deletewish(id);
                    wisheditems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    dialo.dismiss();

                }
            });


        }
        public void edititem(final wishes wishes){
            alertdbuiler=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            final View view=inflater.inflate(R.layout.popup,null);
            final EditText wishitem= view.findViewById(R.id.firsthint);
            final EditText secitem=view.findViewById(R.id.secondhint);
            final TextView title=view.findViewById(R.id.popuptext);
            title.setText("Edit Wish");
            Button svebut=view.findViewById(R.id.savebut);
            alertdbuiler.setView(view);
            dialo=alertdbuiler.create();
            dialo.show();
            svebut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBhandler db = new DBhandler(context);
                    wishes.setName(wishitem.getText().toString());
                    wishes.setSecondwish(secitem.getText().toString());
                    if (!wishitem.getText().toString().isEmpty() &&
                            !secitem.getText().toString().isEmpty()) {
                        db.updatewishes(wishes);
                        notifyItemChanged(getAdapterPosition(), wishes);

                    } else {
                        Snackbar.make(view, "Add Wishes", Snackbar.LENGTH_LONG).show();
                    }
                    dialo.dismiss();

                }

            });

        }
    }
}
