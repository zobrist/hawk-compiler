package lexer;

public class Real extends Token {
	public final float value;
	
	public Real(float value, String f) {
		super(Tag.REAL, f);
		this.value = value;
	}
}
