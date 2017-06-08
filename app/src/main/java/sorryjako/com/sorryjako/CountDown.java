package sorryjako.com.sorryjako;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CountDown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down);


        //odpočet, poté zavolat intent:
        Intent i = new Intent(getApplicationContext(), Game.class);
        startActivityForResult(i, 5);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 5) {
            if(resultCode == RESULT_OK) {
                Intent ii = new Intent();
                ii.putExtra("winner", data.getStringExtra("winner"));
                ii.putExtra("scoreTOP", data.getStringExtra("scoreTOP"));
                ii.putExtra("scoreBOT", data.getStringExtra("scoreBOT"));
                setResult(RESULT_OK, ii);
                finish();
            }
        }
    }
}
