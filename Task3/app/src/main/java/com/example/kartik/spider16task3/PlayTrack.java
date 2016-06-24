package com.example.kartik.spider16task3;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by Kartik on 6/21/2016.
 */
public class PlayTrack extends AsyncTask<String,Void,String> implements MediaPlayer.OnCompletionListener
{

    //Declaring the variables
    MediaPlayer mp;
    Context context;
    Button button1;
    Button button2;

    //Constructor
    public PlayTrack(Context ctx,Button btn1,Button btn2)
    {
        this.context = ctx;
        this.button1 = btn1;
        this.button2 = btn2;
    }

    @Override
    protected String doInBackground(String... params)
    {
        String track = params[0];

        //Choose track
        switch (track)
        {
            case "Counting Stars":
                mp = MediaPlayer.create(context,R.raw.stars);
                break;
            case "Drome Waar":
                mp = MediaPlayer.create(context,R.raw.drome);
                break;
            case "Hall of Fame":
                mp = MediaPlayer.create(context,R.raw.hall);
                break;
            case "Heroes Tonight":
                mp = MediaPlayer.create(context,R.raw.heroes);
                break;
            case "The Nights":
                mp = MediaPlayer.create(context,R.raw.nights);
                break;
            default:
                Toast.makeText(context,"Check the song selected",Toast.LENGTH_SHORT).show();
        }
        mp.start();
        mp.setOnCompletionListener(this);
        long progress = mp.getCurrentPosition();
        while(progress<mp.getDuration() && mp.isPlaying() && !isCancelled())
        {
            progress = mp.getCurrentPosition();
            if(isCancelled())
            {
                progress = mp.getDuration();
                //Toast.makeText(context,"Task is being  cancelled",Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        Toast.makeText(context,"Started finished",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        Toast.makeText(context,"Track finished ",Toast.LENGTH_SHORT).show();
        button1.setEnabled(true);
        button2.setEnabled(false);
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();
        Toast.makeText(context,"Song was cancelled",Toast.LENGTH_SHORT).show();
        button1.setEnabled(true);
        button2.setEnabled(false);
    }
}