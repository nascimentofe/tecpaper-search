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

import com.squareup.picasso.Picasso;
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
            imgProduct.requestFocus();
            if(product.getImage().equals("") || product.getImage().equals("imagem.jpg")){
                // NAO HÁ IMAGEM NO PRODUTO
                Picasso.get().load("https://img.icons8.com/dotty/2x/id-not-verified.png" + product.getImage()).into(imgProduct);
            }else{
                Picasso.get().load("http://tecpaper.tk/" + product.getImage()).into(imgProduct);
            }

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
