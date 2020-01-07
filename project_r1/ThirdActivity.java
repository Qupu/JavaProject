package com.example.project_r1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    private int indeksi;
    private Lista lista = Lista.getInstance();
    private SharedPreferences pref;

    /**
     *
     * @param savedInstanceState hakee tallenetun datan
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Haetaan avaimella "painettuArvo" oleva tieto
        Bundle b = getIntent().getExtras();
        int i = b.getInt("painettuArvo", 0);
        indeksi = i;
        int secs = Lista.getInstance().getLista().get(i).getArvo();
        int mins = secs / 60;
        secs = secs % 60;
        int hours = mins / 60;
        // Haetun tiedon arvo muutetaan muotoon hh.mm.ss
        String curTime = (String.format("%02d", hours) + ":"
                + String.format("%02d", mins) + ":"
                + String.format("%02d", secs));
        String a = Lista.getInstance().getLista().get(i).getNimi();
        // Haetun tiedon nimi tulee näkyviin
        ((TextView)findViewById(R.id.Nimi))
                .setText(a);
        // Haetun tiedon arvo tulee näkyviin
        ((TextView)findViewById(R.id.Arvo))
                .setText(curTime);
    }

    /**
     *
     * @param v tuo nykyisen näkymän
     * Kun painetaan resetButtonia niin kyseisen aktiviteetin arvo nollaantuu
     */
    public void buttonPressedReset (View v) {
        Log.d("Pawan", "buttonPressedReset()");
        Lista.getInstance().getLista().get(indeksi).setArvo(0);
        ((TextView)findViewById(R.id.Arvo))
                .setText("00:00:00");
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

        editor.putInt("eka", Lista.getInstance().getLista().get(0).getArvo());
        editor.putInt("toka", Lista.getInstance().getLista().get(1).getArvo());
        editor.putInt("kolmas", Lista.getInstance().getLista().get(2).getArvo());
        editor.putInt("neljäs", Lista.getInstance().getLista().get(3).getArvo());
        editor.putInt("viides", Lista.getInstance().getLista().get(4).getArvo());

        editor.commit();
    }
}
