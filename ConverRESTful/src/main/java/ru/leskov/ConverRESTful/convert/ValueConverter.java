package ru.leskov.ConverRESTful.convert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValueConverter {
    private String input;
    private String type;
    private final long MAXIMUM_NUMBER = 999_999_999_999L;

    private static final Map<Long, String> unitsMap = Map.ofEntries(
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

    private static final Map<Long, String> tensMap =  Map.ofEntries(
            Map.entry(20L, "twenty"),
            Map.entry(30L, "thirty"),
            Map.entry(40L, "forty"),
            Map.entry(50L, "fifty"),
            Map.entry(60L, "sixty"),
            Map.entry(70L, "seventy"),
            Map.entry(80L, "eighty"),
            Map.entry(90L, "ninety")
    );
//
//    public String getInput() {
//        return input;
//    }
//
//    public void setInput(String input) {
//        this.input = input;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public ValueConverter(String input, String type) {
//        this.input = input;
//        this.type = type;
//    }

    public String toConvert(){
        if (this.type.equals("NumberToString"))
            return numberToString();
        if (this.type.equals("StringToNumber"))
            return stringToNumber();
        return "WRONG";
    }

    public boolean isNumber() {
        return this.input.matches("-?\\d+");
    }


    public String numberToString() {
        if (!isNumber())
            return "WRONG NUMBER";
        long number = Long.parseLong(this.input);
        if (Math.abs(number) > MAXIMUM_NUMBER)
            return "WRONG Number is too large";
        if (number == 0) {
            return "zero";
        } else if (number < 0) {
            return "minus " + convertPositiveNumberToString(-number);
        } else {
            return convertPositiveNumberToString(number);
        }
    }

    public String convertPositiveNumberToString(long number) {
        if (number < 20) {
            return convertUnits(number);
        } else if (number < 100) {
            return convertTens(number / 10 * 10) + " " +convertUnits(number % 10);
        } else if (number < 1000) {
            return convertUnits(number / 100) + " hundred " + convertPositiveNumberToString(number % 100);
        } else if (number < 1_000_000) {
            return convertPositiveNumberToString(number / 1000) + " thousand " + convertPositiveNumberToString(number % 1000);
        } else if (number < 1_000_000_000) {
            return convertPositiveNumberToString(number / 1_000_000) + " million " + convertPositiveNumberToString(number % 1_000_000);
        } else if (number < 1_000_000_000_000L) {
            return convertPositiveNumberToString(number / 1_000_000_000) + " billion " + convertPositiveNumberToString(number % 1_000_000_000);
        }
        return "WRONG CONVERT";
    }

    private String convertUnits(long number) {
        return unitsMap.getOrDefault(number, "");
    }

    private String convertTens(long number) {
        return tensMap.getOrDefault(number, "");
    }


    /**
     * Кейс 2:
     * разбивает входную строку на слова, и для каждого слова определяет,
     * является ли оно числом, десятком, сотней, тысячей или миллионом.
     * Затем собирает числовое значение, учитывая все слова, и возвращает результат.
     */
    public String stringToNumber() {
        if (isNumber())
            return "WRONG STRING";
        String[] words = this.input.split("\\s+");
        long result = 0;
        long currentNumber = 0;
        boolean isNegative = false;

        for (String word : words) {
            word = word.toLowerCase();

            if (word.equals("minus")) {
                isNegative = true;
            } else if (unitsMap.containsValue(word)) {
                currentNumber += getKeyByValue(unitsMap, word);
            } else if (tensMap.containsValue(word)) {
                currentNumber += getKeyByValue(tensMap, word);
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
                return "WRONG Invalid word: " + word;
            }

            if (result + currentNumber > MAXIMUM_NUMBER) {
                return "WRONG Number is too large";
            }
        }

        result += currentNumber;

        if (isNegative) {
            result = -result;
        }

        return String.valueOf(result);
    }

    private Long getKeyByValue(Map<Long, String> map, String value) {
        Optional<Long> key = map.entrySet().stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();

        return key.orElse(0L);
    }
}

