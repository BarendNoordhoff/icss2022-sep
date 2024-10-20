package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.VariableManager;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    VariableManager<String, ExpressionType> variableManager = new VariableManager<>();

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        variableManager.setVariableTypes(variableTypes);
        variableManager.add();

        for (ASTNode child : ast.root.getChildren()) {
            if (child instanceof VariableAssignment)
                checkVariableAssignment(child);
            else if (child instanceof Stylerule)
                checkStylerule(child);
        }

        variableManager.delete();
    }

    public void checkStylerule(ASTNode astNode) {
        variableManager.add();
        Stylerule stylerule = (Stylerule) astNode;
        for (ASTNode child : stylerule.body) {
            if (child instanceof Declaration)
                checkDeclaration((Declaration) child);
            else if (child instanceof IfClause)
                checkIfStatement((IfClause) child);
            else if (child instanceof VariableAssignment)
                checkVariableAssignment(child);
        }
        variableManager.delete();
    }

    public void checkDeclaration(Declaration declaration) {
        PropertyName decProp = declaration.property;
        ASTNode value = declaration.getChildren().get(1);
        ExpressionType expressionDec = getExpression(value);

        switch (decProp.name) {
            case "background-color":
                if (expressionDec != ExpressionType.COLOR) {
                    declaration.setError("background-color needs to be a color");
                }
                break;
            case "color":
                if (expressionDec != ExpressionType.COLOR) {
                    declaration.setError("color needs to be a color");
                }
                break;
            case "width":
                if (!(expressionDec == ExpressionType.PIXEL || expressionDec == ExpressionType.PERCENTAGE)) {
                    declaration.setError("width needs to be defined as either pixels or percentages");
                }
                break;
            case "height":
                if (!(expressionDec == ExpressionType.PIXEL || expressionDec == ExpressionType.PERCENTAGE)) {
                    declaration.setError("height needs to be defined as either pixels or percentages");
                }
                break;
        }
    }

    void checkIfStatement(IfClause clause) {
        ExpressionType expressionType = getExpression(clause.conditionalExpression);

        if (expressionType != ExpressionType.BOOL) {
            clause.setError(clause.conditionalExpression.getNodeLabel() + "Cannot be used as a condition");
        }

        variableManager.add();

        for (ASTNode child : clause.body) {
            if (child instanceof Declaration)
                checkDeclaration((Declaration) child);
            else if (child instanceof IfClause)
                checkIfStatement((IfClause) child);
            else if (child instanceof VariableAssignment)
                checkVariableAssignment(child);
        }

        variableManager.delete();
    }

    public ExpressionType getExpression(ASTNode astNode) {
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
                operator.setError("in an multiplication operation either of the sides need to be a scalar");
                return ExpressionType.UNDEFINED;
            } else {
                return left == ExpressionType.SCALAR ? right : left;
            }
        } else if ((operator instanceof AddOperation || operator instanceof SubtractOperation) && left != right) {
            operator.setError("in a addition or subtraction operation both of the sides need to be of the same type");
            return ExpressionType.UNDEFINED;
        }

        return left;
    }

    ExpressionType getExpressionFromVariable(VariableReference variableReference) {
        ExpressionType expressionType = variableManager.get(variableReference.name);

        if (expressionType == ExpressionType.UNDEFINED || expressionType == null) {
            variableReference.setError("Variable " + variableReference.name + " has not been declared yet, maybe its not in this scope?");
        }

        return expressionType;
    }

    public void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment var = (VariableAssignment) astNode;
        ASTNode value = astNode.getChildren().get(1);
        ExpressionType expressionValue = getExpression(value);

//        put the variable in a variable (ironic)
        HashMap<String, ExpressionType> newVariable = new HashMap<>();
        newVariable.put(var.name.name, expressionValue);

        if (expressionValue == ExpressionType.UNDEFINED) {
            astNode.setError("Variable " + var.name + " needs a value!");
        }

        if (!checkIfVariableExistsWithOtherType(newVariable)) {
            astNode.setError("We recognize the variable " + var.name.name + " but it is not of type " + expressionValue);
        }

//        There are no errors store the variable
        variableManager.addValue(var.name.name, expressionValue);
    }

    boolean checkIfVariableExistsWithOtherType(HashMap<String, ExpressionType> var) {
        for (String key : var.keySet()) {
            ExpressionType expectedExpression = var.get(key);

            for (int i = 0; i < variableTypes.getSize(); i++) {
                if (variableTypes.get(i) != null) {
                    if (variableTypes.get(i).containsKey(key)) {
                        if (variableTypes.get(i).get(key).equals(expectedExpression)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
