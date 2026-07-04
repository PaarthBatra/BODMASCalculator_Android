package com.versionpb.bodmascalculator;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;

public final class CalculatorEngine {

    private static final int EVAL_PRECISION = 128;
    private static final int MAX_DECIMAL_PLACES = 4;
    static final String ERROR = "Error";

    private CalculatorEngine() {
    }

    /**
     * Evaluates a submitted expression (e.g. when the user presses {@code =}).
     * Strips a single trailing operator before evaluation.
     */
    public static String evaluateSubmitted(String expression) {
        return evaluate(stripTrailingOperator(expression));
    }

    /**
     * Evaluates a BODMAS expression using UI symbols (X, ÷).
     * Returns a formatted numeric string, or {@link #ERROR} if evaluation fails.
     */
    public static String evaluate(String expression) {
        if (expression == null || expression.isEmpty()) {
            return ERROR;
        }

        String normalized = normalizeOperators(expression);

        String result = tryEvaluate(normalized);
        if (result == null && normalized.startsWith("(")) {
            // Recover from a dangling leading '(' that was never closed.
            result = tryEvaluate(normalized.substring(1));
        }
        return result != null ? result : ERROR;
    }

    private static String tryEvaluate(String expression) {
        try {
            BigDecimal value = new Expression(expression).setPrecision(EVAL_PRECISION).eval();
            return formatResult(value);
        } catch (Exception e) {
            return null;
        }
    }

    static String stripTrailingOperator(String expression) {
        if (expression == null || expression.isEmpty()) {
            return expression;
        }

        char last = expression.charAt(expression.length() - 1);
        if (last == '+' || last == '-' || last == '\u00F7' || last == 'X' || last == '^') {
            return expression.substring(0, expression.length() - 1);
        }
        return expression;
    }

    static String normalizeOperators(String expression) {
        return expression.replaceAll("X", "*").replaceAll("\u00F7", "/");
    }

    static String formatResult(BigDecimal value) {
        String plainString = value.toPlainString();
        int dotIndex = plainString.indexOf('.');
        if (dotIndex >= 0) {
            String beforeDot = plainString.substring(0, dotIndex);
            String afterDot = plainString.substring(dotIndex + 1);

            if (afterDot.length() > MAX_DECIMAL_PLACES) {
                afterDot = afterDot.substring(0, MAX_DECIMAL_PLACES);
            }

            while (afterDot.endsWith("0")) {
                afterDot = afterDot.substring(0, afterDot.length() - 1);
            }

            if (afterDot.isEmpty()) {
                return beforeDot;
            }
            return beforeDot + "." + afterDot;
        }
        return plainString;
    }
}
