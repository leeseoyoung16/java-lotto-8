package lotto.model;

import lotto.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class WinningNumberParserTest {

    private final WinningNumberParser winningNumberParser = new WinningNumberParser();

    @DisplayName("정상적으로 쉼표를 기준으로 파싱한다.")
    @Test
    void 정상적으로_쉼표_기준_파싱() {
        //given
        String input = "1,2,3,4,5,6";
        //when
        Set<Integer> numbers = winningNumberParser.winningNumbers(input);
        //then
        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("입력이 공백이거나 비어있으면 예외가 발생한다.")
    @Test
    void 공백_입력시_예외() {
        // given
        String input = "";

        // when & then
        assertThatThrownBy(() -> winningNumberParser.winningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EMPTY_INPUT.getMessage());
    }

    @DisplayName("쉼표 구분이 아닌 입력이 들어오면 예외가 발생한다.")
    @Test
    void 구분자_잘못된_입력_예외() {
        // given
        String input = "1.2.3.4.5.6";

        // when & then
        assertThatThrownBy(() -> winningNumberParser.winningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.getMessage());
    }

    @DisplayName("숫자가 아닌 문자가 포함되면 예외가 발생한다.")
    @Test
    void 숫자_아닌_값_예외() {
        // given
        String input = "1,2,a,4,5,6";

        // when & then
        assertThatThrownBy(() -> winningNumberParser.winningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_INPUT.getMessage());
    }

    @DisplayName("6개보다 많은 숫자가 입력되면 예외가 발생한다.")
    @Test
    void 숫자_개수_초과_예외() {
        // given
        String input = "1,2,3,4,5,6,7";

        // when & then
        assertThatThrownBy(() -> winningNumberParser.winningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_COUNT.getMessage());
    }

    @DisplayName("숫자 중복으로 인해 Set 크기가 줄어들면 예외가 발생한다.")
    @Test
    void 중복_숫자_예외() {
        // given
        String input = "1,2,3,3,4,5";

        // when & then
        assertThatThrownBy(() -> winningNumberParser.winningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_WINNING_NUMBER.getMessage());
    }

    @DisplayName("숫자가 1~45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void 숫자_범위_벗어나면_예외() {
        // given
        String input = "0,2,3,4,5,6";

        // when & then
        assertThatThrownBy(() -> winningNumberParser.winningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_NUMBER_RANGE.getMessage());
    }
}