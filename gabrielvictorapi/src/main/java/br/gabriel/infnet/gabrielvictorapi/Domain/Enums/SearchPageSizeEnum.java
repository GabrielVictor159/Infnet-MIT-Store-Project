package br.gabriel.infnet.gabrielvictorapi.Domain.Enums;

public enum SearchPageSizeEnum {

    ONE(1),
    SMALL_TINY(10),
    TINY(30),
    AVERAGE(80),
    BIG(200); 

    private final int value;

    SearchPageSizeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}