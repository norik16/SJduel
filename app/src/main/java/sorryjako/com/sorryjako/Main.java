package sorryjako.com.sorryjako;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;

public class Main extends AppCompatActivity {
    static final int floatingPeriod = 1000;
    static final float floatingFactor = 0.1f;

    ImageButton play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        Settings.System.putInt( getApplicationContext().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, false ? 1 : 0);

        play = (ImageButton) findViewById(R.id.id_main_play_BT);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CountDown.class);
                startActivityForResult(i, 1);
            }
        });

        biggerFaces.run();
    }
    final Runnable biggerFaces = new Runnable() {
        @Override
        public void run() {
            makeBigger(play.animate());
        }

        private void makeBigger(ViewPropertyAnimator a) {
            a.scaleXBy(floatingFactor);
            a.scaleYBy(floatingFactor);
            a.setDuration(floatingPeriod);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(smallerFaces);
            }
            a.start();
        }
    };
    final Runnable smallerFaces = new Runnable() {
        @Override
        public void run() {
            makeBigger(play.animate());
        }

        private void makeBigger(ViewPropertyAnimator a) {
            a.scaleXBy(-floatingFactor);
            a.scaleYBy(-floatingFactor);
            a.setDuration(floatingPeriod);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                a.withEndAction(biggerFaces);
            }
            a.start();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                if(data.getStringExtra("winner") != null) {
                    Intent ii =new Intent(getApplicationContext(), Victory.class);
                    ii.putExtra("winner", data.getStringExtra("winner"));
                    ii.putExtra("scoreTOP", data.getStringExtra("scoreTOP"));
                    ii.putExtra("scoreBOT", data.getStringExtra("scoreBOT"));
                    startActivityForResult(ii, 2);
                }
            }
        }
        if(requestCode == 2) {
            if(resultCode == RESULT_OK) {
                    Intent iii = new Intent(getApplicationContext(), CountDown.class);
                    startActivityForResult(iii, 1);

            }
        }
    }
}