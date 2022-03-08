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

public class JuegoEpicoActivity extends AppCompatActivity {
    //VARIABLES PRIMITIVAS
    private final int NUM_MIN = 1;
    private final int NUM_MAX = 50;
    private int NUMERO_ADIVINAR = 0;
    private int vidas, contadorTiempo, numeroJuegos,    primerjuego = 0;

    //VARIABLES DE COMPONENTES
    private Timer temporizador;
    private TextView tvVidas, tvVidasExtra, tvTiempo, tvJuegos;
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
        setContentView(R.layout.activity_juego_epico);

        //VALOR ASIGNADOS DE COMPONENTES A VARIABLES
        tvVidas = findViewById(R.id.tvVidas);
        tvVidasExtra = findViewById(R.id.tvVidasExtra);
        ivMensaje = findViewById(R.id.ivMensaje);
        tvMensaje = findViewById(R.id.tvMensaje);
        btnAdivinar = findViewById(R.id.btnAdivinar);
        tiet = findViewById(R.id.tiet);
        btnReset = findViewById(R.id.btnReset);
        tietBox = findViewById(R.id.tietBox);
        tvJuegos = findViewById(R.id.tvJuegos);

        temporizador = new Timer();
        contadorTiempo = 30;
        tvTiempo = findViewById(R.id.tv_tiempo);
        vidas = 5;

        NUMERO_ADIVINAR = (int) Math.floor(Math.random() * (NUM_MAX - NUM_MIN + 1) + NUM_MIN);
/*      MENSAJE DANDO LA RESOUESTA CORRECTA
        ESTA DISENADO PARA USO DE DESARROLLADORES
        Y VERIFICAR FACILMENTE
        LA FUNCIONALIDAD DEL JUEGO*/

        //ESTE METODO MUESTRA EL TOAST PARA FACILIDAD EN DESARROLLO
     numeroAdivinar();

//AL PRESIONAR EL BOTON DE ADIVINAR
        btnAdivinar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                //REALIZAMOS VALIDACION DE FORMULARIO EN ESTE CASO DE NUMERO INGRESADO

                if (isAllFieldsChecked) {
                    btnAdivinar.setText("GUESS");

                    //ESTE IF FUNCIONA UNA SOLA VEZ AL INICIO DEL JUEGO
                    if(contadorTiempo > 0 && numeroJuegos==0 && primerjuego ==0){
                        primerjuego = 1;
                        iniciaConteo();
                    }
                    //SI ESTA CORRECTO ENTONCES INICIAMOS JUEGO
                    int numeroEscogido = Integer.parseInt(tiet.getText().toString());
                    tvMensaje.setError(null);

                    //SI EL NUMERO ES CORRECTO ENTONCES MOSTRAMOS NOTIFICACION DE EXITO Y CAMBIAMOS IMAGEN
                    if (numeroEscogido == NUMERO_ADIVINAR) {
                        //SI ADIVINA SE SUMA LA CANTIDAD DE JUEGOS GANADOS+1 Y SE PONE EL TEXTO EN VERDE
                        tvJuegos.setTextColor(0xFF28A745);
                        numeroJuegos++;
                        contadorTiempo = 30;
                        //LAS VIDAS SE RESETEAN
                        vidas = 5;
                        String textoJuegos = "Games won: ";
                        textoJuegos += String.valueOf(numeroJuegos);
                        tvJuegos.setText(textoJuegos);
                        ivMensaje.setImageDrawable(getResources().getDrawable(R.drawable.check));
                        tvMensaje.setText("Yes!, continue playing! \n");
                        tiet.setText("");
                        tvVidas.setText("Lives: ♥♥♥♥♥");
                        //RESETEAMOS EL NUMERO RANDOM
                        NUMERO_ADIVINAR = (int) Math.floor(Math.random() * (NUM_MAX - NUM_MIN + 1) + NUM_MIN);
                        //MOSTRAMOS EL TOAST DEL NUEVO NUMERO GENERADO
                        numeroAdivinar();
                        //REINICIAMOS CONTEO
                        reiniciarConteo();
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
                        tiet.setText("");
                        btnAdivinar.setVisibility(View.GONE);
                        tiet.setVisibility(View.GONE);
                        tietBox.setVisibility(View.GONE);
                        btnReset.setVisibility(View.VISIBLE);
                        tvVidasExtra.setTextAppearance(R.style.boldText);
                        tvVidasExtra.setVisibility(View.VISIBLE);
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
        } else if (Integer.parseInt(tiet.getText().toString()) > 50 || Integer.parseInt(tiet.getText().toString()) < 0) {
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
                        contadorTiempo--;
                        tvTiempo.setText(String.valueOf(contadorTiempo));

                   /*     SI EL TIEMPO EXCEDE 1 MIN SE PARA EL TEMPORIZADOR Y MANDAMOS MENSAJE
                                DE "PERDIO EL JUEGO"*/
                        if (contadorTiempo == 0) {
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
        contadorTiempo = 30;
        tvTiempo.setText(String.valueOf(contadorTiempo));
    }

    public void reiniciarConteo() {
        contadorTiempo = 30;
        tvTiempo.setText(String.valueOf(contadorTiempo));
    }
    public void numeroAdivinar(){
        Snackbar.make(
                findViewById(android.R.id.content),
                "El numero a adivinar es " + NUMERO_ADIVINAR,
                Snackbar.LENGTH_INDEFINITE
        ).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();

    }

}




