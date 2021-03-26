package com.tectoy.fullscanner.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tectoy.fullscanner.R;
import com.tectoy.fullscanner.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @company TECTOY
 * @department development and support
 *
 * @author fenascimento
 *
 */

public class ProductFragment extends Fragment {

    ImageView imgProduct;
    TextView txtNameProduct, txtDescProduct, txtValueProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ArrayList<Product> products = (ArrayList<Product>) getArguments().getSerializable("data");

        ViewGroup vProduct = (ViewGroup) inflater.inflate(R.layout.product, container, false);

        for(Product product : products){

            imgProduct = (ImageView) vProduct.findViewById(R.id.imgImageProductFragment);
            imgProduct.setImageResource(product.getImage());
            imgProduct.requestFocus();

            txtNameProduct = (TextView) vProduct.findViewById(R.id.txtNameProductFragment);
            txtNameProduct.setText(product.getName());

            txtDescProduct = (TextView) vProduct.findViewById(R.id.txtDescProductFragment);
            txtDescProduct.setText(product.getDesc());

            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            txtValueProduct = (TextView) vProduct.findViewById(R.id.txtValueProductFragment);
            txtValueProduct.setText(nf.format(product.getValue()));

        }

        return vProduct;
    }
}
