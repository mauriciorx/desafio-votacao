package com.mauriciorx.votacao.util;

import java.util.Random;
import java.util.regex.Pattern;

public class CpfUtil {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Random RANDOM = new Random();

    public static String generateCpf() {
        int[] numbers = new int[9];

        for (int i = 0; i < 9; i++) {
            numbers[i] = RANDOM.nextInt(10);
        }

        StringBuilder cpfBuilder = new StringBuilder();

        for (int num : numbers) {
            cpfBuilder.append(num);
        }

        int firstCheckDigit = calculateCheckDigit(cpfBuilder.toString(), 9);
        cpfBuilder.append(firstCheckDigit);

        int secondCheckDigit = calculateCheckDigit(cpfBuilder.toString(), 10);
        cpfBuilder.append(secondCheckDigit);

        return formatCpf(cpfBuilder);
    }

    private static String formatCpf(StringBuilder cpfBuilder){
        return String.format("%s.%s.%s-%s",
                cpfBuilder.substring(0, 3),
                cpfBuilder.substring(3, 6),
                cpfBuilder.substring(6, 9),
                cpfBuilder.substring(9, 11));
    }

    public static boolean isCpfValid(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        String digits = cpf.replaceAll("\\D", "");

        if (digits.length() != 11 || hasAllSameDigits(digits)) {
            return false;
        }

        return isValidCheckDigits(digits);
    }

    private static boolean hasAllSameDigits(String cpf) {
        char first = cpf.charAt(0);
        return cpf.chars().allMatch(c -> c == first);
    }

    private static boolean isValidCheckDigits(String cpf) {
        int firstCheckDigit = calculateCheckDigit(cpf, 9);
        int secondCheckDigit = calculateCheckDigit(cpf, 10);

        return firstCheckDigit == Character.getNumericValue(cpf.charAt(9)) &&
                secondCheckDigit == Character.getNumericValue(cpf.charAt(10));
    }

    private static int calculateCheckDigit(String cpf, int length) {
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += (cpf.charAt(i) - '0') * (length + 1 - i);
        }

        int remainder = sum % 11;

        return remainder < 2 ? 0 : 11 - remainder;
    }

    public static boolean isCpfAble() {
        return RANDOM.nextBoolean();
    }
}
