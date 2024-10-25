package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.VariableManager;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Checker {

    private IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;
    VariableManager<String, ExpressionType> variableManager = new VariableManager<>();

    ExpressionChecker expressionChecker;

    public void check(AST ast) {
        variableTypes = new HANLinkedList<>();
        variableManager.setVariableTypes(variableTypes);
        expressionChecker = new ExpressionChecker(variableManager);

//        Add a scope for the variables that are being declared outside stylerules.
        variableManager.add();

//        go through the stylesheet itself.
        for (ASTNode child : ast.root.getChildren()) {
            if (child instanceof VariableAssignment)
                checkVariableAssignment(child);
            else if (child instanceof Stylerule)
                checkStylerule(child);
        }

//        Remove the previously made scope so that all the variables are deleted out of the memory.
        variableManager.delete();
    }

    public void checkStylerule(ASTNode astNode) {
//        make a scope for the variables that might be declared in this style rule.
        variableManager.add();

//        go through the body of the style rule.
        Stylerule stylerule = (Stylerule) astNode;
        for (ASTNode child : stylerule.body) {
            if (child instanceof Declaration)
                checkDeclaration((Declaration) child);
            else if (child instanceof IfClause)
                checkIfStatement((IfClause) child);
            else if (child instanceof VariableAssignment)
                checkVariableAssignment(child);
        }

//        delete the previously made scope.
        variableManager.delete();
    }

    public void checkDeclaration(Declaration declaration) {
//        Put the property of the declaration (width, height etc.) in a variable.
        PropertyName decProp = declaration.property;
//        Get the value of the declaration and the expression type of the value.
        ASTNode value = declaration.getChildren().get(1);
        ExpressionType expressionDec = expressionChecker.getExpression(value);

//        look at the property and see whether the expression type is what the type should be based on the property.
        switch (decProp.name) {
            case "background-color":
                if (expressionDec != ExpressionType.COLOR) {
                    declaration.setError("background-color needs to be a color");
                    return;
                }
                break;
            case "color":
                if (expressionDec != ExpressionType.COLOR) {
                    declaration.setError("color needs to be a color");
                    return;
                }
                break;
            case "width":
                if (!(expressionDec == ExpressionType.PIXEL || expressionDec == ExpressionType.PERCENTAGE)) {
                    declaration.setError("width needs to be defined as either pixels or percentages");
                    return;
                }
                break;
            case "height":
                if (!(expressionDec == ExpressionType.PIXEL || expressionDec == ExpressionType.PERCENTAGE)) {
                    declaration.setError("height needs to be defined as either pixels or percentages");
                    return;
                }
                break;
        }
    }

    void checkIfStatement(IfClause clause) {
//        get the expression type of the conditional expression.
        ExpressionType expressionType = expressionChecker.getExpression(clause.conditionalExpression);

//        Check whether the expressionType is a bool, since a condition should be a bool.
        if (expressionType != ExpressionType.BOOL) {
            clause.setError(clause.conditionalExpression.getNodeLabel() + "Cannot be used as a condition");
            return;
        }

//        Add a new scope since we are going into an if statement.
        variableManager.add();

        for (ASTNode child : clause.body) {
            if (child instanceof Declaration)
                checkDeclaration((Declaration) child);
            else if (child instanceof IfClause)
                checkIfStatement((IfClause) child);
            else if (child instanceof VariableAssignment)
                checkVariableAssignment(child);
        }

//        Delete the newly added scope so that the variables that were added get removed after the scope is left.
        variableManager.delete();
    }

    public void checkVariableAssignment(ASTNode astNode) {
        VariableAssignment var = (VariableAssignment) astNode;
        ASTNode value = astNode.getChildren().get(1);
        ExpressionType expressionValue = expressionChecker.getExpression(value);

//        if the compiler returns expression value as a undefined the variable doesn't have a valid value, which isn't allowed.
        if (expressionValue == ExpressionType.UNDEFINED) {
            astNode.setError("Variable " + var.name + " needs a value!");
            return;
        }

//        Makes it so that if a variable gets assigned with a different value, it will give an error.
        if (variableManager.get(var.name.name) != null && variableManager.get(var.name.name) != expressionValue) {
            astNode.setError("We recognize the variable " + var.name.name + " but it is not of type " + expressionValue);
            return;
        }

//        Store the variable in a variable (ironic).
        variableManager.addValue(var.name.name, expressionValue);
    }

}
