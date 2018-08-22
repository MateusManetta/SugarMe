package br.com.mateus.sugarme.Model.Users;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicoDAO {
    DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private String userId;
    public void inserir(Medico medico){
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("medicos").child(userId).setValue(medico);
    }

    public void logout(){

        FirebaseAuth.getInstance().signOut();
    }
}
