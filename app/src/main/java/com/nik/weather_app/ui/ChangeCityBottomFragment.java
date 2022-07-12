package com.nik.weather_app.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nik.weather_app.MainViewModel;
import com.nik.weather_app.R;
import com.nik.weather_app.databinding.ChangeCityFragmentBinding;

import java.util.List;

public class ChangeCityBottomFragment extends BottomSheetDialogFragment {
    private ChangeCityFragmentBinding binding;
    private ChangeCityViewModel viewModel;
    private MainViewModel mainViewModel;

    private BottomSheetDialog dialog;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangeCityFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.findBtn.setOnClickListener(btn -> {
            viewModel.findCity("Novosibirsk");
        });

        viewModel.getCityListLiveData().observe(getViewLifecycleOwner(), list -> {
            binding.editTv.setAdapter(new ArrayAdapter<String>(requireContext(), R.layout.spinner_item, list));
        });

        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        CoordinatorLayout layout = binding.dialogLayout;
        layout.setMinimumHeight((int) getResources().getDimension(R.dimen.change_city_dialog_height));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel = new ViewModelProvider(requireActivity()).get(ChangeCityViewModel.class);
    }
}
