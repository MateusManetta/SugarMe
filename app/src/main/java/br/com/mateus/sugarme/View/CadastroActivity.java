package br.com.mateus.sugarme.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.com.mateus.sugarme.Controller.MedicoController;
import br.com.mateus.sugarme.Controller.PacienteController;
import br.com.mateus.sugarme.MainActivity;
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
    private TextView textViewCadastro;
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
        textViewCadastro = (TextView) findViewById(R.id.textViewCadastro);


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
        textInputCrm.addTextChangedListener(MaskEditUtil.mask(textInputCrm, MaskEditUtil.FORMAT_CRM));

        //Adapter pro spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, uf);
        spinnerUf.setAdapter(adapter);


        //Cadastrar
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insereFirebase();
            }

        });



        //Editar
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insereFirebase();
            }
        });


        //Excluir
        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder exclusao = new AlertDialog.Builder(CadastroActivity.this);
                exclusao.setMessage(R.string.confirmarExclusao).setCancelable(false)
                .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() { //Excluir
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(radioButtonMedico.isChecked()){
                            MedicoController medicoController = new MedicoController();
                            medicoController.exluir();
                        }
                        else if(radioButtonPaciente.isChecked()){
                            PacienteController pacienteController = new PacienteController();
                            pacienteController.exluir();
                        }

                        //Voltar a tela inicial
                        Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        CadastroActivity.this.startActivity(intent);

                    }
                })
                        .setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel(); //Não excluir
                            }
                        });


                exclusao.create();
                exclusao.setTitle(R.string.tituloExclusao);
                exclusao.show();
            }
        });


        //Parametros do PutExtra
        Intent it = getIntent();
        if(it != null && it.getExtras() != null){
            if(it.getStringExtra("radio").equals("editarMedico")) {
                Medico medico = (Medico) it.getSerializableExtra("medico");
                this.setMedico(medico);
            }
            else if(it.getStringExtra("radio").equals("editarPaciente")){
                Paciente paciente = (Paciente) it.getSerializableExtra("paciente");
                this.setPaciente(paciente);
            }
        }


    }//Fim do onCreate --------------

    private void setMedico(Medico medico){
        this.radioButtonMedico.setChecked(true);
        this.radioButtonMedico.setEnabled(false);
        this.radioButtonPaciente.setEnabled(false);
        this.buttonExcluir.setVisibility(View.VISIBLE);
        this.buttonEditar.setVisibility(View.VISIBLE);
        this.buttonCadastrar.setVisibility(View.INVISIBLE);
        this.textViewCadastro.setText(R.string.edicaoMedico);
        this.textInputNome.setText(medico.getNome());
        this.textInputCpf.setText(medico.getCpf());
        this.textInputDtNascimento.setText(medico.getDtNascimento());
        this.textInputTelefone.setText(medico.getTelefone());
        //CRM
        this.textInputCrm.setText(medico.getCrm());
        this.textInputCrm.setEnabled(true);
        this.textInputEspecialidade.setText(medico.getEspecialidade());
        //Spinner
        int pos = getIndex(medico.getUf());
        spinnerUf.setSelection(pos);
    }

    private void setPaciente(Paciente paciente){
        this.radioButtonPaciente.setChecked(true);
        this.radioButtonMedico.setEnabled(false);
        this.radioButtonPaciente.setEnabled(false);
        buttonExcluir.setVisibility(View.VISIBLE);
        buttonEditar.setVisibility(View.VISIBLE);
        buttonCadastrar.setVisibility(View.INVISIBLE);
        textViewCadastro.setText(R.string.edicaoPaciente);
        this.textInputNome.setText(paciente.getNome());
        this.textInputCpf.setText(paciente.getCpf());
        this.textInputDtNascimento.setText(paciente.getDtNascimento());
        this.textInputTelefone.setText(paciente.getTelefone());
        this.spinnerUf.setEnabled(false);
    }

    //Metodo para descobrir a posicao da Uf no Spinner
    private int getIndex(String uf){
        for (int i=0;i<this.spinnerUf.getCount();i++){
            if (this.spinnerUf.getItemAtPosition(i).toString().equalsIgnoreCase(uf)){
                return i;
            }
        }
        return 0;
    }


    //Preencher Medico
    private Medico preencheMedico(){
        Medico medico = new Medico(textInputNome.getText().toString() ,textInputTelefone.getText().toString(),
                textInputDtNascimento.getText().toString(),textInputCpf.getText().toString(),
                textInputCrm.getText().toString(),textInputEspecialidade.getText().toString() ,
                spinnerUf.getSelectedItem().toString());
        return medico;
    }

    //Preenche Paciente
    private Paciente preenchePaciente(){
        Paciente paciente = new Paciente(textInputNome.getText().toString() ,textInputTelefone.getText().toString(),
                textInputDtNascimento.getText().toString(),textInputCpf.getText().toString());
        return paciente;
    }

    //Cadastrar ou Editar
    private void insereFirebase(){
        //Medico Selecionado
        if(radioButtonMedico.isChecked()){
            Medico medico = preencheMedico();
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
        else if(radioButtonPaciente.isChecked()){
            Paciente paciente = preenchePaciente();
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
        else{
            Toast.makeText(CadastroActivity.this, getString(R.string.selecioneRadio), Toast.LENGTH_SHORT).show();
        }
    }


}
