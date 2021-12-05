package edu.uic.cs474.interpreter;

public class Binding {
    public final Name name;
    public final Value value;

    public Binding(Name name, Value value) {
        this.name = name;
        this.value = value;
    }
}
