package app.ie.mymagiccards.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.ie.mymagiccards.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    private FirebaseAuth                    mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private FirebaseUser                    user;

    @BindView(R.id.createAccountBtn)Button  createAccount;
    @BindView(R.id.loginBtn)        Button  login;
    @BindView(R.id.emailLogin)     EditText emailLogin;
    @BindView(R.id.passwordLogin)  EditText passwordLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(Login.this, "Signed in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this, MyDecks.class));
                    finish();
                }else{
                    Toast.makeText(Login.this, "Signed out", Toast.LENGTH_LONG).show();
                }

            }
        };

    }

    @OnClick(R.id.loginBtn)
    public void setLogin(){
        if(!TextUtils.isEmpty(emailLogin.getText().toString()) && !TextUtils.isEmpty(passwordLogin.getText().toString())){
            String email = emailLogin.getText().toString();
            String password = passwordLogin.getText().toString();
            login(email,password);
        }else{
            Toast.makeText(Login.this, "Email or password field is missing", Toast.LENGTH_LONG).show();
        }
    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Your are signed in", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Login.this, "Email or password is wrong \n try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @OnClick(R.id.createAccountBtn)
    public void register(){
        startActivity(new Intent(Login.this, Signup.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
