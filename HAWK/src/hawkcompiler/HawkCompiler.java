package hawkcompiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lexer.Lexer;
import parser.Parser;

public class HawkCompiler {
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream(new File(args[0])));
		Lexer lexer = new Lexer();
		Parser parser = new Parser(lexer);
		parser.start();
	}
}
