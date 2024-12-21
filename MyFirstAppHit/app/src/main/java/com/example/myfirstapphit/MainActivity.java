package com.example.myfirstapphit;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView textViewExpression;
    private TextView textViewResult;
    private StringBuilder expression;
    private boolean isNewCalculation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewExpression = findViewById(R.id.textViewExpression);
        textViewResult = findViewById(R.id.textViewResult);
        expression = new StringBuilder();

        clearAll();
    }

    public void numFunc(View view) {
        Button button = (Button) view;
        String value = button.getText().toString();

        if (isNewCalculation) {
            expression.setLength(0);
            isNewCalculation = false;
        }

        expression.append(value);
        updateDisplay();
    }

    public void operatorFunc(View view) {
        Button button = (Button) view;
        String op = button.getText().toString();

        if (isNewCalculation && !textViewResult.getText().toString().isEmpty()) {
            expression = new StringBuilder(textViewResult.getText().toString());
            isNewCalculation = false;
        }

        if (expression.length() > 0) {
            char lastChar = expression.charAt(expression.length() - 1);
            if (isOperator(lastChar)) {
                expression.setLength(expression.length() - 1);
            }
            expression.append(" ").append(op).append(" ");
            updateDisplay();
        }
    }

    public void calculateResult(View view) {
        if (expression.length() > 0) {
            try {
                while (expression.length() > 0 && expression.charAt(expression.length() - 1) == ' ') {
                    expression.setLength(expression.length() - 1);
                }

                char lastChar = expression.charAt(expression.length() - 1);
                if (isOperator(lastChar)) {
                    return;
                }

                String expressionStr = expression.toString();
                double result = evaluateExpression(expressionStr);

                textViewResult.setText(formatResult(result));
                isNewCalculation = true;

            } catch (Exception e) {
                textViewResult.setText("Error");
                isNewCalculation = true;
            }
        }
    }

    private double evaluateExpression(String expr) {
        String[] parts = expr.split(" ");
        double result = Double.parseDouble(parts[0]);

        for (int i = 1; i < parts.length; i += 2) {
            String operator = parts[i];
            double number = Double.parseDouble(parts[i + 1]);

            switch (operator) {
                case "+":
                    result += number;
                    break;
                case "-":
                    result -= number;
                    break;
                case "*":
                    result *= number;
                    break;
                case "/":
                    if (number != 0) {
                        result /= number;
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                    break;
            }
        }
        return result;
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    public void clearFunc(View view) {
        clearAll();
    }

    private void clearAll() {
        expression.setLength(0);
        textViewExpression.setText("");
        textViewResult.setText("");
        isNewCalculation = true;
    }

    public void deleteLastChar(View view) {
        if (expression.length() > 0) {
            if (isNewCalculation) {
                clearAll();
                return;
            }
            if (expression.length()==1){
                expression.setLength(0);
            }
            else {
                char lastChar = expression.charAt(expression.length() - 1);
                if (lastChar == ' ' && expression.length() >= 3) {
                    expression.setLength(expression.length() - 3);
                } else {
                    expression.setLength(expression.length() - 1);
                }
            }

            if (expression.length() > 0) {
                updateDisplay();
            } else {
                clearAll();
            }
        }
    }

    public void changeSign(View view) {
        if (expression.length() > 0) {
            try {
                double currentNumber = Double.parseDouble(expression.toString());
                currentNumber = -currentNumber;
                expression.setLength(0);
                expression.append(currentNumber);
                updateDisplay();
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void calculatePercentage(View view) {
        if (expression.length() > 0) {
            try {
                double currentNumber = Double.parseDouble(expression.toString());
                currentNumber = currentNumber / 100;
                expression.setLength(0);
                expression.append(currentNumber);
                updateDisplay();
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dotFunc(View view) {
        if (isNewCalculation) {
            expression.setLength(0);
            expression.append("0");
            isNewCalculation = false;
        }

        String currentNumber = getCurrentNumber();

        if (!currentNumber.contains(".")) {
            expression.append(".");
            updateDisplay();
        }
    }

    private String getCurrentNumber() {
        String expr = expression.toString();
        int lastSpace = expr.lastIndexOf(" ");
        if (lastSpace == -1) {
            return expr;
        }
        return expr.substring(lastSpace + 1);
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private void updateDisplay() {
        textViewExpression.setText(expression.toString());
    }
}