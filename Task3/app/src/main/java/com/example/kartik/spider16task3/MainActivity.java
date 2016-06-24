package com.example.kartik.spider16task3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.os.Handler;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SensorEventListener
{
    ViewFlipper viewFlipper;
    Button buttonss;
    Button buttonstop;
    Button buttonstart;
    Button enable;
    Button disable;
    Spinner spinner;
    private TextView time;
    private long diff = 0;
    private long start_time = 0;
    private int count;
    public String track;
    PlayTrack p;
    private float dist = 0;
    SensorManager sm;
    private boolean enabled_slide = false,playing = false;
    int images[] = {R.drawable.dhawan,R.drawable.rohit, R.drawable.kohli, R.drawable.raina, R.drawable.yuvi, R.drawable.dhoni, R.drawable.pandya,
            R.drawable.jadeja, R.drawable.ashwin, R.drawable.nehra, R.drawable.bumrah};

    private Handler Handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            //super.handleMessage(msg);
            diff = SystemClock.uptimeMillis() - start_time;
            int ms = (int) diff % 1000;
            int secs = (int) (diff / 1000) % 60;
            int mins = (int) (diff / 60000) % 60;
            time.setText(Integer.toString(mins) + " : " + String.format("%02d", secs) + " : " + String.format("%03d", ms));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonss = (Button) findViewById(R.id.buttonss);
        buttonstop = (Button) findViewById(R.id.buttonstop);
        buttonstart = (Button) findViewById(R.id.buttonstart);
        time = (TextView) findViewById(R.id.time);
        enable = (Button) findViewById(R.id.enable);
        disable = (Button) findViewById(R.id.disable);
        disable.setEnabled(false);

        spinner = (Spinner) findViewById(R.id.tracks);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.tracks, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper1);
        for (int i = 0; i < images.length; i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setFlipperImage(images[i]);
        }

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sm.getSensorList(Sensor.TYPE_PROXIMITY).size()!=0)
        {
            Sensor s = sm.getSensorList(Sensor.TYPE_PROXIMITY).get(0);
            //Toast.makeText(MainActivity.this,"Maximum range is "+Float.toString(s.getMaximumRange()),Toast.LENGTH_SHORT).show();
            sm.registerListener((SensorEventListener) this,s,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
        {
            Toast.makeText(MainActivity.this,"Sorry, but your phone doesn't have a proximity sensor",Toast.LENGTH_SHORT).show();
        }

        //startTimerThread();

        buttonss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               /* if(count < images.length)
                {
                    viewFlipper.setFlipInterval(3000);
                    viewFlipper.startFlipping();
                    count++;
                }
                else
                {
                    viewFlipper.stopFlipping();
                }*/
                for(count = 0; count < images.length; count++)
                {
                    viewFlipper.setFlipInterval(3000);
                    viewFlipper.startFlipping();
                }
                Runnable timer = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        while (diff < 3000*images.length)
                        {
                            try
                            {
                                Thread.sleep(1);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "InterruptedException caught", Toast.LENGTH_SHORT).show();
                            }
                            Handle.sendEmptyMessage(0);
                        }
                    }
                };
                start_time = SystemClock.uptimeMillis();
                Thread time = new Thread(timer);
                time.start();
            }
        });

        buttonstart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                p = new PlayTrack(MainActivity.this, buttonstart, buttonstop);
                p.execute(track);
                buttonstart.setEnabled(false);
                buttonstop.setEnabled(true);
                playing = true;
            }
        });

        buttonstop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                p.cancel(true);
                p.mp.stop();
                p.mp.release();
                buttonstart.setEnabled(true);
                buttonstop.setEnabled(false);
                playing = false;
            }
        });

        enable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                enabled_slide = true;
                enable.setEnabled(false);
                buttonss.setEnabled(false);
                disable.setEnabled(true);
            }
        });

        //Disable the proximity sensor
        disable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                enabled_slide = false;
                enable.setEnabled(true);
                buttonss.setEnabled(true);
                disable.setEnabled(false);
            }
        });
    }

    private void setFlipperImage(int res)
    {
            //Log.i("Set Flipper Called", res + "");
            ImageView image = new ImageView(getApplicationContext());
            image.setBackgroundResource(res);
            viewFlipper.addView(image);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        track = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        //not needed
    }

    /* private void startTimerThread() {
        Thread th = new Thread(new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            time.setText(""+((System.currentTimeMillis()-startTime)/1000));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        });
        th.start();
    }*/


    @Override
    protected void onPause()
    {
        super.onPause();
        //Toast.makeText(MainActivity.this,"The song isCancelled value was "+p.isCancelled(), Toast.LENGTH_SHORT).show();
        if(playing)
        {
            p.cancel(true);
            p.mp.stop();
            p.mp.release();
        }
        //Unregister the listener for the sensor
        sm.unregisterListener(MainActivity.this);
        //Toast.makeText(MainActivity.this,"The song isCancelled value is "+p.isCancelled(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        dist = event.values[0];
        if(enabled_slide)
        {
            //Toast.makeText(MainActivity.this, "Dist now is " + Float.toString(dist), Toast.LENGTH_SHORT).show();
            if(dist<1.0)
            {
                for (int i = 0; i < images.length; i++)
                {
                    //  This will create dynamic image view and add them to ViewFlipper
                    setFlipperImage(images[i]);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //Not needed
    }
}



