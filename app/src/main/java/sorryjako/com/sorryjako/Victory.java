package sorryjako.com.sorryjako;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Victory extends AppCompatActivity {

    static MediaPlayer mp;
    ImageButton restart;

    String scoreTOP;
    String scoreBOT;
    String winner;

    TextView tScoreBOT;
    TextView elseBOT;
    TextView winBOT;

    TextView tScoreTOP;
    TextView elseTOP;
    TextView winTOP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.victory);

        mp = MediaPlayer.create(getApplicationContext(),R.raw.finish);
        mp.start();

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        if(bundle != null) {
            scoreBOT = (String) bundle.get("scoreBOT");
            scoreTOP = (String) bundle.get("scoreTOP");
            winner = (String) bundle.get("winner");
            //Toast.makeText(getApplicationContext(), "BOT: " + scoreBOT + " TOP: " + scoreTOP + " winner: " + winner, Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getApplicationContext(), "Sorry jako, problém", Toast.LENGTH_SHORT).show();
        }

        tScoreBOT = (TextView) findViewById(R.id.id_victory_scoreBOT_TV);
        elseBOT = (TextView) findViewById(R.id.id_victory_elseBOT_TV);
        winBOT = (TextView) findViewById(R.id.id_victory_winBOT_TV);

        tScoreTOP = (TextView) findViewById(R.id.id_victory_scoreTOP_TV);
        elseTOP = (TextView) findViewById(R.id.id_victory_elseTOP_TV);
        winTOP = (TextView) findViewById(R.id.id_victory_winTOP_TV);

        restart = (ImageButton) findViewById(R.id.id_victory_restart_BT);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        if(winner.equals("B"))  {
            winBOT.setText("Vyhrává MODRÝ");
            winTOP.setText("Vyhrává MODRÝ");
            elseBOT.setText("ZÍSKÁVÁŠ DOTACI VE VÝŠI 10 MILIONŮ");
            elseTOP.setText("Bude líp");
            tScoreBOT.setText(scoreBOT + "/" + scoreTOP);
            tScoreTOP.setText(scoreTOP + "/" + scoreBOT);
        } else  {
            winBOT.setText("Vyhrává ČERVENÝ");
            winTOP.setText("Vyhrává ČERVENÝ");
            elseBOT.setText("Bude líp");
            elseTOP.setText("ZÍSKÁVÁŠ DOTACI VE VÝŠI 10 MILIONŮ");
            tScoreBOT.setText(scoreBOT + "/" + scoreTOP);
            tScoreTOP.setText(scoreTOP + "/" + scoreBOT);
        }
    }
}
