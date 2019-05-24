package hawkcompiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JTextArea;

import lexer.Lexer;
import parser.Parser;

public class HawkCompiler {
	
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File(args[0])));
		Lexer lexer = new Lexer();
		Parser parser = new Parser(lexer, null);
		parser.start();
	}
	
	public void compile(String filename, JTextArea terminal) throws IOException {
		
		System.setIn(new FileInputStream(new File(filename)));
		Lexer lexer = new Lexer();
		Parser parser = new Parser(lexer, terminal);
//		terminal.append("You called?\n");
		parser.start();
	}
}
