package app.ie.mymagiccards.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.ie.mymagiccards.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup extends AppCompatActivity {
    @BindView(R.id.namesignup)     EditText nameSignup;
    @BindView(R.id.emailsignup)    EditText emailSignup;
    @BindView(R.id.passwordsignup) EditText passwordSignup;
    @BindView(R.id.registerBtn)    Button   register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerBtn)
    public void register(){
        if(!TextUtils.isEmpty(nameSignup.getText().toString())
                && !TextUtils.isEmpty(emailSignup.getText().toString())
                && !TextUtils.isEmpty(passwordSignup.getText().toString())){
            // TODO: 12/04/2018  Add user to firebase
             startActivity(new Intent(Signup.this, MainMenu.class));
        }else{
            Toast.makeText(Signup.this, "All fields must be filled in to register", Toast.LENGTH_LONG).show();
        }
    }
}
