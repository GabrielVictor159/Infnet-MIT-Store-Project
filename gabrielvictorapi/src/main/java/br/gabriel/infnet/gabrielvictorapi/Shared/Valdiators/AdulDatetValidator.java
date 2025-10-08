package br.gabriel.infnet.gabrielvictorapi.Shared.Valdiators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.AdultDate;

public class AdulDatetValidator implements ConstraintValidator<AdultDate, Date> {

    @Override
    public boolean isValid(Date birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) return true;

        LocalDate nascimento = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoje = LocalDate.now();
        return nascimento.plusYears(18).isBefore(hoje) || nascimento.plusYears(18).isEqual(hoje);
    }
}