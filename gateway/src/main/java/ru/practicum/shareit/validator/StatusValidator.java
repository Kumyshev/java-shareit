package ru.practicum.shareit.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.exception.UnsupportedStatusException;

public class StatusValidator implements ConstraintValidator<StatusConstraint, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(StatusConstraint annotation) {
        acceptedValues = Stream.of(BookingState.values())
                .map(Enum::name)
                .collect(Collectors.toList());
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
