package nl.han.ica.gen;// Generated from C:/Users/baren/Documents/School/ASD/APP/ICSS/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.1
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
	 * Enter a parse tree produced by {@link ICSSParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ICSSParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ICSSParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ICSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ICSSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(ICSSParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(ICSSParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(ICSSParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(ICSSParser.FunctionContext ctx);
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
	 * Enter a parse tree produced by {@link ICSSParser#conditon}.
	 * @param ctx the parse tree
	 */
	void enterConditon(ICSSParser.ConditonContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#conditon}.
	 * @param ctx the parse tree
	 */
	void exitConditon(ICSSParser.ConditonContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#else}.
	 * @param ctx the parse tree
	 */
	void enterElse(ICSSParser.ElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#else}.
	 * @param ctx the parse tree
	 */
	void exitElse(ICSSParser.ElseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(ICSSParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(ICSSParser.PropertyContext ctx);
}