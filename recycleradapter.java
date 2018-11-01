package app.sample.app.android_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class recycleradapter extends RecyclerView.Adapter<recycleradapter.ViewHolder> {

    Context c;
    List<String> text = new ArrayList<>();
    String type;

    public recycleradapter(Context c, List<String> text ,String type) {
        this.c = c;
        this.text = text;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(c).inflate(R.layout.recycleritemlayout , viewGroup , false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.update.setEnabled(true);
        viewHolder.delete.setEnabled(true);

        if(type.equals("update"))
        {
            viewHolder.delete.setEnabled(true);
            viewHolder.delete.setText("");
            viewHolder.delete.setBackgroundColor(Color.WHITE);
        }
        else if(type.equals("delete"))
        {
            viewHolder.update.setEnabled(false);
            viewHolder.update.setText("");
            viewHolder.update.setBackgroundColor(Color.WHITE);
        }
        else
        {
            viewHolder.update.setText("");
            viewHolder.delete.setText("");
            viewHolder.delete.setBackgroundColor(Color.WHITE);
            viewHolder.update.setBackgroundColor(Color.WHITE);
        }

        final String[] array = text.get(i).split(",");
        viewHolder.name.setText(" Name :: " + array[1]);
        viewHolder.rate.setText("Rate :: "+ array[2]);
        viewHolder.wrate.setText("Whole_Rate :: " + array[3]);

        final sql db =new sql(c);
        final Dialog dialog = new Dialog(c);
        dialog.setContentView(R.layout.additem);

        final Cursor res = db.getDatafromneu();

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(c);
                alert.setMessage("Are you sure ....you want to delete this field");
                alert.setTitle("Confirmation");
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int i = db.delete(array[0]);
                                if(i>0){
                                    Toast.makeText(c, "Item Deleted Successfully..!!!", Toast.LENGTH_SHORT).show();
                                    Intent l  = new Intent(c , OwnerMenuPage.class);
                                    c.startActivity(l);
                                    }
                                else
                                {
                                    Toast.makeText(c, "Not Deleted..", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                alert.show();

            }
        });

        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "bhadd mein jaa", Toast.LENGTH_SHORT).show();
                dialog.show();
                final TextInputLayout name, rate , w_rate;
                TextView text = dialog.findViewById(R.id.dialogtext);
                text.setText("Update item");
                name = dialog.findViewById(R.id.itemname);
                rate = dialog.findViewById(R.id.itemrate);
                w_rate = dialog.findViewById(R.id.itemW_rate);
                Button b = dialog.findViewById(R.id.additemtotable);

                EditText one ,three,four;
                one = dialog.findViewById(R.id.one);
                three = dialog.findViewById(R.id.three);
                four = dialog.findViewById(R.id.four);

                while(res.moveToNext()) {
                    if (res.getString(0).equals(i+1)) {
                        one.setText(res.getString(1));
                        three.setText(res.getString(3));
                        four.setText(res.getString(4));
                    }
                }



                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String N = name.getEditText().getText().toString().trim();
                        int r = Integer.parseInt(rate.getEditText().getText().toString().trim());
                        int w_r = Integer.parseInt(w_rate.getEditText().getText().toString().trim());

                        if(db.updateitem( array[0] , N ,r,w_r))
                        {
                            dialog.dismiss();
                            Toast.makeText(c, "Item Updated Successfully .... !!!", Toast.LENGTH_SHORT).show();
                            Intent a  = new Intent(c , OwnerMenuPage.class);
                            c.startActivity(a);
                        }
                        else
                            Toast.makeText(c, "error", Toast.LENGTH_SHORT).show();


                    }
                });


            }


        });

    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, rate, wrate;
        TextView update , delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recycleritemname);
            rate = itemView.findViewById(R.id.recyleritemrate);
            wrate = itemView.findViewById(R.id.recycleritemwrate);
            update = itemView.findViewById(R.id.updatethisitem);
            delete = itemView.findViewById(R.id.deletethisitem);

        }
    }

}
