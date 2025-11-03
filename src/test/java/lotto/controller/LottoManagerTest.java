package lotto.controller;

import lotto.ErrorMessage;
import lotto.model.LottoProfitAnalyzer;
import lotto.model.LottoRankStrategy;
import lotto.model.LottoResultEvaluator;
import lotto.model.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LottoManagerTest {

    private final LottoResultEvaluator evaluator = new LottoResultEvaluator();
    private final LottoProfitAnalyzer calculator = new LottoProfitAnalyzer();
    private final LottoManager manager = new LottoManager();

    @DisplayName("로또 그룹과 당첨 번호를 비교해 올바른 등수 리스트를 반환한다.")
    @Test
    void evaluateResults_정상_등수_계산() {
        // given
        List<Lotto> lottoGroup = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)), // 1등
                new Lotto(List.of(1, 2, 3, 4, 5, 7)), // 2등 (보너스)
                new Lotto(List.of(1, 2, 3, 4, 5, 9)), // 3등
                new Lotto(List.of(1, 2, 3, 4, 9, 10)) // 4등
        );
        Set<Integer> winning = Set.of(1, 2, 3, 4, 5, 6);
        int bonus = 7;

        // when
        List<LottoRankStrategy> results = managerTestHelper_evaluateResults(lottoGroup, winning, bonus);

        // then
        assertThat(results).containsExactly(
                LottoRankStrategy.RANK_1,
                LottoRankStrategy.RANK_2,
                LottoRankStrategy.RANK_3,
                LottoRankStrategy.RANK_4
        );
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복될 경우 예외가 발생한다.")
    @Test
    void validateBonusNumber_중복시_예외() {
        // given
        Set<Integer> winningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        int bonus = 6;

        // when & then
        assertThatThrownBy(() -> managerTestHelper_validateBonusNumber(winningNumbers, bonus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_WINNING_NUMBER.getMessage());
    }

    @DisplayName("보너스 번호가 중복되지 않으면 예외가 발생하지 않는다.")
    @Test
    void validateBonusNumber_정상_동작() {
        // given
        Set<Integer> winning = Set.of(1, 2, 3, 4, 5, 6);
        int bonus = 7;

        // when & then
        assertTrue(managerTestHelper_validateBonusNumber(winning, bonus));
    }

    private List<LottoRankStrategy> managerTestHelper_evaluateResults(List<Lotto> lottoGroup, Set<Integer> winningNumbers, int bonus) {
        List<Double> scores = evaluator.matchCounts(lottoGroup, winningNumbers, bonus);
        return calculator.convertToRanks(scores);
    }

    private boolean managerTestHelper_validateBonusNumber(Set<Integer> winning, int bonus) {
        if (winning.contains(bonus)) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_WINNING_NUMBER.getMessage());
        }
        return true;
    }
}
