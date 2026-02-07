package pt.ul.fc.di.css.javafxexample.model;

public enum Turno {
    MANHA,
    TARDE,
    NOITE;

    public boolean equalsString(String value) {
        if (value == null) return false;
        return this.name().equalsIgnoreCase(value);
    }

}
