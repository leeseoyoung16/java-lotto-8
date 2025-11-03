package lotto.controller;

import lotto.ErrorMessage;
import lotto.model.*;
import lotto.model.domain.Lotto;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LottoManager {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoGenerator lottoGenerator;
    private final WinningNumberParser winningNumberParser;
    private final LottoResultEvaluator resultEvaluator;
    private final LottoProfitAnalyzer profitCalculator;

    public LottoManager() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoGenerator = new LottoGenerator();
        this.winningNumberParser = new WinningNumberParser();
        this.resultEvaluator = new LottoResultEvaluator();
        this.profitCalculator = new LottoProfitAnalyzer();
    }

    public void start() {
        int purchaseAmount = retryUntilValid(this::inputPriceSafely);
        List<Lotto> purchasedLottoGroup = generateAndDisplayLottoGroup(purchaseAmount);

        Set<Integer> winningNumbers = retryUntilValid(this::inputWinningNumbersSafely);
        int bonusNumber = retryUntilValid(() -> inputBonusNumberSafely(winningNumbers));

        List<LottoRankStrategy> rankResults = evaluateResults(purchasedLottoGroup, winningNumbers, bonusNumber);
        displayResults(rankResults, purchaseAmount);
    }

    private List<Lotto> generateAndDisplayLottoGroup(int purchaseAmount) {
        List<Lotto> purchasedLottoGroup = lottoGenerator.issueAll(purchaseAmount);
        outputView.displayEmptyLine();
        outputView.displayLottoGroup(purchasedLottoGroup);
        return purchasedLottoGroup;
    }

    private int inputPriceSafely() {
        return inputView.inputPrice();
    }

    private Set<Integer> inputWinningNumbersSafely() {
        String input = inputView.inputWinningNumber();
        outputView.displayEmptyLine();
        return winningNumberParser.winningNumbers(input);
    }

    private int inputBonusNumberSafely(Set<Integer> winningNumbers) {
        int bonusNumber = inputView.inputBonusNumber();
        outputView.displayEmptyLine();
        validateBonusNumber(winningNumbers, bonusNumber);
        return bonusNumber;
    }

    private <T> T retryUntilValid(SupplierWithException<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.displayErrorMessage(e.getMessage());
                outputView.displayRetryMessage();
            }
        }
    }

    private List<LottoRankStrategy> evaluateResults(List<Lotto> lottoGroup, Set<Integer> winningNumbers, int bonusNumber) {
        List<Double> matchScores = resultEvaluator.matchCounts(lottoGroup, winningNumbers, bonusNumber);
        return profitCalculator.convertToRanks(matchScores);
    }

    private void displayResults(List<LottoRankStrategy> rankResults, int purchaseAmount) {
        Map<LottoRankStrategy, Long> rankCount = rankResults.stream()
                .collect(Collectors.groupingBy(rank -> rank, Collectors.counting()));

        double profitRate = profitCalculator.calculateRate(rankResults, purchaseAmount);

        outputView.displayLottoGroupResult(rankCount, profitRate);
    }

    private void validateBonusNumber(Set<Integer> numbers, int bonusNumber) {
        if (numbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_WINNING_NUMBER.getMessage());
        }
    }

    @FunctionalInterface
    private interface SupplierWithException<T> {
        T get();
    }
}
