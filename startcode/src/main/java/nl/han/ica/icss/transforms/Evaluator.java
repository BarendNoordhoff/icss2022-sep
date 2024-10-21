package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.VariableManager;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.HashMap;

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;
    private VariableManager<String, Literal> variableManager;

    public Evaluator() {
    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        variableManager = new VariableManager<>();
        variableManager.setVariableTypes(variableValues);
        variableManager.add();

        for (ASTNode child : ast.root.getChildren()) {
            if (child instanceof Stylerule)
                transformStylerule((Stylerule) child);
            else if (child instanceof VariableAssignment)
                handleVariableAssignment((VariableAssignment) child);
        }
    }

    public void transformStylerule(Stylerule stylerule) {
        for (ASTNode child : stylerule.body) {
            if (child instanceof Declaration)
                transformDeclaration((Declaration) child);
            else if (child instanceof VariableAssignment)
                handleVariableAssignment((VariableAssignment) child);
//            else if (child instanceof IfClause)
//                transformIfClause((IfClause) child);
        }
    }

    public void handleVariableAssignment(VariableAssignment variableAssignment) {
        Literal literal;

        if (variableAssignment.expression instanceof PixelLiteral) {
            literal = (PixelLiteral) variableAssignment.expression;
        } else if (variableAssignment.expression instanceof PercentageLiteral) {
            literal = (PercentageLiteral) variableAssignment.expression;
        } else if (variableAssignment.expression instanceof ScalarLiteral){
            literal = (ScalarLiteral) variableAssignment.expression;
        } else {
            literal = variableManager.get(((VariableReference) variableAssignment.name).name);
        }

        variableManager.addValue(variableAssignment.name.name, literal);
    }

    public void transformDeclaration(Declaration declaration) {
        Expression value = declaration.expression;

        if (value instanceof Operation)
            declaration.expression = transformOperation((Operation) value);
    }

    public Literal transformOperation(Operation operation) {
        Literal left;
        Literal right;

        int leftValue = 0;
        int rightValue = 0;

        if (operation.lhs instanceof Operation) {
            left = transformOperation((Operation) operation.lhs);
        } else if (operation.lhs instanceof VariableReference) {
            left = variableManager.get(((VariableReference) operation.lhs).name);
        } else {
            left = (Literal) operation.lhs;
        }

        if (operation.rhs instanceof Operation) {
            right = transformOperation((Operation) operation.rhs);
        } else if (operation.rhs instanceof VariableReference) {
            right = variableManager.get(((VariableReference) operation.rhs).name);
        } else {
            right = (Literal) operation.rhs;
        }

        leftValue = extractValueFromLiteral(left);
        rightValue = extractValueFromLiteral(right);

        if (operation instanceof AddOperation) {
            return returnLiteral(left, leftValue + rightValue);
        } else if (operation instanceof SubtractOperation) {
            return returnLiteral(left, leftValue - rightValue);
        } else {
            return
                returnLiteral(left, leftValue * rightValue) instanceof ScalarLiteral
                ? returnLiteral(right, leftValue * rightValue)
                : returnLiteral(left, leftValue * rightValue);
        }
    }

    int extractValueFromLiteral(Literal literal) {
        if (literal instanceof PixelLiteral) {
            return ((PixelLiteral) literal).value;
        } else if (literal instanceof PercentageLiteral) {
            return ((PercentageLiteral) literal).value;
        } else {
            return ((ScalarLiteral) literal).value;
        }
    }

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
