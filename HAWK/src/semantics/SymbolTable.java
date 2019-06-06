package semantics;

import java.util.Hashtable;

import lexer.Token;

public class SymbolTable {
	
//	private String name;
//	private Object value;
	private Hashtable<String, Token> nameToValueHash = new Hashtable<String, Token>();
	private Hashtable<String, String> nameToScopeHash = new Hashtable<String, String>(); // scope is either local or global
	private Hashtable<String, Integer> nameToReturnTypeHash = new Hashtable<String, Integer>(); // for methods
	
	public SymbolTable() {
		
	}
	
	public void addToSymbolTable(String name, Token value, String scope) {
		
		nameToValueHash.put(name, value);
		nameToScopeHash.put(name, scope);
	}
	
	public void addToSymbolTableForMethods(String name, int returnType) {
		
		nameToReturnTypeHash.put(name, returnType);
//		nameToScopeHash.put(name, scope);
	}
	
	public Token getValue(String name) {
		
		return nameToValueHash.get(name);
	}
	
	public boolean isVariableAlreadyPresent(String name) {
		
		return nameToValueHash.containsKey(name);
	}
	
	public String getScope(String name) {
		
		return nameToScopeHash.get(name);
	}
}
