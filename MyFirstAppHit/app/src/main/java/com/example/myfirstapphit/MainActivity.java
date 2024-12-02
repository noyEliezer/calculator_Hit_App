package com.example.myfirstapphit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private double firstNumber = 0;
    private String operator = "";
    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        textViewResult.setText("");
    }

    public void numFunc(View view) {
        Button button = (Button) view;
        String value = button.getText().toString();

        if (isOperatorPressed) {
            textViewResult.setText("");
            isOperatorPressed = false;
        }

        textViewResult.append(value);
    }

    public void operatorFunc(View view) {
        Button button = (Button) view;
        operator = button.getText().toString();
        firstNumber = Double.parseDouble(textViewResult.getText().toString());
        textViewResult.setText("");
        isOperatorPressed = true;
    }

    public void calculateResult(View view) {
        String currentText = textViewResult.getText().toString();

        if (currentText.isEmpty()) {
            textViewResult.setText("");
            return;
        }

        if (operator.isEmpty()) {
            textViewResult.setText(currentText);
            return;
        }

        try {
            double secondNumber = Double.parseDouble(currentText);
            double calculationResult = 0;

            switch (operator) {
                case "+":
                    calculationResult = firstNumber + secondNumber;
                    break;
                case "-":
                    calculationResult = firstNumber - secondNumber;
                    break;
                case "*":
                    calculationResult = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        calculationResult = firstNumber / secondNumber;
                    } else {
                        textViewResult.setText("Math Error");
                        return;
                    }
                    break;
            }

            textViewResult.setText(String.valueOf(calculationResult));
        } catch (NumberFormatException e) {
            textViewResult.setText("Error");
        }
    }


    public void clearFunc(View view) {
        textViewResult.setText("");
        firstNumber = 0;
        operator = "";
        isOperatorPressed = false;
    }
    public void changeSign(View view) {
        if (!textViewResult.getText().toString().isEmpty()) {
            double currentNumber = Double.parseDouble(textViewResult.getText().toString());
            currentNumber = -currentNumber;
            textViewResult.setText(String.valueOf(currentNumber));
        }
    }

    public void calculatePercentage(View view) {
        if (!textViewResult.getText().toString().isEmpty()) {
            double currentNumber = Double.parseDouble(textViewResult.getText().toString());
            currentNumber = currentNumber / 100;
            textViewResult.setText(String.valueOf(currentNumber));
        }
    }
    public void dotFunc(View view) {
        String currentText = textViewResult.getText().toString();
        if (currentText.isEmpty()) {
            textViewResult.setText("0.");
            return;
        }
        if (currentText.contains(".")) {
            return;
        }
        textViewResult.append(".");
    }

}
