package br.com.mateus.sugarme.Model.Users;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PacienteDAO {
    DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private String userId;


    public PacienteDAO() {
    }

    //Insercao
    public void inserir(Paciente paciente){
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("pacientes").child(userId).setValue(paciente);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
