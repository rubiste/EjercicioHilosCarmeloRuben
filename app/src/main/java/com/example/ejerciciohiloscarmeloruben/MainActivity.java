package com.example.ejerciciohiloscarmeloruben;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int contThread =0, contAsync = 0;
    TextView tvContThread, tvContAsync;
    Button btThread, btAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initComponents();
    }

    private void initComponents() {
        tvContAsync = findViewById(R.id.tvContAsyn);
        tvContThread = findViewById(R.id.tvContThread);
        btThread = findViewById(R.id.btThread);
        btThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contThread == 0){
                    MiHilo hilo = new MiHilo();
                    hilo.start();
                }
            }
        });

        btAsync = findViewById(R.id.btAsync);
        btAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contAsync == 0){
                    new MiAsyncTask().execute();
                    tvContAsync.setText(""+contAsync);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void espera(int tiempo){
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class MiHilo extends Thread{
        @Override
        public void run() {
            for (int i=1; i<=11; i++){
                espera(1000);
                tvContThread.post(new Runnable() {
                    @Override
                    public void run() {
                        contThread++;
                        tvContThread.setText(""+ contThread);
                    }
                });
            }
            contThread = -1;
        }
    }

    private class MiAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i=1; i<=11; i++){
                espera(1000);
                tvContAsync.post(new Runnable() {
                    @Override
                    public void run() {
                        contAsync++;
                        tvContAsync.setText(""+ contAsync);
                    }
                });
            }
            contAsync = -1;
            return null;
        }
    }
}
