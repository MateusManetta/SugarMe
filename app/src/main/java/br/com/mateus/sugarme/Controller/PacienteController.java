package br.com.mateus.sugarme.Controller;

import android.app.Activity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.mateus.sugarme.Model.Users.Paciente;
import br.com.mateus.sugarme.Model.Users.PacienteDAO;

public class PacienteController {

    private PacienteDAO pacienteDAO = new PacienteDAO();
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public PacienteController() {
    }


    public boolean isDadosOk(Paciente paciente, Activity activity){
        if(!paciente.getNome().isEmpty()){
            if(isData(paciente.getDtNascimento())){
                if (isCpf(paciente.getCpf())) {
                    if (isTel(paciente.getTelefone())){
                        return true;
                    }
                    else{
                        Toast.makeText(activity, "Telefone inválido!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(activity, "CPF inválido!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(activity, "Data inválida!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(activity, "Nome inválido!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //Logout
    public void logout(){
        pacienteDAO.logout();
    }

    //Excluir
    public void exluir(){
        pacienteDAO.excluir();
    }

    //Validação de CPF
    private boolean isCpf(String cpf){
        if(cpf.length() != 14){
            return false;
        }
        cpf = cpf.replaceAll("[^0-9]", "");
        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    //Validação CPF
    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }


    //Validar Telefone
    private boolean isTel(String telefone){
        if(telefone.length() < 13){
            return false;
        }
        telefone = telefone.replaceAll("[^0-9]", "");
        if(telefone.length() >= 10){
            return true;
        }
        return false;
    }

    //Validar Data de Nascimento
    private boolean isData(String dataNascimento){
        if(dataNascimento.length() != 10){
            return false;
        }
        int ano = Integer.parseInt(dataNascimento.substring(6,10));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient (false);
        try {
            df.parse (dataNascimento); //Data é valida
            if(ano > 1900 && ano < 2018){
                return true;
            }
        } catch (ParseException ex) {
            //Data invalida
            return false;
        }
        return false;
    }


    public void recebePaciente(Activity activity) {
        pacienteDAO.consultaPaciente(activity);
    }
}
