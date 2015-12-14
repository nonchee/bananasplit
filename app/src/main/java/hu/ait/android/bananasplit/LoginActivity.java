package hu.ait.android.bananasplit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dd.morphingbutton.MorphingButton;
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
    public final String user_name = "USER_NAME";

    //use butterknife library to get references to user components
    @Bind(R.id.login_progress)
    ProgressBar login_progress;

    @Bind(R.id.etUserName)
    EditText etUserName;

    @Bind(R.id.banana)
    ImageView banana;

    @Bind(R.id.etPassword)
    EditText etPassword;

    @Bind(R.id.btnRegister)
    MorphingButton btnRegister;


    @Bind(R.id.btnLogin)
    MorphingButton btnLogin;


    @OnClick(R.id.btnRegister)
    public void register() {
        onRegisterClicked(btnRegister);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        onLoginClicked(btnLogin);
        //login_progress.setVisibility(View.VISIBLE);

    }


    private void onLoginClicked(final MorphingButton btnMorph) {

        ParseUser.logInInBackground(etUserName.getText().toString(),
                etPassword.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            morphToSuccess(btnMorph);
                            //login_progress.setVisibility(View.GONE);
                            Intent startMain = new Intent();
                            startMain.setClass(LoginActivity.this, MainActivity.class);
                            startMain.putExtra(user_name, etUserName.getText().toString());
                            startActivity(startMain);

                        } else {
                            morphToFailure(btnMorph,(R.integer.mb_animation));
                            Toast.makeText(LoginActivity.this, "login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void onRegisterClicked(final MorphingButton btnMorph) {


        //parse already gives you user functions and tables
        ParseUser user = new ParseUser();
        user.setUsername(etUserName.getText().toString());
        user.setPassword(etPassword.getText().toString());

        // login_progress.setVisibility(View.VISIBLE);


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                login_progress.setVisibility(View.GONE);
                //successful registration


                if (e == null) {
                    morphToSuccess(btnMorph);
                    Toast.makeText(LoginActivity.this, "registered a-okay", Toast.LENGTH_SHORT).show();
                } else {
                    morphToFailure(btnMorph, (R.integer.mb_animation));
                    Toast.makeText(LoginActivity.this, "registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(R.integer.mb_animation)
                .cornerRadius(R.dimen.mb_height_56)
                .width(R.dimen.mb_height_56)
                .height(R.dimen.mb_height_56)
                .color(R.color.mb_green)
                .colorPressed(R.color.mb_green_dark)
                .icon(R.drawable.ic_done);
        btnMorph.morph(circle);
    }

    private void morphToFailure(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(R.dimen.mb_height_56)
                .width(R.dimen.mb_height_56)
                .height(R.dimen.mb_height_56)
                .color(R.color.mb_red)
                .colorPressed(R.color.mb_red_dark)
                .icon(R.drawable.ic_lock);
        btnMorph.morph(circle);
    }
}