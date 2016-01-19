package cgk.bibliothouris.learning.service;

import cgk.bibliothouris.learning.application.valueobject.PaginationParams;
import cgk.bibliothouris.learning.service.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.utils.Pair;

import java.util.function.Supplier;

public class BiblioUtilityService {

    public static void validatePaginationParams(PaginationParams paginationParams) throws ValidationException {
        if (paginationParams.getStart() == null)
            return;
        if (!StringUtils.isNumeric(paginationParams.getStart()))
            throw new ValidationException("Start parameter for pagination is not numeric!");
        if (Integer.parseInt(paginationParams.getStart()) < 0)
            throw new ValidationException("Start parameter for pagination is not positive!");

        if (paginationParams.getEnd() == null)
            return;
        if (!StringUtils.isNumeric(paginationParams.getEnd()))
            throw new ValidationException("End parameter for pagination is not numeric!");
        if (Integer.parseInt(paginationParams.getEnd()) < 0)
            throw new ValidationException("End parameter for pagination is not positive!");
    }

    public static PaginationParams findPaginationParameters(PaginationParams pagination, Supplier<Long> countItems) {
        Integer startPosition, endPosition;

        if (pagination.getStart() == null)
            startPosition = 0;
        else
            startPosition = Integer.valueOf(pagination.getStart());

        if (pagination.getEnd() == null)
            endPosition = countItems.get().intValue();
        else
            endPosition = Integer.valueOf(pagination.getEnd());

        return new PaginationParams(startPosition.toString(), endPosition.toString());
    }
}
