package br.gabriel.infnet.gabrielvictorapi.Domain.Enums;

public enum FileTypeEnum {
    Icon(UserRulesEnum.User),
    Image_Gallery(UserRulesEnum.User),
    User_Document(UserRulesEnum.Admin),
    Icon_Owner(UserRulesEnum.User);

    private final UserRulesEnum requiredRole;

    FileTypeEnum(UserRulesEnum requiredRole) {
        this.requiredRole = requiredRole;
    }

    public UserRulesEnum getRequiredRole() {
        return requiredRole;
    }
}

