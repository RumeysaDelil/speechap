package com.example.speechtotext;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
		// Thread haz�rlan�yor
        Thread thread = new Thread() {
 
            @Override
            public void run() {
 
                try {
                    synchronized (this) {
                        // Uygulama 4 saniye ayn� ekranda bekliyor
                        wait(2000);
                    }
                } catch (InterruptedException e) {
 
                    // Hata y�netimi
 
                } finally {
 
                    finish();
 
                    // Yeni a��lmak istenen Intent
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),Anasayfa.class);
                    startActivity(intent);
 
                }
 
            }
        };
 
        // Thread ba�lat�l�yor
        thread.start();
	}

		
		
}
	

