package lotto.model;

import lotto.ErrorMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WinningNumberParser {

    private static final String DELIMITER = ",";

    public Set<Integer> winningNumbers(String input) {
        validateInput(input);

        List<String> tokens = splitInput(input);
        validateTokensAreNumbers(tokens);

        Set<Integer> numbers = convertNumberSet(tokens);
        validateUniqueNumberCount(numbers, tokens);
        validateNumberCount(numbers);
        validateNumberRange(numbers);

        return numbers;
    }

    private List<String> splitInput(String input) {
        return Arrays.stream(input.split(DELIMITER))
                .map(String::trim)
                .toList();
    }

    private Set<Integer> convertNumberSet(List<String> tokens) {
        return tokens.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
    }

    private void validateTokensAreNumbers(List<String> tokens) {
        boolean hasInvalid = tokens.stream().anyMatch(token -> !token.matches("\\d+"));
        if (hasInvalid) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    private void validateUniqueNumberCount(Set<Integer> numbers, List<String> tokens) {
        if(numbers.size() != tokens.size()) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_WINNING_NUMBER.getMessage());
        }
    }

    private void validateNumberCount(Set<Integer> numbers) {
        if (numbers.size() != LottoGenerator.LOTTO_SIZE) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_COUNT.getMessage());
        }
    }

    private void validateNumberRange(Set<Integer> numbers) {
        boolean isInvalidRange = numbers.stream()
                .anyMatch(n -> n < LottoGenerator.LOTTO_MIN || n > LottoGenerator.LOTTO_MAX);
        if (isInvalidRange) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_RANGE.getMessage());
        }
    }

    private void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_INPUT.getMessage());
        }
    }
}
