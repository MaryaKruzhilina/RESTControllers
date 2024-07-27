package ru.maryKr.bootCrud.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

//не удалось создать рабочий валидатор, хочу спросить что не так

public @interface UniqueEmail {
    String message() default "Email уже существует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
