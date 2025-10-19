package br.gabriel.infnet.gabrielvictorapi.Shared.Valdiators;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null) return true;

        cnpj = cnpj.replaceAll("[^\\d]", "");
        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        int[] pesos1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 12; i++) {
            int digito = Character.getNumericValue(cnpj.charAt(i));
            soma1 += digito * pesos1[i];
            soma2 += digito * pesos2[i];
        }

        int dv1 = soma1 % 11;
        dv1 = (dv1 < 2) ? 0 : 11 - dv1;
        soma2 += dv1 * pesos2[12];

        int dv2 = soma2 % 11;
        dv2 = (dv2 < 2) ? 0 : 11 - dv2;

        return dv1 == Character.getNumericValue(cnpj.charAt(12)) &&
               dv2 == Character.getNumericValue(cnpj.charAt(13));
    }
}
