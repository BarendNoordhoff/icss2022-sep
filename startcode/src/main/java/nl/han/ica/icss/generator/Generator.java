package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class Generator {

	HashMap<String, String> variableValues;
	HashMap<String, ExpressionType> variableType;

	public String generate(AST ast) {
		variableValues = new HashMap<>();
		variableType = new HashMap<>();

		String css = "";
		for (ASTNode child : ast.root.getChildren()) {
			if (child instanceof VariableAssignment)
				saveVariable((VariableAssignment) child);
			else if (child instanceof Stylerule)
				css += generateStylerule((Stylerule) child);
		}

		System.out.println(variableValues.toString());
        return css;
	}

	void saveVariable(VariableAssignment variableAssignment) {
		String value = "";
		Expression expression = variableAssignment.expression;
		ExpressionType expressionType = ExpressionType.UNDEFINED;

		if (expression instanceof ColorLiteral) {
			value = ((ColorLiteral) expression).value;
			expressionType = ExpressionType.COLOR;
		} else if (expression instanceof PixelLiteral) {
			value = String.valueOf(((PixelLiteral) expression).value);
			expressionType = ExpressionType.PIXEL;
		} else if (expression instanceof PercentageLiteral) {
			value = String.valueOf(((PercentageLiteral) expression).value);
			expressionType = ExpressionType.PERCENTAGE;
		} else if (expression instanceof ScalarLiteral) {
			value = String.valueOf(((ScalarLiteral) expression).value);
			expressionType = ExpressionType.SCALAR;
		} else if (expression instanceof BoolLiteral) {
			value = String.valueOf(((BoolLiteral) expression).value);
			expressionType = ExpressionType.BOOL;
		}

		String id = variableAssignment.name.name;

		variableValues.put(id, value);
		variableType.put(id, expressionType);
	}

	String generateStylerule(Stylerule stylerule) {
		String sr = "";
		for (Selector selector : stylerule.selectors) {
			String tag = "";

			if (selector instanceof IdSelector)
				tag = ((IdSelector) selector).id;
			else if (selector instanceof ClassSelector)
				tag = ((ClassSelector) selector).cls;
			else
				tag = ((TagSelector) selector).tag;

			sr += tag + " { " + System.lineSeparator();
		}

		for (ASTNode child : stylerule.body) {
			if (child instanceof IfClause)
				child.setError("it seems you forgot to transform the tree before generating");
			else if (child instanceof Declaration)
				sr += "\t" + generateDeclaration((Declaration) child);
		}

//		two line separators to make sure there is some space between classes.
//		makes the final result a bit more readable in my opinion;
		sr += " } " + System.lineSeparator() + System.lineSeparator();
		return sr;
	}

	String generateDeclaration(Declaration declaration) {
		String prop = declaration.property.name;
		String expres = "";

		if (declaration.expression instanceof ColorLiteral) {
			expres = ((ColorLiteral) declaration.expression).value;
		} else if (declaration.expression instanceof PercentageLiteral) {
			String percVal = String.valueOf(((PercentageLiteral) declaration.expression).value);
			expres = percVal + "%";
		} else if (declaration.expression instanceof PixelLiteral) {
			String pixVal = String.valueOf(((PixelLiteral) declaration.expression).value);
			expres = pixVal + "px";
		} else if (declaration.expression instanceof ScalarLiteral) {
			expres = String.valueOf(((ScalarLiteral) declaration.expression).value);
		} else if (declaration.expression instanceof VariableReference) {
			expres = getValueFromVariable(((VariableReference) declaration.expression).name);
		}

		return prop + ": " + expres + ";" + System.lineSeparator();
	}

	String getValueFromVariable(String key) {
		String varValue = variableValues.get(key);
		String varSuffix = "";

		switch (variableType.get(key)) {
			case PIXEL:
				varSuffix = "px";
				break;
			case PERCENTAGE:
				varSuffix = "%";
				break;
		}

		return varValue + varSuffix;
	}
}
