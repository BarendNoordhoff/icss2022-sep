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

//		goes trough the body of the AST tree.
		for (ASTNode child : ast.root.getChildren()) {
			if (child instanceof VariableAssignment)
				saveVariable((VariableAssignment) child);
			else if (child instanceof Stylerule)
				css += generateStylerule((Stylerule) child);
		}

        return css;
	}

	void saveVariable(VariableAssignment variableAssignment) {
		String value = "";
		Expression expression = variableAssignment.expression;
		ExpressionType expressionType = ExpressionType.UNDEFINED;

//		checks what kind of literal it is and then stores the expression type and value of the assignment.
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

//		get the variable name.
		String id = variableAssignment.name.name;

//		Store the variable typen and expression.
		variableValues.put(id, value);
		variableType.put(id, expressionType);
	}

	String generateStylerule(Stylerule stylerule) {
		String sr = "";


		for (int i = 0; i < stylerule.selectors.size(); i++) {
			String tag = "";

			if (stylerule.selectors.get(i) instanceof IdSelector)
				tag = ((IdSelector) stylerule.selectors.get(i)).id;
			else if (stylerule.selectors.get(i) instanceof ClassSelector)
				tag = ((ClassSelector) stylerule.selectors.get(i)).cls;
			else
				tag = ((TagSelector) stylerule.selectors.get(i)).tag;

//			my grammar doesn't support it but just in case there are multiple selectors this makes sure that they are seperated
//			with a comma, and that the selectors don't end with a comma.
			sr += tag;
			if (i == stylerule.selectors.size())
				 sr += ", ";
		}

		sr += " { " + System.lineSeparator();

		for (ASTNode child : stylerule.body) {
			if (child instanceof IfClause)
				child.setError("it seems you forgot to transform the tree before generating");
			else if (child instanceof Declaration)
				sr += "\t" + generateDeclaration((Declaration) child);
		}

//		two line separators to make sure there is some space between style rules.
//		makes the final result a bit more readable.
		sr += " } " + System.lineSeparator() + System.lineSeparator();
		return sr;
	}

	String generateDeclaration(Declaration declaration) {
//		get the name of the property (width, height etc.).
		String prop = declaration.property.name;
		String expres = "";

//		gets value of a literal and appends a suffix if necessary.
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

//	uses the stored expression type of a given variable to see what the suffix should be in the generated CSS.
//	Using this method pixels can be displayed with 'px' at the end and percentages with '%' at the end.
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
