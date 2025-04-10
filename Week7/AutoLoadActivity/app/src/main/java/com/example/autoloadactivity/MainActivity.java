package com.example.autoloadactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new Thread(() -> {
            int n = 8;
            try {
                do {
                    if (n >= 2000) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish(); // Gọi finish() sau khi startActivity()
                        return;
                    }
                    Thread.sleep(100);
                    n += 100;
                } while (true);
            } catch (InterruptedException interruptedException) {
                MainActivity.this.finish();
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), HomeActivity.class);
                MainActivity.this.startActivity(intent);
            } catch (Throwable throwable) {
                MainActivity.this.finish();
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), HomeActivity.class);
                MainActivity.this.startActivity(intent);
                throw throwable;
            }
        }).start();

    }
}