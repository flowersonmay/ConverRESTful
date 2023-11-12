package ru.leskov.converrestful.convert;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueConverterTest {

    private ValueConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ValueConverter();
    }

    @Test
    public void testNumberToString() {
        assertEquals("one hundred twenty three", converter.numberToString(123));
    }

    @Test
    public void testStringToNumber() {
        assertEquals(25, converter.stringToNumber("twenty five"));
    }

    @Test
    public void testInvalidStringToNumber() {
        RuntimeException myException = assertThrows(RuntimeException.class, () -> converter.stringToNumber("one hundred seventysss"));
        assertEquals("Incorrect word seventysss", myException.getMessage());
    }

    @Test
    public void testLargeNumberToString() {
        RuntimeException myException = assertThrows(RuntimeException.class, () -> converter.numberToString(1000000000000L));
        assertEquals("Number is too large", myException.getMessage());

    }
    @Test
    public void testNumberToStringLargeNegative() {
        RuntimeException myException = assertThrows(RuntimeException.class, () -> converter.numberToString(-1000000000000L));
        assertEquals("Number is too large", myException.getMessage());

    }
    @Test
    public void testLargeStringToNumber() {
        RuntimeException myException = assertThrows(RuntimeException.class, () -> converter.stringToNumber("one trillion"));
        assertEquals("Incorrect word trillion", myException.getMessage());
    }
    @Test
    public void testZeroNumberToString() {

        assertEquals("zero", converter.numberToString(0));
    }
    @Test
    public void testNegativeNumberToString() {
        assertEquals("minus five hundred sixty seven", converter.numberToString(-567));
    }

    @Test
    public void testLargeStringToNumberWithSpaces() {
        assertEquals(123456789, converter.stringToNumber("one hundred twenty three million four hundred fifty six thousand seven hundred  eighty nine"));
    }


}