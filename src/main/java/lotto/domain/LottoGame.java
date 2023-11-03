package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoGame {

    private List<Lotto> lottos = new ArrayList<>();
    private Map<LottoResult, Integer> lottoMap = new HashMap<>();

    public LottoGame(int lottoMoney) {
        buildLottos(lottoMoney);
        buildLottoMap();
    }

    private void buildLottos(int lottoMoney) {
        int lottoCount = lottoMoney / 1000;
        for (int i = 0; i < lottoCount; i++) {
            List<Integer> lottoNums = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            List<Integer> sortedLottoNums = lottoNums.stream().sorted().toList();
            lottos.add(new Lotto(sortedLottoNums));
        }
    }

    private void buildLottoMap() {
        for (LottoResult lottoResult : LottoResult.values()) {
            lottoMap.put(lottoResult, 0);
        }
    }

    public void addValueLottoMap(List<Integer> winNums, int bonusNum) {
        for (Lotto lotto : lottos) {
            List<Integer> lottoNums = lotto.getNumbers();
            int count = calculateCounts(lottoNums, winNums);
            boolean bonusNumContained = isBonusNumContained(lottoNums, bonusNum);

            LottoResult lottoResult = LottoResult.findByCountAndValidBonus(count, bonusNumContained);
            if (lottoResult != null) {
                lottoMap.put(lottoResult, lottoMap.get(lottoResult) + 1);
            }
        }
    }

    private int calculateCounts(List<Integer> lottoNums, List<Integer> winNums) {
        int count = 0;
        for (int i = 0; i < lottoNums.size(); i++) {
            if (winNums.contains(lottoNums.get(i))) {
                count++;
            }
        }
        return count;
    }

    private boolean isBonusNumContained(List<Integer> lottoNums, int bonusNum) {
        return lottoNums.contains(bonusNum);
    }

    public List<Lotto> getLottos() {
        return lottos;
    }

    public Map<LottoResult, Integer> getLottoMap() {
        return lottoMap;
    }
}