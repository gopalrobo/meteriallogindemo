package com.katomaran.example.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by gopalsamy.k on 20/3/17.
 */

public class LoginActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String username = "usernameKey";
    public static final String password = "passwordKey";
    public static final String email = "emailKey";
    public static final String loginUser = "loginUserKey";

    private Button login;
    private Button signup;
    private TextView emailtx;
    private TextView passtx;
    private EditText emailed;
    private EditText passed;
    private FloatingActionButton fabLogin;
    private CardView login_cardview;
    private TextView closetxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
            setupExitAnimation();
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent io = new Intent(LoginActivity.this, SignupActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Pair<View, String> pair1 = Pair.create(signup.findViewWithTag(getResources().getString(R.string.transition_signup)), signup.getTransitionName());
                    Pair<View, String> pair2 = Pair.create(emailtx.findViewWithTag(getResources().getString(R.string.emailtxt)), emailtx.getTransitionName());
                    Pair<View, String> pair3 = Pair.create(emailed.findViewWithTag(getResources().getString(R.string.email_edittxt)), emailed.getTransitionName());
                    Pair<View, String> pair4 = Pair.create(passtx.findViewWithTag(getResources().getString(R.string.passwordtxt)), passtx.getTransitionName());
                    Pair<View, String> pair5 = Pair.create(passed.findViewWithTag(getResources().getString(R.string.passwoed_edittxt)), passed.getTransitionName());
                    Pair<View, String> pair6 = Pair.create(closetxt.findViewWithTag(getResources().getString(R.string.transition_close)), closetxt.getTransitionName());
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation(LoginActivity.this, pair1, pair2, pair3, pair4, pair5, pair6);
                    startActivity(io, options.toBundle());
                } else {
                    startActivity(io);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                if (sharedpreferences.contains(username)) {
                    String savedEmail = sharedpreferences.getString(email, null);
                    String savedPass = sharedpreferences.getString(password, null);
                    if (emailed.getText().toString().equals(savedEmail)
                            && passed.getText().toString().equals(savedPass)) {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences(MyPREFERENCES, 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(loginUser, sharedpreferences.getString(username, null));
                        editor.commit();
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Credentials not matched", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Sign up", Toast.LENGTH_SHORT).show();
                }
            }
        });
        closetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                final View myView = login_cardview.findViewWithTag(getResources().getString(R.string.transition_login));
                int cx = myView.getMeasuredWidth() / 2;
                int cy = myView.getMeasuredHeight() / 2;
                int finalRadius = Math.max(myView.getWidth(), myView.getHeight()) / 2;
                Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
                myView.setVisibility(View.VISIBLE);
                anim.start();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        final View myView = login_cardview.findViewWithTag(getResources().getString(R.string.transition_login));
        int cx = myView.getMeasuredWidth() / 2;
        int cy = myView.getMeasuredHeight() / 2;
        int initialRadius = myView.getWidth() / 2;
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
                backPressed();
            }
        });
        anim.start();

    }

    private void backPressed() {
        super.onBackPressed();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(300);
    }

    public void initViews() {
        closetxt = (TextView) findViewById(R.id.closetxt);
        login_cardview = (CardView) findViewById(R.id.login_cardview);
        fabLogin = (FloatingActionButton) findViewById(R.id.activity_login_fab);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        emailtx = (TextView) findViewById(R.id.emailtxt);
        emailed = (EditText) findViewById(R.id.emailEdittxt);
        passtx = (TextView) findViewById(R.id.passtxt);
        passed = (EditText) findViewById(R.id.passEdittxt);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/DroidSans.ttf");
        login.setTypeface(custom_font);
        signup.setTypeface(custom_font);
        emailtx.setTypeface(custom_font);
        emailed.setTypeface(custom_font);
        emailed.getBackground().mutate().setColorFilter(getResources().getColor(R.color.editText), PorterDuff.Mode.SRC_ATOP);
        passtx.setTypeface(custom_font);
        passed.setTypeface(custom_font);
        passed.getBackground().mutate().setColorFilter(getResources().getColor(R.color.editText), PorterDuff.Mode.SRC_ATOP);
    }

}
