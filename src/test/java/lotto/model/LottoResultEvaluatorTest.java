package lotto.model;

import lotto.model.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LottoResultEvaluatorTest {

    private final LottoResultEvaluator evaluator = new LottoResultEvaluator();

    @DisplayName("로또 번호가 3개 일치하면 3점으로 계산")
    @Test
    void 세개_일치하면_3점() {
        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 7, 8, 9));
        Set<Integer> winning = Set.of(1, 2, 3, 10, 11, 12);
        int bonus = 13;

        // when
        List<Double> result = evaluator.matchCounts(List.of(lotto), winning, bonus);

        // then
        assertThat(result).containsExactly(3.0);
    }

    @DisplayName("로또 번호가 5개 + 보너스 번호 일치하면 5.5점으로 계산")
    @Test
    void 다섯개_보너스포함시_5점5() {
        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        Set<Integer> winning = Set.of(1, 2, 3, 4, 5, 9);
        int bonus = 7;

        // when
        List<Double> result = evaluator.matchCounts(List.of(lotto), winning, bonus);

        // then
        assertThat(result).containsExactly(5.5);
    }

    @DisplayName("로또 번호가 6개 모두 일치하면 6점으로 계산")
    @Test
    void 여섯개_모두_일치하면_6점() {
        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Set<Integer> winning = Set.of(1, 2, 3, 4, 5, 6);
        int bonus = 7;

        // when
        List<Double> result = evaluator.matchCounts(List.of(lotto), winning, bonus);

        // then
        assertThat(result).containsExactly(6.0);
    }

    @DisplayName("로또 번호가 5개만 일치하고 보너스는 불일치하면 5점으로 계산")
    @Test
    void 다섯개만_일치하면_5점() {
        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        Set<Integer> winning = Set.of(1, 2, 3, 4, 5, 8);
        int bonus = 9;

        // when
        List<Double> result = evaluator.matchCounts(List.of(lotto), winning, bonus);

        // then
        assertThat(result).containsExactly(5.0);
    }

}