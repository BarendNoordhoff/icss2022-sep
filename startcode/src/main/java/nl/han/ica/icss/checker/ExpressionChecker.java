package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.VariableManager;
import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

public class ExpressionChecker {
    VariableManager<String, ExpressionType> variableManager;

    public ExpressionChecker(VariableManager<String, ExpressionType> variableManager) {
        this.variableManager = variableManager;
    }

    ExpressionType getExpression(ASTNode astNode) {
        if (astNode instanceof VariableReference) {
            return getExpressionFromVariable((VariableReference) astNode);
        } else if (astNode instanceof Operation) {
            return getExpressionFromOperation((Operation) astNode);
        } else {
            if (astNode instanceof BoolLiteral) {
                return ExpressionType.BOOL;
            } else if (astNode instanceof ColorLiteral) {
                return ExpressionType.COLOR;
            } else if (astNode instanceof PercentageLiteral) {
                return ExpressionType.PERCENTAGE;
            } else if (astNode instanceof PixelLiteral) {
                return ExpressionType.PIXEL;
            } else if (astNode instanceof ScalarLiteral) {
                return ExpressionType.SCALAR;
            }
        }

        return ExpressionType.UNDEFINED;
    }

    ExpressionType getExpressionFromOperation(Operation operator) {
        ExpressionType left;
        ExpressionType right;

        Expression exprLeft = operator.lhs;
        Expression exprRight = operator.rhs;

        if ((operator.lhs instanceof Operation)) {
            left = getExpressionFromOperation((Operation) operator.lhs);
        } else {
            left = getExpression(exprLeft);
        }

        if ((operator.rhs instanceof Operation)) {
            right = getExpressionFromOperation((Operation) operator.rhs);
        } else {
            right = getExpression(exprRight);
        }

        if (left == ExpressionType.BOOL || left == ExpressionType.COLOR || right == ExpressionType.BOOL || right == ExpressionType.COLOR) {
            operator.setError("bool's and colors arent allowed in operations");
            return ExpressionType.UNDEFINED;
        }

        if (operator instanceof MultiplyOperation) {
            if (left != ExpressionType.SCALAR && right != ExpressionType.SCALAR) {
                operator.setError("in an multiplication operation one of the sides need to be a scalar");
                return ExpressionType.UNDEFINED;
            } else {
                return left == ExpressionType.SCALAR ? right : left;
            }
        } else if ((operator instanceof AddOperation || operator instanceof SubtractOperation) && left != right) {
            operator.setError("in a addition or subtraction operation both of the sides need to be of the same type");
            return ExpressionType.UNDEFINED;
        }
        System.out.println(left + " : " + right);
        return left;
    }

    ExpressionType getExpressionFromVariable(VariableReference variableReference) {
        ExpressionType expressionType = variableManager.get(variableReference.name);

        if (expressionType == ExpressionType.UNDEFINED || expressionType == null) {
            variableReference.setError("Variable " + variableReference.name + " has not been declared yet, maybe its not in this scope?");
        }

        return expressionType;
    }
}
