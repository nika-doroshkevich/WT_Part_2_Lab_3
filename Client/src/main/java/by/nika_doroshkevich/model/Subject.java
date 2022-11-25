package by.nika_doroshkevich.model;

public enum Subject {

    MATH,
    PHYSICS,
    LITERATURE,
    GEOGRAPHY,
    ENGLISH;

    @Override
    public String toString() {
        return super.toString().substring(0, 1).toUpperCase()
                .concat(super.toString().substring(1).toLowerCase());
    }
}
