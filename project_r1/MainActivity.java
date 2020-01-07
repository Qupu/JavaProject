package com.example.project_r1;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int laskettava;
    private Lista lista = Lista.getInstance();
    private Button startButton;
    private Button pauseButton;
    private TextView aika;
    private Handler customHandler = new Handler();
    private long startTime = 0L;
    long timeSwapBuff = 0L;
    long elapsedTime = 0L;
    long updatedTime = 0L;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private SharedPreferences pref;

    /**
     *
     * @param savedInstanceState hakee mahdolliset tallennetut datat
     * Asettaa ajastimen nollaksi jos ei löydy tallennettua dataa
     * Laittaa kuunitelian startButtonille
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences shared = getSharedPreferences("info", MODE_PRIVATE);
        Lista.getInstance().getLista().get(0).setArvo(shared.getInt("eka", 0));
        Lista.getInstance().getLista().get(1).setArvo(shared.getInt("toka", 0));
        Lista.getInstance().getLista().get(2).setArvo(shared.getInt("kolmas", 0));
        Lista.getInstance().getLista().get(3).setArvo(shared.getInt("neljäs", 0));
        Lista.getInstance().getLista().get(4).setArvo(shared.getInt("viides", 0));
        setContentView(R.layout.activity_main);
        aika = (TextView) findViewById(R.id.aika);
        aika.setText("00:00:00");
        startButton = (Button) findViewById(R.id.button2);
        startButton.setOnClickListener(new View.OnClickListener() {
            //Painamalla käynnistä ajastin ajastin lähtee käyntiin
            //Tiettyä radioButtonia valittaessa kyseisen aktiviteetin aika lisääntyy

            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(ajastinKayntiin, 0);
                switch (laskettava) {
                    case 0:
                        Lista.getInstance().getLista().get(0).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(0).getArvo());
                        break;
                    case 1:
                        Lista.getInstance().getLista().get(1).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(1).getArvo());
                        break;
                    case 2:
                        Lista.getInstance().getLista().get(2).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(2).getArvo());
                        break;
                    case 3:
                        Lista.getInstance().getLista().get(3).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(3).getArvo());
                        break;
                    case 4:
                        Lista.getInstance().getLista().get(4).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(4).getArvo());
                        break;
                }
            }
        });

        // Laittaa kuunitelian pauseButtonille
        // Painamalla pysäytä ajastin ajastin pysähtyy ja samalla aika nollaantuu ja tallentaa ajan kyseisellä hetkellä valittuun aktiviteettiin

        pauseButton = (Button) findViewById(R.id.button4);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                customHandler.removeCallbacks(ajastinKayntiin);
                switch (laskettava){
                    case 0: Lista.getInstance().getLista().get(0).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(0).getArvo()); break;
                    case 1: Lista.getInstance().getLista().get(1).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(1).getArvo()); break;
                    case 2: Lista.getInstance().getLista().get(2).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(2).getArvo()); break;
                    case 3: Lista.getInstance().getLista().get(3).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(3).getArvo()); break;
                    case 4: Lista.getInstance().getLista().get(4).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(4).getArvo()); break;
                }
                elapsedTime = 0L;
                aika.setText("00:00:00");
            }
        });
    }

    /**
     *
     * @param view tuo nykyisen näkymän
     * Metodi jota tuloksetButton käyttää
     * Tallentaa ajastimella olevan ajan sille aktiviteetille joka on valittuna
     * Siirrytään toiselle sivulle ja samalla nollataan ajastin
     */
    public void openMain2(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        switch (laskettava){
            case 0: Lista.getInstance().getLista().get(0).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(0).getArvo()); break;
            case 1: Lista.getInstance().getLista().get(1).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(1).getArvo()); break;
            case 2: Lista.getInstance().getLista().get(2).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(2).getArvo()); break;
            case 3: Lista.getInstance().getLista().get(3).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(3).getArvo()); break;
            case 4: Lista.getInstance().getLista().get(4).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(4).getArvo()); break;
        }
        customHandler.removeCallbacks(ajastinKayntiin);
        elapsedTime = 0L;
        aika.setText("00:00:00");
        TextView editext = (TextView) findViewById(R.id.aika);
        String message = editext.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /**
     * Luo ajastimen
     */
    private Runnable ajastinKayntiin = new Runnable() {
        public void run() {
            elapsedTime = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + elapsedTime;
            // muuttaa ajan halutun näköiseksi tallennetusta arvosta
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int hours = mins / 60;
            String curTime = (String.format("%02d", hours) + ":"
                    + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            aika.setText(curTime);
            // Ajastin toimii millisekunnin tarkkuudella
            customHandler.postDelayed(this, 0L);
        }
    };

    /**
     *
     * @param view tuo nykyisen näkymän
     * Kun valitaan tietty radioButton niin aika tallentuu edellä valittuun radioButtoniin ja ajastin nollaantuu
     * Aika lähtee heti nollasta käyntiin
     * Laskettava pitää kirjaa mikä radioButtonista on valittuna
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked)
                    Lista.getInstance().getLista().get(laskettava).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(laskettava).getArvo());
                startTime = SystemClock.uptimeMillis();
                laskettava=0;
                break;
            case R.id.radioButton2:
                if (checked)
                    Lista.getInstance().getLista().get(laskettava).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(laskettava).getArvo());
                startTime = SystemClock.uptimeMillis();
                laskettava=1;
                break;
            case R.id.radioButton3:
                if (checked)
                    Lista.getInstance().getLista().get(laskettava).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(laskettava).getArvo());
                startTime = SystemClock.uptimeMillis();
                laskettava=2;
                break;
            case R.id.radioButton4:
                if (checked)
                    Lista.getInstance().getLista().get(laskettava).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(laskettava).getArvo());
                startTime = SystemClock.uptimeMillis();
                laskettava=3;
                break;
            case R.id.radioButton5:
                if (checked)
                    Lista.getInstance().getLista().get(laskettava).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(laskettava).getArvo());
                startTime = SystemClock.uptimeMillis();
                laskettava=4;
                break;
        }
        elapsedTime = 0L;
    }

    /**
     *
     * @param v tuo nykyisen näkymän
     * Kun Nollaa kaikki buttonia painetaan niin nollaantuu kaikki tallennettu tieto sekä ajastin nollaantuu
     */

    public void buttonPressedReset (View v) {
        Log.d("Pawan", "buttonPressedReset()");
        Lista.getInstance().getLista().get(0).setArvo(0);
        Lista.getInstance().getLista().get(1).setArvo(0);
        Lista.getInstance().getLista().get(2).setArvo(0);
        Lista.getInstance().getLista().get(3).setArvo(0);
        Lista.getInstance().getLista().get(4).setArvo(0);
        aika.setText("00:00:00");
        elapsedTime = 0L;
        laskettava = 0;
        RadioButton test = (RadioButton) findViewById(R.id.radioButton);
        test.setChecked(true);
    }

    /**
     * Kun ohjelma lopetetaan kaikki tieto tallentuu avaimeen "info"
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d("SANTERI1234", "OnStop");

        pref = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        switch (laskettava){
            case 0: Lista.getInstance().getLista().get(0).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(0).getArvo()); break;
            case 1: Lista.getInstance().getLista().get(1).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(1).getArvo()); break;
            case 2: Lista.getInstance().getLista().get(2).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(2).getArvo()); break;
            case 3: Lista.getInstance().getLista().get(3).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(3).getArvo()); break;
            case 4: Lista.getInstance().getLista().get(4).setArvo((int) elapsedTime / 1000 + Lista.getInstance().getLista().get(4).getArvo()); break;
        }

        editor.putInt("eka", Lista.getInstance().getLista().get(0).getArvo());
        editor.putInt("toka", Lista.getInstance().getLista().get(1).getArvo());
        editor.putInt("kolmas", Lista.getInstance().getLista().get(2).getArvo());
        editor.putInt("neljäs", Lista.getInstance().getLista().get(3).getArvo());
        editor.putInt("viides", Lista.getInstance().getLista().get(4).getArvo());

        editor.commit();
    }

}




















