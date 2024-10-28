package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.VariableManager;
import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

public class OperationsEvaluator {
    VariableManager<String, Literal> variableManager;

    public OperationsEvaluator(VariableManager<String, Literal> variableManager) {
        this.variableManager = variableManager;
    }

    Literal transformOperation(Operation operation) {
//        Variables where the left and right operators are going to be put in.
        Literal left;
        Literal right;

//        Variables where the value of the left and right operators are going to be in.
        int leftValue;
        int rightValue;

//        Check whether the left operator is an operation, if so it calls this function recursively.
//        if it is a variable reference it retrieves the value from the variable.
        if (operation.lhs instanceof Operation) {
            left = transformOperation((Operation) operation.lhs);
        } else if (operation.lhs instanceof VariableReference) {
            left = variableManager.get(((VariableReference) operation.lhs).name);
        } else {
            left = (Literal) operation.lhs;
        }

//        Check whether the right operator is a operation, if so it calls this function recursively.
//        if it is a variable reference it retrieves the value from the variable.
        if (operation.rhs instanceof Operation) {
            right = transformOperation((Operation) operation.rhs);
        } else if (operation.rhs instanceof VariableReference) {
            right = variableManager.get(((VariableReference) operation.rhs).name);
        } else {
            right = (Literal) operation.rhs;
        }

//        get the values of the operations.
        leftValue = extractValueFromLiteral(left);
        rightValue = extractValueFromLiteral(right);

//
        if (operation instanceof AddOperation) {
            return returnLiteral(left, leftValue + rightValue);
        } else if (operation instanceof SubtractOperation) {
            return returnLiteral(left, leftValue - rightValue);
        } else {
            return
                    left instanceof ScalarLiteral
                            ? returnLiteral(right, leftValue * rightValue)
                            : returnLiteral(left, leftValue * rightValue);
        }
    }

//    Get the value from a literal, this function has to exist since the Class Literal doesn't contain the variable value.
    int extractValueFromLiteral(Literal literal) {
        if (literal instanceof PixelLiteral) {
            return ((PixelLiteral) literal).value;
        } else if (literal instanceof PercentageLiteral) {
            return ((PercentageLiteral) literal).value;
        } else {
            return ((ScalarLiteral) literal).value;
        }
    }

//    Makes an appropriate literal with a give value.
    Literal returnLiteral(Literal literal, int value) {
        if (literal instanceof PixelLiteral) {
            return new PixelLiteral(value);
        } else if (literal instanceof PercentageLiteral) {
            return new PercentageLiteral(value);
        } else {
            return new ScalarLiteral(value);
        }
    }
}
