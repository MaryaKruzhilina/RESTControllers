package ru.maryKr.bootCrud.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.maryKr.bootCrud.service.AdminService;
import ru.maryKr.bootCrud.service.AdminServiceImpl;

@Component
public class UniqueUserValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AdminService service;

    public UniqueUserValidator(AdminServiceImpl service) {
        this.service = service;
    }

    //не удалось создать рабочий валидатор, хочу спросить что не так

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return (!service.isNotEmailUnique(email));
    }
}
