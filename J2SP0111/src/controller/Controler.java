/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import View.View;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author ACER
 */

//25+6=
public class Controler {

    private View view;
    private boolean incal = false;
    private boolean reset = false;
    private boolean can = false;
    private BigDecimal curentNumber;
    private BigDecimal curentNumber2;
    private BigDecimal firstNum = new BigDecimal("0");
    private BigDecimal secondNum;
    private BigDecimal temp;
    private String operator = null;
    private JTextField text;

    public Controler(View view) {
        this.view = view;
        this.text = view.getTxtDisplay();
        ControlAction();
    }

    private void ControlAction() {
        // Press Number action:
        view.getBtn0().addActionListener((ae) -> { // use lamda expression for funtional interface ActionListener
            PressNumber(view.getBtn0());
        });
        view.getBtn1().addActionListener((ae) -> {
            PressNumber(view.getBtn1());
        });
        view.getBtn2().addActionListener((ae) -> {
            PressNumber(view.getBtn2());
        });
        view.getBtn3().addActionListener((ae) -> {
            PressNumber(view.getBtn3());
        });
        view.getBtn4().addActionListener((ae) -> {
            PressNumber(view.getBtn4());
        });
        view.getBtn5().addActionListener((ae) -> {
            PressNumber(view.getBtn5());
        });
        view.getBtn6().addActionListener((ae) -> {
            PressNumber(view.getBtn6());
        });
        view.getBtn7().addActionListener((ae) -> {
            PressNumber(view.getBtn7());
        });
        view.getBtn8().addActionListener((ae) -> {
            PressNumber(view.getBtn8());
        });
        view.getBtn9().addActionListener((ae) -> {
            PressNumber(view.getBtn9());
        });

        //caculate Action 's
        view.getBtnPlus().addActionListener((ae) -> {
            caculate();
            operator = "+";
        });
        view.getBtnSub().addActionListener((ae) -> {
            
            caculate();
            operator = "-";
        });
        view.getBtnMul().addActionListener((ae) -> {
            caculate();
            operator = "*";
        });
        view.getBtnDiv().addActionListener((ae) -> {
            caculate();
            operator = "/";
        });
        view.getBtnEqual().addActionListener((ae) -> {
            equal();
        });

        view.getBtnC().addActionListener((ae) -> {
            PressC();
        });

        // press Back action
        view.getBtnBack().addActionListener((ae) -> {
            PressBack();
        });

        //press dot action
        view.getBtnDot().addActionListener((ae) -> {
            pressDot();
        });
        //press negate action
        view.getBtnPorM().addActionListener((ae) -> {
            pressNegate();
        });
        //press invert action
        view.getBtnInvert().addActionListener((ae) -> {
            pressInvert();
        });
        //press precent action
        view.getBtnPercent().addActionListener((ae) -> {
            pressPercent();
        });
        //press sqrt action
        view.getBtnSqrt().addActionListener((ae) -> {
            pressSqrt();
        });
        view.getBtnCE().addActionListener((ae) -> {
            PressC();
        });

    }

    private void lockIferror() {  // if trigger error lock the all callulation
        if (text.getText().equalsIgnoreCase("ERROR")) {
            view.getBtnPlus().setEnabled(false);
            view.getBtnSub().setEnabled(false);
            view.getBtnSqrt().setEnabled(false);
            view.getBtnPorM().setEnabled(false);
            view.getBtnDiv().setEnabled(false);
            view.getBtnDot().setEnabled(false);
            view.getBtnInvert().setEnabled(false);
            view.getBtnPercent().setEnabled(false);
            view.getBtnMul().setEnabled(false);
            view.getBtnEqual().setFocusable(true);
            view.getBtnEqual().requestFocus();
        } else {
            view.getBtnPlus().setEnabled(true);
            view.getBtnSub().setEnabled(true);
            view.getBtnSqrt().setEnabled(true);
            view.getBtnPorM().setEnabled(true);
            view.getBtnDiv().setEnabled(true);
            view.getBtnDot().setEnabled(true);
            view.getBtnInvert().setEnabled(true);
            view.getBtnPercent().setEnabled(true);
            view.getBtnMul().setEnabled(true);

        }
    }

    private BigDecimal getNumber() {
        curentNumber = new BigDecimal(text.getText());
        return curentNumber;
    }

    private void PressNumber(JButton button) {
        if (incal == true || reset == true) { // if in the caculator or reset clear text
            text.setText("0");
            incal = false;
            reset = false;
        }
        curentNumber = new BigDecimal(text.getText() + button.getText());
        text.setText(curentNumber.toPlainString());
        lockIferror();

    }

    private void caculate() {

        if (incal == false) { // before calculate
            if (operator == null) {  //before press             
                firstNum = getNumber(); // take the number before press operator 
            } else if (operator == "p+") {
                firstNum = getNumber();
                firstNum = new BigDecimal(Double.valueOf(temp.doubleValue() + firstNum.doubleValue()).toString());
                operator = null;
            } else if (operator == "p-") {
                firstNum = getNumber();
                firstNum = new BigDecimal(Double.valueOf(temp.doubleValue() - firstNum.doubleValue()).toString());
            } else if (operator == "p*") {
                firstNum = getNumber();
                firstNum = new BigDecimal(Double.valueOf(temp.doubleValue() * firstNum.doubleValue()).toString());
            } else if (operator == "p/") {
                firstNum = getNumber();
                firstNum = new BigDecimal(Double.valueOf(temp.doubleValue() / firstNum.doubleValue()).toString());
            } else { // after press oper
                lockIferror();
                secondNum = getNumber();
                switch (operator) {
                    case "+":// Bigdecimal not have overload contructor for Double but Double better represent than primitive type double
                        firstNum = new BigDecimal(Double.valueOf(firstNum.doubleValue() + secondNum.doubleValue()).toString());
                        break;
                    case "-":
                        firstNum = new BigDecimal(Double.valueOf(firstNum.doubleValue() - secondNum.doubleValue()).toString());
                        break;
                    case "*":
                        if ( can == false) {
                            firstNum = new BigDecimal(Double.valueOf(firstNum.doubleValue() * secondNum.doubleValue()).toString());
                        } else{
                            firstNum = new BigDecimal(Double.valueOf(firstNum.doubleValue() * secondNum.doubleValue()).toString()).setScale(15, RoundingMode.HALF_UP);
                            can = false;
                        }
                        break;
                    case "/":
                        if (secondNum.doubleValue() == 0) {
                            text.setText("ERROR");
                            reset = true; // error reset
                            return;
                        } else {
                            firstNum = new BigDecimal(Double.valueOf(firstNum.doubleValue() / secondNum.doubleValue()).toString());
                            break;
                        }

                }

            }
            firstNum = firstNum.stripTrailingZeros(); //  remove unnesscessary zero digit            
            // remove 0 digit in last
            text.setText(firstNum.toPlainString());
            incal = true;
        }
    }

    private void equal() {

        if (text.getText().equalsIgnoreCase("ERROR")) {
            text.setText("0"); // reset 
            reset = true;
        } else {
            caculate();  // just excute on calculate
            operator = null; // reset operator
        }
        lockIferror();
    }

    private void PressBack() {
        StringBuilder builder = new StringBuilder(text.getText());
        if (text.getText().equalsIgnoreCase("ERROR")) {  // if error reset 
            text.setText("0");
            reset = true;
        }
        if (text.getText().startsWith("-")) {
            if (text.getText().length() > 2) {  // if text length than 2 dele
                text.setText(builder.deleteCharAt(text.getText().length() - 1).toString()); // delete last number digit
            } else {
                text.setText("0");
                reset = true;
            }
        } else {
            if (text.getText().length() > 1) {
                text.setText(builder.deleteCharAt(text.getText().length() - 1).toString()); // delete last number 
            } else {
                text.setText("0");
                reset = true;
            }
        }
        lockIferror();
        incal = false;

    }

    private void PressC() { // clear all
        text.setText("0");
        operator = null;
        incal = false;
        reset = true;
        lockIferror();
    }

    private void pressDot() {

        if (incal == true || reset == true) { // if in the caculator or reset clear text
            text.setText("0");
            incal = false;
            reset = false;
        }
        if (text.getText().contains(".") == false) {
            text.setText(text.getText() + ".");
        }
        lockIferror();

    }

    private void pressNegate() {
        lockIferror();
        text.setText(getNumber().negate().toPlainString());
        incal = false;// make it to be first number maybe
    }

    //1/x
    private void pressInvert() {

        // take number in text
        if (text.getText().equalsIgnoreCase("0")) {
            text.setText("ERROR");
            reset = true;
        } else {
            curentNumber = getNumber();
            curentNumber = new BigDecimal(Double.valueOf(1 / curentNumber.doubleValue()).toString());
            text.setText(curentNumber.stripTrailingZeros().toPlainString());
            incal = false; // make it source to calculator
            secondNum = curentNumber;
        }
        reset = true; // if you press any number get new number
        lockIferror();

    }

    private void pressPercent() {
        if (incal ==false) {
            curentNumber = new BigDecimal("0");
        }
        if (operator == "+") {
            temp = firstNum;
            operator = "*";
            equal();
            curentNumber = getNumber();
            operator = "p+";
        } else if (operator == "-") {
            temp = firstNum;
            operator = "*";
            equal();
            curentNumber = getNumber();
            operator = "p-";
        } else if (operator == "*") {
            operator = "/";
            temp = firstNum;
            curentNumber = getNumber();
            operator = "p*";
        } else if (operator == "/") {
            temp = firstNum;
            curentNumber = getNumber();
            operator = "p/";
        }
        curentNumber = new BigDecimal(Double.valueOf(curentNumber.doubleValue() / 100).toString());
        text.setText(curentNumber.stripTrailingZeros().toPlainString());
        incal = false;
        reset = true;
        lockIferror();
    }

    private void pressSqrt() {
        if (text.getText().startsWith("-")) { // sqrt can not negative
            text.setText("ERROR");
            reset = true;
        } else {
            curentNumber = getNumber();
            curentNumber = new BigDecimal(Double.valueOf(Math.sqrt(curentNumber.doubleValue())).toString()).setScale(16, RoundingMode.HALF_UP);
            text.setText(curentNumber.stripTrailingZeros().toPlainString());
            incal = false;
            reset = true;
            can = true;
        }
        lockIferror();
    }

}
