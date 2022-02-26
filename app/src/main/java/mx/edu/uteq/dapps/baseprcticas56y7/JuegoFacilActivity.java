package mx.edu.uteq.dapps.baseprcticas56y7;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class JuegoFacilActivity extends AppCompatActivity {
    private ActionBar actionBar;

    private final int NUM_MIN = 1;
    private final int NUM_MAX = 10;
    private int NUMERO_ADIVINAR = (int) Math.floor(Math.random() * (NUM_MAX - NUM_MIN + 1) + NUM_MIN);
    private int vidas;

    private TextView tvVidas, tvVidasExtra;
    private ImageView ivMensaje;
    private TextView tvMensaje;
    private TextInputEditText tiet;
    private Button btnAdivinar, btnReset;
    private TextInputLayout tietBox;

    boolean isAllFieldsChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_facil);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvVidas = findViewById(R.id.tvVidas);
        tvVidasExtra = findViewById(R.id.tvVidasExtra);
        ivMensaje = findViewById(R.id.ivMensaje);
        tvMensaje = findViewById(R.id.tvMensaje);
        btnAdivinar = findViewById(R.id.btnAdivinar);
        tiet = findViewById(R.id.tiet);
        btnReset = findViewById(R.id.btnReset);
        tietBox = findViewById(R.id.tietBox);
        vidas = 3;

        Snackbar.make(
                findViewById(android.R.id.content),
                "El numero a adivinar es " + NUMERO_ADIVINAR,
                Snackbar.LENGTH_INDEFINITE
        ).setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).show();

        btnAdivinar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFields();
                if (isAllFieldsChecked) {
                    int numeroEscogido = Integer.parseInt(tiet.getText().toString());
                    tvMensaje.setError(null);
                    if (numeroEscogido == NUMERO_ADIVINAR) {
                        ivMensaje.setImageDrawable(getResources().getDrawable(R.drawable.check));
                        tvMensaje.setText("Yes!, secret number is " + NUMERO_ADIVINAR);
                        tiet.setText("");
                        btnAdivinar.setVisibility(View.GONE);
                        tiet.setVisibility(View.GONE);
                        tietBox.setVisibility(View.GONE);
                        btnReset.setVisibility(View.VISIBLE);
                        tvVidasExtra.setVisibility(View.VISIBLE);
                        tvVidasExtra.append(String.valueOf(vidas) + " lives still!\n\nCongratulations player");

                    } else if (vidas > 1 && numeroEscogido != NUMERO_ADIVINAR) {
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
                    } else {
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

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean CheckAllFields() {
        if (Objects.requireNonNull(tiet.getText()).toString().equals("")) {
            tvMensaje.setError("");
            tvMensaje.setText("Please, enter right numbers ...");
            return false;
        }
        else  if (Integer.parseInt(tiet.getText().toString()) > 10 || Integer.parseInt(tiet.getText().toString()) < 0) {
            tvMensaje.setError("");
            tvMensaje.setText("Please, enter right numbers ...");
            return false;
        }
        else
        // after all validation return true.
        return true;
    }
}




