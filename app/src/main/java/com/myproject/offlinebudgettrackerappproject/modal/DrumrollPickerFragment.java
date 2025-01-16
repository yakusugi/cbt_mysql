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
import com.myproject.offlinebudgettrackerappproject.util.DrumrollConstants;

import java.util.Arrays;
import java.util.List;

public class DrumrollPickerFragment extends DialogFragment {

    private static final String ARG_LIST_KEY = "LIST_KEY";
    private NumberPicker.OnValueChangeListener valueChangeListener;
    public DrumrollPickerFragment() {
        // Required empty public constructor
    }

    // Create a new instance with the list key
    public static DrumrollPickerFragment newInstance(String listKey) {
        DrumrollPickerFragment fragment = new DrumrollPickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LIST_KEY, listKey);
        fragment.setArguments(args);
        return fragment;
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
        String listKey = getArguments() != null ? getArguments().getString("LIST_KEY") : null;

        // Determine which item list to use
        String[] categories;
        if (DrumrollConstants.LIST_KEY_MYSQL_INCOME.equals(listKey)) {
            categories = getSelectionStrings(requireContext(), R.array.category_items).toArray(new String[0]);
        } else if (DrumrollConstants.LIST_KEY_MYSQL_SPENDING.equals(listKey)) {
            categories = getSelectionStrings(requireContext(), R.array.spending_type_items).toArray(new String[0]);
        }else if (DrumrollConstants.LIST_KEY_MYSQL_CURRENCY.equals(listKey)) {
            categories = getSelectionStrings(requireContext(), R.array.currency_items).toArray(new String[0]);
        } else {
            categories = new String[] {"Default Item"}; // Fallback in case of no key
        }

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

    public static List<String> getSelectionStrings(Context context, int resId) {
        try {
            // Fetch the string array using the provided resource ID
            String[] selectionArray = context.getResources().getStringArray(resId);
            return Arrays.asList(selectionArray); // Convert array to a list
        } catch (Exception e) {
            // Log an error and return an empty list if the resource ID is invalid
            Log.e("DrumrollPickerFragment", "Invalid resource ID: " + resId, e);
            return Arrays.asList("Error loading items");
        }
    }

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
