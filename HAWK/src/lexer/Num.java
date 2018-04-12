package lexer;

public class Num extends Token {
	public final int value;
	
	public Num(int value, String num) {
		super(Tag.NUM, num);
		this.value = value;
	}
}
