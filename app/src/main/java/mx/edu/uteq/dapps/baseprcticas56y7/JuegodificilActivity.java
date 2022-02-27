package mx.edu.uteq.dapps.baseprcticas56y7;
//DEPOENDENCIAS Y/O LIBRERIAS
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import mx.edu.uteq.dapps.baseprcticas56y7.R;

public class JuegodificilActivity extends AppCompatActivity {
    //VARIABLES PRIMITIVAS
    private final int NUM_MIN = 1;
    private final int NUM_MAX = 30;
    private int NUMERO_ADIVINAR = (int) Math.floor(Math.random() * (NUM_MAX - NUM_MIN + 1) + NUM_MIN);
    private int vidas, contadorTiempo;

    //VARIABLES DE COMPONENTES
    private Timer temporizador;
    private TextView tvVidas, tvVidasExtra, tvTiempo;
    private ImageView ivMensaje;
    private TextView tvMensaje;
    private TextInputEditText tiet;
    private Button btnAdivinar, btnReset;
    private TextInputLayout tietBox;

    boolean isAllFieldsChecked = false;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_dificil);

        //VALOR ASIGNADOS DE COMPONENTES A VARIABLES
        tvVidas = findViewById(R.id.tvVidas);
        tvVidasExtra = findViewById(R.id.tvVidasExtra);
        ivMensaje = findViewById(R.id.ivMensaje);
        tvMensaje = findViewById(R.id.tvMensaje);
        btnAdivinar = findViewById(R.id.btnAdivinar);
        tiet = findViewById(R.id.tiet);
        btnReset = findViewById(R.id.btnReset);
        tietBox = findViewById(R.id.tietBox);

        temporizador = new Timer();
        contadorTiempo = 0;
        tvTiempo = findViewById(R.id.tv_tiempo);

        vidas = 4;

/*      MENSAJE DANDO LA RESOUESTA CORRECTA
        ESTA DISENADO PARA USO DE DESARROLLADORES
        Y VERIFICAR FACILMENTE
        LA FUNCIONALIDAD DEL JUEGO*/
        Snackbar.make(
                findViewById(android.R.id.content),
                "El numero a adivinar es " + NUMERO_ADIVINAR,
                Snackbar.LENGTH_INDEFINITE
        ).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();

//AL PRESIONAR EL BOTON DE ADIVINAR
        btnAdivinar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                //REALIZAMOS VALIDACION DE FORMULARIO EN ESTE CASO DE NUMERO INGRESADO
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    btnAdivinar.setText("GUESS");
                    if(contadorTiempo < 1){
                        iniciaConteo();
                    }
                    //SI ESTA CORRECTO ENTONCES INICIAMOS JUEGO

                    int numeroEscogido = Integer.parseInt(tiet.getText().toString());
                    tvMensaje.setError(null);

                    //SI EL NUMERO ES CORRECTO ENTONCES MOSTRAMOS NOTIFICACION DE EXITO Y CAMBIAMOS IMAGEN
                    if (numeroEscogido == NUMERO_ADIVINAR) {
                        stopConteo();
                        ivMensaje.setImageDrawable(getResources().getDrawable(R.drawable.check));
                        tvMensaje.setText("Yes!, secret number is " + NUMERO_ADIVINAR);
                        tiet.setText("");
                        btnAdivinar.setVisibility(View.GONE);
                        tiet.setVisibility(View.GONE);
                        tietBox.setVisibility(View.GONE);
                        btnReset.setVisibility(View.VISIBLE);
                        tvVidasExtra.setVisibility(View.VISIBLE);
                        tvVidasExtra.append(String.valueOf(vidas) + " lives still!\n\nCongratulations player");

                    }
                    //SI SE EQUIVOCA Y AUN CONSERVA VIDAS SIMPLEMENTE RESTAMOS UNA VIDA Y MOSTRAMOS MENSAJE DE "UPS"
                    else if (vidas > 1 && numeroEscogido != NUMERO_ADIVINAR) {
                        vidas--;
                        tiet.setText("");
                        tiet.setHint("Try another one");
                        ivMensaje.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
                        String numeroBuscadoEs = NUMERO_ADIVINAR > numeroEscogido ? "greater" : "less";
                        tvMensaje.setText("Sorry, the number is " + numeroBuscadoEs + " than " + numeroEscogido);
                        String textoVidas = "Lives: ";
                        for (int i = 0; i < vidas; i++) {
                            System.out.println("a");
                            textoVidas += "♥";
                        }
                        tvVidas.setText(textoVidas);
                    }
                    //SI EL NUMERO ES INCORRECTO Y NO TENEMOS MAS VIDAS ENTONCES MOSTRAMOS NOTIFICACION DE FAIL Y CAMBIAMOS IMAGEN
                    else {
                        stopConteo();
                        String textoVidas = "Lives: ";
                        tvVidas.setText(textoVidas);
                        ivMensaje.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
                        tvMensaje.setText("No!, secret number is " + NUMERO_ADIVINAR);
                        tiet.setText("");
                        btnAdivinar.setVisibility(View.GONE);
                        tiet.setVisibility(View.GONE);
                        tietBox.setVisibility(View.GONE);
                        btnReset.setVisibility(View.VISIBLE);
                        tvVidasExtra.setText("You lost the GAME");
                        tvVidasExtra.setTextAppearance(R.style.boldText);
                        tvVidasExtra.setVisibility(View.VISIBLE);
                        tvVidasExtra.append("\n0 lives remaining!");
                    }
                }
            }
        });

        /*//UNA VEZ TERMINADO EL JUEGO DAMOS LA POSIBILIDAD DE COMENZAR DE NUEVO DESDE LA MISMA PANTALLA
        ESTO LO LOGRAMOS RESTEANDO LA ACTIVIDAD Y QUITANDO ANIMACIONES PARA DAR UNA APARIENCIA
                DE RESETEO DE VALORES EN CAMPOS Y NO DE LA PANTALLA COMO TAL*/
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        });

    }

    //METODO DE COMPROBACION  CON MENSAJES DE ERROR
    private boolean CheckAllFields() {
        if (Objects.requireNonNull(tiet.getText()).toString().equals("")) {
            tvMensaje.setError("");
            tvMensaje.setText("Please, enter right numbers ...");
            return false;
        } else if (Integer.parseInt(tiet.getText().toString()) > 30 || Integer.parseInt(tiet.getText().toString()) < 0) {
            tvMensaje.setError("");
            tvMensaje.setText("Please, enter right numbers ...");
            return false;
        } else
            // after all validation return true.
            return true;
    }

    //METODO PARA INICIAR TEMPORIZADOR
    private void iniciaConteo() {
        temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        contadorTiempo++;
                        tvTiempo.setText(String.valueOf(contadorTiempo));

                   /*     SI EL TIEMPO EXCEDE 1 MIN SE PARA EL TEMPORIZADOR Y MANDAMOS MENSAJE
                                DE "PERDIO EL JUEGO"*/
                        if (contadorTiempo == 60) {
                            stopConteo();
                            ivMensaje.setImageDrawable(getResources().getDrawable(R.drawable.cancel));
                            tvMensaje.setText("RUN OUT OF TIME");
                            tiet.setText("");
                            btnAdivinar.setVisibility(View.GONE);
                            tiet.setVisibility(View.GONE);
                            tietBox.setVisibility(View.GONE);
                            btnReset.setVisibility(View.VISIBLE);
                            tvVidasExtra.setText("You lost the GAME");
                            tvVidasExtra.setTextAppearance(R.style.boldText);
                            tvVidasExtra.setVisibility(View.VISIBLE);
                            tvVidasExtra.append("\n 0 SECONDS left!");
                        }
                    }
                });
            }
        }, 1000, 1000);
    }

//METODO DE PARAR EL TEMPORIZADOR Y LO DEJAMOS EN 60 SEGUNDOS
    public void stopConteo() {
        temporizador.cancel();
        contadorTiempo = 60;
        tvTiempo.setText(String.valueOf(contadorTiempo));
    }

}




