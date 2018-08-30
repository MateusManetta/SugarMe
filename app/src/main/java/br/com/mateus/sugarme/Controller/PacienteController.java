package br.com.mateus.sugarme.Controller;

import br.com.mateus.sugarme.Model.Users.Paciente;
import br.com.mateus.sugarme.Model.Users.PacienteDAO;

public class PacienteController {

    private PacienteDAO pacienteDAO = new PacienteDAO();

    public PacienteController() {
    }


    public boolean isDadosOk(Paciente paciente){
        return true;
    }

    public void logout(){
        pacienteDAO.logout();
    }


}
