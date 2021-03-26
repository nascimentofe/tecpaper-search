package com.tectoy.fullscanner.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.scwang.wave.MultiWaveHeader;
import com.tectoy.fullscanner.R;
import com.tectoy.fullscanner.model.DatabaseContract;
import com.tectoy.fullscanner.model.DatabaseHelper;
import com.tectoy.fullscanner.utils.Constant;
import com.tectoy.fullscanner.view.fragment.HomeFragment;

/**
 * @company TECTOY
 * @department development and support
 *
 * @author fenascimento
 *
 */

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    public Activity app = this;
    MultiWaveHeader waveHeader;
    private View decorView;
    ImageView imgIconHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideStatusBarAndNavigationBar();

        initProducts();

        initViews();

        startMainFragment();

        startAnimation();

    }

    private void hideStatusBarAndNavigationBar() {
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility == 0){
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
    }

    private void startMainFragment() {
        HomeFragment home = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerFragment, home, "Home");
        ft.addToBackStack("Home");
        ft.commit();
    }

    @Override
    protected void onResume() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        super.onResume();
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
        waveHeader.setVelocity(4f);
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

    private void initProducts() {
        // NOVA INSTANCIA DO HELPER
        dbHelper = new DatabaseHelper(this);
        // OBTENDO O REPOSITÓRIO OU BASE DE DADOS, EM MODO 'ESCRITA'
        db = dbHelper.getWritableDatabase();

        // CRIANDO UM NOVO MAPA DE VALORES, ONDE O NOME DAS COLUNAS SERÃO AS CHAVES
        ContentValues valuesOne = new ContentValues();
        valuesOne.put(DatabaseContract.FeedProduct.COLUMN_NAME_ID, 1);
        valuesOne.put(DatabaseContract.FeedProduct.COLUMN_NAME_NAME, "Mini post-it");
        valuesOne.put(DatabaseContract.FeedProduct.COLUMN_NAME_DESC, "Notas auto-adesivas removíveis. 4 blocos de 100 folhas.");
        valuesOne.put(DatabaseContract.FeedProduct.COLUMN_NAME_VALUE, 4.25);
        valuesOne.put(DatabaseContract.FeedProduct.COLUMN_NAME_CODE, "7891040091027");
        valuesOne.put(DatabaseContract.FeedProduct.COLUMN_NAME_IMAGE, R.drawable.postit);

        ContentValues valuesTwo = new ContentValues();
        valuesTwo.put(DatabaseContract.FeedProduct.COLUMN_NAME_ID, 2);
        valuesTwo.put(DatabaseContract.FeedProduct.COLUMN_NAME_NAME, "Cola em bastão");
        valuesTwo.put(DatabaseContract.FeedProduct.COLUMN_NAME_DESC, "A cola em bastão Pritt não tem solventes nem PVC e pode ser eliminada lavando a peça manchada a 30 ºC.");
        valuesTwo.put(DatabaseContract.FeedProduct.COLUMN_NAME_VALUE, 7.60);
        valuesTwo.put(DatabaseContract.FeedProduct.COLUMN_NAME_CODE, "40151748");
        valuesTwo.put(DatabaseContract.FeedProduct.COLUMN_NAME_IMAGE, R.drawable.cola);

        ContentValues valuesThree = new ContentValues();
        valuesThree.put(DatabaseContract.FeedProduct.COLUMN_NAME_ID, 3);
        valuesThree.put(DatabaseContract.FeedProduct.COLUMN_NAME_NAME, "Corretivo Líquido");
        valuesThree.put(DatabaseContract.FeedProduct.COLUMN_NAME_DESC, "Cobre e corrige textos impressos e esferográficas, não resseca e é inodoro.");
        valuesThree.put(DatabaseContract.FeedProduct.COLUMN_NAME_VALUE, 13.90);
        valuesThree.put(DatabaseContract.FeedProduct.COLUMN_NAME_CODE, "7897254100401");
        valuesThree.put(DatabaseContract.FeedProduct.COLUMN_NAME_IMAGE, R.drawable.corretivo);

        ContentValues valuesFour = new ContentValues();
        valuesFour.put(DatabaseContract.FeedProduct.COLUMN_NAME_ID, 4);
        valuesFour.put(DatabaseContract.FeedProduct.COLUMN_NAME_NAME, "Marca texto");
        valuesFour.put(DatabaseContract.FeedProduct.COLUMN_NAME_DESC, "Pincel Marca Texto Cor: Amarelo. Modelo: Officelogic. Caixa:12 unidades. Marca: Radex");
        valuesFour.put(DatabaseContract.FeedProduct.COLUMN_NAME_VALUE, 2.05);
        valuesFour.put(DatabaseContract.FeedProduct.COLUMN_NAME_CODE, "7897254118345");
        valuesFour.put(DatabaseContract.FeedProduct.COLUMN_NAME_IMAGE, R.drawable.marca_texto);

        String selectQuery = "SELECT * FROM " + DatabaseContract.FeedProduct.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.i(Constant.TAG, String.valueOf(cursor.getCount()));

        if(cursor.getCount() == 0){
            db.insert(DatabaseContract.FeedProduct.TABLE_NAME, null, valuesOne);
            db.insert(DatabaseContract.FeedProduct.TABLE_NAME, null, valuesTwo);
            db.insert(DatabaseContract.FeedProduct.TABLE_NAME, null, valuesThree);
            db.insert(DatabaseContract.FeedProduct.TABLE_NAME, null, valuesFour);
        }
        cursor.close();
    }

    @Override
    protected void onDestroy() {
        if(dbHelper != null)
            dbHelper.close();
        super.onDestroy();
    }

    private int hideSystemBars() {
        return
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}