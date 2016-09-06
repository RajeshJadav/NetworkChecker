package com.fabcoder.networkchecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternetConnection();
            }
        });
    }

    private void checkInternetConnection() {
        if (NetworkUtils.isInternetAvailable(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (NetworkUtils.isInternetActiveWithPing() == NetworkUtils.STATUS_CONNECTED) {
                        performNetworkingOperations();
                    } else {
                        if (NetworkUtils.isInternetActiveWithInetAddress()) {
                            performNetworkingOperations();
                        } else {
                            displayConnectionMessage();
                        }
                    }
                }
            }).start();

        } else {
            displayConnectionMessage();
        }
    }

    private void performNetworkingOperations() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Internet is Available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayConnectionMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NetworkUtils.displayInternetConnectionMessage(MainActivity.this);
            }
        });
    }
}
