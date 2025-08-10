package ru.yandex.practicum.filmorate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotAfterTodayValidator.class)
public @interface NotAfterToday {
    String message() default "Дата не может быть позже текущей даты";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}