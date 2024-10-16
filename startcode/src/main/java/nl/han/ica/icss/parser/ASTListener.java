package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;


/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
	}

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
		Stylesheet stylesheet = new Stylesheet();
		currentContainer.push(stylesheet);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		ast.root = (Stylesheet) currentContainer.pop();
	}

	@Override
	public void enterStylerule(ICSSParser.StyleruleContext ctx) {
		Stylerule stylerule = new Stylerule();
		currentContainer.push(stylerule);
	}

	@Override
	public void exitStylerule(ICSSParser.StyleruleContext ctx) {
		Stylerule stylerule = (Stylerule) currentContainer.pop();
		currentContainer.peek().addChild(stylerule);
	}

	@Override
	public void enterVariable_assignment(ICSSParser.Variable_assignmentContext ctx) {
		VariableAssignment variableAssignment = new VariableAssignment();
		currentContainer.push(variableAssignment);
	}

	@Override
	public void exitVariable_assignment(ICSSParser.Variable_assignmentContext ctx) {
		VariableAssignment variableAssignment = (VariableAssignment) currentContainer.pop();
		currentContainer.peek().addChild(variableAssignment);
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		VariableReference variableReference = new VariableReference(ctx.getText());
		currentContainer.push(variableReference);
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		VariableReference variableReference = (VariableReference) currentContainer.pop();
		currentContainer.peek().addChild(variableReference);
	}

	@Override
	public void enterTag_selector(ICSSParser.Tag_selectorContext ctx) {
		Selector selector;
		String value = ctx.getText();
		if (value.startsWith("#")) {
			selector = new IdSelector(value);
		} else if (value.startsWith(".")) {
			selector = new ClassSelector(value);
		} else {
			selector = new TagSelector(value);
		}
		currentContainer.push(selector);
	}

	@Override
	public void exitTag_selector(ICSSParser.Tag_selectorContext ctx) {
		Selector selector;
		String value = ctx.getText();
		if (value.startsWith("#")) {
			selector = (IdSelector) currentContainer.pop();
		} else if (value.startsWith(".")) {
			selector = (ClassSelector) currentContainer.pop();
		} else {
			selector = (TagSelector) currentContainer.pop();
		}
		currentContainer.peek().addChild(selector);
	}

	@Override
	public void enterElement_assignment(ICSSParser.Element_assignmentContext ctx) {
		Declaration declaration = new Declaration();
		currentContainer.push(declaration);
	}

	@Override
	public void exitElement_assignment(ICSSParser.Element_assignmentContext ctx) {
		Declaration declaration = (Declaration) currentContainer.pop();
		currentContainer.peek().addChild(declaration);
	}

	@Override
	public void enterElement(ICSSParser.ElementContext ctx) {
		PropertyName propertyName = new PropertyName(ctx.getText());
		currentContainer.push(propertyName);
	}

	@Override
	public void exitElement(ICSSParser.ElementContext ctx) {
		PropertyName propertyName = (PropertyName) currentContainer.pop();
		currentContainer.peek().addChild(propertyName);
	}

	@Override
	public void enterVariable_value(ICSSParser.Variable_valueContext ctx) {
		ASTNode literal;
		String value = ctx.getText();
		if (value.startsWith("#")) {
			literal = new ColorLiteral(value);
		} else if (value.endsWith("px")) {
			literal = new PixelLiteral(value);
		} else if (value.equals("TRUE") | value.equals("FALSE")) {
			literal = new BoolLiteral(value);
		} else if (value.endsWith("%")) {
			literal = new PercentageLiteral(value);
		} else if (isNumeric(value)) {
			literal = new ScalarLiteral(value);
		} else {
			literal = new VariableReference(value);
		}

		currentContainer.push(literal);
	}

	@Override
	public void exitVariable_value(ICSSParser.Variable_valueContext ctx) {
		ASTNode literal;
		String value = ctx.getText();
		if (value.startsWith("#")) {
			literal = (ColorLiteral) currentContainer.pop();
		} else if (value.endsWith("px")) {
			literal = (PixelLiteral) currentContainer.pop();
		} else if (value.equals("TRUE") | value.equals("FALSE")) {
			literal = (BoolLiteral) currentContainer.pop();
		} else if (value.endsWith("%")) {
			literal = (PercentageLiteral) currentContainer.pop();
		} else if (isNumeric(value)) {
			literal = (ScalarLiteral) currentContainer.pop();
		} else {
			literal = (VariableReference) currentContainer.pop();
		}

		currentContainer.peek().addChild(literal);
	}

	boolean isNumeric(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void enterEquation(ICSSParser.EquationContext ctx) {
		AddOperation addOperation = new AddOperation();
		currentContainer.push(addOperation);
	}

	@Override
	public void exitEquation(ICSSParser.EquationContext ctx) {
		AddOperation addOperation = (AddOperation) currentContainer.pop();
		currentContainer.peek().addChild(addOperation);
	}

	@Override
	public void enterMultiply(ICSSParser.MultiplyContext ctx) {
		MultiplyOperation multiplyOperation = new MultiplyOperation();
		currentContainer.push(multiplyOperation);
	}

	@Override
	public void exitMultiply(ICSSParser.MultiplyContext ctx) {
		MultiplyOperation multiplyOperation = (MultiplyOperation) currentContainer.pop();
		currentContainer.peek().addChild(multiplyOperation);
	}

	@Override
	public void enterIf_statement(ICSSParser.If_statementContext ctx) {
		IfClause ifClause = new IfClause();
		currentContainer.push(ifClause);
	}

	@Override
	public void exitIf_statement(ICSSParser.If_statementContext ctx) {
		IfClause ifClause = (IfClause) currentContainer.pop();
		currentContainer.peek().addChild(ifClause);
	}

	@Override
	public void enterElse_statement(ICSSParser.Else_statementContext ctx) {
		ElseClause elseClause = new ElseClause();
		currentContainer.push(elseClause);
 	}

	@Override
	public void exitElse_statement(ICSSParser.Else_statementContext ctx) {
		ElseClause elseClause = (ElseClause) currentContainer.pop();
		currentContainer.peek().addChild(elseClause);
	}

    public AST getAST() {
        return ast;
    }
}