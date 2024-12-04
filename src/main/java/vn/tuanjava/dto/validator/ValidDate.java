package vn.tuanjava.dto.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface ValidDate {
    String message() default "Invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}