package edu.uic.cs474.interpreter;

public abstract class Expression extends Statement {
    public static class IntConstant extends Expression {
        public final int c;

        public IntConstant(int c) {
            this.c = c;
        }
    }

    public static class BinOpExpression extends Expression {
        // +, -, *, /
        public final Expression left, right;
        public final Operation op;

        public enum Operation { PLUS, MINUS, TIMES, DIV };

        public BinOpExpression(Operation op, Expression left, Expression right) {
            this.left = left;
            this.right = right;
            this.op = op;
        }
    }

    public static class LetExpression extends Expression {
        public final Name variableName;
        public final Expression value;
        public final Statement body;

        public LetExpression(Name variableName, Expression value, Statement body) {
            this.variableName = variableName;
            this.value = value;
            this.body = body;
        }
    }

    public static class VariableExpression extends Expression {
        public final Name variable;

        public VariableExpression(Name variable) {
            this.variable = variable;
        }
    }

    public static class EqExpression extends Expression {
        public final Expression left;
        public final Expression right;

        public EqExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class IfExpression extends Expression {
        public final Expression cond;
        public final Statement thenSide;
        public final Statement elseSide;

        public IfExpression(Expression cond, Statement thenSide, Statement elseSide) {
            this.cond = cond;
            this.thenSide = thenSide;
            this.elseSide = elseSide;
        }
    }

    public static class FunctionDeclExpression extends Expression {
        public final Name name;
        public final Name[] formalArguments;
        public final Expression functionBody;
        public final Expression scope;

        public FunctionDeclExpression(Name name, Name[] formalArguments, Expression functionBody, Expression scope) {
            this.name = name;
            this.formalArguments = formalArguments;
            this.functionBody = functionBody;
            this.scope = scope;
        }
    }

    public static class FunctionCallExpression extends Expression {
        public final Name name;
        public final Expression[] actualArguments;

        public FunctionCallExpression(Name name, Expression[] actualArguments) {
            this.name = name;
            this.actualArguments = actualArguments;
        }
    }

    public static class FirstClassFunctionDeclExpression extends Expression {
        public final Name name;
        public final Name[] formalArguments;
        public final Expression functionBody;

        public FirstClassFunctionDeclExpression(Name name, Name[] formalArguments, Expression functionBody) {
            this.name = name;
            this.formalArguments = formalArguments;
            this.functionBody = functionBody;
        }
    }

    public static class FirstClassFunctionCallExpression extends Expression {
        public final Expression functionToBeCalled;
        public final Expression[] actualArguments;

        public FirstClassFunctionCallExpression(Expression functionToBeCalled, Expression[] actualArguments) {
            this.functionToBeCalled = functionToBeCalled;
            this.actualArguments = actualArguments;
        }
    }

    public static class LambdaDeclExpression extends Expression {
        public final Name[] formalArguments;
        public final Expression functionBody;

        public LambdaDeclExpression(Name[] formalArguments, Expression functionBody) {
            this.formalArguments = formalArguments;
            this.functionBody = functionBody;
        }
    }

    public static class IncExpression extends Expression {
        public final Name variableName;

        public IncExpression(Name variableName) {
            this.variableName = variableName;
        }
    }

    public static class NeqExpression extends Expression {
        public final Expression left;
        public final Expression right;

        public NeqExpression(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }
    }
}