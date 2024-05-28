package ru.practicum.shareit.booking.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.practicum.shareit.Status;
import ru.practicum.shareit.booking.exception.UnsupportedStatusException;

public class StatusValidator implements ConstraintValidator<StatusConstraint, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(StatusConstraint annotation) {
        acceptedValues = Stream.of(Status.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        } else if (!acceptedValues.contains(value.toString()))
            throw new UnsupportedStatusException();

        return true;
    }
}
