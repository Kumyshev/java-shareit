package ru.practicum.shareit.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.practicum.shareit.exception.UnsupportedStatusException;

public class StatusValidator implements ConstraintValidator<StatusConstraint, CharSequence> {

    @Override
    public void initialize(StatusConstraint annotation) {

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else if ("UNSUPPORTED_STATUS".equals(value.toString()))
            throw new UnsupportedStatusException();

        return true;
    }
}
