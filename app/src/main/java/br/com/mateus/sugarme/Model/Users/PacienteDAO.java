package br.com.mateus.sugarme.Model.Users;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import br.com.mateus.sugarme.View.CadastroActivity;

public class PacienteDAO {
    DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private Paciente paciente;
    private DatabaseReference databaseReference;


    public PacienteDAO() {
    }

    //Inserir ou Atualizar
    public void inserir(Paciente paciente){
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("pacientes").child(userId).setValue(paciente);
    }

    //Excluir
    public void excluir(){
        getUserId();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("pacientes").child(userId).removeValue();
        logout();
    }

    //Pegar Id Usuario
    public void getUserId(){
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
    }

    //Logout
    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }


    //Consultar paciente e ir para a tela de edição
    public void consultaPaciente(final Activity activity) {
        paciente = new Paciente();
        getUserId();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("users").child("pacientes").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                paciente = dataSnapshot.getValue(Paciente.class);
                //Trocar de Activity
                Intent intent = new Intent(activity, CadastroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("radio","editarPaciente");
                intent.putExtra("paciente", (Serializable) paciente);
                activity.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
