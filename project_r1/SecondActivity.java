package com.example.project_r1;

/**
 Täällä lueteltu aktiviteetit (5), ja niitä klikkaamalla aukeaa third main.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private Lista lista = Lista.getInstance();

    /**
     * Luo näkymän
     * @param savedInstanceState hakee mahdolliset tallennetut datat
     * Laittaa Lista-singletonin listan ListViewiin.
     * Tekee kuuntelijan ListViewille joka kuuntelee mitä listan osista painetaan, joten ohjelma tietää mitä viedä seuraavaan Activityyn.
     * Tallentaa nextActivitylle i:n avaimellelle "painettuArvo", joka on monesko alkio listassa on painettu.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ListView lv = findViewById(R.id.Lista);
        lv.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                Lista.getInstance().getLista()));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "onItemClick(" + i + ")");
                Intent nextActivity = new Intent(SecondActivity.this, ThirdActivity.class);
                nextActivity.putExtra("painettuArvo",i);
                startActivity(nextActivity);
            }
        });


    }

    /**
     * kun ohjelma pysäytetään se tallentaa Lista-singletonin arvot muistiin.
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



