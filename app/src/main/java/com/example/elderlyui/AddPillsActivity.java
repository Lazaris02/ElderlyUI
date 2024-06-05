package com.example.elderlyui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elderlyui.models.Pill;
import com.example.elderlyui.persistence.MyApp;

public class AddPillsActivity extends AppCompatActivity {
    private Pill pill;
    EditText addedPill;
    TextView messageText;
    ImageView next;
    ImageView before;
    TextView left;
    TextView right;
    ImageView exit;
    CheckBox morning;
    CheckBox mesimeri;
    CheckBox noon;
    CheckBox night;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pill=new Pill();
        addName();
    }
    private void addName(){
        setContentView(R.layout.add_pills);
        onClicks();
        addedPill.setText(pill.getName());
        String text = "Πληκτρολόγησε το \nόνομα του \nΦαρμάκου";


        SpannableString spannableString = new SpannableString(text);


        int start = text.indexOf("όνομα");
        int end = start + "όνομα".length();
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(Color.BLACK);
        spannableString.setSpan(blackSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
        spannableString.setSpan(whiteSpan, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(whiteSpan, end, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        messageText.setText(spannableString);
        before.setVisibility(TextView.GONE);
        left.setVisibility(TextView.GONE);


        //User added pill name
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedPillstr=addedPill.getText().toString();
                if(addedPillstr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "ΠΡΟΣΘΕΣΤΕ ΟΝΟΜΑ ΦΑΡΜΑΚΟΥ", Toast.LENGTH_SHORT).show();
                }
                else{
                    pill.setName(addedPillstr);
                    setDose();
                }
            }
        });
    }
    private void setDose(){
        setContentView(R.layout.add_pills);
        onClicks();
        addedPill.setText(pill.getDose());
        String text = "Πληκτρολόγησε τη \nδοσολογία του Φαρμάκου";


        SpannableString spannableString = new SpannableString(text);


        int start = text.indexOf("δοσολογία");
        int end = start + "δοσολογία".length();
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(Color.BLACK);
        spannableString.setSpan(blackSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
        spannableString.setSpan(whiteSpan, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(whiteSpan, end, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        messageText.setText(spannableString);

        //Set visibility from stuff that was not visible before
        before.setVisibility(TextView.VISIBLE);
        left.setVisibility(TextView.VISIBLE);

        //User goes back
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedDosestr=addedPill.getText().toString();
                pill.setDose(addedDosestr);
                addName();
            }
        });

        //user added dose
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedDosestr=addedPill.getText().toString();
                if(addedDosestr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "ΠΡΟΣΘΕΣΤΕ ΔΟΣΟΛΟΓΙΑ ΦΑΡΜΑΚΟΥ", Toast.LENGTH_SHORT).show();
                }
                else{
                    pill.setDose(addedDosestr);
                    setTimes();
                }
            }
        });
    }
    private void setTimes(){
        setContentView(R.layout.add_times);
        onClicks();
        morning.setChecked(pill.isMorning());
        mesimeri.setChecked(pill.isMesimeri());
        noon.setChecked(pill.isNoon());
        night.setChecked(pill.isNight());
        String text = "Πόσες φορές την \nμέρα θα \nλαμβάνεται το \nφάρμακο";


        SpannableString spannableString = new SpannableString(text);


        int start = text.indexOf("φορές την \nμέρα");
        int end = start + "φορές την \nμέρα".length();
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(Color.BLACK);
        spannableString.setSpan(blackSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
        spannableString.setSpan(whiteSpan, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(whiteSpan, end, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        messageText.setText(spannableString);

        //User goes back
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pill.setMorning(morning.isChecked());
                pill.setNoon(noon.isChecked());
                pill.setNight(night.isChecked());
                pill.setMesimeri(mesimeri.isChecked());
                setDose();
            }
        });

        //user added times per day
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pill.setMorning(morning.isChecked());
                pill.setNoon(noon.isChecked());
                pill.setNight(night.isChecked());
                pill.setMesimeri(mesimeri.isChecked());
                ((MyApp) getApplication()).addPill(pill);
            }
        });
    }
    private void onClicks(){
        addedPill = findViewById(R.id.pill_input);
        messageText = findViewById(R.id.input_message);
        next=findViewById(R.id.nextImage);
        before=findViewById(R.id.beforeImage);
        left=findViewById(R.id.before);
        right=findViewById(R.id.next);
        exit=findViewById(R.id.exit_text);
        morning=findViewById(R.id.morning);
        mesimeri=findViewById(R.id.mesimeri);
        noon=findViewById(R.id.evening);
        night=findViewById(R.id.night);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
}
