package com.versionpb.bodmascalculator;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

// import com.google.android.gms.ads.AdListener;
// import com.google.android.gms.ads.AdRequest;
// import com.google.android.gms.ads.AdView;
// import com.google.android.gms.ads.LoadAdError;
// import com.google.android.gms.ads.interstitial.InterstitialAd;
// import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
// import com.google.android.gms.ads.FullScreenContentCallback;
import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/*
Version History :

Date            Author              Version                     Description
1st Nov 2018    Paarth Batra        Code : 4 , name : 1.3       Addition of AdMob Banner Ad

 */

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul, button10, buttonC, buttonEqual, buttonLBracket, buttonRBracket, buttonDel, buttonSkin,
            buttonOf, buttonDollar, buttonV;
    EditText vpbEditText;
    TextView vpbTextView, linkTextView;

    String LineTextView;
    String LineEditView;
    String SetLineTextView;
    String SetLineEditView;

    // private Object mBannerAd;
    // private Object mInterstitialAd;

    TableLayout rootLayout;

    private int theme = 0;
    private String title;

    private void log(String msg) {
        android.util.Log.d("BODMASCalculator", msg);
    }

    public void CalculatorInputLogic(View v, String input) {
        log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Value Entered is " + input);
        log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : LineEditView is " + LineEditView);
        log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : LineTextView  is " + LineTextView);
        log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : SetLineEditView is " + SetLineEditView);
        log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : SetLineTextView is " + SetLineTextView);

        // Default if clause , i.e. when very first time any numeric value is entered
        List<String> numerics = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        List<String> numericsWithDotandMinus = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "-",
                "0");
        List<String> numericsWithDot = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", ".");
        List<String> numericsWithZero = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
        List<String> numericsZeroDot = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".");
        List<String> numericsZeroDotLBracket = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".",
                "(");
        List<String> numericsZeroDotRBracket = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".",
                ")");
        List<String> numericsZeroDotBothBracket = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".",
                "(", ")");
        List<String> operator = Arrays.asList("+", "-", "\u00F7", "X", "^");
        List<String> operatorDotZero = Arrays.asList("+", "-", "\u00F7", "X", ".", "0", "^");
        if (LineTextView.isEmpty() && "0".equals(LineEditView) && numericsWithDotandMinus.contains(input)) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Input Value is a Numeric and this is 1st Character Entered "
                    + input);
            if (input.equalsIgnoreCase(".")) {
                LineTextView = "0.";
                LineEditView = "0.";
                SetLineEditView = "0.";
                SetLineTextView = "";
            } else {
                LineTextView = input;
                LineEditView = input;
                SetLineEditView = input;
                SetLineTextView = "";
            }

            vpbEditText.setText(SetLineEditView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);
        } else if ("-".equals(LineTextView) && "-".equals(LineEditView) && numericsZeroDot.contains(input)) {
            if (input.equalsIgnoreCase(".")) {
                LineTextView = "-0.";
                LineEditView = "-0.";
                SetLineEditView = "-0.";
                SetLineTextView = "";
            } else {
                LineTextView = "-" + input;
                LineEditView = "-" + input;
                SetLineEditView = "-" + input;
                SetLineTextView = "";
            }

            vpbEditText.setText(SetLineEditView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);

        }
        // 1st Elseif clause , i.e. when C is entered
        else if ("C".equals(input)) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 1 C : Everything is cleared ");
            LineTextView = "";
            LineEditView = "0";
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);
        }
        // 2nd Elseif clause , When ts first entry and its an operator or 0 or .
        else if (LineTextView.isEmpty() && "0".equals(LineEditView) && operatorDotZero.contains(input)) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 2 Very First Entry and that too an Operator or . or 0 . Nothing Changes  ");
            LineTextView = "";
            LineEditView = "0";
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit View  is "
                    + SetLineEditView);
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Text View  is "
                    + SetLineTextView);
        }

        // 2.1 Elseif clause , When ts first entry and its backspace
        else if (LineTextView.isEmpty() && "0".equals(LineEditView) && "D".equals(input)) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 2.1 Very First Entry and its a Backspace. Nothing Changes  ");
            // Value in SetLineEditView: 0
            // Value in SetLineTextView
            // Value in LineEditView is 0
            // Value in LineTextView

        }

        // Elif 2.2 When D is entered i.e. Backspace and its not the very first input
        // Condition 1 : Input is D
        else if (input.equalsIgnoreCase("D")) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 2.2 , Input is = D and this is not very first input.Entered Input  : "
                    + input);
            /*
             * 1st is text view
             * 2nd is edit view
             * Conditions :
             * 1. When Linetextview is empty and SetLineTextView is empty and Edit view
             * length is 1 and setEditView length is 1 , i.e.
             * //SetLineEditView : 1
             * SetLineTextView :
             * LineEditView is 1:::
             * LineTextView 1
             * 
             * then . Make everything default i.e. as if C is pressed
             * 
             * 
             * 2. When LineTextView last character is an operator but SetLineTextView i.e.
             * Expression last character is also a operator i.e. nothing after operator is
             * pressed
             * and length could be anything i.e.
             * Value in SetLineEditView : 19::
             * Value in SetLineTextView : 19+:::
             * Value in LineEditView is 19:::
             * Value in LineTextView19+
             * 
             * then nothing changes .. i.e. everything like as it as , as in case of D for
             * very first entry , just write a SOP in this else if
             * 
             * 3.When Last entry is oprator in LineTextView and last entry not Operator in
             * SetLineTextView
             * Value in SetLineEditView : 6:::
             * Value in SetLineTextView : 19+6:::
             * Value in LineEditView is 19:::
             * Value in LineTextView19+
             * 
             * then remove last character from SetLineTextView
             * Make Last Remove last entry from SetLineEditView . If length is 1 , make it 0
             * 
             * 
             * 4. When no operator is pressed so far and value entered length is more than 1
             * i.e. something linke below
             * Value in SetLineEditView : 69:::
             * Value in SetLineTextView : :::
             * Value in LineEditView is 69:::
             * Value in LineTextView69
             * 
             * we remove last entry from SetLineEditView , LineEditView and LineTextView
             * 
             */

            if (SetLineEditView.length() == 1 && LineEditView.length() == 1 && LineTextView.length() == 1) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 1 , when there is only 1 chacrater entered in Edit View , Everything as if we clear screen ");
                LineTextView = "";
                LineEditView = "0";
                SetLineEditView = LineEditView;
                SetLineTextView = LineTextView;

                vpbEditText.setText(SetLineEditView);
                vpbTextView.setText(SetLineTextView);
            }

            else if (operator.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1))) &&
                    !operator.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 3 Last  entry is oprator in LineTextView and last entry not Operator in SetLineTextView ");
                SetLineTextView = SetLineTextView.substring(0, SetLineTextView.length() - 1);

                if (SetLineEditView.length() == 1) {
                    SetLineEditView = "0";
                } else {
                    SetLineEditView = SetLineEditView.substring(0, SetLineEditView.length() - 1);
                }
                vpbEditText.setText(SetLineEditView);
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                        + SetLineEditView);
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                        + SetLineTextView);

            } else if (SetLineEditView.equalsIgnoreCase(LineEditView)
                    && SetLineEditView.equalsIgnoreCase(LineTextView)) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 4 , when No operator is pressed yet  ");
                SetLineEditView = SetLineEditView.substring(0, SetLineEditView.length() - 1);
                LineTextView = LineTextView.substring(0, LineTextView.length() - 1);
                LineEditView = SetLineEditView;
                vpbEditText.setText(SetLineEditView);
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                        + SetLineEditView);
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                        + SetLineTextView);
            } else if (operator.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1))) &&
                    operator.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 2 when there is operator at the end and nothing numeric is entered . Nothing changes");
            }

        }
        // Elif 2.3 When Bracket is entered
        // Condition 1 : Input is (
        else if (input.equalsIgnoreCase("(")) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.3 Left Bracket . Input is : "
                    + input);
            // Case 1 i.e. very first time entry and its a (
            // Value in SetLineEditView : 0:::
            // Value in SetLineTextView : :::
            // Value in LineEditView is 0:::
            // Value in LineTextView

            // Case 2 i.e. there is a operator in last digit then and its a ( i.e. example
            // 89+4+(
            // Value in SetLineEditView : 93::: after ( will be 93
            // Value in SetLineTextView : 89+4+::: after ( will be 89 + 4 + (
            // Value in LineEditView is 93::: after ( will be 93
            // Value in LineTextView89+4+ after ( will be 89 + 4 + (

            if (LineTextView.isEmpty() && "0".equals(LineEditView)) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Case 1 . First Entry as Left Bracket . Input is : "
                        + input);

                LineTextView = "(";
                LineEditView = "(";
                SetLineEditView = LineEditView;
                SetLineTextView = "";

                vpbEditText.setText(SetLineEditView);

                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                        + SetLineEditView);
            }
            // Case 2
            else if (operator.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))) {
                LineTextView = LineTextView.concat("(");
                SetLineTextView = LineTextView;

                vpbTextView.setText(SetLineTextView);

                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                        + SetLineTextView);
            } else {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else . No Changes . Input is : "
                        + input);
            }

        }

        // Elif 2.4 When Bracket is entered
        // Condition 1 : Input is )
        else if (input.equalsIgnoreCase(")")) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.4 Right Bracket . Input is : "
                    + input);
            // Case 1 i.e. very first time entry and its a (
            // Value in SetLineEditView : 0:::
            // Value in SetLineTextView : :::
            // Value in LineEditView is 0:::
            // Value in LineTextView

            if ((LineTextView.isEmpty() && "0".equals(LineEditView))
                    || !(SetLineEditView.contains("(") || SetLineTextView.contains("("))) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Case 1 . First Entry as Right Bracket , Nothing Happens. Input is : "
                        + input);

            }
            // Case 2
            // Not first time entry for right bracket and last character is a digit or .
            // with no operator yet present i.e. SetLineTextView is empty
            else if (numericsZeroDot.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))
                    && SetLineTextView.length() == 0) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.4 , Case 2 . Input Right Bracket ,last character is numerics.zero , no operator yet entered . Input is : "
                        + input);

                LineTextView = LineTextView.concat(input);
                LineEditView = LineEditView.concat(input);
                SetLineEditView = LineEditView;
                SetLineTextView = LineTextView;

                vpbEditText.setText(SetLineEditView);
                // vpbTextView.setText(SetLineTextView);

                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                        + SetLineEditView);

            }
            // Case 3
            // Not first time entry for right bracket and last character is a digit or and
            // operator alredy present
            else if (numericsZeroDot.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1))) &&
                    numericsZeroDot
                            .contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))) {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.4 , Case 2 . INput Right Bracket ,last character is numerics.zero . Input is : "
                        + input);

                LineTextView = SetLineTextView.concat(input);
                LineEditView = SetLineTextView.concat(input);
                SetLineEditView = SetLineTextView.concat(input);
                SetLineTextView = SetLineTextView.concat(input);

                // vpbEditText.setText(SetLineTextView);
                vpbTextView.setText(SetLineTextView);

                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To TextView  is "
                        + SetLineTextView);

            } else {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else . No Changes . Input is : "
                        + input);
            }
        }
        // Elif 3 When = is entered
        // Condition 1 : Input is =
        else if (input.equalsIgnoreCase("=")) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 3 , Input is = . Input  : " + input);

            String Expression = SetLineTextView;

            if (!Expression.isEmpty()) {

                if (operator.contains(Character.toString(Expression.charAt(Expression.length() - 1)))) {
                    Expression = Expression.substring(0, Expression.length() - 1);
                    log("Cls --> MainActivity --> Fn --> Last Charater in Expression was operator . New Expression is   : "
                            + Expression);
                }

                // Get Result of Expression
                String Result = "Calculate Me";
                Result = CalculateBODMASExpression(Expression);

                SetLineEditView = Result;
                SetLineTextView = "";
                LineEditView = SetLineEditView;
                LineTextView = SetLineEditView;

                vpbEditText.setText(SetLineEditView);
                vpbTextView.setText(SetLineTextView);

                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                        + SetLineEditView);
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                        + SetLineTextView);
            } else {
                log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Expression is null , could be that = entered again . Nothing Changes");
            }
        }
        // 4th Elseif clause , Not first entry but second time entry for .
        // when last character in LineView and EditView is numerics , input Value is .
        // and setEditView doesnt contain a . already
        else if (".".equals(input) && SetLineEditView.contains(".")) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 4 You are entering . second time . Nothing Changes  ");
            log("Paarth numericsZeroDot.toString() " + numericsZeroDot.toString());
        }

        // 5th Else If , Not first time entry and its numericswithZero or . i.e.
        // numericsZeroDot and editview last value is not an operator but is an ( or )
        else if ("(".contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))
                && numericsWithDotandMinus.contains(input)) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 5 Input Value is a Numeric and this is not First Entry . Input is : "
                    + input);
            LineTextView = LineTextView.concat(input);
            LineEditView = input;
            SetLineEditView = input;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);
        }

        // 5.1th Else If , Not first time entry and its numericswithZero or . i.e.
        // numericsZeroDot and editview last value is not an operator
        else if (numericsZeroDotLBracket.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1))) &&
                numericsZeroDotLBracket.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))
                && numericsZeroDot.contains(input)) {
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 5.1 Input Value is a Numeric and this is not First Entry . Input is : "
                    + input);
            LineTextView = LineTextView.concat(input);
            LineEditView = LineEditView.concat(input);
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Text View  is "
                    + SetLineTextView);
        }

        // 6th Else If Operator Entry + LineEditView last char is either a numeric or .
        // + LineTextView last char is either a numeric or .
        else if (operator.contains(input)
                && numericsZeroDotRBracket.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))
                && numericsZeroDotRBracket
                        .contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 6 Not first time entry , Operator Entry . Input  : "
                    + input);
            LineTextView = LineTextView.concat(input);
            // LineEditView = LineEditView; No Changes
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            // vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To TextView  is "
                    + SetLineTextView);
        }

        // Elif 7 Condition 1 : Input is numerics + Zero
        // Condition 2 when last Character in SetLineTextView is operator
        // Condition 3 last character in lineEditView is numerics + Zero + .
        else if (numericsWithDotandMinus.contains(input)
                && operator.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))
                && numericsZeroDotBothBracket
                        .contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 7 Not first time entry , First Digit entered after Operator. Input  : "
                    + input);
            // No changes in Line Text View
            // New Input in Line Edit View
            SetLineEditView = input;
            SetLineTextView = SetLineTextView.concat(input);
            LineEditView = SetLineTextView;
            LineTextView = SetLineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                    + SetLineEditView);
        }

        // Elif 8 Condition 1 : Input is numerics + Zero + .
        // Condition 2 when last Character in SetLineTextView is not operator
        // Condition 3 last character in lineEditView is numerics + Zero + .
        // Condition 4 last character in SetLineText View is numerics + 0 + .
        else if (numericsZeroDot.contains(input)
                && numericsZeroDot.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))
                && numericsZeroDotRBracket
                        .contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 8 Not first time entry , Second + Digit entered after operator . Input  : "
                    + input);
            // No changes in Line Text View
            // New Input in Line Edit View
            SetLineEditView = SetLineEditView.concat(input);
            SetLineTextView = SetLineTextView.concat(input);
            LineEditView = SetLineEditView;
            LineTextView = LineTextView.concat(input);

            vpbEditText.setText(SetLineEditView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                    + SetLineEditView);
        }

        // SetLineEditView : -:::
        // Value in SetLineTextView: 615X(-:::
        // Value in LineEditView is -:::
        // Value in LineTextView 615X(-
        // Else if 8.5
        // Condition 1 : Input is numerics + Zero + .
        // Condition 2 when last Character is -
        // Condition 3 last character in lineEditView is -
        // Condition 4 last character in SetLineText View is -

        // inserted becase 615 x (- anything aftre this goes to else clause
        else if (numericsZeroDot.contains(input)
                && "-".contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))
                && "-".contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 8.5 Not first time entry , Anythng after -  : "
                    + input);
            // No changes in Line Text View
            // New Input in Line Edit View
            SetLineEditView = SetLineEditView.concat(input);
            SetLineTextView = SetLineTextView.concat(input);
            LineEditView = SetLineEditView;
            LineTextView = LineTextView.concat(input);

            vpbEditText.setText(SetLineEditView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                    + SetLineEditView);
        }

        // Elif 9 Second Operator Entry
        // Condition 1 : Input is operator
        // Condition 2 when last Character in LineTextView is operator
        // Condition 3 last character in lineEditView is numerics + Zero + . + )
        // Condition 4 last character in SetLineText View is numerics + 0 + .
        else if (operator.contains(input)
                && operator.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))
                && numericsZeroDot.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))
                && numericsZeroDotRBracket
                        .contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))) {

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 9 Not first time entry , Another Operator entered after operator . Input  : "
                    + input);

            // Edit View will show value Calculated as per previous operators using BODMAS
            // Rule
            // Text View will show full expression so far

            // Get String which needs to be passed for calculation
            String Expression = SetLineTextView;

            // Get Result of Expression
            String Result = "Calculate Me";
            Result = CalculateBODMASExpression(Expression);

            SetLineEditView = Result;
            SetLineTextView = SetLineTextView.concat(input);
            LineEditView = SetLineEditView;
            LineTextView = SetLineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                    + SetLineEditView);
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                    + SetLineTextView);
        }

        // If this is not the first Value entered and if operator is entered
        else {
            // We will change this
            log("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else Clause");
            log("Cls --> MainActivity --> Fn --> ElseClause : Values :::: Value in SetLineEditView : "
                    + SetLineEditView
                    + ":::Value in SetLineTextView: " + SetLineTextView + ":::Value in LineEditView is " + LineEditView
                    + ":::Value in LineTextView " + LineTextView);
        }
    }

    public void AddText(View v, String input) {
        log("\n\n\n\n\n\n\n\nNew Value Entered Cls --> MainActivity --> Fn --> AddText :::: Value Entered is "
                        + input);
        log("Cls --> MainActivity --> Fn --> AddText :Before Setting Any Value :::Value in SetLineEditView: "
                        + SetLineEditView
                        + " :::Value in SetLineTextView : " + SetLineTextView + "::: Value in LineEditView is "
                        + LineEditView + "::: Value in LineTextView " + LineTextView);

        int TextViewHeight = vpbTextView.getHeight();
        log("Cls --> MainActivity --> Fn --> AddText :::: vpbTextView Height is " + TextViewHeight);
        log("Cls --> MainActivity --> Fn --> AddText :::: buttonEqual.getY()  is " + buttonEqual.getY());
        log("Cls --> MainActivity --> Fn --> AddText :::: buttonEqual.getHeight();  is " + buttonEqual.getHeight());
        log("Cls --> MainActivity --> Fn --> AddText :::: vpbTextView.getTextSize();  is "
                + vpbTextView.getTextSize());

        float TextViewTextSizesp = vpbTextView.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        log("Cls --> MainActivity --> Fn --> AddText :::: TextViewTextSizesp  is " + TextViewTextSizesp);

        // Logic for TextView TextSize Adjustment
        if (vpbTextView.getText().length() < 10) {
            vpbTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        }

        if (vpbTextView.getLineCount() > 2 && TextViewTextSizesp == 40) {
            vpbTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        } else if (vpbTextView.getLineCount() > 3 && TextViewTextSizesp == 20) {
            vpbTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        } else if (vpbTextView.getLineCount() > 5 && TextViewTextSizesp == 15) {
            vpbTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
        }

        CalculatorInputLogic(v, input);

        log("Cls --> MainActivity --> Fn --> AddText :After Setting Any Value :::: Value in SetLineEditView : "
                        + SetLineEditView
                        + ":::Value in SetLineTextView : " + SetLineTextView + ":::Value in LineEditView is "
                        + LineEditView + ":::Value in LineTextView" + LineTextView);

    }

    public String CalculateBODMASExpression(String P_Expression) {
        log("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Input Expression is " + P_Expression);

        // change to * for multiplication in Expression
        String NewExpression = P_Expression.replaceAll("X", "*");
        NewExpression = NewExpression.replaceAll("\u00F7", "/");
        log("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Expression with multiplication substitution is "
                        + NewExpression);
        P_Expression = NewExpression;

        // If mismatched paranthesis , then change expression

        try {
            // If mispatched parenthesis , i.e. if fist character is paranhesis , remove it
            Expression test = new Expression(P_Expression);
            BigDecimal ValueComputed_Test = test.setPrecision(128).eval();

        } catch (Exception E) {
            log("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Exception for checking mismatches paramthesis  . Exception : "
                            + E);
            if (P_Expression.substring(0, 1).equalsIgnoreCase("(")) {
                P_Expression = P_Expression.substring(1, P_Expression.length());
                log("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Expression after ( Cleaning is "
                                + P_Expression);
            }

        }

        /* Interpreter interpreter = new Interpreter(); */
        String result = null;
        try {

            Expression e = new Expression(P_Expression);
            BigDecimal ValueComputed = e.setPrecision(128).eval();

            // Force plain string output to prevent E/scientific notation
            String plainString = ValueComputed.toPlainString();
            int dotIndex = plainString.indexOf('.');
            if (dotIndex >= 0) {
                String beforeDot = plainString.substring(0, dotIndex);
                String afterDot = plainString.substring(dotIndex + 1);
                
                // Keep up to 4 decimal places
                if (afterDot.length() > 4) {
                    afterDot = afterDot.substring(0, 4);
                }
                
                // Trim trailing zeros from the fractional part
                while (afterDot.endsWith("0")) {
                    afterDot = afterDot.substring(0, afterDot.length() - 1);
                }
                
                if (afterDot.isEmpty()) {
                    result = beforeDot;
                } else {
                    result = beforeDot + "." + afterDot;
                }
            } else {
                result = plainString;
            }

        } catch (Exception e) {
            android.util.Log.e("BODMASCalculator", "CalculateBODMASExpression Exception", e);
            log("Cls --> MainActivity --> Fn --> CalculateBODMASExpression Exception  :::: " + e.getMessage());
            result = "Error";
        }
        log("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Result is  : " + result);
        return result;
    }

    private void showBannerAd() {
        // AdRequest adRequest = new AdRequest.Builder().build();
        // mBannerAd.loadAd(adRequest);
    }

    private void showInterstitialAd() {
        // System.out.println("Cls --> MainActivity --> Fn --> showInterstitialAd :::: Loading Interestitial Ad");
        // AdRequest adRequest = new AdRequest.Builder().build();
        // com.google.android.gms.ads.interstitial.InterstitialAd.load(this,
        //         getResources().getString(R.string.interstitial_ad_unit_id), adRequest,
        //         new InterstitialAdLoadCallback() {
        //             @Override
        //             public void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
        //                 mInterstitialAd = interstitialAd;
        //                 mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
        //                     @Override
        //                     public void onAdDismissedFullScreenContent() {
        //                         mInterstitialAd = null;
        //                         showInterstitialAd(); // reload
        //                     }
        //                 });
        //             }
        // 
        //             @Override
        //             public void onAdFailedToLoad(LoadAdError loadAdError) {
        //                 mInterstitialAd = null;
        //             }
        //         });
    }

    public void setTheme(View v, int c, int actionBarColor, int titleColor, int editTextColor, int nextColor) {

        Button[] buttons = {
                buttonLBracket, buttonRBracket, buttonC, buttonDel, button7, button8,
                button9, buttonDivision, button4, button5, button6, button1, button2,
                button3, button10, button0, buttonEqual, buttonAdd, buttonOf, buttonDollar,
                buttonV, buttonSkin, buttonMul, buttonSub
        };
        int btnColor = ContextCompat.getColor(this, c);
        for (Button btn : buttons) {
            btn.setBackgroundColor(btnColor);
        }
        buttonSkin.setTextColor(ContextCompat.getColor(this, nextColor));
        linkTextView.setBackgroundColor(ContextCompat.getColor(this, actionBarColor));
        // linkTextView.setTextColor(getResources().getColor(R.color.black));

        vpbTextView.setBackgroundColor(ContextCompat.getColor(this, editTextColor));

        // getSupportActionBar().setTitle(Html.fromHtml("<font color=\"pink\">" +
        // getString(R.string.app_name) + "</font>"));
        // getSupportActionBar().setTitle(Html.fromHtml("<font color=" +
        // getString(Color) + ">" + getString(R.string.app_name) + "</font>"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            getSupportActionBar().setTitle(Html.fromHtml("<font color=" + getString(titleColor) + ">" + title + "</font>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            getSupportActionBar().setTitle(Html.fromHtml("<font color=" + getString(titleColor) + ">" + title + "</font>"));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, actionBarColor)));

    }

    public void ChangeSkin(View v) {
        log("Cls --> MainActivity --> Fn --> ChangeSkin :::: Setting the new Skin ");

        if (theme == 0) {
            setTheme(v, R.color.pink, R.color.red, R.string.black, R.color.lavenderblush, R.color.colorPrimaryDark);
            theme = 1;
        } else if (theme == 1) {
            setTheme(v, R.color.ButtonColor, R.color.colorPrimaryDark, R.string.white, R.color.TextViewBg,
                    R.color.pink);
            theme = 0;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        log("Cls --> MainActivity --> Fn --> onCreate :::: Just After Super.onCreate ");
        setContentView(R.layout.activity_main);

        log("Cls --> MainActivity --> Fn --> onCreate :::: Just After setContentView ");

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        buttonAdd = (Button) findViewById(R.id.buttonadd);
        buttonSub = (Button) findViewById(R.id.buttonsub);
        buttonMul = (Button) findViewById(R.id.buttonmul);
        buttonDivision = (Button) findViewById(R.id.buttondiv);
        buttonC = (Button) findViewById(R.id.buttonC);
        buttonEqual = (Button) findViewById(R.id.buttoneql);
        vpbEditText = (EditText) findViewById(R.id.edt1);
        vpbTextView = (TextView) findViewById(R.id.infoTextView);
        linkTextView = (TextView) findViewById(R.id.textViewLink);

        buttonLBracket = (Button) findViewById(R.id.buttonLBracket);
        buttonRBracket = (Button) findViewById(R.id.buttonRBracket);
        buttonDel = (Button) findViewById(R.id.buttondel);

        buttonSkin = (Button) findViewById(R.id.buttonskin);
        buttonOf = (Button) findViewById(R.id.buttonof);
        buttonDollar = (Button) findViewById(R.id.buttoninterad);
        buttonV = (Button) findViewById(R.id.buttonvedioad);

        rootLayout = (TableLayout) findViewById(R.id.rootLayout);

        // Load the add into Admob banner view.
        // mBannerAd = (AdView) findViewById(R.id.banner_AdView);

        // Load BannerAd
        // showBannerAd();
        // showInterstitialAd();

        title = "\t" + getString(R.string.app_name) + "\t\t\t\t\t\t\t" + getString(R.string.Version);
        setTitle(title);

        log("Cls --> MainActivity --> Fn --> onCreate :::: Just After fnding all layout views ");

        TextView link = (TextView) findViewById(R.id.textViewLink);
        String linkText = "<a href='https://www.versionpb.co.in'>www.versionpb.co.in</a>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            link.setText(Html.fromHtml(linkText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            link.setText(Html.fromHtml(linkText));
        }
        link.setMovementMethod(LinkMovementMethod.getInstance());

        SetLineEditView = "0";
        LineEditView = "0";
        vpbEditText.setText(SetLineEditView);

        SetLineTextView = "";
        LineTextView = "";
        vpbTextView.setText(SetLineTextView);

        log("Cls --> MainActivity --> Fn --> onCreate :::: Just Before Calling ClickListener");

        View.OnClickListener calcClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = "";
                int id = v.getId();
                if (id == R.id.button1) input = "1";
                else if (id == R.id.button2) input = "2";
                else if (id == R.id.button3) input = "3";
                else if (id == R.id.button4) input = "4";
                else if (id == R.id.button5) input = "5";
                else if (id == R.id.button6) input = "6";
                else if (id == R.id.button7) input = "7";
                else if (id == R.id.button8) input = "8";
                else if (id == R.id.button9) input = "9";
                else if (id == R.id.button0) input = "0";
                else if (id == R.id.buttonadd) input = "+";
                else if (id == R.id.buttonsub) input = "-";
                else if (id == R.id.buttonmul) input = "X";
                else if (id == R.id.buttondiv) input = "\u00F7";
                else if (id == R.id.buttoneql) input = "=";
                else if (id == R.id.buttonC) input = "C";
                else if (id == R.id.button10) input = ".";
                else if (id == R.id.buttonLBracket) input = "(";
                else if (id == R.id.buttonRBracket) input = ")";
                else if (id == R.id.buttonof) input = "^";
                else if (id == R.id.buttondel) input = "D";

                if (!input.isEmpty()) {
                    AddText(v, input);
                }
            }
        };

        int[] calcButtons = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonadd, R.id.buttonsub, R.id.buttonmul, R.id.buttondiv,
                R.id.buttoneql, R.id.buttonC, R.id.button10, R.id.buttonLBracket,
                R.id.buttonRBracket, R.id.buttonof, R.id.buttondel
        };
        for (int id : calcButtons) {
            findViewById(id).setOnClickListener(calcClickListener);
        }

        buttonSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeSkin(v);
            }
        });

        buttonDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (mInterstitialAd != null) {
                //     mInterstitialAd.show(MainActivity.this);
                // }
            }
        });

        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (mInterstitialAd != null) {
                //     mInterstitialAd.show(MainActivity.this);
                // }
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    @SuppressWarnings("deprecation")
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.exit_msg), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
