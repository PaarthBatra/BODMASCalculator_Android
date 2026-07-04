package com.versionpb.bodmascalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CalculatorStateTest {

    private CalculatorState state;

    @Before
    public void setUp() {
        state = new CalculatorState();
    }

    @Test
    public void clear_resetsToInitialDisplay() {
        state.lineTextView = "12";
        state.lineEditView = "12";
        state.setLineTextView = "3+";
        state.setLineEditView = "12";

        CalculatorState.InputResult result = state.onInput("C");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.BOTH, result.uiUpdate);
        assertEquals("", state.lineTextView);
        assertEquals("0", state.lineEditView);
        assertEquals("", state.setLineTextView);
        assertEquals("0", state.setLineEditView);
    }

    @Test
    public void firstNumericEntry_replacesInitialZero() {
        CalculatorState.InputResult result = state.onInput("7");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.EDIT_ONLY, result.uiUpdate);
        assertEquals("7", state.lineTextView);
        assertEquals("7", state.lineEditView);
        assertEquals("7", state.setLineEditView);
        assertEquals("", state.setLineTextView);
    }

    @Test
    public void firstDecimalEntry_startsWithZeroPoint() {
        CalculatorState.InputResult result = state.onInput(".");

        assertTrue(result.handled);
        assertEquals("0.", state.lineEditView);
        assertEquals("0.", state.setLineEditView);
        assertEquals("", state.setLineTextView);
    }

    @Test
    public void firstMinusEntry_startsNegativeNumber() {
        state.onInput("-");
        CalculatorState.InputResult result = state.onInput("3");

        assertTrue(result.handled);
        assertEquals("-3", state.lineEditView);
        assertEquals("-3", state.setLineEditView);
    }

    @Test
    public void firstMinusDecimalEntry_startsWithNegativeZeroPoint() {
        state.onInput("-");
        CalculatorState.InputResult result = state.onInput(".");

        assertTrue(result.handled);
        assertEquals("-0.", state.lineEditView);
        assertEquals("-0.", state.setLineEditView);
    }

    @Test
    public void ignoredFirstEntry_keepsInitialZero() {
        CalculatorState.InputResult result = state.onInput("+");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.BOTH, result.uiUpdate);
        assertEquals("0", state.lineEditView);
        assertEquals("", state.setLineTextView);
    }

    @Test
    public void firstBackspace_doesNothing() {
        CalculatorState.InputResult result = state.onInput("D");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.NONE, result.uiUpdate);
        assertEquals("0", state.lineEditView);
    }

    @Test
    public void operatorAfterValue_isHandledAndUpdatesExpression() {
        state.onInput("3");
        CalculatorState.InputResult result = state.onInput("+");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.TEXT_ONLY, result.uiUpdate);
        assertEquals("3+", state.lineTextView);
        assertEquals("3+", state.setLineTextView);
        assertEquals("3", state.setLineEditView);
    }

    @Test
    public void backspace_removesLastDigitOfOperand() {
        feed("5", "6");
        CalculatorState.InputResult result = state.onInput("D");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.EDIT_ONLY, result.uiUpdate);
        assertEquals("5", state.setLineEditView);
    }

    @Test
    public void backspace_onSingleDigitResetsToZero() {
        state.onInput("7");
        state.onInput("D");

        assertEquals("0", state.setLineEditView);
        assertEquals("", state.setLineTextView);
    }

    @Test
    public void duplicateDot_isIgnored() {
        feed("1", ".", "5");
        CalculatorState.InputResult result = state.onInput(".");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.NONE, result.uiUpdate);
        assertEquals("1.5", state.setLineEditView);
    }

    @Test
    public void fullExpression_multiplicationPrecedence() {
        feed("2", "+", "3", "X", "5");
        state.onInput("=");

        assertEquals("17", state.setLineEditView);
        assertEquals("", state.setLineTextView);
    }

    @Test
    public void fullExpression_simpleAddition() {
        feed("7", "+", "3");
        state.onInput("=");

        assertEquals("10", state.setLineEditView);
    }

    @Test
    public void fullExpression_withBrackets() {
        feed("(", "7", "+", "3", ")");
        state.onInput("=");

        assertEquals("10", state.setLineEditView);
    }

    @Test
    public void operatorAfterValue_appendsToExpressionWithoutEvaluating() {
        feed("8", "+", "2");
        CalculatorState.InputResult result = state.onInput("X");

        assertTrue(result.handled);
        assertEquals(CalculatorState.UiUpdate.TEXT_ONLY, result.uiUpdate);
        assertEquals("8+2X", state.setLineTextView);
    }

    private void feed(String... inputs) {
        for (String input : inputs) {
            state.onInput(input);
        }
    }
}
