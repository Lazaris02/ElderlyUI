package com.example.elderlyui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.elderlyui.models.Pill;

public class AddPillsActivity extends AppCompatActivity {
    private Pill pill;
    EditText addedPill;
    TextView messageText;
    ImageView next;
    ImageView before;
    TextView left;
    TextView right;
    ImageView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pill=new Pill();
        setContentView(R.layout.add_pills);
        addedPill = findViewById(R.id.pill_input);
        messageText = findViewById(R.id.input_message);
        next=findViewById(R.id.nextImage);
        before=findViewById(R.id.beforeImage);
        left=findViewById(R.id.before);
        right=findViewById(R.id.next);
        exit=findViewById(R.id.exit_text);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        addName();
    }
    private void addName(){
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
                System.out.println(addedPillstr);
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
                pill.setDose(null);
                addName();
            }
        });

        //user added dose
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedDosestr=addedPill.getText().toString();
                System.out.println(addedDosestr);
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
        addedPill.setText(pill.getTimes());
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
                pill.setTimes(null);
                setDose();
            }
        });

        //user added times per day
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addedTimestr=addedPill.getText().toString();
                System.out.println(addedTimestr);
                if(addedTimestr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "ΠΡΟΣΘΕΣΤΕ ΤΙΣ ΦΟΡΕΣ ΠΟΥ ΘΑ ΛΑΜΒΑΝΕΤΕ ΤΟ ΦΑΡΜΑΚΟ ΚΑΘΗΜΕΡΙΝΑ", Toast.LENGTH_SHORT).show();
                }
                else{
                    pill.setTimes(addedTimestr);
                    setDose();
                }
            }
        });
    }
}
