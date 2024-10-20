package nl.han.ica.icss.parser;// Generated from C:/Users/baren/Documents/School/ASD/APP/ICSS/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ICSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ICSSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(ICSSParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#variable_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_value(ICSSParser.Variable_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#tag_selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTag_selector(ICSSParser.Tag_selectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(ICSSParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#variable_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_assignment(ICSSParser.Variable_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(ICSSParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#element_assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_assignment(ICSSParser.Element_assignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylerule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylerule(ICSSParser.StyleruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(ICSSParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#equation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquation(ICSSParser.EquationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#addition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddition(ICSSParser.AdditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#subtraction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtraction(ICSSParser.SubtractionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#multiply}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiply(ICSSParser.MultiplyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(ICSSParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#else_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_statement(ICSSParser.Else_statementContext ctx);
}