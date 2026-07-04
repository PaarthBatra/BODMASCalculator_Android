package com.versionpb.bodmascalculator;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CalculatorEngineTest {

    @Test
    public void evaluate_documentedUseCases() {
        assertEquals("17", CalculatorEngine.evaluate("2+3X5"));
        assertEquals("5", CalculatorEngine.evaluate("3+2"));
        assertEquals("3", CalculatorEngine.evaluate("5-2"));
        assertEquals("21", CalculatorEngine.evaluate("3X7"));
        assertEquals("9", CalculatorEngine.evaluate("99\u00F711"));
        assertEquals("84", CalculatorEngine.evaluate("(78+6)"));
        assertEquals("-2", CalculatorEngine.evaluate("3+5-10"));
        assertEquals("13", CalculatorEngine.evaluate("3+2X5"));
        assertEquals("28", CalculatorEngine.evaluate("8X7\u00F72"));
        assertEquals("7", CalculatorEngine.evaluate("(7)"));
        assertEquals("10", CalculatorEngine.evaluate("(7+3)"));
        assertEquals("26", CalculatorEngine.evaluate("(8X3)+2"));
        assertEquals("26", CalculatorEngine.evaluate("2+(8X3)"));
        assertEquals("17", CalculatorEngine.evaluate("2+3X4+(2+1)"));
        assertEquals("0.16", CalculatorEngine.evaluate("0.2X0.8"));
        assertEquals("-112", CalculatorEngine.evaluate("-56X2"));
        assertEquals("36", CalculatorEngine.evaluate("-6X-6"));
        assertEquals("25", CalculatorEngine.evaluate("5^2"));
        assertEquals("12", CalculatorEngine.evaluate("2^2X3"));
        assertEquals("0.04", CalculatorEngine.evaluate("0.2^2"));
    }

    @Test
    public void evaluate_formatsPlainIntegerWithoutDecimalPoint() {
        assertEquals("25", CalculatorEngine.evaluate("5^2"));
    }

    @Test
    public void evaluate_truncatesToFourDecimalPlaces() {
        assertEquals("0.3333", CalculatorEngine.evaluate("1\u00F73"));
    }

    @Test
    public void evaluate_trimsTrailingZeros() {
        assertEquals("1.5", CalculatorEngine.formatResult(new BigDecimal("1.5000")));
    }

    @Test
    public void evaluate_avoidsScientificNotationForLargeResults() {
        String result = CalculatorEngine.evaluate("33+66336626266");
        assertEquals("66336626299", result);
    }

    @Test
    public void evaluate_returnsErrorForInvalidExpressions() {
        assertEquals(CalculatorEngine.ERROR, CalculatorEngine.evaluate(""));
        assertEquals(CalculatorEngine.ERROR, CalculatorEngine.evaluate("5\u00F70"));
        assertEquals(CalculatorEngine.ERROR, CalculatorEngine.evaluate("(5+"));
    }

    @Test
    public void evaluate_evalExParsesDoublePlusAsUnaryPlus() {
        assertEquals("8", CalculatorEngine.evaluate("5++3"));
    }

    @Test
    public void evaluate_recoversFromDanglingLeadingOpenParen() {
        assertEquals("10", CalculatorEngine.evaluate("(7+3"));
        assertEquals("7", CalculatorEngine.evaluate("(7"));
    }

    @Test
    public void evaluate_truncatesRatherThanRounds() {
        assertEquals("0.6666", CalculatorEngine.evaluate("2\u00F73"));
    }

    @Test
    public void stripTrailingOperator_removesSingleTrailingOperator() {
        assertEquals("3+5", CalculatorEngine.stripTrailingOperator("3+5+"));
        assertEquals("3+5", CalculatorEngine.stripTrailingOperator("3+5-"));
        assertEquals("8", CalculatorEngine.stripTrailingOperator("8X"));
        assertEquals("2", CalculatorEngine.stripTrailingOperator("2^"));
        assertEquals("20", CalculatorEngine.stripTrailingOperator("20\u00F7"));
    }

    @Test
    public void stripTrailingOperator_leavesExpressionUnchangedWhenNotNeeded() {
        assertEquals("3+5", CalculatorEngine.stripTrailingOperator("3+5"));
        assertEquals("", CalculatorEngine.stripTrailingOperator(""));
    }

    @Test
    public void evaluateSubmitted_stripsTrailingOperatorBeforeEvaluating() {
        assertEquals("8", CalculatorEngine.evaluateSubmitted("3+5+"));
        assertEquals("17", CalculatorEngine.evaluateSubmitted("2+3X5+"));
    }

    @Test
    public void normalizeOperators_replacesUiSymbols() {
        assertEquals("2*3/4", CalculatorEngine.normalizeOperators("2X3\u00F74"));
    }
}
