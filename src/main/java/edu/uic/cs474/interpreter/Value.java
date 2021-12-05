package edu.uic.cs474.interpreter;

import java.util.function.Function;

public abstract class Value {
    public static class IntValue extends Value {
        public final int val;

        public IntValue(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "IntValue{" + "val=" + val + '}';
        }
    }

    public static class BoolValue extends Value {
        public final boolean val;

        public BoolValue(boolean val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "BoolValue{" + "val=" + val + '}';
        }
    }

    public static class FunctionValue extends Value {
        public final Function<Value[], Value> javaFunction;

        public FunctionValue(Function<Value[], Value> javaFunction) {
            this.javaFunction = javaFunction;
        }
    }

    public static class Closure extends Value {
        public Environment capturedEnvironment;
        public final Name[] formalArguments;
        public final Expression functionBody;

        public Closure(Environment capturedEnvironment, Name[] formalArguments, Expression functionBody) {
            this.capturedEnvironment = capturedEnvironment;
            this.formalArguments = formalArguments;
            this.functionBody = functionBody;
        }
    }

    public static class VoidValue extends Value {
        public static final VoidValue VOID = new VoidValue();

        private VoidValue() { }
    }
}

