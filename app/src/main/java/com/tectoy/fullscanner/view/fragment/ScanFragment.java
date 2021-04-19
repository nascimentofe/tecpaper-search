package com.tectoy.fullscanner.view.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.koushikdutta.ion.Ion;
import com.tectoy.fullscanner.R;
import com.tectoy.fullscanner.model.sqlite.DatabaseContract;
import com.tectoy.fullscanner.model.sqlite.DatabaseHelper;
import com.tectoy.fullscanner.model.Product;
import com.tectoy.fullscanner.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * @company TECTOY
 * @department development and support
 *
 * @author fenascimento
 *
 */

public class ScanFragment extends Fragment {

    EditText editCode;
    List<Product> listProduct;
    ProgressBar progressBar;
    GifImageView imgGif;
    TextView txt1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vScan = (ViewGroup) inflater.inflate(R.layout.scan, container, false);

        editCode = (EditText) vScan.findViewById(R.id.editCode);
        editCode.setInputType(InputType.TYPE_NULL);
        editCode.requestFocus();

        progressBar = (ProgressBar) vScan.findViewById(R.id.progressBar);

        imgGif = (GifImageView) vScan.findViewById(R.id.img_gifscan);

        txt1 = (TextView) vScan.findViewById(R.id.txt_textoscan1);

        initActions();

        return vScan;
    }

    private void initActions() {
        editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0 && !s.toString().isEmpty()){

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        if(!editCode.getText().toString().equals("")){

                            progressBar.setVisibility(View.VISIBLE);
                            txt1.setText("Pesquisando seu ");

                            searchItem(
                                    editCode.getText().toString().replace("\n", "").replace(" ", "").trim(),
                                    getActivity());
                        }
                    }, 1000);
                }
            }
        });
    }

    private void showProduct(FragmentActivity activity) {
        if(listProduct.size() > 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) listProduct);

            editCode.requestFocus();
            clearEditCode();
            editCode.requestFocus();

            startProductFragment(bundle, activity);
        }
    }

    private void searchItem(String item, FragmentActivity activity) {
        if(item.isEmpty()){
            Log.i(Constant.TAG, "VALOR VAZIO");
            return;
        }

        Ion.with(getContext())
                .load("http://tecpaper.tk/tecpaper/public/api/products?id=" + item)
                .asJsonObject()
                .setCallback((e, result) -> {

                    Product product = new Product(
                            0,
                            "Não encontrado",
                            "Nenhum produto foi localizado.",
                            0.0,
                            ""
                    );

                    if (result != null){
                        if(result.get("name") != null){
                            product = new Product(
                                    result.get("id").getAsLong(),
                                    result.get("name").getAsString(),
                                    result.get("description").getAsString(),
                                    result.get("price").getAsDouble(),
                                    result.get("image").getAsString()
                            );
                        }
                    }

                    listProduct = new ArrayList<>();
                    listProduct.add(product);

                    progressBar.setVisibility(View.GONE);
                    txt1.setText("Aproxime um ");

                    showLogs();
                    showProduct(activity);
                });
    }

    private void clearEditCode(){
        editCode.setText("");
    }

    private void showLogs() {
        if(listProduct.size() > 0){
            for(Product product : listProduct){
                Log.i(Constant.TAG, product.getName());
                Log.i(Constant.TAG, product.getDesc());
                Log.i(Constant.TAG, String.valueOf(product.getValue()));
            }
        }
    }

    private void startProductFragment(Bundle bundle, FragmentActivity activity){
        ProductFragment product = new ProductFragment();
        product.setArguments(bundle);
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerFragment, product, "Product");
        ft.commit();
    }

}
