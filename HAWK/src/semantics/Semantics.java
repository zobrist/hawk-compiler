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
	
	public Semantics(JTextArea terminal) {
		
		symbolTable = new SymbolTable();
		this.terminal = terminal;
	}
	
	public SymbolTable getSymbolTable() {
		
		return symbolTable;
	}
	
	private void error(String s, Lexer lexer) {
		if(terminal != null) {
			terminal.append("Semantics error in line " + lexer.line + " : " + s + "\n");
		}
	}
	
	public void duplicateCheck(String name, Lexer lexer) {
		
//		System.out.println( "[" + symbolTable.isVariableAlreadyPresent(name) + "] ");
		if(symbolTable.isVariableAlreadyPresent(name)) {
			error("Variable duplication error.", lexer);
		}
	}
	
	public void existenceCheck(Token look, Lexer lexer) {
		if(!symbolTable.isVariableAlreadyPresent(look.lexeme)) {
			error("Variable undeclared error.", lexer);
		} else {
			initializationCheck(symbolTable.getValue(look.lexeme), lexer);
		}
	}
	
	/**
	 * for now, only works on simple initializations
	 * e.g.
	 * 		int i = 0;
	 */
	public void initializationCheck(Token look, Lexer lexer) {
		
		if (look.tag == ';') {
			for (int i = 2; i < initializationChecker.size(); i++) {
				System.out.println(" |" + initializationChecker.get(0).tag + " " + initializationChecker.get(1).lexeme + " " + initializationChecker.get(i).tag + "| ");
//				error(" |" + initializationChecker.get(0).lexeme + " " + initializationChecker.get(1).lexeme + " " + initializationChecker.get(i).lexeme + "| ", lexer);
				if (initializationChecker.get(0).tag == initializationChecker.get(i).tag
						|| ((initializationChecker.get(i) instanceof Num) && (initializationChecker.get(0).tag == Tag.NUM))
						|| ((initializationChecker.get(i) instanceof Real) && (initializationChecker.get(0).tag == Tag.REAL))) {
					symbolTable.addToSymbolTable(initializationChecker.get(1).lexeme, initializationChecker.get(i), "not yet");
				} else {
					error("Type cast error", lexer);
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
}
