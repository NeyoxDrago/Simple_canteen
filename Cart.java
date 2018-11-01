package app.sample.app.android_project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {

    private RecyclerView mrecycler;
    private sql db;
    private Button confirm;
    List<String> urls = new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<Integer> rates = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ImageView image = findViewById(R.id.empty);
        image.setVisibility(View.INVISIBLE);

        final String User  = getIntent().getStringExtra("username");

        db = new sql(this);
        mrecycler = findViewById(R.id.cartrecycler);

        Cursor res = db.getFromCart();

        if(res.getCount()==0)
        {
            image.setVisibility(View.VISIBLE);
        }
        while(res.moveToNext())
        {
            urls.add(res.getString(0));
            names.add(res.getString(1));
            rates.add(res.getInt(2));
        }



        cartAdapter adapter = new cartAdapter(this , urls,names , rates);
        mrecycler.setAdapter(adapter);
        mrecycler.setLayoutManager(new LinearLayoutManager(this));


        confirm =findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog a = new Dialog(Cart.this);
                a.setContentView(R.layout.confirmdialog);
                ImageView image = a.findViewById(R.id.tik);

                Cursor res = db.getFromCart();
                while(res.moveToNext())
                {
                    int t =res.getInt(2)*res.getInt(3);
                    db.insertintoorders(res.getString(1) , res.getInt(3) ,res.getInt(2)
                            ,t , User );
                }

//                RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
//                Glide.with(a.getContext()).load("https://image.shutterstock.com/image-vector/flat-round-check-mark-green-260nw-652023034.jpg").apply(options).into(image);

                a.show();
                a.setCancelable(false);
                a.setCanceledOnTouchOutside(true);
                a.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent  a = new Intent(Cart.this , MenuPage.class);
                        startActivity(a);
                        db.dropcart();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        db.dropcart();
        finish();
    }
}
