package com.alfonso.capstone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.R;
import com.alfonso.capstone.databinding.FragmentAddRouteBinding;

public class AddRouteFragment extends DialogFragment {

    private FragmentAddRouteBinding binding;
    private final AddRouteDialogListener listener;

    public interface  AddRouteDialogListener {
        void onDialogOkClick(DialogFragment dialog,String routeName);
        void onDialogCancelClick(DialogFragment dialog);
    }

    public AddRouteFragment(@NonNull AddRouteDialogListener listener) {
        this.listener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_Material_Light_Dialog_NoMinWidth);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddRouteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.okButton.setOnClickListener(view1 -> listener.onDialogOkClick(this,binding.edtRouteName.getText().toString()));
        binding.cancelButton.setOnClickListener(view1 -> listener.onDialogCancelClick(this));
        return view;
    }
}