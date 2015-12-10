package hu.ait.android.bananasplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    //use butterknife library to get references to user components
    @Bind(R.id.login_progress)
    ProgressBar login_progress;

    @Bind(R.id.etUserName)
    EditText etUserName;

    @Bind(R.id.etPassword)
    EditText etPassword;

    @Bind(R.id.btnRegister)
    Button btnRegister;


    @Bind(R.id.btnLogin)
    Button btnLogin;


    @OnClick(R.id.btnRegister)
    public void register() {

        //parse already gives you user functions and tables
        ParseUser user = new ParseUser();
        user.setUsername(etUserName.getText().toString());
        user.setPassword(etPassword.getText().toString());

        login_progress.setVisibility(View.VISIBLE);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                login_progress.setVisibility(View.GONE);
                //successful registration
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "registered a-okayly", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @OnClick(R.id.btnLogin)
    public void login() {

        login_progress.setVisibility(View.VISIBLE);

        ParseUser.logInInBackground(etUserName.getText().toString(),
                etPassword.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            login_progress.setVisibility(View.GONE);
                            Intent startMain = new Intent();
                            startMain.setClass(LoginActivity.this, ExpenseSheetActivity.class);
                            startActivity(startMain);

                        } else {
                            Toast.makeText(LoginActivity.this, "login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}
