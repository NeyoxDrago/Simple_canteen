package app.sample.app.android_project;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivty extends AppCompatActivity {

    private TextInputLayout name , pass , year , roll;
    private Button login;
    sql db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        db = new sql(this);

        name = findViewById(R.id.name);
        pass = findViewById(R.id.loginpassword);
        year = findViewById(R.id.year);
        roll = findViewById(R.id.rollnumber);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getEditText().getText().toString().trim();
                String password = pass.getEditText().getText().toString().trim();
                String Year = year.getEditText().getText().toString().trim();
                String Roll = roll.getEditText().getText().toString().trim();

                Cursor res = db.getData();
                int c= 0;
                while(res.moveToNext())
                {
                    if(username.equals(res.getString(1)))
                    {
                        Toast.makeText(LoginActivty.this, "Error... This user already Exists.", Toast.LENGTH_SHORT).show();
                        c=1;
                    }
                }
                if(c==0)
                {
                    if(db.insert(username,password,Year,Roll))
                    {
                        Intent a = new Intent(LoginActivty.this , MenuPage.class);
                        startActivity(a);
                    }
                }

            }
        });

    }
}
