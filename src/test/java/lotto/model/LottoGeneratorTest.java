package lotto.model;

import lotto.model.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class LottoGeneratorTest {

    private final LottoGenerator lottoGenerator = new LottoGenerator();

    @DisplayName("로또 1장은 6개의 중복 없는 번호로 생성된다.")
    @Test
    void 로또_한장_6개_번호_생성() {
        // when
        List<Integer> numbers = lottoGenerator.issueAll(1000).getFirst().getNumbers();

        // then
        assertThat(numbers).hasSize(LottoGenerator.LOTTO_SIZE);
        assertThat(numbers).doesNotHaveDuplicates();
        assertThat(numbers).allMatch(num ->
                num >= LottoGenerator.LOTTO_MIN && num <= LottoGenerator.LOTTO_MAX);
    }

    @DisplayName("로또 구입 금액만큼 로또가 생성된다.")
    @Test
    void 구입금액에_비례해_로또_생성() {
        // given
        int price = 5000;

        // when
        List<Lotto> lottoGroup = lottoGenerator.issueAll(price);

        // then
        assertThat(lottoGroup).hasSize(5);
    }

    @DisplayName("로또 번호는 항상 오름차순으로 정렬되어 있다.")
    @Test
    void 로또_번호_오름차순_정렬() {
        // when
        List<Integer> numbers = lottoGenerator.issueAll(1000).getFirst().getNumbers();

        // then
        List<Integer> sorted = numbers.stream().sorted().toList();
        assertThat(numbers).isEqualTo(sorted);
    }

}