package com.versionpb.bodmascalculator;

import java.util.Arrays;
import java.util.List;

public final class CalculatorState {

    enum UiUpdate {
        NONE,
        EDIT_ONLY,
        TEXT_ONLY,
        BOTH
    }

    static final class InputResult {
        final boolean handled;
        final UiUpdate uiUpdate;

        private InputResult(boolean handled, UiUpdate uiUpdate) {
            this.handled = handled;
            this.uiUpdate = uiUpdate;
        }

        static InputResult notHandled() {
            return new InputResult(false, UiUpdate.NONE);
        }

        static InputResult handled(UiUpdate uiUpdate) {
            return new InputResult(true, uiUpdate);
        }
    }

    private static final List<String> NUMERICS_WITH_DOT_AND_MINUS = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "-", "0");
    private static final List<String> NUMERICS_ZERO_DOT = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".");
    private static final List<String> OPERATOR_DOT_ZERO = Arrays.asList(
            "+", "-", "\u00F7", "X", ".", "0", "^");
    private static final List<String> OPERATOR = Arrays.asList(
            "+", "-", "\u00F7", "X", "^");
    private static final List<String> NUMERICS_ZERO_DOT_LBRACKET = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "(");
    private static final List<String> NUMERICS_ZERO_DOT_RBRACKET = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", ")");
    private static final List<String> NUMERICS_ZERO_DOT_BOTH_BRACKET = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "(", ")");

    String lineTextView = "";
    String lineEditView = "0";
    String setLineTextView = "";
    String setLineEditView = "0";

    InputResult onInput(String input) {
        InputResult result = tryClear(input);
        if (result.handled) {
            return result;
        }

        result = tryFirstNumericEntry(input);
        if (result.handled) {
            return result;
        }

        result = tryNegativePrefixEntry(input);
        if (result.handled) {
            return result;
        }

        result = tryIgnoredFirstEntry(input);
        if (result.handled) {
            return result;
        }

        result = tryFirstBackspace(input);
        if (result.handled) {
            return result;
        }

        result = tryBackspace(input);
        if (result.handled) {
            return result;
        }

        result = tryLeftBracket(input);
        if (result.handled) {
            return result;
        }

        result = tryRightBracket(input);
        if (result.handled) {
            return result;
        }

        result = tryEquals(input);
        if (result.handled) {
            return result;
        }

        result = tryDuplicateDot(input);
        if (result.handled) {
            return result;
        }

        result = tryDigitAfterOpenBracket(input);
        if (result.handled) {
            return result;
        }

        result = tryAppendDigitOrBracket(input);
        if (result.handled) {
            return result;
        }

        result = tryOperatorAfterValue(input);
        if (result.handled) {
            return result;
        }

        result = tryFirstDigitAfterOperator(input);
        if (result.handled) {
            return result;
        }

        result = trySubsequentDigitAfterOperator(input);
        if (result.handled) {
            return result;
        }

        result = tryDigitAfterNegative(input);
        if (result.handled) {
            return result;
        }

        result = trySecondOperator(input);
        if (result.handled) {
            return result;
        }

        // Original chain's final else: input consumed, nothing changes.
        return InputResult.handled(UiUpdate.NONE);
    }

    private InputResult tryClear(String input) {
        if (!"C".equals(input)) {
            return InputResult.notHandled();
        }

        lineTextView = "";
        lineEditView = "0";
        setLineEditView = lineEditView;
        setLineTextView = lineTextView;
        return InputResult.handled(UiUpdate.BOTH);
    }

    private InputResult tryFirstNumericEntry(String input) {
        if (!lineTextView.isEmpty() || !"0".equals(lineEditView)
                || !NUMERICS_WITH_DOT_AND_MINUS.contains(input)) {
            return InputResult.notHandled();
        }

        if (".".equalsIgnoreCase(input)) {
            lineTextView = "0.";
            lineEditView = "0.";
            setLineEditView = "0.";
            setLineTextView = "";
        } else {
            lineTextView = input;
            lineEditView = input;
            setLineEditView = input;
            setLineTextView = "";
        }
        return InputResult.handled(UiUpdate.EDIT_ONLY);
    }

    private InputResult tryNegativePrefixEntry(String input) {
        if (!"-".equals(lineTextView) || !"-".equals(lineEditView)
                || !NUMERICS_ZERO_DOT.contains(input)) {
            return InputResult.notHandled();
        }

        if (".".equalsIgnoreCase(input)) {
            lineTextView = "-0.";
            lineEditView = "-0.";
            setLineEditView = "-0.";
            setLineTextView = "";
        } else {
            lineTextView = "-" + input;
            lineEditView = "-" + input;
            setLineEditView = "-" + input;
            setLineTextView = "";
        }
        return InputResult.handled(UiUpdate.EDIT_ONLY);
    }

    private InputResult tryIgnoredFirstEntry(String input) {
        if (!lineTextView.isEmpty() || !"0".equals(lineEditView)
                || !OPERATOR_DOT_ZERO.contains(input)) {
            return InputResult.notHandled();
        }

        lineTextView = "";
        lineEditView = "0";
        setLineEditView = lineEditView;
        setLineTextView = lineTextView;
        return InputResult.handled(UiUpdate.BOTH);
    }

    private InputResult tryFirstBackspace(String input) {
        if (!lineTextView.isEmpty() || !"0".equals(lineEditView) || !"D".equals(input)) {
            return InputResult.notHandled();
        }
        return InputResult.handled(UiUpdate.NONE);
    }

    private InputResult tryBackspace(String input) {
        if (!"D".equalsIgnoreCase(input)) {
            return InputResult.notHandled();
        }

        if (setLineEditView.length() == 1 && lineEditView.length() == 1 && lineTextView.length() == 1) {
            lineTextView = "";
            lineEditView = "0";
            setLineEditView = lineEditView;
            setLineTextView = lineTextView;
            return InputResult.handled(UiUpdate.BOTH);
        } else if (OPERATOR.contains(lastOf(lineTextView))
                && !OPERATOR.contains(lastOf(setLineTextView))) {
            setLineTextView = setLineTextView.substring(0, setLineTextView.length() - 1);
            if (setLineEditView.length() == 1) {
                setLineEditView = "0";
            } else {
                setLineEditView = setLineEditView.substring(0, setLineEditView.length() - 1);
            }
            return InputResult.handled(UiUpdate.EDIT_ONLY);
        } else if (setLineEditView.equalsIgnoreCase(lineEditView)
                && setLineEditView.equalsIgnoreCase(lineTextView)) {
            setLineEditView = setLineEditView.substring(0, setLineEditView.length() - 1);
            lineTextView = lineTextView.substring(0, lineTextView.length() - 1);
            lineEditView = setLineEditView;
            return InputResult.handled(UiUpdate.EDIT_ONLY);
        }
        return InputResult.handled(UiUpdate.NONE);
    }

    private InputResult tryLeftBracket(String input) {
        if (!"(".equalsIgnoreCase(input)) {
            return InputResult.notHandled();
        }

        if (lineTextView.isEmpty() && "0".equals(lineEditView)) {
            lineTextView = "(";
            lineEditView = "(";
            setLineEditView = lineEditView;
            setLineTextView = "";
            return InputResult.handled(UiUpdate.EDIT_ONLY);
        } else if (OPERATOR.contains(lastOf(lineTextView))) {
            lineTextView = lineTextView.concat("(");
            setLineTextView = lineTextView;
            return InputResult.handled(UiUpdate.TEXT_ONLY);
        }
        return InputResult.handled(UiUpdate.NONE);
    }

    private InputResult tryRightBracket(String input) {
        if (!")".equalsIgnoreCase(input)) {
            return InputResult.notHandled();
        }

        if ((lineTextView.isEmpty() && "0".equals(lineEditView))
                || !(setLineEditView.contains("(") || setLineTextView.contains("("))) {
            return InputResult.handled(UiUpdate.NONE);
        } else if (NUMERICS_ZERO_DOT.contains(lastOf(lineEditView))
                && setLineTextView.length() == 0) {
            lineTextView = lineTextView.concat(input);
            lineEditView = lineEditView.concat(input);
            setLineEditView = lineEditView;
            setLineTextView = lineTextView;
            return InputResult.handled(UiUpdate.EDIT_ONLY);
        } else if (NUMERICS_ZERO_DOT.contains(lastOf(lineEditView))
                && NUMERICS_ZERO_DOT.contains(lastOf(setLineTextView))) {
            lineTextView = setLineTextView.concat(input);
            lineEditView = setLineTextView.concat(input);
            setLineEditView = setLineTextView.concat(input);
            setLineTextView = setLineTextView.concat(input);
            return InputResult.handled(UiUpdate.TEXT_ONLY);
        }
        return InputResult.handled(UiUpdate.NONE);
    }

    private InputResult tryEquals(String input) {
        if (!"=".equalsIgnoreCase(input)) {
            return InputResult.notHandled();
        }

        String expression = setLineTextView;
        if (!expression.isEmpty()) {
            String result = CalculatorEngine.evaluateSubmitted(expression);
            setLineEditView = result;
            setLineTextView = "";
            lineEditView = setLineEditView;
            lineTextView = setLineEditView;
            return InputResult.handled(UiUpdate.BOTH);
        }
        return InputResult.handled(UiUpdate.NONE);
    }

    private InputResult tryDuplicateDot(String input) {
        if (".".equals(input) && setLineEditView.contains(".")) {
            return InputResult.handled(UiUpdate.NONE);
        }
        return InputResult.notHandled();
    }

    private InputResult tryDigitAfterOpenBracket(String input) {
        if ("(".equals(lastOf(lineTextView)) && NUMERICS_WITH_DOT_AND_MINUS.contains(input)) {
            lineTextView = lineTextView.concat(input);
            lineEditView = input;
            setLineEditView = input;
            setLineTextView = lineTextView;
            return InputResult.handled(UiUpdate.BOTH);
        }
        return InputResult.notHandled();
    }

    private InputResult tryAppendDigitOrBracket(String input) {
        if (NUMERICS_ZERO_DOT_LBRACKET.contains(lastOf(lineEditView))
                && NUMERICS_ZERO_DOT_LBRACKET.contains(lastOf(lineTextView))
                && NUMERICS_ZERO_DOT.contains(input)) {
            lineTextView = lineTextView.concat(input);
            lineEditView = lineEditView.concat(input);
            setLineEditView = lineEditView;
            setLineTextView = lineTextView;
            return InputResult.handled(UiUpdate.BOTH);
        }
        return InputResult.notHandled();
    }

    private InputResult tryOperatorAfterValue(String input) {
        if (OPERATOR.contains(input)
                && NUMERICS_ZERO_DOT_RBRACKET.contains(lastOf(lineEditView))
                && NUMERICS_ZERO_DOT_RBRACKET.contains(lastOf(lineTextView))) {
            lineTextView = lineTextView.concat(input);
            setLineEditView = lineEditView;
            setLineTextView = lineTextView;
            return InputResult.handled(UiUpdate.TEXT_ONLY);
        }
        return InputResult.notHandled();
    }

    private InputResult tryFirstDigitAfterOperator(String input) {
        if (NUMERICS_WITH_DOT_AND_MINUS.contains(input)
                && OPERATOR.contains(lastOf(setLineTextView))
                && NUMERICS_ZERO_DOT_BOTH_BRACKET.contains(lastOf(lineEditView))) {
            setLineEditView = input;
            setLineTextView = setLineTextView.concat(input);
            lineEditView = setLineTextView;
            lineTextView = setLineTextView;
            return InputResult.handled(UiUpdate.BOTH);
        }
        return InputResult.notHandled();
    }

    private InputResult trySubsequentDigitAfterOperator(String input) {
        if (NUMERICS_ZERO_DOT.contains(input)
                && NUMERICS_ZERO_DOT.contains(lastOf(setLineTextView))
                && NUMERICS_ZERO_DOT_RBRACKET.contains(lastOf(lineEditView))) {
            setLineEditView = setLineEditView.concat(input);
            setLineTextView = setLineTextView.concat(input);
            lineEditView = setLineEditView;
            lineTextView = lineTextView.concat(input);
            return InputResult.handled(UiUpdate.EDIT_ONLY);
        }
        return InputResult.notHandled();
    }

    private InputResult tryDigitAfterNegative(String input) {
        if (NUMERICS_ZERO_DOT.contains(input)
                && "-".equals(lastOf(setLineTextView))
                && "-".equals(lastOf(lineEditView))) {
            setLineEditView = setLineEditView.concat(input);
            setLineTextView = setLineTextView.concat(input);
            lineEditView = setLineEditView;
            lineTextView = lineTextView.concat(input);
            return InputResult.handled(UiUpdate.EDIT_ONLY);
        }
        return InputResult.notHandled();
    }

    private InputResult trySecondOperator(String input) {
        if (OPERATOR.contains(input)
                && OPERATOR.contains(lastOf(lineTextView))
                && NUMERICS_ZERO_DOT.contains(lastOf(setLineTextView))
                && NUMERICS_ZERO_DOT_RBRACKET.contains(lastOf(lineEditView))) {
            String expression = setLineTextView;
            String result = CalculatorEngine.evaluate(expression);
            setLineEditView = result;
            setLineTextView = setLineTextView.concat(input);
            lineEditView = setLineEditView;
            lineTextView = setLineTextView;
            return InputResult.handled(UiUpdate.BOTH);
        }
        return InputResult.notHandled();
    }

    private static String lastOf(String s) {
        return Character.toString(s.charAt(s.length() - 1));
    }
}
