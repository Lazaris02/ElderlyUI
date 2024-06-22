package com.example.elderlyui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.elderlyui.adapters.TefteriAdapter;
import com.example.elderlyui.models.TefteriItem;
import com.example.elderlyui.persistence.MyApp;

import java.util.List;

public class TefteriActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TefteriAdapter superMarketAdapter;
    private Button writeButton, deleteButton;

    private Button  confirmButton;
    private EditText itemInput;
    private LinearLayout inputLayout;
    private boolean isDeleteMode = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tefteri);

        ImageView exitButton = findViewById(R.id.exit_button);
        writeButton = findViewById(R.id.write_button);
        deleteButton = findViewById(R.id.delete_button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inputLayout = findViewById(R.id.input_layout);
        confirmButton = inputLayout.findViewById(R.id.confirm_button); // Correct way to find confirmButton
        itemInput = inputLayout.findViewById(R.id.item_input);

        MyApp myApp = (MyApp) getApplicationContext();
        List<TefteriItem> itemList = myApp.getSupermarketItems();

        superMarketAdapter = new TefteriAdapter(itemList, new TefteriAdapter.OnRemoveClickListener() {
            @Override
            public void onRemoveClick(TefteriItem item) {
                myApp.removeSupermarketItem(item);
                superMarketAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(superMarketAdapter);

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputLayout.setVisibility(View.VISIBLE);
                itemInput.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(itemInput, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        confirmButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("fwfqwefqwwefqw");
                String itemName = itemInput.getText().toString().trim();
                if (!itemName.isEmpty()) {
                    TefteriItem newItem = new TefteriItem(itemName);
                    myApp.addSupermarketItem(newItem);
                    superMarketAdapter.notifyDataSetChanged();
                    itemInput.setText("");
                    inputLayout.setVisibility(View.GONE);
                }
                return false;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeleteMode = !isDeleteMode;
                superMarketAdapter.setDeleteMode(isDeleteMode);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
