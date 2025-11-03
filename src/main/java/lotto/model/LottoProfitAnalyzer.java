package lotto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LottoProfitAnalyzer {

    public List<LottoRankStrategy> convertToRanks(List<Double> matchScores) {
        List<LottoRankStrategy> ranks = new ArrayList<>();

        return matchScores.stream()
                .map(LottoRankStrategy::fromResult)
                .filter(Objects::nonNull)
                .toList();
    }

    public double calculateRate(List<LottoRankStrategy> ranks, int purchaseAmount) {
        long totalPrize = (long) ranks.stream()
                .mapToDouble(LottoRankStrategy::getPrize)
                .sum();

        double profitRate = ((double) totalPrize / purchaseAmount) * 100;
        return Math.round(profitRate * 10) / 10.0;
    }
}
