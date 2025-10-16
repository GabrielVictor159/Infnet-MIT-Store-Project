package br.gabriel.infnet.gabrielvictorapi.Application.Commands.Owner;

import java.util.Optional;

import br.gabriel.infnet.gabrielvictorapi.Shared.MediatorPattern.Command;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// public class CreateOwnerCommand implements Command<Integer> {
//     @NotNull
//     private Integer requestUserId;
//     @NotNull
//     private Integer userId;
//     @NotBlank(message = "O nome não pode ser nulo ou vazio.")
//     @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
//     private String name;
//     @NotBlank(message = "A descrição não pode ser nulo ou vazio.")
//     @Size(min = 3, max = 1000, message = "A descrição deve ter entre 3 e 1000 caracteres.")
//     private String description;
//     private Optional<String> contactPhone;
//     private Optional<String> contactEmail;
//     private Optional<String> cnpj;
//     private Optional<String> cep;
//     private Optional<String> address;
  
// }
