package lotto.view;


import camp.nextstep.edu.missionutils.Console;
import lotto.ErrorMessage;

public class InputView {
    private static final String INPUT_PRICE_MESSAGE = "구입금액을 입력해 주세요.";
    private static final String INPUT_WINNING_MESSAGE = "당첨 번호를 입력해 주세요.";
    private static final String INPUT_BONUS_MESSAGE = "보너스 번호를 입력해 주세요.";

    public int inputPrice() {
        System.out.println(INPUT_PRICE_MESSAGE);
        String input = Console.readLine();
        validatePrice(input);
        return Integer.parseInt(input);
    }

    public String inputWinningNumber() {
        System.out.println(INPUT_WINNING_MESSAGE);
        String input = Console.readLine();
        validateNotEmpty(input);
        return input;
    }

    public int inputBonusNumber() {
        System.out.println(INPUT_BONUS_MESSAGE);
        String input = Console.readLine();
        validateBonusNumber(input);
        return Integer.parseInt(input);
    }

    private void validatePrice(String input) {
        validateNumber(input);
        int price = Integer.parseInt(input);
        if (price <= 1000) {
            throw new IllegalArgumentException(ErrorMessage.NEGATIVE_OR_ZERO_PRICE.getMessage());
        }
        if (price % 1000 != 0) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_PURCHASE_UNIT.getMessage());
        }
        validatePurchasePrice(price);
    }

    private void validatePurchasePrice(int price) {
        if (price > 100_000) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_MAX_PURCHASE.getMessage());
        }
    }

    private void validateBonusNumber(String input) {
        validateNumber(input);
        int bonus = Integer.parseInt(input);
        if (bonus < 1 || bonus > 45) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_NUMBER_RANGE.getMessage());
        }
    }

    private void validateNumber(String input) {
        validateNotEmpty(input);
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }

    private void validateNotEmpty(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_INPUT.getMessage());
        }
    }
}
