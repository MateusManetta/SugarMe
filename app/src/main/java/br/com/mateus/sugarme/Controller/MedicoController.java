package br.com.mateus.sugarme.Controller;

import br.com.mateus.sugarme.Model.Users.Medico;
import br.com.mateus.sugarme.Model.Users.MedicoDAO;

public class MedicoController {

    private MedicoDAO medicoDAO;


    public MedicoController() {
    }

    public boolean isDadosOk(Medico medico){
        return true;
    }


    public void logout(){
        medicoDAO = new MedicoDAO();
        medicoDAO.logout();
    }
}
