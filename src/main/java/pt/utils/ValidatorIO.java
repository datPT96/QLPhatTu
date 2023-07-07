package pt.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@Data
public class ValidatorIO {

    private List<String> violationList = new ArrayList<>();

    public <T> boolean validator(T obj) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(obj);

//        final Map<String, String> messages = new HashMap<>();
        for (ConstraintViolation<T> violation : violations) {
            log.error(violation.getPropertyPath().toString() + " " + violation.getMessage());
            violationList.add(violation.getPropertyPath().toString() + " " + violation.getMessage());
        }
        return violations.isEmpty();
    }

}
