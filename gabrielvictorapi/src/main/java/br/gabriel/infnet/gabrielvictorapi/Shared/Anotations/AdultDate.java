package br.gabriel.infnet.gabrielvictorapi.Shared.Anotations;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import br.gabriel.infnet.gabrielvictorapi.Shared.Valdiators.AdulDatetValidator;

@Documented
@Constraint(validatedBy = AdulDatetValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultDate {
    String message() default "O usuário deve ter pelo menos 18 anos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}