package com.example.verrane.calculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newnumber;
    private TextView operation;

    // Variables that hold data
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    //Variables for restoring data on change of screen orientation
    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) findViewById(R.id.result);
        newnumber = (EditText) findViewById(R.id.newnumber);
        operation = (TextView) findViewById(R.id.operation);

        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);

        Button buttondot = (Button) findViewById(R.id.buttondot);
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);    //For negative number input

        Button buttonequal = (Button) findViewById(R.id.buttonequal);
        Button buttonplus = (Button) findViewById(R.id.buttonplus);
        Button buttonminus = (Button) findViewById(R.id.buttonminus);
        Button buttonmultiply = (Button) findViewById(R.id.buttonmultiply);
        Button buttondivide = (Button) findViewById(R.id.buttondivide);

        View.OnClickListener mylistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newnumber.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(mylistener);
        button1.setOnClickListener(mylistener);
        button2.setOnClickListener(mylistener);
        button3.setOnClickListener(mylistener);
        button4.setOnClickListener(mylistener);
        button5.setOnClickListener(mylistener);
        button6.setOnClickListener(mylistener);
        button7.setOnClickListener(mylistener);
        button8.setOnClickListener(mylistener);
        button9.setOnClickListener(mylistener);
        buttondot.setOnClickListener(mylistener);

        View.OnClickListener oplistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button a = (Button) v;
                String op = a.getText().toString();
                String val = newnumber.getText().toString();
                try {
                    Double doubleval = Double.valueOf(val);
                    performOperation(doubleval, op);
                } catch (NumberFormatException e) {
                    newnumber.setText("");
                }
                pendingOperation = op;
                operation.setText(pendingOperation);
            }
        };

        buttonequal.setOnClickListener(oplistener);
        buttonplus.setOnClickListener(oplistener);
        buttonminus.setOnClickListener(oplistener);
        buttonmultiply.setOnClickListener(oplistener);
        buttondivide.setOnClickListener(oplistener);

        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = newnumber.getText().toString();
                if(val.length() == 0)
                {
                    newnumber.setText("-");
                }else
                {
                    try {
                        Double doubval = Double.valueOf(val);
                        doubval *= -1;
                        newnumber.setText(doubval.toString());
                    }catch (NumberFormatException e)
                    {
                        newnumber.setText("");
                    }
                }
            }
        });

    }

    private void performOperation(Double value, String operation) {
        if (operand1 == null) {
            operand1 = value;
        } else {
            operand2 = value;
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= operand2;
                    }
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "+":
                    operand1 += operand2;
                    break;
            }
        }
        result.setText(operand1.toString());
        newnumber.setText("");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        operation.setText(pendingOperation);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }
}
