package com.tectoy.fullscanner.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tectoy.fullscanner.R;

/**
 * @company TECTOY
 * @department development and support
 *
 * @author fenascimento
 *
 */

public class HomeFragment extends Fragment {

    LinearLayout btnScan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vHome = (ViewGroup) inflater.inflate(R.layout.home, container, false);

        btnScan = vHome.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v -> startScanFragment());

        return vHome;
    }

    private void startScanFragment(){
        ScanFragment scan = new ScanFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.containerFragment, scan).commit();
    }


}
