package app.sample.app.android_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class signinpage extends AppCompatActivity {

    private TextView text , newuser;
    private TextInputLayout username ,password;
    private Button submit;
    sql db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinpage);

        db = new sql(this);

        text = findViewById(R.id.welcometext);
        final String add = getIntent().getStringExtra("text");

        text.setText("Welcome "+ add);

        newuser = findViewById(R.id.newuser);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(signinpage.this, LoginActivty.class);
                startActivity(m);
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User = username.getEditText().getText().toString().trim();
                String pass = password.getEditText().getText().toString().trim();

                Cursor res = db.getData();

                int c=0;
                if(add.equals("Student"))
                {
                    while(res.moveToNext()) {

                        if (User.equals(res.getString(1)) && pass.equals(res.getString(2))) {
                            username.getEditText().setText("");
                            password.getEditText().setText("");

                            Intent a = new Intent(signinpage.this, MenuPage.class);
                            a.putExtra("username" , User);
                            startActivity(a);
                            return;
                        }
                    }

                    if(c==0)
                    {
                        Toast.makeText(signinpage.this, "Entered Wrong Credentials...", Toast.LENGTH_SHORT).show();
                    }
                }
                if (add.equals("Owner")) {

                        if (User.equals("owner") && pass.equals("admin")) {
                            username.getEditText().setText("");
                            password.getEditText().setText("");
                            Intent a = new Intent(signinpage.this, OwnerMenuPage.class);
                            startActivity(a);
                        }
                        else
                            Toast.makeText(signinpage.this, "Entered Wrong Credentials...", Toast.LENGTH_SHORT).show();
                    }
                    if(add.equals("null"))
                        Toast.makeText(signinpage.this, "Entered Wrong Credentials...", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("on Back Pressed" ," nhi jaana");
    }

}
