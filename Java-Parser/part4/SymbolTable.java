import java.util.*;

public class SymbolTable {
	private Stack<HashMap <String, Integer> > stack;
	private int scope;
// Mark: Initialize
	public SymbolTable(){
		stack = new Stack < HashMap<String, Integer> >();
		scope = -1;
	}
//Mark: Setters
	public void PushBlock() {
		HashMap<String, Integer> newBlock = new HashMap<String, Integer>();
		stack.push(newBlock);
		scope++;
	}
	public void PopBlock() {
		stack.pop();
		scope--;
	}
	public boolean InsertToTable(String name) {
		String query = null;
		HashMap<String,Integer> block = stack.peek();
        if(block.containsKey(name))
            query = name;
		if(query != null) {
			System.err.println(" redeclaration of variable " + name);
			return false;
		}	
		String entry = new String(name);
		stack.peek().put(entry, scope);
		return true;	
	}
	public boolean SetValueForKey(String name, int value) {
		String query = null;
		// if current scope has key
		HashMap<String,Integer> block = stack.peek();
        if(block.containsKey(name)){
            query = name;
		 	String entry = new String(name);
            stack.peek().put(entry, value);
			//System.out.print("// CHECK2 : " + entry + " " + value);
        }
       
		if(query != null) {
			return false;
		}	
		return true;	
	}
//Mark: Getters
	public int GetScope() {
		return scope;
	}
	public Stack<HashMap <String, Integer> > GetTable() {
		return stack;
	}
	
}











