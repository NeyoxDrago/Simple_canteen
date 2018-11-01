package app.sample.app.android_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView student , owner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student = findViewById(R.id.student);
        owner = findViewById(R.id.Owner);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("Student");
            }
        });

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send("Owner");
            }
        });

    }

    public void send(String text) {

        Intent a = new Intent(MainActivity.this , signinpage.class);
        a.putExtra("text",text);
        startActivity(a);
    }

    @Override
    public void onBackPressed() {

    }
}
