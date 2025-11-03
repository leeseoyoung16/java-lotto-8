package lotto.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LottoReturnStrategyTest {
    @DisplayName("일치 개수별 등수를 정상 변환한다.")
    @Test
    void 일치_개수별_등수_변환() {
        assertThat(LottoRankStrategy.fromResult(6)).isEqualTo(LottoRankStrategy.RANK_1);
        assertThat(LottoRankStrategy.fromResult(5.5)).isEqualTo(LottoRankStrategy.RANK_2);
        assertThat(LottoRankStrategy.fromResult(5)).isEqualTo(LottoRankStrategy.RANK_3);
        assertThat(LottoRankStrategy.fromResult(4)).isEqualTo(LottoRankStrategy.RANK_4);
        assertThat(LottoRankStrategy.fromResult(3)).isEqualTo(LottoRankStrategy.RANK_5);
        assertThat(LottoRankStrategy.fromResult(2)).isNull();
    }

    @DisplayName("각 등수별 당첨 금액을 정상 도출한다.")
    @Test
    void 등수별_당첨금_검증() {
        assertThat(LottoRankStrategy.RANK_1.getPrize()).isEqualTo(2_000_000_000);
        assertThat(LottoRankStrategy.RANK_2.getPrize()).isEqualTo(30_000_000);
        assertThat(LottoRankStrategy.RANK_3.getPrize()).isEqualTo(1_500_000);
        assertThat(LottoRankStrategy.RANK_4.getPrize()).isEqualTo(50_000);
        assertThat(LottoRankStrategy.RANK_5.getPrize()).isEqualTo(5_000);
    }

    @DisplayName("각 등수별 일치 개수와 보너스 여부를 정상 도출한다.")
    @Test
    void 등수별_조건_검증() {
        assertThat(LottoRankStrategy.RANK_1.getMatchCount()).isEqualTo(6);
        assertThat(LottoRankStrategy.RANK_1.isBonus()).isFalse();

        assertThat(LottoRankStrategy.RANK_2.getMatchCount()).isEqualTo(5);
        assertThat(LottoRankStrategy.RANK_2.isBonus()).isTrue();

        assertThat(LottoRankStrategy.RANK_3.getMatchCount()).isEqualTo(5);
        assertThat(LottoRankStrategy.RANK_3.isBonus()).isFalse();

        assertThat(LottoRankStrategy.RANK_4.getMatchCount()).isEqualTo(4);
        assertThat(LottoRankStrategy.RANK_5.getMatchCount()).isEqualTo(3);
    }

}