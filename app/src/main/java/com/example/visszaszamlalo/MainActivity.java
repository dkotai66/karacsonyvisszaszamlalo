package com.example.visszaszamlalo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView szamlalalo;
    private Timer timer;
    private Date karacsony;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        szamlalalo = findViewById(R.id.szamlalo);
        Calendar most = Calendar.getInstance();
        int ev = most.get(Calendar.YEAR);
        int honap = most.get(Calendar.MONTH);
        int nap = most.get(Calendar.DATE);

        if (honap==11 && nap>=24){
            ev++;
        }
        Calendar karacsony_calendar = Calendar.getInstance();
        karacsony_calendar.set(ev, 11, 24, 0, 0, 0);
        karacsony = karacsony_calendar.getTime();
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date most = Calendar.getInstance().getTime();
                long hatralevoido = karacsony.getTime()-most.getTime();

                long masodPercMili= 1000;
                long percMili = masodPercMili * 60;
                long oraMili = percMili * 60;
                long napMili = oraMili * 24;

                long nap = hatralevoido / napMili;
                hatralevoido = hatralevoido % napMili;
                long ora = hatralevoido / oraMili;
                hatralevoido = hatralevoido % oraMili;
                long perc = hatralevoido / percMili;
                hatralevoido = hatralevoido % percMili;
                long masodperc = hatralevoido / masodPercMili;

                String hatralevoSzoveg = getString(R.string.szamlaloFormatum, nap, ora, perc, masodperc);
                runOnUiThread(() -> szamlalalo.setText(hatralevoSzoveg));

            }
        };
        timer.schedule(task, 0, 500);
    }
}