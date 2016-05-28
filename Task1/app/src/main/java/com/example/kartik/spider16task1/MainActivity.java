package com.example.kartik.spider16task1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = 0;

        TextView text = (TextView) findViewById(R.id.textView);
        String string = "You have clicked the button " + Integer.toString(count) + " times";
        text.setText(string);


        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String string;
                TextView text = (TextView) findViewById(R.id.textView);
                count++;
                string = "You have clicked the button " + Integer.toString(count) + " times";
                text.setText(string);
            }
        });
    }
}
