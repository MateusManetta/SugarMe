package br.com.mateus.sugarme.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.mateus.sugarme.Controller.MedicoController;
import br.com.mateus.sugarme.MainActivity;
import br.com.mateus.sugarme.R;

public class MedicoActivity extends AppCompatActivity {
    private MedicoController medicoController;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);
        medicoController = new MedicoController();

        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicoController.logout();
                Intent intent = new Intent(MedicoActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                MedicoActivity.this.startActivity(intent);
            }
        });
    }
}
