package lotto.view;

import lotto.model.LottoRankStrategy;
import lotto.model.domain.Lotto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {
    private static final String LOTTO_COUNT_MESSAGE  = "개를 구매했습니다.";
    private static final String RESULT_HEADER  = "당첨 통계";
    private static final String SEPARATOR_LINE  = "---";
    private static final String CORRECT_MESSAGE  = "개 일치";
    private static final String BONUS_MESSAGE  = ", 보너스 볼 일치";
    private static final String PRIZE_MESSAGE  = " (%,d원)";
    private static final String COUNT_MESSAGE  = " - %d개";
    private static final String PROFIT_MESSAGE = "총 수익률은 %.1f%%입니다.";
    private static final String RETRY_MESSAGE = "다시 입력해주세요.";

    public void displayEmptyLine() {
        System.out.println();
    }

    public void displayLottoGroup(List<Lotto> lottoGroup) {
        System.out.println(lottoGroup.size() + LOTTO_COUNT_MESSAGE);
        lottoGroup.forEach(lotto -> System.out.println(formatLotto(lotto)));
        displayEmptyLine();
    }

    public void displayLottoGroupResult(Map<LottoRankStrategy, Long> rankResult, double rate) {
        displayResultHeader();
        displayRankResults(rankResult);
        displayProfitRate(rate);
    }

    public void displayErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void displayRetryMessage() {
        System.out.println(RETRY_MESSAGE);
        displayEmptyLine();
    }

    private void displayResultHeader() {
        System.out.println(RESULT_HEADER);
        System.out.println(SEPARATOR_LINE);
    }

    private void displayRankResults(Map<LottoRankStrategy, Long> rankResult) {
        for (LottoRankStrategy rank : LottoRankStrategy.values()) {
            long count = rankResult.getOrDefault(rank, 0L);
            System.out.println(formatRankResult(rank, count));
        }
    }

    private void displayProfitRate(double rate) {
        System.out.printf((PROFIT_MESSAGE), rate);
        displayEmptyLine();
    }

    private String formatRankResult(LottoRankStrategy rank, long count) {
        StringBuilder sb = new StringBuilder();
        sb.append(rank.getMatchCount()).append(CORRECT_MESSAGE);
        if (rank.isBonus()) sb.append(BONUS_MESSAGE);
        sb.append(String.format(PRIZE_MESSAGE, rank.getPrize()));
        sb.append(String.format(COUNT_MESSAGE, count));
        return sb.toString();
    }

    private String formatLotto(Lotto lotto) {
        return lotto.getNumbers().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ","[","]"));
    }
}

