package edu.uic.cs474.interpreter;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public class Interpreter {

//    Map<String, Value> variables = new HashMap<>();

    // An interpreter takes an expression and produces a value
    public Value eval(Statement c, Environment e) {
        switch (c.getClass().getSimpleName()) {
            case "IntConstant": {
                Expression.IntConstant intConstant = (Expression.IntConstant) c;
                return new Value.IntValue(intConstant.c);
            }
            case "BinOpExpression": {
                Expression.BinOpExpression binop = (Expression.BinOpExpression) c;

                Value.IntValue left  = (Value.IntValue) eval(binop.left, e);
                Value.IntValue right = (Value.IntValue) eval(binop.right, e);

                switch (binop.op) {
                    case PLUS:
                        return new Value.IntValue(left.val + right.val);
                    case MINUS:
                        return new Value.IntValue(left.val - right.val);
                    case TIMES:
                        return new Value.IntValue(left.val * right.val);
                    case DIV:
                        return new Value.IntValue(left.val / right.val);
                }
            }
            case "LetExpression": {
                Expression.LetExpression let = (Expression.LetExpression) c;

                Value val = eval(let.value, e);

//                Map<String, Value> newV = new HashMap<>();
//                newV.putAll(e);
//                newV.put(let.variableName.theName, val);

                Environment newE = e.bind(let.variableName, val);

                return eval(let.body, newE);
            }
            case "VariableExpression": {
                Expression.VariableExpression v = (Expression.VariableExpression) c;

                return e.lookup(v.variable);

//                if (e.containsKey(v.variable.theName)) {
//                    return e.get(v.variable.theName);
//                }
//
//                throw new NoSuchElementException();
            }
            case "EqExpression": {
                Expression.EqExpression eq = (Expression.EqExpression) c;

                Value.IntValue left  = (Value.IntValue) eval(eq.left, e);
                Value.IntValue right = (Value.IntValue) eval(eq.right, e);

                boolean result = (left.val == right.val);

                return new Value.BoolValue(result);
            }
            case "IfExpression": {
                Expression.IfExpression ife = (Expression.IfExpression) c;

                Value.BoolValue cond = (Value.BoolValue) eval(ife.cond, e);

                if (cond.val)
                    return eval(ife.thenSide, e);
                else
                    return eval(ife.elseSide, e);
            }
//            case "FunctionDeclExpression": {
//                Expression.FunctionDeclExpression d = (Expression.FunctionDeclExpression) c;
//
//                Function<Value[], Value> javaFunction = (actualValues -> {
//                    Environment envThatKnowsTheArguments = e;
//
//                    for (int i = 0 ; i < actualValues.length ; i++) {
//                        envThatKnowsTheArguments = envThatKnowsTheArguments.bind(d.formalArguments[i], actualValues[i]);
//                    }
//
//                    return eval(d.functionBody, envThatKnowsTheArguments);
//                });
//
//                knownFunctions.put(d.name.theName, javaFunction);
//
//                return eval(d.scope, e);
//            }
//            case "FunctionCallExpression": {
//                Expression.FunctionCallExpression call = (Expression.FunctionCallExpression) c;
//
//                Function<Value[], Value> javaFunction = knownFunctions.get(call.name.theName);
//
//                Value[] actualValues = new Value[call.actualArguments.length];
//
//                for (int i = 0 ; i < actualValues.length ; i++) {
//                    actualValues[i] = eval(call.actualArguments[i], e);
//                }
//
//                return javaFunction.apply(actualValues);
//            }
            case "FirstClassFunctionDeclExpression": {
                Expression.FirstClassFunctionDeclExpression d = (Expression.FirstClassFunctionDeclExpression) c;

                Value.Closure cc = new Value.Closure(e, d.formalArguments, d.functionBody);

                cc.capturedEnvironment = cc.capturedEnvironment.bind(d.name, cc);

                return cc;

//                Function<Value[], Value> javaFunction = (actualValues -> {
//                    Environment envThatKnowsTheArguments = e;
//
//                    for (int i = 0 ; i < actualValues.length ; i++) {
//                        envThatKnowsTheArguments = envThatKnowsTheArguments.bind(d.formalArguments[i], actualValues[i]);
//                    }
//
//                    return eval(d.functionBody, envThatKnowsTheArguments, knownFunctions);
//                });
//
//                return new Value.FunctionValue(javaFunction);
            }
            case "LambdaDeclExpression": {
                Expression.LambdaDeclExpression l = (Expression.LambdaDeclExpression) c;

                Value.Closure cc = new Value.Closure(e, l.formalArguments, l.functionBody);

                return cc;
            }
            case "FirstClassFunctionCallExpression": {
                Expression.FirstClassFunctionCallExpression call = (Expression.FirstClassFunctionCallExpression) c;

                Value.Closure cc = (Value.Closure) eval(call.functionToBeCalled, e);

                Value[] actualValues = new Value[call.actualArguments.length];

                for (int i = 0 ; i < actualValues.length ; i++) {
                    actualValues[i] = eval(call.actualArguments[i], e);
                }

                return apply(cc, actualValues);
            }

            case "IncExpression": {
            }
            case "SeqStatement": {
            }
            case "SetStatement": {
            }
            case "NeqExpression": {
            }
            case "WhileStatement": {
            }
            case "LoopStatement": {
            }
            case "ReturnStatement": {
            }
            default:
                throw new Error("I don't know the expression: " + c.getClass().getSimpleName());
        }
    }

    public Value apply(Value.Closure c, Value[] actualValues) {
        Environment envThatKnowsTheArguments = c.capturedEnvironment;

        for (int i = 0 ; i < actualValues.length ; i++) {
            envThatKnowsTheArguments = envThatKnowsTheArguments.bind(c.formalArguments[i], actualValues[i]);
        }

        try {
            return eval(c.functionBody, envThatKnowsTheArguments);
        } catch (Statement.ReturnException e) {
            return e.ret;
        }
    }
}