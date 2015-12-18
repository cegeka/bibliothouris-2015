package cgk.bibliothouris.learning.service;

import org.glassfish.grizzly.utils.Pair;

import java.util.function.Supplier;

public abstract class BiblioService {

    public Pair<Integer, Integer> findPaginationParameters(String start, String end, Supplier<Long> countItems) {
        Integer startPosition, endPosition;

        if (start == null || isNegative(start))
            startPosition = 0;
        else
            startPosition = Integer.valueOf(start);

        if (end == null || isNegative(end))
            endPosition = countItems.get().intValue();
        else
            endPosition = Integer.valueOf(end);

        return new Pair<>(startPosition, endPosition);
    }

    private boolean isNegative(String number) {
        try {
            if (Integer.parseInt(number) < 0)
                return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}
