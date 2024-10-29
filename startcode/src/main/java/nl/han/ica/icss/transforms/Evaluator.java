package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.VariableManager;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Transform {

    IHANLinkedList<HashMap<String, Literal>> variableValues;
    VariableManager<String, Literal> variableManager;

    OperationsEvaluator operationsEvaluator;

    public Evaluator() {
    }

    @Override
    public void apply(AST ast) {
        variableValues = new HANLinkedList<>();
        variableManager = new VariableManager<>();
        variableManager.setVariableTypes(variableValues);
        operationsEvaluator = new OperationsEvaluator(variableManager);
        transformTree(ast);
    }

    void transformTree(AST ast) {
        ArrayList<ASTNode> newBody = new ArrayList<>();

        transformStylesheet(ast.root, newBody);

        ast.root = new Stylesheet(newBody);
    }

    void transformStylesheet(ASTNode astNode, ArrayList<ASTNode> newBody) {
        variableManager.add();

        for (ASTNode child : astNode.getChildren()) {
            if (child instanceof Stylerule) {
                transformStylerule((Stylerule) child);
                newBody.add(child);
            } else if (child instanceof VariableAssignment) {
                handleVariableAssignment((VariableAssignment) child);
            }
        }

        variableManager.delete();
    }

    void transformStylerule(Stylerule stylerule) {
        ArrayList<ASTNode> toAdd = new ArrayList<>();

        variableManager.add();

        for (ASTNode child : stylerule.body) {
            transformBody(child, toAdd);
        }

        variableManager.delete();

        stylerule.body = toAdd;
    }

    void transformBody(ASTNode astNode, ArrayList<ASTNode> body) {
        if (astNode instanceof VariableAssignment) {
            handleVariableAssignment((VariableAssignment) astNode);
        }

        if (astNode instanceof Declaration) {
            transformDeclaration((Declaration) astNode);
            body.add(astNode);
        }

        if (astNode instanceof IfClause) {
            IfClause clause = (IfClause) astNode;
            boolean condition = checkIfConditionIsTrue(clause.conditionalExpression);

            if (condition) {
                if (clause.elseClause != null) {
                    clause.elseClause.body = new ArrayList<>();
                }
            } else {
                if (clause.elseClause == null) {
                    clause.body = new ArrayList<>();
                } else {
                    clause.body = clause.elseClause.body;
                    clause.elseClause.body = new ArrayList<>();
                }
            }

            transformIfClause(clause, body);
        }
    }

    void transformIfClause(IfClause clause, ArrayList<ASTNode> body) {
        variableManager.add();

        for (ASTNode child : clause.getChildren()) {
            this.transformBody(child, body);
        }

        variableManager.delete();
    }

    boolean checkIfConditionIsTrue(Expression expression) {
        Literal literal;

        if (expression instanceof VariableReference) {
            literal = variableManager.get(((VariableReference) expression).name);
        } else {
            literal = (Literal) expression;
        }

        return ((BoolLiteral) literal).value;
    }

    void handleVariableAssignment(VariableAssignment variableAssignment) {
        Literal literal;

        if (variableAssignment.expression instanceof PixelLiteral) {
            literal = (PixelLiteral) variableAssignment.expression;
        } else if (variableAssignment.expression instanceof PercentageLiteral) {
            literal = (PercentageLiteral) variableAssignment.expression;
        } else if (variableAssignment.expression instanceof ScalarLiteral){
            literal = (ScalarLiteral) variableAssignment.expression;
        } else if (variableAssignment.expression instanceof BoolLiteral){
            literal = (BoolLiteral) variableAssignment.expression;
        } else if (variableAssignment.expression instanceof ColorLiteral) {
            literal = (ColorLiteral) variableAssignment.expression;
        } else if (variableAssignment.expression instanceof Operation){
            literal = operationsEvaluator.transformOperation((Operation) variableAssignment.expression);
            System.out.println(literal);
        } else {
            literal = variableManager.get(variableAssignment.name.name);
        }

        variableManager.addValue(variableAssignment.name.name, literal);
    }

    void transformDeclaration(Declaration declaration) {
        Expression value = declaration.expression;

        if (value instanceof Operation)
            declaration.expression = operationsEvaluator.transformOperation((Operation) value);
        else if (value instanceof VariableReference)
            declaration.expression = variableManager.get(((VariableReference) value).name);
    }
}
