package app.ie.mymagiccards.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.ie.mymagiccards.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup extends AppCompatActivity {
    @BindView(R.id.namesignup)     EditText nameSignup;
    @BindView(R.id.emailsignup)    EditText emailSignup;
    @BindView(R.id.passwordsignup) EditText passwordSignup;
    @BindView(R.id.registerBtn)    Button   register;
    private FirebaseAuth    auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
    }


    @OnClick(R.id.registerBtn)
    public void register(){
        String email    =  emailSignup.getText().toString().trim();
        String password =  passwordSignup.getText().toString().trim();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                             if(!task.isSuccessful()){
                                 Toast.makeText(Signup.this, "Could not sign you up", Toast.LENGTH_LONG).show();
                             }else{
                                 startActivity(new Intent(Signup.this, MainActivity.class));
                                 finish();
                             }
                        }
                    });
            // TODO: 12/04/2018  Add user to firebase
             startActivity(new Intent(Signup.this, MainMenu.class));
        }else{
            Toast.makeText(Signup.this, "All fields must be filled in to register", Toast.LENGTH_LONG).show();
        }
    }
}
