package lexer;

public class Token {
	public final int tag;
	public final String lexeme;
	
	public Token(int tag, String lexeme) {
		this.tag = tag;
		this.lexeme = lexeme;
	}
}
