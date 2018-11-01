package app.sample.app.android_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class showmenupage extends AppCompatActivity {

    ListView menulist;
    sql sb ;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmenupage);


        final String type = getIntent().getStringExtra("type");

        menulist = findViewById(R.id.menulist);

        sb = new sql(this);
        final Cursor res = sb.getDatafromneu();

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.additem);
        ArrayList<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        while(res.moveToNext())
        {
            list.add(res.getString(0) + "," +
                        res.getString(1) +","+
                        res.getString(2) + "," +
                        res.getString(3));
        }

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        recycleradapter recycleradapter = new recycleradapter(this , list ,type);
        recycler.setAdapter(recycleradapter);


        final ArrayAdapter adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , list);

        menulist.setAdapter(adapter);

        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                final String texth = menulist.getItemAtPosition(position).toString();
                final String[] str = texth.split(",");
                Toast.makeText(showmenupage.this,str[0] , Toast.LENGTH_SHORT).show();

                if(type.equals("delete"))
                {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(showmenupage.this);
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

                            int i = sb.delete(str[0]);
                            if(i>0){
                                Toast.makeText(showmenupage.this, "Item Deleted Successfully..!!!", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(showmenupage.this, "Not Deleted..", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    alert.show();

                }
                if(type.equals("update"))
                {
                    dialog.show();
                    final TextInputLayout name, rate , w_rate;
                    TextView text = dialog.findViewById(R.id.dialogtext);
                    text.setText("Update item");
                    name = dialog.findViewById(R.id.itemname);
                    rate = dialog.findViewById(R.id.itemrate);
                    w_rate = dialog.findViewById(R.id.itemW_rate);
                    Button b = dialog.findViewById(R.id.additemtotable);
                    b.setText("Update");

                    EditText one ,three,four;
                    one = dialog.findViewById(R.id.one);
                    three = dialog.findViewById(R.id.three);
                    four = dialog.findViewById(R.id.four);

                    Toast.makeText(showmenupage.this, "" + position, Toast.LENGTH_SHORT).show();
                    while(res.moveToNext()) {
                        if (res.getString(0).equals(position+1)) {
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

                            if(sb.updateitem( str[0] , N ,r,w_r))
                            {
                                dialog.dismiss();
                                Toast.makeText(showmenupage.this, "Item Updated Successfully .... !!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                                Toast.makeText(showmenupage.this, "error", Toast.LENGTH_SHORT).show();


                        }
                    });


                }

            }
        });
    }
}
