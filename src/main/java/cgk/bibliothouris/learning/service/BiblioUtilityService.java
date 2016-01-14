package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import org.glassfish.grizzly.utils.Pair;

import java.util.function.Supplier;

public class BiblioUtilityService {

    public static PaginationParams findPaginationParameters(PaginationParams pagination, Supplier<Long> countItems) {
        Integer startPosition, endPosition;

        if (pagination.getStart() == null || isNegativeNumber(pagination.getStart()))
            startPosition = 0;
        else
            startPosition = Integer.valueOf(pagination.getStart());

        if (pagination.getEnd() == null || isNegativeNumber(pagination.getEnd()))
            endPosition = countItems.get().intValue();
        else
            endPosition = Integer.valueOf(pagination.getEnd());

        return new PaginationParams(startPosition.toString(), endPosition.toString());
    }

    private static boolean isNegativeNumber(String number) {
        try {
            if (Integer.parseInt(number) < 0)
                return true;
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
}
