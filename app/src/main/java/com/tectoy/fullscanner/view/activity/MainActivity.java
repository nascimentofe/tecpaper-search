package com.tectoy.fullscanner.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.scwang.wave.MultiWaveHeader;
import com.tectoy.fullscanner.R;
import com.tectoy.fullscanner.view.fragment.HomeFragment;

/**
 * @company TECTOY
 * @department development and support
 *
 * @author fenascimento
 *
 */

public class MainActivity extends AppCompatActivity {

    MultiWaveHeader waveHeader;
    ImageView imgIconHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        startAnimation();

        startMainFragment();

    }

    @Override
    protected void onStart() {
        hideStatusBarAndNavigationBar();
        super.onStart();
    }

    private void hideStatusBarAndNavigationBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void startMainFragment() {
        HomeFragment home = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerFragment, home, "Home");
        ft.commit();
    }

    private void startAnimation() {
        YoYo.with(Techniques.RotateIn)
                .duration(3000)
                .repeat(0)
                .playOn(findViewById(R.id.imgHomeDefault));
    }

    private void initViews() {
        // WAVE
        waveHeader = (MultiWaveHeader) findViewById(R.id.waveHeader);
        waveHeader.setVelocity(6f);
        waveHeader.setProgress(.8f);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(360);
        waveHeader.setWaveHeight(40);
        waveHeader.setColorAlpha(.5f);

        // ICON HOME
        imgIconHome = (ImageView) findViewById(R.id.imgIconHome);
        imgIconHome.setOnClickListener(v -> {
            startMainFragment();
        });
    }

}