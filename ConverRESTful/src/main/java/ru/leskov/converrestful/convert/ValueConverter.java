package ru.leskov.converrestful.convert;


import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;


@Component
public class ValueConverter {
    private static final long MAXIMUM_NUMBER = 999_999_999_999L;

    private static final Map<Long, String> UNITS_MAP = Map.ofEntries(
            Map.entry(1L,"one"),
            Map.entry(2L, "two"),
            Map.entry(3L, "three"),
            Map.entry(4L, "four"),
            Map.entry(5L, "five"),
            Map.entry(6L, "six"),
            Map.entry(7L, "seven"),
            Map.entry(8L, "eight"),
            Map.entry(9L, "nine"),
            Map.entry(10L, "ten"),
            Map.entry(11L, "eleven"),
            Map.entry(12L, "twelve"),
            Map.entry(13L, "thirteen"),
            Map.entry(14L, "fourteen"),
            Map.entry(15L, "fifteen"),
            Map.entry(16L, "sixteen"),
            Map.entry(17L, "seventeen"),
            Map.entry(18L, "eighteen"),
            Map.entry(19L, "nineteen"));

    private static final Map<Long, String> TENS_MAP =  Map.ofEntries(
            Map.entry(20L, "twenty"),
            Map.entry(30L, "thirty"),
            Map.entry(40L, "forty"),
            Map.entry(50L, "fifty"),
            Map.entry(60L, "sixty"),
            Map.entry(70L, "seventy"),
            Map.entry(80L, "eighty"),
            Map.entry(90L, "ninety")
    );

    public String numberToString(long number) {
        if (number<0)
            return "minus " + numberToString(-number);
        if (number==0){
            return "zero";
        }
        if (number < 20) {
            return convertUnits(number);
        } else if (number < 100) {
            return convertTens(number / 10 * 10) + " " +convertUnits(number % 10);
        } else if (number < 1000) {
            return convertUnits(number / 100) + " hundred " + numberToString(number % 100);
        } else if (number < 1_000_000) {
            return numberToString(number / 1000) + " thousand " + numberToString(number % 1000);
        } else if (number < 1_000_000_000) {
            return numberToString(number / 1_000_000) + " million " + numberToString(number % 1_000_000);
        } else if (number < 1_000_000_000_000L) {
            return numberToString(number / 1_000_000_000) + " billion " + numberToString(number % 1_000_000_000);
        }
        throw new RuntimeException("Number is too large");
    }

    private String convertUnits(long number) {
        return UNITS_MAP.getOrDefault(number, "");
    }

    private String convertTens(long number) {
        return TENS_MAP.getOrDefault(number, "");
    }


    /**
     * Кейс 2:
     * разбивает входную строку на слова, и для каждого слова определяет,
     * является ли оно числом, десятком, сотней, тысячей или миллионом.
     * Затем собирает числовое значение, учитывая все слова, и возвращает результат.
     */
    public long stringToNumber(String input) {
        String[] words = input.split("\\s+");
        long result = 0;
        long currentNumber = 0;
        boolean isNegative = false;

        for (String word : words) {
            word = word.toLowerCase();
            if (word.equals("zero")){
                return 0;
            }
            if (word.equals("minus")) {
                isNegative = true;
            } else if (UNITS_MAP.containsValue(word)) {
                currentNumber += getKeyByValue(UNITS_MAP, word);
            } else if (TENS_MAP.containsValue(word)) {
                currentNumber += getKeyByValue(TENS_MAP, word);
            } else if (word.equals("hundred")) {
                currentNumber *= 100;
            } else if (word.equals("thousand")) {
                currentNumber *= 1000;
                result += currentNumber;
                currentNumber = 0;
            } else if (word.equals("million")) {
                currentNumber *= 1_000_000;
                result += currentNumber;
                currentNumber = 0;
            } else if (word.equals("billion")) {
                currentNumber *= 1_000_000_000;
                result += currentNumber;
                currentNumber = 0;
            } else {
                throw new RuntimeException("Incorrect word " + word);
            }

            if (result + currentNumber > MAXIMUM_NUMBER) {
                throw new RuntimeException("Number is too large");
            }
        }
        result += currentNumber;
        if (isNegative) {
            result = -result;
        }
        return result;
    }

    private Long getKeyByValue(Map<Long, String> map, String value) {
        Optional<Long> key = map.entrySet().stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return key.orElse(0L);
    }
}

