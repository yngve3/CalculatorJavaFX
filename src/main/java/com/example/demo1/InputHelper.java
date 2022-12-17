package com.example.demo1;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class InputHelper {

    private final Label textCalculate;
    private final TextArea input;

    private String calcString = "";

    private final String separator = ",";
    private boolean isNum = true;
    private boolean unary;
    private final RPN rpn;

    public InputHelper(Label textCalculate, TextArea input) {
        this.input = input;
        this.textCalculate = textCalculate;
        rpn = new RPN();
    }

    public void addSym(String sym) {

        if (sym.equals(separator) && inputIsContains(separator)) return;

        if (isNumeric(sym) || sym.equals(separator)) {
            if (!isNum) clearInputField();
            addSymToInput(sym);
            isNum = true;
            unary = false;
        } else {
            String num = input.getText();
            if (isUnary(sym)) {
                calcString += sym + num;
                isNum = true;
                unary = true;
            } else {
                if (!isNum) {
                    calcString = calcString.substring(0, calcString.length() - 1) + sym;
                } else {
                    if (!unary) calcString += num;
                    calcString += sym;
                }
                isNum = false;
                unary = false;
            }

            textCalculate.setText(calcString);
        }

    }

    public void result() {
        try {
            if (!isNumeric(String.valueOf(calcString.charAt(calcString.length() - 1)))) {
                calcString += input.getText();
            }
            String result = String.valueOf(rpn.calculateExpression(calcString));
            textCalculate.setText(calcString + "=");
            calcString = "";
            clearInputField();
            addSymToInput(round(result));
            isNum = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void negative() {
        if (!(inputIsContains("-") || input.getText().equals("0")))
            if (isNum) {
                String num = input.getText();
                clearInputField();
                addSymToInput("-" + num);
            }
    }

    public void square() {
        addSym("^");
        addSym("2");
    }

    public void recNum() {
        calcString += "1/";
        calcString += input.getText();
        textCalculate.setText(calcString);
        isNum = true;
        unary = true;
    }


    public void clearAllFields() {
        clearInputField();
        if (!textCalculate.getText().isEmpty()) textCalculate.setText("");
        if (!calcString.isEmpty()) calcString = "";
    }

    public void clearInputField() {
        if (!input.getText().equals("0")) {
            input.clear();
        }
        addSymToInput("0");
    }

    public void eraseSym() {
        if (isNum) {
            input.deleteText(input.getText().length() - 1, input.getText().length());
            if (input.getText().isEmpty()) addSymToInput("0");

            if (!calcString.equals(""))
                calcString = calcString.substring(0, calcString.length() - 1);
        }
    }

    private String round(String res) {
        DecimalFormat df;

        String[] expStr = res.split("E");
        int exp = 0;
        if (expStr.length > 1) exp = Integer.parseInt(expStr[1]);

        if (exp <= 14) {
            df = new DecimalFormat("#.#############");
            df.setRoundingMode(RoundingMode.CEILING);
        } else {
            df = new DecimalFormat("0.0E0");
            df.setRoundingMode(RoundingMode.HALF_UP);
            df.setMinimumFractionDigits(9);
        }

        return df.format(Double.parseDouble(res));
    }

    private boolean isUnary(String sym) {
        return sym.equals("√");
    }

    private boolean inputIsContains(String sym) {
        return input.getText().contains(sym);
    }

    private void addSymToInput(String sym) {
        if (!sym.equals(separator)) {
            if (input.getText().equals("0")) input.setText("");
        }

        if (input.getText().length() <= 13) {
            input.appendText(sym);
        } else
            System.out.println("Невозможно ввести болеее 15 символов");
    }

    private boolean isNumeric(String sym) {
        try {
            Double.parseDouble(sym);
            return true;
        } catch (Exception e){
            return false;
        }
    }


}
