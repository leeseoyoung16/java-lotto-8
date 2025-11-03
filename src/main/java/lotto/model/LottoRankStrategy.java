package lotto.model;

public enum LottoRankStrategy {

    RANK_5(3, false, 5_000),
    RANK_4(4, false, 50_000),
    RANK_3(5, false, 1_500_000),
    RANK_2(5, true, 30_000_000),
    RANK_1(6, false, 2_000_000_000);

    private final int matchCount;
    private final boolean isBonus;
    private final int prize;

    LottoRankStrategy(int matchCount, boolean isBonus, int prize) {
        this.matchCount = matchCount;
        this.isBonus = isBonus;
        this.prize = prize;
    }

    public int getMatchCount() { return matchCount; }
    public boolean isBonus() { return isBonus; }
    public int getPrize() { return prize; }

    public static LottoRankStrategy fromResult(double result) {
        if (result == 6) return RANK_1;
        if (result == 5.5) return RANK_2;
        if (result == 5) return RANK_3;
        if (result == 4) return RANK_4;
        if (result == 3) return RANK_5;
        return null;
    }
}
