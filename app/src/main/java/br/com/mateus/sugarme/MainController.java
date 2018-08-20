package br.com.mateus.sugarme;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import br.com.mateus.sugarme.View.CadastroActivity;
import br.com.mateus.sugarme.View.MedicoActivity;
import br.com.mateus.sugarme.View.PacienteActivity;

public class MainController {

    public MainController() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    FirebaseAuth firebaseAuth;

    boolean verificaLogin() {

        return firebaseAuth.getCurrentUser() != null;

    }

    void fazerLogin(Activity activity) {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                1);
    }

    void onLoginResult(Activity activity ,int requestCode){
        if(requestCode == 1){
            if(verificaLogin()){
                verificaTipoUsuario(activity);
            }
        }
    }

    void verificaTipoUsuario(final Activity activity){
        final String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        //Ver se é paciente
        databaseReference.child("users").child("pacientes").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                        Intent intent = new Intent(activity, PacienteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //Ver se é medico
        databaseReference.child("users").child("medicos").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Intent intent = new Intent(activity, MedicoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                }
                //Ainda nao possui tipo de usuario
                else{
                    Intent intent = new Intent(activity, CadastroActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    } //Fim do verifica tipo usuario

}
