package com.tectoy.fullscanner.view.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tectoy.fullscanner.R;
import com.tectoy.fullscanner.model.DatabaseContract;
import com.tectoy.fullscanner.model.DatabaseHelper;
import com.tectoy.fullscanner.model.Product;
import com.tectoy.fullscanner.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vScan = (ViewGroup) inflater.inflate(R.layout.scan, container, false);

        editCode = (EditText) vScan.findViewById(R.id.editCode);
        editCode.setInputType(InputType.TYPE_NULL);
        editCode.requestFocus();

        initProducts();
        initActions();

        return vScan;
    }

    private void initProducts(){
        dbHelper = new DatabaseHelper(getContext());
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
                            searchItem(editCode.getText().toString().replace("\n", "").replace(" ", "").trim());
                        }
                    }, 1000);
                }
            }
        });
    }

    private void showProduct() {
        if(listProduct.size() > 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) listProduct);

            editCode.requestFocus();
            clearEditCode();

            startProductFragment(bundle);
        }
    }

    private void searchItem(String item) {
        if(item.isEmpty()){
            Log.i(Constant.TAG, "VALOR VAZIO");
            return;
        }

        // INICIANDO O DATABASE COMO MODO LEITURA
        db = dbHelper.getReadableDatabase();

        // SELECIONANDO QUAIS COLUNAS QUERO OBTER NA QUERY
        String[] projection = {
                DatabaseContract.FeedProduct.COLUMN_NAME_ID,
                DatabaseContract.FeedProduct.COLUMN_NAME_NAME,
                DatabaseContract.FeedProduct.COLUMN_NAME_DESC,
                DatabaseContract.FeedProduct.COLUMN_NAME_VALUE,
                DatabaseContract.FeedProduct.COLUMN_NAME_CODE,
                DatabaseContract.FeedProduct.COLUMN_NAME_IMAGE
        };

        // DEFININDO UMA COLUNA E O VALOR PARA A CLAUSULA WHERE (WHERE COLUMN = VALUE)
        String selection = DatabaseContract.FeedProduct.COLUMN_NAME_CODE + " = " + item;

        // ORDEM DE EXIBIÇÃO DOS DADOS
        String sortOrder = DatabaseContract.FeedProduct.COLUMN_NAME_NAME + " DESC";
        // EXECUTANDO A CONSULTA
        Cursor cursor = db.query(
                DatabaseContract.FeedProduct.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                sortOrder
        );

        listProduct = new ArrayList<>();
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                listProduct.add(new Product(
                        cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.FeedProduct.COLUMN_NAME_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedProduct.COLUMN_NAME_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedProduct.COLUMN_NAME_DESC)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.FeedProduct.COLUMN_NAME_VALUE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FeedProduct.COLUMN_NAME_CODE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FeedProduct.COLUMN_NAME_IMAGE))
                ));
            }
            cursor.close();
            db.close();

            showLogs();
            showProduct();
        }else{
            Log.i("##TESTE", "PRODUTO NAO CADASTRADO!");
        }
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

    private void startProductFragment(Bundle bundle){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ProductFragment pf = new ProductFragment();
        pf.setArguments(bundle);
        ft.replace(R.id.containerFragment, pf).commit();
    }

}
