package com.myproject.offlinebudgettrackerappproject.modal;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.myproject.offlinebudgettrackerappproject.R;

import java.util.Arrays;
import java.util.List;

public class DrumrollPickerFragment extends DialogFragment {

    private NumberPicker.OnValueChangeListener valueChangeListener;

    public DrumrollPickerFragment() {
        // Required empty public constructor
    }

    public static DrumrollPickerFragment newInstance() {
        return new DrumrollPickerFragment();
    }

    public interface OnCategorySelectedListener {
        void onCategorySelected(String selectedCategory);
    }

    private OnCategorySelectedListener listener;

    public void setOnCategorySelectedListener(OnCategorySelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drumroll_picker_dialog, container, false);

        NumberPicker numberPicker = view.findViewById(R.id.category_picker);

        // Disable touch interaction
        numberPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        String[] categories = getSelectionStrings(requireContext()).toArray(new String[0]);

        //debugging
        for (String category : categories) {
            System.out.println("Category: " + category);
            Log.d("categ", "onCreateView: " + category);
        }

        // Configure NumberPicker for strings
        numberPicker.setMinValue(0); // Set the minimum index
        numberPicker.setMaxValue(categories.length - 1); // Set the maximum index
        numberPicker.setDisplayedValues(categories); // Set the string values to display
        numberPicker.setWrapSelectorWheel(true); // Enable cyclic scrolling

        // Handle value change
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Get the selected string value
            String selectedCategory = categories[newVal];
            System.out.println("Selected Category: " + selectedCategory);

            // Pass the selected category to the parent fragment
            if (listener != null) {
                listener.onCategorySelected(selectedCategory);
            }
        });


        return view;
    }

    public static List<String> getSelectionStrings(Context context) {
        String[] selectionArray = context.getResources().getStringArray(R.array.category_items);
        return Arrays.asList(selectionArray); // Convert array to a list
    }

//    private void disableTouchInteraction(NumberPicker numberPicker) {
//        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS); // Prevent focus and keyboard interaction
//        numberPicker.setOnTouchListener((v, event) -> true); // Block all touch events
//    }

    private void openIncomeCategoryDialog(EditText editText) {
        // Create a simple dialog with a list or other UI
        String[] categories = {"Salary", "Bonus", "Investment", "Other"};

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Income Category")
                .setItems(categories, (dialog, which) -> {
                    // Set the selected category to the EditText
                    editText.setText(categories[which]);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }


    // Method to set a callback listener
    public void setValueChangeListener(NumberPicker.OnValueChangeListener listener) {
        this.valueChangeListener = listener;
    }
}
