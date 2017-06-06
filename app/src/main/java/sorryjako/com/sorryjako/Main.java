package sorryjako.com.sorryjako;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Main extends AppCompatActivity {

    ImageButton play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        play = (ImageButton) findViewById(R.id.id_main_play_BT);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Game.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Intent ii =new Intent(getApplicationContext(), Victory.class);
                ii.putExtra("winner", data.getStringExtra("winner"));
                ii.putExtra("scoreTOP", data.getStringExtra("scoreTOP"));
                ii.putExtra("scoreBOT", data.getStringExtra("scoreBOT"));
                startActivityForResult(ii, 2);
            }
        }
    }
}