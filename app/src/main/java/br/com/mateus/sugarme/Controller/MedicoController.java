package br.com.mateus.sugarme.Controller;

import android.app.Activity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.mateus.sugarme.Model.Users.Medico;
import br.com.mateus.sugarme.Model.Users.MedicoDAO;

public class MedicoController {

    private MedicoDAO medicoDAO = new MedicoDAO();
    private Medico medico;
    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    public MedicoController() {
    }

    public boolean isDadosOk(Medico medico, final Activity activity){
        if(!medico.getNome().isEmpty()){
            if(isData(medico.getDtNascimento())){
                if (isCpf(medico.getCpf())) {
                    if (isTel(medico.getTelefone())){
                        if (!medico.getCrm().isEmpty()) {
                            if (!medico.getEspecialidade().isEmpty()) {
                                return true;
                            } else {
                                Toast.makeText(activity, "Especialidade inválida!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, "CRM inválido!", Toast.LENGTH_SHORT).show();
                        }
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
        medicoDAO.logout();
    }

    //Excluir
    public void exluir(){
        medicoDAO.excluir();
    }

    //Receber Médico
    public void recebeMedico(Activity activity) {
        medicoDAO.consultaMedico(activity);
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

}