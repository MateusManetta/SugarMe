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

public class MedicoDAO {
    DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private DatabaseReference databaseReference;
    private Medico medico;


    //Inserir ou Atualizar
    public void inserir(Medico medico){
        getUserId();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("medicos").child(userId).setValue(medico);
    }

    //Excluir
    public void excluir(){
        getUserId();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child("medicos").child(userId).removeValue();
        logout();
    }

    //Logout
    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }


    //Consultar medico e ir para a tela de edição
    public void consultaMedico(final Activity activity) {
        medico = new Medico();
        getUserId();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("users").child("medicos").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medico = dataSnapshot.getValue(Medico.class);
                //Trocar de Activity
                Intent intent = new Intent(activity, CadastroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("radio","editarMedico");
                intent.putExtra("medico", (Serializable) medico);
                activity.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Pegar Id Usuario
    public void getUserId(){
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getCurrentUser().getUid();
    }
}
