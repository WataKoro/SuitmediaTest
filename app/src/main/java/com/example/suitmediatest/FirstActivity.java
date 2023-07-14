package com.example.suitmediatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {
    private EditText username;
    private EditText palindrome;
    private Button buttonPalindrome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        palindrome = findViewById(R.id.Palindrome);
        buttonPalindrome = findViewById(R.id.buttonPalindrome);

        buttonPalindrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = palindrome.getText().toString();

                // Remove any spaces and convert to lowercase for accurate palindrome check
                String processedText = inputText.replaceAll("\\s+", "").toLowerCase();

                // Check if the processedText is a palindrome
                boolean isPalindrome = checkPalindrome(processedText);

                // Display the result with a dialog message
                String resultMessage = isPalindrome ? "It's a palindrome!" : "It's not a palindrome!";
                Toast.makeText(FirstActivity.this, resultMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkPalindrome(String text) {
        // Compare the text with its reversed version
        String reversedText = new StringBuilder(text).reverse().toString();
        return text.equals(reversedText);
    }

    public void nextActivity(View v){
        username = findViewById(R.id.Name);
        String Username = username.getText().toString();
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        switchActivityIntent.putExtra("Username", Username);
        startActivity(switchActivityIntent);
    }
}