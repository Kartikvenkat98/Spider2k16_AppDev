package com.example.kartik.spider16task2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;


public class MainActivity2 extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        ((TextView) findViewById(R.id.resp)).setText("Your response has been recorded !!!" + "\n" + "Thank You " + name + " :)");

        ((Button) findViewById(R.id.goback)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent inte = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(inte);
            }
        });

        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        String ts = timestamp.toString();
        ((TextView) findViewById(R.id.timeStamp)).setText(ts);
    }

}