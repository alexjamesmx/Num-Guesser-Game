package mx.edu.uteq.dapps.baseprcticas56y7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnJuegofacil, btnJuegodificil;
    private Button btnInstrucciones;
    private Button btnContadortiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInstrucciones = findViewById(R.id.btn_instrucciones);
        btnContadortiempo = findViewById(R.id.btn_contadortiempo);
        btnJuegofacil = findViewById(R.id.btn_juegofacil);
        btnJuegodificil = findViewById(R.id.btn_juegodificil);

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

        btnContadortiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                MainActivity.this,
                                ContadorTiempoActivity.class
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


    }
}