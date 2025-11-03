package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.model.domain.Lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoGenerator {

    private static final int LOTTO_PRICE = 1000;
    public static final int LOTTO_MIN = 1;
    public static final int LOTTO_MAX = 45;
    public static final int LOTTO_SIZE = 6;

    public List<Lotto> issueAll(int price) {
        int count = price / LOTTO_PRICE;
        List<Lotto> lottoGroup = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            lottoGroup.add(new Lotto(drawNumbers()));
        }
        return lottoGroup;
    }

    private List<Integer> drawNumbers() {
        List<Integer> numbers = new ArrayList<>(Randoms.pickUniqueNumbersInRange(LOTTO_MIN, LOTTO_MAX, LOTTO_SIZE));
        Collections.sort(numbers);
        return numbers;
    }

}
