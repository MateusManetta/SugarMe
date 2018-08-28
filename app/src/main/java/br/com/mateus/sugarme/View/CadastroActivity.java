package br.com.mateus.sugarme.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.mateus.sugarme.Controller.MedicoController;
import br.com.mateus.sugarme.Controller.PacienteController;
import br.com.mateus.sugarme.Model.Users.Medico;
import br.com.mateus.sugarme.Model.Users.MedicoDAO;
import br.com.mateus.sugarme.Model.Users.Paciente;
import br.com.mateus.sugarme.Model.Users.PacienteDAO;
import br.com.mateus.sugarme.R;
import br.com.mateus.sugarme.Utils.MaskEditUtil;

public class CadastroActivity extends AppCompatActivity {
    private String[] uf = new String[]{"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA","MG",
            "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS",
            "SC", "SE", "SP", "TO"};



    //Objetos
    private RadioButton radioButtonPaciente;
    private RadioButton radioButtonMedico;
    private TextInputEditText textInputNome;
    private TextInputEditText textInputTelefone;
    private TextInputEditText textInputDtNascimento;
    private TextInputEditText textInputCpf;
    private TextInputEditText textInputEspecialidade;
    private TextInputEditText textInputCrm;
    private Spinner spinnerUf;
    private Button buttonCadastrar;
    private Button buttonExcluir;
    private Button buttonEditar;


    private PacienteDAO pacienteDAO;
    private MedicoDAO medicoDAO;


    //ON CREATE ----------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //FindViewById dos objetos
        radioButtonPaciente = (RadioButton) findViewById(R.id.radioButtonPaciente);
        radioButtonMedico = (RadioButton) findViewById(R.id.radioButtonMedico);
        textInputNome = (TextInputEditText) findViewById(R.id.textInputNome);
        textInputTelefone = (TextInputEditText) findViewById(R.id.textInputTelefone);
        textInputDtNascimento = (TextInputEditText) findViewById(R.id.textInputDtNascimento);
        textInputCpf = (TextInputEditText) findViewById(R.id.textInputCpf);
        textInputEspecialidade = (TextInputEditText) findViewById(R.id.textInputEspecialidade);
        textInputCrm = (TextInputEditText) findViewById(R.id.textInputCrm);
        spinnerUf = (Spinner) findViewById(R.id.spinnerUf);
        buttonCadastrar = (Button) findViewById(R.id.buttonCadastrar);
        buttonEditar = (Button) findViewById(R.id.buttonEditar);
        buttonExcluir = (Button) findViewById(R.id.buttonExcluir);


        // Desabilitar CRM
        radioButtonPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputCrm.setEnabled(false);
                spinnerUf.setEnabled(false);
                textInputEspecialidade.setEnabled(false);
            }
        });

        // Habilitar CRM
        radioButtonMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInputCrm.setEnabled(true);
                spinnerUf.setEnabled(true);
                textInputEspecialidade.setEnabled(true);
            }
        });

        //Formatar Mascaras de entrada
        textInputCpf.addTextChangedListener(MaskEditUtil.mask(textInputCpf, MaskEditUtil.FORMAT_CPF));
        textInputDtNascimento.addTextChangedListener(MaskEditUtil.mask(textInputDtNascimento, MaskEditUtil.FORMAT_DATE));
        textInputTelefone.addTextChangedListener(MaskEditUtil.mask(textInputTelefone, MaskEditUtil.FORMAT_FONE));

        //Adapter pro spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uf);
        spinnerUf.setAdapter(adapter);


        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Medico Selecionado
                if(radioButtonMedico.isChecked()){
                    Medico medico = new Medico(textInputNome.getText().toString() ,textInputTelefone.getText().toString(),
                            textInputDtNascimento.getText().toString(),textInputCpf.getText().toString(),
                            textInputCrm.getText().toString(),textInputEspecialidade.getText().toString() ,
                            spinnerUf.getSelectedItem().toString());
                    MedicoController medicoController = new MedicoController();
                    if(medicoController.isDadosOk(medico)){
                        medicoDAO = new MedicoDAO();
                        medicoDAO.inserir(medico);
                        Toast.makeText(CadastroActivity.this, getString(R.string.inseridoSucesso), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadastroActivity.this, MedicoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        CadastroActivity.this.startActivity(intent);
                    }
                    else{
                        Toast.makeText(CadastroActivity.this, getString(R.string.dadosIncorretos), Toast.LENGTH_SHORT).show();
                    }

                }
                //Paciente Selecionado
                else{
                    Paciente paciente = new Paciente(textInputNome.getText().toString() ,textInputTelefone.getText().toString(),
                            textInputDtNascimento.getText().toString(),textInputCpf.getText().toString());
                    PacienteController pacienteController = new PacienteController();
                    if(pacienteController.isDadosOk(paciente)){
                        pacienteDAO = new PacienteDAO();
                        pacienteDAO.inserir(paciente);
                        Toast.makeText(CadastroActivity.this, getString(R.string.inseridoSucesso), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CadastroActivity.this, PacienteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        CadastroActivity.this.startActivity(intent);
                    }
                    else{
                        Toast.makeText(CadastroActivity.this, getString(R.string.dadosIncorretos), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }//Fim do onCreate --------------
}
