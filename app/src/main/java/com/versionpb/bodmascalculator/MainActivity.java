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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.FullScreenContentCallback;
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

    private AdView mBannerAd;
    private InterstitialAd mInterstitialAd;

    TableLayout rootLayout;

    private int theme = 0;
    private String title;

    public void CalculatorInputLogic(View v, String input) {
        System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Value Entered is " + input);
        System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic : LineEditView is " + LineEditView);
        System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic : LineTextView  is " + LineTextView);
        System.out.println(
                "Cls --> MainActivity --> Fn --> CalculatorInputLogic : SetLineEditView is " + SetLineEditView);
        System.out.println(
                "Cls --> MainActivity --> Fn --> CalculatorInputLogic : SetLineTextView is " + SetLineTextView);

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
        if (LineTextView == "" && LineEditView == "0" && numericsWithDotandMinus.contains(input)) {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Input Value is a Numeric and this is 1st Character Entered "
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

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);
        } else if (LineTextView == "-" && LineEditView == "-" && numericsZeroDot.contains(input)) {
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

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);

        }
        // 1st Elseif clause , i.e. when C is entered
        else if (input == "C") {
            System.out
                    .println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 1 C : Everything is cleared ");
            LineTextView = "";
            LineEditView = "0";
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);
        }
        // 2nd Elseif clause , When ts first entry and its an operator or 0 or .
        else if (LineTextView == "" && LineEditView == "0" && operatorDotZero.contains(input)) {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 2 Very First Entry and that too an Operator or . or 0 . Nothing Changes  ");
            LineTextView = "";
            LineEditView = "0";
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);
            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit View  is "
                    + SetLineEditView);
            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Text View  is "
                    + SetLineTextView);
        }

        // 2.1 Elseif clause , When ts first entry and its backspace
        else if (LineTextView == "" && LineEditView == "0" && input == "D") {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 2.1 Very First Entry and its a Backspace. Nothing Changes  ");
            // Value in SetLineEditView: 0
            // Value in SetLineTextView
            // Value in LineEditView is 0
            // Value in LineTextView

        }

        // Elif 2.2 When D is entered i.e. Backspace and its not the very first input
        // Condition 1 : Input is D
        else if (input.equalsIgnoreCase("D")) {

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 2.2 , Input is = D and this is not very first input.Entered Input  : "
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
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 1 , when there is only 1 chacrater entered in Edit View , Everything as if we clear screen ");
                LineTextView = "";
                LineEditView = "0";
                SetLineEditView = LineEditView;
                SetLineTextView = LineTextView;

                vpbEditText.setText(SetLineEditView);
                vpbTextView.setText(SetLineTextView);
            }

            else if (operator.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1))) &&
                    !operator.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))) {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 3 Last  entry is oprator in LineTextView and last entry not Operator in SetLineTextView ");
                SetLineTextView = SetLineTextView.substring(0, SetLineTextView.length() - 1);

                if (SetLineEditView.length() == 1) {
                    SetLineEditView = "0";
                } else {
                    SetLineEditView = SetLineEditView.substring(0, SetLineEditView.length() - 1);
                }
                vpbEditText.setText(SetLineEditView);
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                                + SetLineEditView);
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                                + SetLineTextView);

            } else if (SetLineEditView.equalsIgnoreCase(LineEditView)
                    && SetLineEditView.equalsIgnoreCase(LineTextView)) {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 4 , when No operator is pressed yet  ");
                SetLineEditView = SetLineEditView.substring(0, SetLineEditView.length() - 1);
                LineTextView = LineTextView.substring(0, LineTextView.length() - 1);
                LineEditView = SetLineEditView;
                vpbEditText.setText(SetLineEditView);
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                                + SetLineEditView);
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                                + SetLineTextView);
            } else if (operator.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1))) &&
                    operator.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))) {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic . Condition 2 when there is operator at the end and nothing numeric is entered . Nothing changes");
            }

        }
        // Elif 2.3 When Bracket is entered
        // Condition 1 : Input is (
        else if (input.equalsIgnoreCase("(")) {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.3 Left Bracket . Input is : "
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

            if (LineTextView == "" && LineEditView == "0") {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Case 1 . First Entry as Left Bracket . Input is : "
                                + input);

                LineTextView = "(";
                LineEditView = "(";
                SetLineEditView = LineEditView;
                SetLineTextView = "";

                vpbEditText.setText(SetLineEditView);

                System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                        + SetLineEditView);
            }
            // Case 2
            else if (operator.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))) {
                LineTextView = LineTextView.concat("(");
                SetLineTextView = LineTextView;

                vpbTextView.setText(SetLineTextView);

                System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                        + SetLineTextView);
            } else {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else . No Changes . Input is : "
                                + input);
            }

        }

        // Elif 2.4 When Bracket is entered
        // Condition 1 : Input is )
        else if (input.equalsIgnoreCase(")")) {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.4 Right Bracket . Input is : "
                            + input);
            // Case 1 i.e. very first time entry and its a (
            // Value in SetLineEditView : 0:::
            // Value in SetLineTextView : :::
            // Value in LineEditView is 0:::
            // Value in LineTextView

            if ((LineTextView == "" && LineEditView == "0")
                    || !(SetLineEditView.contains("(") || SetLineTextView.contains("("))) {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Case 1 . First Entry as Right Bracket , Nothing Happens. Input is : "
                                + input);

            }
            // Case 2
            // Not first time entry for right bracket and last character is a digit or .
            // with no operator yet present i.e. SetLineTextView is empty
            else if (numericsZeroDot.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))
                    && SetLineTextView.length() == 0) {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.4 , Case 2 . Input Right Bracket ,last character is numerics.zero , no operator yet entered . Input is : "
                                + input);

                LineTextView = LineTextView.concat(input);
                LineEditView = LineEditView.concat(input);
                SetLineEditView = LineEditView;
                SetLineTextView = LineTextView;

                vpbEditText.setText(SetLineEditView);
                // vpbTextView.setText(SetLineTextView);

                System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                        + SetLineEditView);

            }
            // Case 3
            // Not first time entry for right bracket and last character is a digit or and
            // operator alredy present
            else if (numericsZeroDot.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1))) &&
                    numericsZeroDot
                            .contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))) {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else if 2.4 , Case 2 . INput Right Bracket ,last character is numerics.zero . Input is : "
                                + input);

                LineTextView = SetLineTextView.concat(input);
                LineEditView = SetLineTextView.concat(input);
                SetLineEditView = SetLineTextView.concat(input);
                SetLineTextView = SetLineTextView.concat(input);

                // vpbEditText.setText(SetLineTextView);
                vpbTextView.setText(SetLineTextView);

                System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To TextView  is "
                        + SetLineTextView);

            } else {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else . No Changes . Input is : "
                                + input);
            }
        }
        // Elif 3 When = is entered
        // Condition 1 : Input is =
        else if (input.equalsIgnoreCase("=")) {

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 3 , Input is = . Input  : " + input);

            String Expression = SetLineTextView;

            if (!Expression.isEmpty()) {

                if (operator.contains(Character.toString(Expression.charAt(Expression.length() - 1)))) {
                    Expression = Expression.substring(0, Expression.length() - 1);
                    System.out.println(
                            "Cls --> MainActivity --> Fn --> Last Charater in Expression was operator . New Expression is   : "
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

                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                                + SetLineEditView);
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                                + SetLineTextView);
            } else {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Expression is null , could be that = entered again . Nothing Changes");
            }
        }
        // 4th Elseif clause , Not first entry but second time entry for .
        // when last character in LineView and EditView is numerics , input Value is .
        // and setEditView doesnt contain a . already
        else if (input == "." && SetLineEditView.contains(".")) {

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 4 You are entering . second time . Nothing Changes  ");
            System.out.println("Paarth numericsZeroDot.toString() " + numericsZeroDot.toString());
        }

        // 5th Else If , Not first time entry and its numericswithZero or . i.e.
        // numericsZeroDot and editview last value is not an operator but is an ( or )
        else if ("(".contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))
                && numericsWithDotandMinus.contains(input)) {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 5 Input Value is a Numeric and this is not First Entry . Input is : "
                            + input);
            LineTextView = LineTextView.concat(input);
            LineEditView = input;
            SetLineEditView = input;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);
        }

        // 5.1th Else If , Not first time entry and its numericswithZero or . i.e.
        // numericsZeroDot and editview last value is not an operator
        else if (numericsZeroDotLBracket.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1))) &&
                numericsZeroDotLBracket.contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))
                && numericsZeroDot.contains(input)) {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 5.1 Input Value is a Numeric and this is not First Entry . Input is : "
                            + input);
            LineTextView = LineTextView.concat(input);
            LineEditView = LineEditView.concat(input);
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Edit Text  is "
                    + SetLineEditView);
            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To Text View  is "
                    + SetLineTextView);
        }

        // 6th Else If Operator Entry + LineEditView last char is either a numeric or .
        // + LineTextView last char is either a numeric or .
        else if (operator.contains(input)
                && numericsZeroDotRBracket.contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))
                && numericsZeroDotRBracket
                        .contains(Character.toString(LineTextView.charAt(LineTextView.length() - 1)))) {

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic :Elif 6 Not first time entry , Operator Entry . Input  : "
                            + input);
            LineTextView = LineTextView.concat(input);
            // LineEditView = LineEditView; No Changes
            SetLineEditView = LineEditView;
            SetLineTextView = LineTextView;

            // vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To TextView  is "
                    + SetLineTextView);
        }

        // Elif 7 Condition 1 : Input is numerics + Zero
        // Condition 2 when last Character in SetLineTextView is operator
        // Condition 3 last character in lineEditView is numerics + Zero + .
        else if (numericsWithDotandMinus.contains(input)
                && operator.contains(Character.toString(SetLineTextView.charAt(SetLineTextView.length() - 1)))
                && numericsZeroDotBothBracket
                        .contains(Character.toString(LineEditView.charAt(LineEditView.length() - 1)))) {

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 7 Not first time entry , First Digit entered after Operator. Input  : "
                            + input);
            // No changes in Line Text View
            // New Input in Line Edit View
            SetLineEditView = input;
            SetLineTextView = SetLineTextView.concat(input);
            LineEditView = SetLineTextView;
            LineTextView = SetLineTextView;

            vpbEditText.setText(SetLineEditView);
            vpbTextView.setText(SetLineTextView);

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
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

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 8 Not first time entry , Second + Digit entered after operator . Input  : "
                            + input);
            // No changes in Line Text View
            // New Input in Line Edit View
            SetLineEditView = SetLineEditView.concat(input);
            SetLineTextView = SetLineTextView.concat(input);
            LineEditView = SetLineEditView;
            LineTextView = LineTextView.concat(input);

            vpbEditText.setText(SetLineEditView);

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
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

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 8.5 Not first time entry , Anythng after -  : "
                            + input);
            // No changes in Line Text View
            // New Input in Line Edit View
            SetLineEditView = SetLineEditView.concat(input);
            SetLineTextView = SetLineTextView.concat(input);
            LineEditView = SetLineEditView;
            LineTextView = LineTextView.concat(input);

            vpbEditText.setText(SetLineEditView);

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
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

            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculatorInputLogic : Elif 9 Not first time entry , Another Operator entered after operator . Input  : "
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

            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineEditView  is "
                    + SetLineEditView);
            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic :Value Set To SetLineTextView  is "
                    + SetLineTextView);
        }

        // If this is not the first Value entered and if operator is entered
        else {
            // We will change this
            System.out.println("Cls --> MainActivity --> Fn --> CalculatorInputLogic : Else Clause");
            System.out.println("Cls --> MainActivity --> Fn --> ElseClause : Values :::: Value in SetLineEditView : "
                    + SetLineEditView
                    + ":::Value in SetLineTextView: " + SetLineTextView + ":::Value in LineEditView is " + LineEditView
                    + ":::Value in LineTextView " + LineTextView);
        }
    }

    public void AddText(View v, String input) {
        System.out.println(
                "\n\n\n\n\n\n\n\nNew Value Entered Cls --> MainActivity --> Fn --> AddText :::: Value Entered is "
                        + input);
        System.out.println(
                "Cls --> MainActivity --> Fn --> AddText :Before Setting Any Value :::Value in SetLineEditView: "
                        + SetLineEditView
                        + " :::Value in SetLineTextView : " + SetLineTextView + "::: Value in LineEditView is "
                        + LineEditView + "::: Value in LineTextView " + LineTextView);

        int TextViewHeight = vpbTextView.getHeight();
        System.out.println("Cls --> MainActivity --> Fn --> AddText :::: vpbTextView Height is " + TextViewHeight);
        System.out.println("Cls --> MainActivity --> Fn --> AddText :::: buttonEqual.getY()  is " + buttonEqual.getY());
        System.out.println(
                "Cls --> MainActivity --> Fn --> AddText :::: buttonEqual.getHeight();  is " + buttonEqual.getHeight());
        System.out.println("Cls --> MainActivity --> Fn --> AddText :::: vpbTextView.getTextSize();  is "
                + vpbTextView.getTextSize());

        float TextViewTextSizesp = vpbTextView.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
        System.out.println("Cls --> MainActivity --> Fn --> AddText :::: TextViewTextSizesp  is " + TextViewTextSizesp);

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

        System.out.println(
                "Cls --> MainActivity --> Fn --> AddText :After Setting Any Value :::: Value in SetLineEditView : "
                        + SetLineEditView
                        + ":::Value in SetLineTextView : " + SetLineTextView + ":::Value in LineEditView is "
                        + LineEditView + ":::Value in LineTextView" + LineTextView);

    }

    public String CalculateBODMASExpression(String P_Expression) {
        System.out.println(
                "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Input Expression is " + P_Expression);

        // change to * for multiplication in Expression
        String NewExpression = P_Expression.replaceAll("X", "*");
        NewExpression = NewExpression.replaceAll("\u00F7", "/");
        System.out.println(
                "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Expression with multiplication substitution is "
                        + NewExpression);
        P_Expression = NewExpression;

        // If mismatched paranthesis , then change expression

        try {
            // If mispatched parenthesis , i.e. if fist character is paranhesis , remove it
            Expression test = new Expression(P_Expression);
            BigDecimal ValueComputed_Test = test.setPrecision(128).eval();

        } catch (Exception E) {
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Exception for checking mismatches paramthesis  . Exception : "
                            + E);
            if (P_Expression.substring(0, 1).equalsIgnoreCase("(")) {
                P_Expression = P_Expression.substring(1, P_Expression.length());
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Expression after ( Cleaning is "
                                + P_Expression);
            }

        }

        /* Interpreter interpreter = new Interpreter(); */
        String result = null;
        try {

            String ValueComputedAsString = "";
            Expression e = new Expression(P_Expression);
            // Expression expression = new Expression("1+1/3");
            BigDecimal ValueComputed = e.setPrecision(128).eval();
            Expression multi = new Expression("65*6");
            System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: multi " + multi);
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: multi.eval() " + multi.eval());

            ValueComputedAsString = ValueComputed.toString();
            if (ValueComputed.toString().contains("E")) {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Output had Zeros in the end ");
                int IndexOfDotWithE = ValueComputed.toString().indexOf('.');
                int IndexOfPlusWithE = ValueComputed.toString().indexOf('+');
                int IndexOfEWithE = ValueComputed.toString().indexOf('E');
                String valueAfterPlus = ValueComputed.toString().substring(IndexOfPlusWithE + 1);
                String valueBeforeE = ValueComputed.toString().substring(0, IndexOfEWithE);

                BigInteger resultWithoutE = (BigInteger) ValueComputed.toBigInteger();

                System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: IndexOfDotWithE"
                        + IndexOfDotWithE);
                System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: IndexOfPlusWithE"
                        + IndexOfPlusWithE);
                System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: valueAfterPlus"
                        + valueAfterPlus);
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: valueBeforeE" + valueBeforeE);
                System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: resultWithoutE"
                        + resultWithoutE);
                ValueComputedAsString = resultWithoutE.toString();

            }

            // ValueComputed.setScale(4, BigDecimal.ROUND_HALF_EVEN);
            int IndexOfDot = ValueComputedAsString.indexOf('.');
            int NumbersAfterDot = ValueComputed.toString().length() - IndexOfDot - 1;
            int NumersBeforeDot = IndexOfDot;
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: ValueComputed: " + ValueComputed);
            System.out
                    .println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Length Of ValueComputed: "
                            + ValueComputed.toString().length());
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: IndexOfDot: " + IndexOfDot);
            System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: NumersBeforeDot : "
                    + NumersBeforeDot);
            System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: NumbersAfterDot  : "
                    + NumbersAfterDot);

            if (IndexOfDot > 0) {
                String valueAfterDot = ValueComputed.toString().substring(IndexOfDot + 1);
                System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: valueAfterDot: "
                        + valueAfterDot);
                String valueBeforeDot = ValueComputed.toString().substring(0, IndexOfDot);
                String TruncatedNumbersAfterDot = "";
                if (NumbersAfterDot >= 4) {
                    TruncatedNumbersAfterDot = valueAfterDot.substring(0, 4);
                } else
                    TruncatedNumbersAfterDot = valueAfterDot;

                System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: valueBeforeDot: "
                        + valueBeforeDot);

                // Setting 8 places after . as Integer
                BigDecimal BDAfterdot = new BigDecimal(TruncatedNumbersAfterDot);
                String BDString = BDAfterdot.toString();
                if (BDAfterdot.intValueExact() > 0) {
                    result = valueBeforeDot.concat(".").concat(BDString);
                } else {
                    result = valueBeforeDot;
                }

                // result = ValueComputed.setScale(8,
                // BigDecimal.ROUND_HALF_EVEN).toPlainString();

            } else {
                System.out.println(
                        "Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: There s no . in output: ");
                result = ValueComputed.toPlainString();
            }

            /*
             * if (valueAfterDecimalPlaces == 0){
             * result = ValueComputed.toString().substring(1,decimalPlaces);
             * }
             * 
             * else if(decimalPlaces > 4){
             * result = ValueComputed.setScale(4,
             * BigDecimal.ROUND_HALF_EVEN).toPlainString();
             * 
             * }
             * else{
             * result = ValueComputed.toPlainString();
             * }
             */

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(
                    "Cls --> MainActivity --> Fn --> CalculateBODMASExpression Exception  :::: " + e.getMessage());
            result = "Error";
        }
        System.out.println("Cls --> MainActivity --> Fn --> CalculateBODMASExpression :::: Result is  : " + result);
        return result;
    }

    private void showBannerAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAd.loadAd(adRequest);
    }

    private void showInterstitialAd() {
        System.out.println("Cls --> MainActivity --> Fn --> showInterstitialAd :::: Loading Interestitial Ad");
        AdRequest adRequest = new AdRequest.Builder().build();
        com.google.android.gms.ads.interstitial.InterstitialAd.load(this,
                getResources().getString(R.string.interstitial_ad_unit_id), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                mInterstitialAd = null;
                                showInterstitialAd(); // reload
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }

    public void setTheme(View v, int c, int actionBarColor, int titleColor, int editTextColor, int nextColor) {

        buttonLBracket.setBackgroundColor(getResources().getColor(c));
        buttonRBracket.setBackgroundColor(getResources().getColor(c));
        buttonC.setBackgroundColor(getResources().getColor(c));
        buttonDel.setBackgroundColor(getResources().getColor(c));
        button7.setBackgroundColor(getResources().getColor(c));
        button8.setBackgroundColor(getResources().getColor(c));
        button9.setBackgroundColor(getResources().getColor(c));
        buttonDivision.setBackgroundColor(getResources().getColor(c));
        button4.setBackgroundColor(getResources().getColor(c));
        button5.setBackgroundColor(getResources().getColor(c));
        button6.setBackgroundColor(getResources().getColor(c));
        button1.setBackgroundColor(getResources().getColor(c));
        button2.setBackgroundColor(getResources().getColor(c));
        button3.setBackgroundColor(getResources().getColor(c));
        button10.setBackgroundColor(getResources().getColor(c));
        // System.out.println("Cls --> MainActivity --> Fn --> ChangeSkin :::: Done
        // 10");
        button0.setBackgroundColor(getResources().getColor(c));
        buttonEqual.setBackgroundColor(getResources().getColor(c));
        buttonAdd.setBackgroundColor(getResources().getColor(c));
        buttonOf.setBackgroundColor(getResources().getColor(c));
        // System.out.println("Cls --> MainActivity --> Fn --> ChangeSkin :::: Done
        // Of");
        buttonDollar.setBackgroundColor(getResources().getColor(c));
        // System.out.println("Cls --> MainActivity --> Fn --> ChangeSkin :::: Done
        // Dollar");
        buttonV.setBackgroundColor(getResources().getColor(c));
        // System.out.println("Cls --> MainActivity --> Fn --> ChangeSkin :::: Done V");
        buttonSkin.setBackgroundColor(getResources().getColor(c));
        // System.out.println("Cls --> MainActivity --> Fn --> ChangeSkin :::: Done
        // Skin");
        buttonMul.setBackgroundColor(getResources().getColor(c));
        buttonSub.setBackgroundColor(getResources().getColor(c));
        buttonSkin.setTextColor(getResources().getColor(nextColor));
        linkTextView.setBackgroundColor(getResources().getColor(actionBarColor));
        // linkTextView.setTextColor(getResources().getColor(R.color.black));

        vpbTextView.setBackgroundColor(getResources().getColor(editTextColor));

        // getSupportActionBar().setTitle(Html.fromHtml("<font color=\"pink\">" +
        // getString(R.string.app_name) + "</font>"));
        // getSupportActionBar().setTitle(Html.fromHtml("<font color=" +
        // getString(Color) + ">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=" + getString(titleColor) + ">" + title + "</font>"));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(actionBarColor)));

    }

    public void ChangeSkin(View v) {
        System.out.println("Cls --> MainActivity --> Fn --> ChangeSkin :::: Setting the new Skin ");

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

        System.out.println("Cls --> MainActivity --> Fn --> onCreate :::: Just After Super.onCreate ");
        setContentView(R.layout.activity_main);

        System.out.println("Cls --> MainActivity --> Fn --> onCreate :::: Just After setContentView ");

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
        mBannerAd = (AdView) findViewById(R.id.banner_AdView);

        // Load BannerAd
        showBannerAd();
        showInterstitialAd();

        title = "\t" + getString(R.string.app_name) + "\t\t\t\t\t\t\t" + getString(R.string.Version);
        setTitle(title);

        System.out.println("Cls --> MainActivity --> Fn --> onCreate :::: Just After fnding all layout views ");

        TextView link = (TextView) findViewById(R.id.textViewLink);
        String linkText = "<a href='https://www.versionpb.co.in'>www.versionpb.co.in</a>";
        link.setText(Html.fromHtml(linkText));
        link.setMovementMethod(LinkMovementMethod.getInstance());

        SetLineEditView = "0";
        LineEditView = "0";
        vpbEditText.setText(SetLineEditView);

        SetLineTextView = "";
        LineTextView = "";
        vpbTextView.setText(SetLineTextView);

        System.out.println("Cls --> MainActivity --> Fn --> onCreate :::: Just Before Calling ClickListener");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // vpbEditText.setText(vpbEditText.getText() + "1");
                AddText(v, "1");
            }
        });

        System.out.println("Cls --> MainActivity --> Fn --> onCreate :::: After button 1");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "2");
                AddText(v, "2");
            }
        });

        System.out.println("Cls --> MainActivity --> Fn --> onCreate :::: After button 2");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "3");
                AddText(v, "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "4");
                AddText(v, "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "5");
                AddText(v, "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "6");
                AddText(v, "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "7");
                AddText(v, "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "8");
                AddText(v, "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "9");
                AddText(v, "9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "0");
                AddText(v, "0");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddText(v, "+");
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddText(v, "-");
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddText(v, "X");
            }
        });

        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddText(v, "\u00F7");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddText(v, "=");
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddText(v, "C");
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddText(v, ".");
            }
        });

        buttonLBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "0");
                AddText(v, "(");
            }
        });

        buttonRBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "0");
                AddText(v, ")");
            }
        });

        buttonSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vpbEditText.setText(vpbEditText.getText() + "0");
                ChangeSkin(v);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // vpbEditText.setText(vpbEditText.getText() + "1");
                AddText(v, "1");
            }
        });

        buttonOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // vpbEditText.setText(vpbEditText.getText() + "1");
                AddText(v, "^");
            }
        });

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // vpbEditText.setText(vpbEditText.getText() + "1");
                AddText(v, "D");
            }
        });

        buttonDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
            }
        });

        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                }
            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
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
