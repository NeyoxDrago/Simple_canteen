package app.sample.app.android_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OwnerMenuPage extends AppCompatActivity {
private Button show , add,delete ,update,feedback , Orders;
private Dialog dialog;
private sql db;
private ListView feedbacklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_menu_page);

        show = findViewById(R.id.showmenu2);
        add = findViewById(R.id.additem2);
        delete = findViewById(R.id.deleteitem2);
        update = findViewById(R.id.updateitem2);
        feedback = findViewById(R.id.feedback2);
        Orders = findViewById(R.id.orders);

        feedbacklist = findViewById(R.id.feedbacklist2);

        db = new sql(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(OwnerMenuPage.this , showmenupage.class);
                a.putExtra("type","show");
                startActivity(a);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(OwnerMenuPage.this , showmenupage.class);
                a.putExtra("type","update");
                startActivity(a);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(OwnerMenuPage.this , showmenupage.class);
                a.putExtra("type","delete");
                startActivity(a);
            }
        });

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.additem);
        String Text = "";
        Cursor order = db.getOrders();

        while(order.moveToNext())
        {
            Text = Text + "\n"+order.getString(0)+" "
                    +"Name : " + order.getString(1)+"\n"
                    +"Quantity : " + order.getString(2)+"\n"
                    +"Rate : " + order.getString(3)+"\n"
                    +"Total Price : " + order.getString(4)+"\n";
        }


        final AlertDialog.Builder orders = new AlertDialog.Builder(this);
        orders.setTitle("Your Orders").setMessage(Text);
        orders.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                final TextInputLayout name , quantity , rate , w_rate;
                name = dialog.findViewById(R.id.itemname);
                rate = dialog.findViewById(R.id.itemrate);
                w_rate = dialog.findViewById(R.id.itemW_rate);
                Button b = dialog.findViewById(R.id.additemtotable);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        String N = name.getEditText().getText().toString().trim();
                        int r = Integer.parseInt(rate.getEditText().getText().toString().trim());
                        int w_r = Integer.parseInt(w_rate.getEditText().getText().toString().trim());

                        if(TextUtils.isEmpty(N)|| TextUtils.isEmpty(r+"")|| TextUtils.isEmpty(w_r+""))
                        {
                            Toast.makeText(OwnerMenuPage.this, "Fill the required fields correctly.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(db.insertintomenu(N, r, w_r))
                        {
                            dialog.dismiss();
                            Toast.makeText(OwnerMenuPage.this, "Item Added Successfully..!!", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(OwnerMenuPage.this, N +"", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        ArrayList list = new ArrayList();

        Cursor res = db.getFeedback();
        while(res.moveToNext())
        {
            list.add(res.getString(0)+ "  " + res.getString(1));
        }

        final ArrayAdapter adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , list);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbacklist.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("on Back Pressed" ," nhi jaana");
    }
}
