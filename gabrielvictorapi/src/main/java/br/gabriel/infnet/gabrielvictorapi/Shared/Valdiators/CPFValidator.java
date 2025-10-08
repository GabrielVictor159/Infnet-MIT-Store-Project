package br.gabriel.infnet.gabrielvictorapi.Shared.Valdiators;

import br.gabriel.infnet.gabrielvictorapi.Shared.Anotations.CPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null) return true;

        cpf = cpf.replaceAll("[^\\d]", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cpf.charAt(i));
            soma1 += digito * (10 - i);
            soma2 += digito * (11 - i);
        }

        int dv1 = 11 - (soma1 % 11);
        dv1 = (dv1 >= 10) ? 0 : dv1;
        soma2 += dv1 * 2;

        int dv2 = 11 - (soma2 % 11);
        dv2 = (dv2 >= 10) ? 0 : dv2;

        return dv1 == Character.getNumericValue(cpf.charAt(9)) &&
               dv2 == Character.getNumericValue(cpf.charAt(10));
    }
}
