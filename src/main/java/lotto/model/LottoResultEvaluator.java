package lotto.model;

import lotto.model.domain.Lotto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LottoResultEvaluator {

    public List<Double> matchCounts(List<Lotto> purchasedLottoGroup, Set<Integer> winningNum, int bonusNum) {
        return purchasedLottoGroup.stream()
                .map(lotto -> calculatorMatchScore(lotto, winningNum, bonusNum))
                .collect(Collectors.toList());
    }

    private double calculatorMatchScore(Lotto lotto, Set<Integer> winningNum, int bonusNum) {
        long matchCount = lotto.getNumbers().stream()
                .filter(winningNum::contains)
                .count();
        if(isBonusMatched(matchCount, lotto, bonusNum)) {
            return 5.5;
        }
        return (double) matchCount;
    }

    private boolean isBonusMatched(long matchCount, Lotto lotto, int bonusNum) {
        return matchCount == 5 && lotto.getNumbers().contains(bonusNum);
    }
}

