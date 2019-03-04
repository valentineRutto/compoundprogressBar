package com.valentinerutto.compoundprogressbar;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private int hStatus = 0, cStatus = 0;
    private Handler handler = new Handler();
    ProgressBar hProgress, pBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hProgress = findViewById(R.id.progress_horizontal);
        pBar = findViewById(R.id.pBar);
        hProgress.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));


    }

    @Override
    protected void onStart() {
        super.onStart();
        lineProgressBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lineProgressBar();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lineProgressBar();

    }

    private void lineProgressBar() {
        new Thread(new Runnable() {
            public void run() {
                while (hStatus <= 100) {
                    hStatus += 1;
                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            hProgress.setProgress(hStatus);
                            if (hStatus == 100 && cStatus == 0) {
                                circularProgressBar();
                            }
                        }
                    });

                    try {

                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    public void circularProgressBar() {
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                while (cStatus < 100) {
                    cStatus += 2;
                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            pBar.setProgress(cStatus);
                            if (cStatus == 100) {
                                lineProgressBar();

                            } else {
                                pBar.setProgress(cStatus);
                            }
                        }
                    });
                    try {


                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hStatus = 0;
        cStatus = 0;
        lineProgressBar();
    }
}
