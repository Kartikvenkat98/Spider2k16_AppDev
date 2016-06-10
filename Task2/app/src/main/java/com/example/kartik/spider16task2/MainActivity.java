package com.example.kartik.spider16task2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private String name, rollno, dept;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.department);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.Departments,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name = ((EditText) findViewById(R.id.name)).getText().toString();
                rollno = ((EditText) findViewById(R.id.rollno)).getText().toString();

                boolean algos, app, web, tronix, mentor;
                web = ((CheckBox) findViewById(R.id.web)).isChecked();
                app = ((CheckBox) findViewById(R.id.app)).isChecked();
                algos = ((CheckBox) findViewById(R.id.algos)).isChecked();
                tronix = ((CheckBox) findViewById(R.id.tronix)).isChecked();
                mentor = ((Switch) findViewById(R.id.mentor)).isChecked();

                if(name.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Enter your name",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(rollno.equals(""))
                    {
                        Toast.makeText(MainActivity.this,"Enter your roll number",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(!algos && !app && !web && !tronix)
                        {
                            Toast.makeText(MainActivity.this,"Select at least 1 profile to apply for the inductions",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append("Are you sure you want to submit ?");
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(buffer.toString()).setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int t)
                                {
                                    Intent i = new Intent(MainActivity.this,MainActivity2.class);
                                    i.putExtra("name",name);
                                    startActivity(i);
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int t)
                                {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.setTitle("Check Details");
                            alert.show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        dept = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        Toast.makeText(MainActivity.this,"Select a department :(",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}