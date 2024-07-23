package alex.com.juego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnJuegofacil, btnJuegodificil, btnJuegoEpico;
    private Button btnInstrucciones;
    private Button btnContadortiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInstrucciones = findViewById(R.id.btn_instrucciones);
        btnJuegofacil = findViewById(R.id.btn_juegofacil);
        btnJuegodificil = findViewById(R.id.btn_juegodificil);
        btnJuegoEpico = findViewById(R.id.btn_juegoepico);

        btnInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                MainActivity.this,
                                InstruccionesActivity.class
                        )
                );
            }
        });


        btnJuegofacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                MainActivity.this,
                                JuegoFacilActivity.class
                        )
                );
            }
        });
        btnJuegodificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                MainActivity.this,
                                JuegodificilActivity.class
                        )
                );
            }
        });
        btnJuegoEpico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                MainActivity.this,
                                JuegoEpicoActivity.class
                        )
                );
            }
        });


    }
}