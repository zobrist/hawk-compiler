package semantics;

import java.util.ArrayList;

import javax.swing.JTextArea;

import lexer.Lexer;
import lexer.Num;
import lexer.Real;
import lexer.Tag;
import lexer.Token;

public class Semantics {
	
	private SymbolTable symbolTable;
	private JTextArea terminal;
	
//	private Token[] initializationChecker = new Token[3];
	private ArrayList<Token> initializationChecker = new ArrayList<Token>();
//	private int initializationCheckerIndex = 0;
//	private String[] methodDeclarationHolder = new String[2];
	private int methodReturnType = 0;
	
	public Semantics(JTextArea terminal) {
		
		symbolTable = new SymbolTable();
		this.terminal = terminal;
	}
	
	public SymbolTable getSymbolTable() {
		
		return symbolTable;
	}
	
	private void error(String s) {
		if(terminal != null) {
			terminal.append("Semantics error in line " + Lexer.line + " : " + s + "\n");
		}
	}
	
	/**
	 * Check for variable duplication in the symbol table.
	 * @param name
	 * @param lexer
	 */
	public void duplicateCheck(String name) {
		
//		System.out.println( "[" + symbolTable.isVariableAlreadyPresent(name) + "] ");
		if(symbolTable.isVariableAlreadyPresent(name)) {
			error("Variable duplication error.");
		}
	}
	
	/**
	 * Check if the variable already exists in the symbol table.
	 * @param look
	 * @param lexer
	 */
	public void existenceCheck(Token look) {
		if(!symbolTable.isVariableAlreadyPresent(look.lexeme)) {
			error("Variable undeclared error.");
		} else {
			initializationCheck(symbolTable.getValue(look.lexeme));
		}
	}
	
	/**
	 * For type compatibility checking for initialization.
	 * For now, only works on simple initializations
	 * e.g.
	 * 		int i = 0;
	 * @param look
	 * @param lexer
	 */
	public void initializationCheck(Token look) {
		
		if (look.tag == ';') {
			for (int i = 2; i < initializationChecker.size(); i++) {
//				System.out.println(" |" + initializationChecker.get(0).tag + " " + initializationChecker.get(1).lexeme + " " + initializationChecker.get(i).tag + "| ");
//				error(" |" + initializationChecker.get(0).lexeme + " " + initializationChecker.get(1).lexeme + " " + initializationChecker.get(i).lexeme + "| ", lexer);
				if (initializationChecker.get(0).tag == initializationChecker.get(i).tag
						|| ((initializationChecker.get(i) instanceof Num) && (initializationChecker.get(0).tag == Tag.NUM))
						|| ((initializationChecker.get(i) instanceof Real) && (initializationChecker.get(0).tag == Tag.REAL))) {
					symbolTable.addToSymbolTable(initializationChecker.get(1).lexeme, initializationChecker.get(i), "not yet");
				} else {
					error("Type mismatch error.");
					break;
				}
			}
			initializationChecker.clear();
		} else {
			initializationChecker.add(look);
		}
		
		/*
		initializationChecker[initializationCheckerIndex] = look;
		initializationCheckerIndex++;
		
		if (initializationChecker[2] != null) {
			System.out.println(" |" + initializationChecker[0].tag + " " + initializationChecker[1].lexeme + " "  + (initializationChecker[2].tag) + "| ");
			if (initializationChecker[0].tag == initializationChecker[2].tag 
					|| ((initializationChecker[2] instanceof Num) && (initializationChecker[0].tag == Tag.NUM)) 
					|| ((initializationChecker[2] instanceof Real) && (initializationChecker[0].tag == Tag.REAL))) {
				symbolTable.addToSymbolTable(initializationChecker[1].lexeme, initializationChecker[2], "not yet");
			} else {
				error("Type cast error", lexer);
			}
			
			initializationChecker = new Token[3];
			initializationCheckerIndex = 0;
		}
		*/
	}
	
	/*public void clearArrayListTokenContents() {
		
		initializationChecker.clear();
	}*/
	
	/**
	 * Adds method name and its return type to the symbol table.
	 * @param look
	 * @param index
	 */
	public void methodDeclaration(Token look) {
		
		if (look.tag != Tag.ID) {
			methodReturnType = look.tag;
		} else {
			symbolTable.addToSymbolTableForMethods(look.lexeme, methodReturnType);
			methodReturnType = 0;
		}
		
		/*methodDeclarationHolder[index] = look.lexeme;
		if (index == 1) {
			symbolTable.addToSymbolTableForMethods(methodDeclarationHolder[1], methodDeclarationHolder[0], "method");
			methodDeclarationHolder = new String[2];
		}*/
	}
	
	/**
	 * Checks if the value being returned by a method is the same as the method return type. 
	 * @param look
	 * @param lexer
	 */
	public void returnTypeCheck(Token look, int methodTypeTag) {
		
//		error(methodTypeTag + " " + look.tag);
//		System.out.println(methodTypeTag + " " + look.tag);
		if (look.tag != methodTypeTag) {
			error("Return type mismatch error.");
		}
	}
}
