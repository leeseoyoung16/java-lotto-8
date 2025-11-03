package lotto;

public enum ErrorMessage {
    EMPTY_INPUT("입력값이 들어오지 않았습니다."),
    INVALID_INPUT("숫자만 입력할 수 있습니다."),
    EXCEED_MAX_PURCHASE("로또는 한번에 100,000원까지 구매할 수 있습니다."),
    INVALID_PURCHASE_UNIT("로또는 1000원 단위로 구매할 수 있습니다."),
    NEGATIVE_OR_ZERO_PRICE("로또는 1000원 이상 구매할 수 있습니다."),
    DUPLICATE_WINNING_NUMBER("당첨 번호는 중복될 수 없습니다."),
    INVALID_NUMBER_RANGE("당첨 번호는 1~45 사이여야 합니다."),
    INVALID_NUMBER_COUNT("당첨 번호는 6개여야 합니다."),
    ;

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + message;
    }
}
