package org.accula.api.converter;

import org.accula.api.db.model.Project;
import org.accula.api.handlers.dto.InputDto;
import org.accula.api.handlers.dto.ProjectConfDto;

/**
 * @author Anton Lamtev
 */
public final class DtoToModelConverter {
    private DtoToModelConverter() {
    }

    public static Project.Conf convert(final ProjectConfDto conf) {
        validate(conf);
        return Project.Conf.builder()
                .adminIds(conf.getAdmins())
                .cloneMinLineCount(conf.getCloneMinLineCount())
                .build();
    }

    private static void validate(final InputDto dto) {
        if (!dto.isValid()) {
            throw new ValidationException();
        }
    }

    public static class ValidationException extends RuntimeException {
        private static final long serialVersionUID = -885047004998370035L;
    }
}
