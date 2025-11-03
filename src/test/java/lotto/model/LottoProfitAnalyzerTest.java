package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LottoProfitAnalyzerTest {

    private final LottoProfitAnalyzer lottoProfitCalculator = new LottoProfitAnalyzer();

    @DisplayName("일치 결과 리스트를 등수 리스트로 정상 변환한다.")
    @Test
    void matchScore를_rank로_변환() {
        // given
        List<Double> matchScores = List.of(6.0, 5.5, 4.0, 3.0, 2.0);

        // when
        List<LottoRankStrategy> result = lottoProfitCalculator.convertToRanks(matchScores);

        // then
        assertThat(result).containsExactly(
                LottoRankStrategy.RANK_1,
                LottoRankStrategy.RANK_2,
                LottoRankStrategy.RANK_4,
                LottoRankStrategy.RANK_5
        );
    }

    @DisplayName("등수 리스트로 총 수익률을 정확히 계산한다.")
    @Test
    void 수익률_정상_계산() {
        // given
        List<LottoRankStrategy> ranks = List.of(
                LottoRankStrategy.RANK_1,
                LottoRankStrategy.RANK_3,
                LottoRankStrategy.RANK_5
        );
        int purchaseAmount = 3000;

        // when
        double result = lottoProfitCalculator.calculateRate(ranks, purchaseAmount);

        // then
        assertThat(result).isEqualTo(66716833.3);
    }
}