package edu.uic.cs474.interpreter;

public class Statement {

    public static class SeqStatement extends Statement {
        public final Statement[] statements;

        public SeqStatement(Statement ... statements) {
            this.statements = statements;
        }
    }

    public static class SetStatement extends Statement {
        public final Name variableName;
        public final Expression newValue;

        public SetStatement(Name variableName, Expression newValue) {
            this.variableName = variableName;
            this.newValue = newValue;
        }
    }

    public static class WhileStatement extends Statement {
        public final Expression guard;
        public final Statement body;

        public WhileStatement(Expression guard, Statement body) {
            this.guard = guard;
            this.body = body;
        }
    }

    public static class LoopStatement extends Statement {
        public final Statement body;

        public LoopStatement(Statement body) {
            this.body = body;
        }
    }

    public static class ReturnStatement extends Statement {
        public final Expression value;

        public ReturnStatement(Expression value) {
            this.value = value;
        }
    }

    public static class ReturnException extends RuntimeException {
        public final Value ret;

        public ReturnException(Value ret) {
            this.ret = ret;
        }
    }
}
