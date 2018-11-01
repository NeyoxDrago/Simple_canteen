package app.sample.app.android_project;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuPage extends AppCompatActivity {

    private sql db;
    private RecyclerView mrecycler;
    List<String> urls = new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<Integer> rates = new ArrayList<>();
    String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        db = new sql(this);

         User  = getIntent().getStringExtra("username");
        Cursor res = db.getDatafromneu();
        while(res.moveToNext())
        {
            urls.add(res.getString(0));
            names.add(res.getString(1));
            rates.add(res.getInt(2));
        }

        mrecycler = findViewById(R.id.mrecycler);
        mrecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter dapter = new adapter(this ,urls , names , rates);
        mrecycler.setAdapter(dapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.tocart:
                Intent l = new Intent(MenuPage.this , Cart.class);
                l.putExtra("username",User);
                startActivity(l);
                break;

            case R.id.logout:
                Intent w = new Intent(MenuPage.this , MainActivity.class);
                startActivity(w);
                break;
            case R.id.feedbackoption:
                final Dialog a = new Dialog(this);
                a.setContentView(R.layout.feedback);

                final EditText text = a.findViewById(R.id.feedbacktext);
                Button submit = a.findViewById(R.id.submitfeedback);
                a.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String matter = text.getText().toString().trim();

                        if(TextUtils.isEmpty(matter))
                        {
                            Toast.makeText(MenuPage.this, "Please Enter something .... ", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(db.insertfeedback(matter)) {
                            Toast.makeText(MenuPage.this, "Thank you for your feedback..", Toast.LENGTH_SHORT).show();
                            a.dismiss();
                        }
                    }
                });
                a.show();
                break;

        }
        return true;
    }
    public void onBackPressed() {
        Log.d("on Back Pressed" ," nhi jaana");
    }
}
