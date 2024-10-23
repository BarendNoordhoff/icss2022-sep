package nl.han.ica.icss.parser;// Generated from C:/Users/baren/Documents/School/ASD/APP/ICSS/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ICSSParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ICSSParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variable_value}.
	 * @param ctx the parse tree
	 */
	void enterVariable_value(ICSSParser.Variable_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variable_value}.
	 * @param ctx the parse tree
	 */
	void exitVariable_value(ICSSParser.Variable_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#tag_selector}.
	 * @param ctx the parse tree
	 */
	void enterTag_selector(ICSSParser.Tag_selectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#tag_selector}.
	 * @param ctx the parse tree
	 */
	void exitTag_selector(ICSSParser.Tag_selectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(ICSSParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(ICSSParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variable_assignment}.
	 * @param ctx the parse tree
	 */
	void enterVariable_assignment(ICSSParser.Variable_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variable_assignment}.
	 * @param ctx the parse tree
	 */
	void exitVariable_assignment(ICSSParser.Variable_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(ICSSParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(ICSSParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#element_assignment}.
	 * @param ctx the parse tree
	 */
	void enterElement_assignment(ICSSParser.Element_assignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#element_assignment}.
	 * @param ctx the parse tree
	 */
	void exitElement_assignment(ICSSParser.Element_assignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylerule}.
	 * @param ctx the parse tree
	 */
	void enterStylerule(ICSSParser.StyleruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylerule}.
	 * @param ctx the parse tree
	 */
	void exitStylerule(ICSSParser.StyleruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#body}.
	 * @param ctx the parse tree
	 */
	void enterBody(ICSSParser.BodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#body}.
	 * @param ctx the parse tree
	 */
	void exitBody(ICSSParser.BodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterEquation(ICSSParser.EquationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitEquation(ICSSParser.EquationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code var_val}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVar_val(ICSSParser.Var_valContext ctx);
	/**
	 * Exit a parse tree produced by the {@code var_val}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVar_val(ICSSParser.Var_valContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subtraction}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubtraction(ICSSParser.SubtractionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subtraction}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubtraction(ICSSParser.SubtractionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiply}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiply(ICSSParser.MultiplyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiply}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiply(ICSSParser.MultiplyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addition}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddition(ICSSParser.AdditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addition}
	 * labeled alternative in {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddition(ICSSParser.AdditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void enterIf_statement(ICSSParser.If_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#if_statement}.
	 * @param ctx the parse tree
	 */
	void exitIf_statement(ICSSParser.If_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#else_statement}.
	 * @param ctx the parse tree
	 */
	void enterElse_statement(ICSSParser.Else_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#else_statement}.
	 * @param ctx the parse tree
	 */
	void exitElse_statement(ICSSParser.Else_statementContext ctx);
}