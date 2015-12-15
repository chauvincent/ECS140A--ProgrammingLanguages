/* *** This file is given as part of the programming assignment. *** */
import java.util.*;


public class Parser {
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    SymbolTable table = new SymbolTable(); // Declare our symbol table
    private void scan() {
    	tok = scanner.scan();
    }
    private Scan scanner;
    Parser(Scan scanner) {   	
    	this.scanner = scanner;
    	scan();
    	program();
 
    	if( tok.kind != TK.EOF )
    		parse_error("junk after logical end of program");
    }
    private void program() {
    	block();
    }
    private void block(){
        start_range();
        table.PushBlock(); // Push for new block 
    	declaration_list();
        statement_list();
        table.PopBlock(); // When comes back, finish with block
    }
    private void declaration_list() {
	// below checks whether tok is in first set of declaration.
	// here, that's easy since there's only one token kind in the set.
	// in other places, though, there might be more.
	// so, you might want to write a general function to handle that.
	    while( is(TK.DECLARE) ) {
	    	declaration();
		}
    }
    private void declaration() {
        mustbe(TK.DECLARE);
        if( is(TK.ID) ) {
            if( SearchInBlock(tok.string) == null ) {
                table.InsertToTable(tok.string);  // Insert to hash 
            }     
            else
                System.err.println("redeclaration of variable " + tok.string);
        }
        mustbe(TK.ID);
        while( is(TK.COMMA) ) {
            scan();
            if( is(TK.ID) ) {
                if( SearchInBlock(tok.string) == null ) {
                    table.InsertToTable(tok.string);       
                }
                else
                    System.err.println("redeclaration of variable " + tok.string);
            }            
            mustbe(TK.ID);
        }
    }
    private void statement_list() {
    // Syntax: First(statement_list) ::= {e { {~, id, !, <, [ }
   
    	while(is(TK.none) || is(TK.TILDE) || is(TK.ID) || is(TK.PRINT) || is(TK.DO) || is(TK.IF)){ 
    		// Syntax: Statement ::= assignment | print | do | if
    		if (is(TK.TILDE) || is(TK.ID)){
        		assignment();
        	}else if (is(TK.PRINT)){
        		print();
        	}else if (is(TK.DO)){
        		dooDoo();
        	}else if (is(TK.IF)){
        		if2(); 
        	}else{
        		System.err.println("Error in statement");
        		System.exit(1);
        	}
    	}
    }
    private void start_range (){
    // Syntax:
        int start = -999, end = -999;
        mustbe(TK.CARET);
        
        if((start = Integer.parseInt(tok.string)) != -999){
            scan();
            if(is(TK.SCOLON)){
                scan();
                if(is(TK.NUM)){
                    end = Integer.parseInt(tok.string);
                    scan();
                }
            }
        }else{
            System.err.println("Please enter integers only");
        }
        if(start != -999 && end != -999){
            for(int i = start; i<=end; i++){
                System.out.print(i + " ");
            }
        }
        else{
            System.err.println("Please enter integers only");
        }
        return;
    }
    private void assignment (){
    // Syntax: First(Assignment) = { ~, id} PREDICT: = expr 
    	ref_id();
    	mustbe(TK.ASSIGN);
    	expr();
    }
    // Syntax: First(ref_id)  OPTIONAL: { ~, number} PREDICT: id,
    private void ref_id() {
        int scopeNumber = -1;
        Boolean isCurrentScope = false;
        if( is(TK.TILDE) ) {
            scan();
            if( is(TK.NUM) ) {
                if((scopeNumber = Integer.parseInt(tok.string)) == 0){ // if current
                    scopeNumber = -1;
                    isCurrentScope = true;
                }
                isCurrentScope = false;
                mustbe(TK.NUM);
            }
            else{
                isCurrentScope = false;
                scopeNumber = 0;
            }
        }
        if( is(TK.ID) ) {
            if (scopeNumber != -1 && isCurrentScope == false){ 
                // if not found in prev scope
                if(SearchInScope(tok.string, scopeNumber) == null ) { 
                    if(scopeNumber == 0) 
                        System.err.println("no such variable ~" + tok.string +
                            " on line " + tok.lineNumber);
                    else
                        System.err.println("no such variable ~" + scopeNumber + tok.string +
                            " on line " + tok.lineNumber);
                }
            }
            else if (scopeNumber == -1 || isCurrentScope == true) { 
                // check to see if declared before
                if(SearchInStack(tok.string) == null ) {
                    System.err.println(tok.string + " is an undeclared variable on line "
                        + tok.lineNumber);
                    System.exit(1);                         
                }
            }
        }
        mustbe(TK.ID);
    }      
    private void expr(){
    // Syntax: First(expr) = term Optional: +/- term
    	term();
    	while(is(TK.PLUS) || is(TK.MINUS)){
    	    scan();
    		term();
    	}
    }
    private void term(){
    // Syntax: First(term) = { factor: ’(’ expr ’)’, ref_id(must have id), number }  OPTIONAL: *, /, (, ref_id, number }
    	if(is(TK.LPAREN)){
    		scan();
    		expr();
    		mustbe(TK.RPAREN);
    	}
    	else if(is(TK.TILDE) || is(TK.ID)){
    		ref_id();
    	}
    	else if(is(TK.NUM)){
    		scan();
    	}
    	// OPTIONAL *, /, (, ref_id, number
    	while(is(TK.TIMES) || is(TK.DIVIDE)){
    	   	   scan();   	
    	   	if(is(TK.LPAREN)){
           		scan();
        		expr();
        		mustbe(TK.RPAREN);
        	}
        	else if(is(TK.TILDE) || is(TK.ID)){
        		ref_id();
        	}
        	else{ 
        		mustbe(TK.NUM);
        	}
    	}
    }
    private void print() {
    // Syntax: First(print) = ! PREDICT = expr
    	if(is(TK.PRINT)){
    		scan();
    		expr(); 
    	}
    }
    private void dooDoo (){
    // Syntax: First(do) = < PREDICT: Guarded_command (, [, number } : block >
    	mustbe(TK.DO);
    	guarded_command();
    	mustbe(TK.ENDDO);
    }
    private void guarded_command(){
    // Syntax: First(guarded_command) = { (, [, number } : block 
    	expr();
    	mustbe(TK.THEN);
    	block();
    }
    private void if2(){
    // Syntax: First(if) = [ predict: Guarded_command OPTIONAL: | Guarded_comand OPTION: % block MUST ]
    	mustbe(TK.IF);
    	guarded_command();
    	// OPTIONAL
    	while(is(TK.ELSEIF)){
    		scan();
    		guarded_command();
    	}
    	if(is(TK.ELSE)){
    		scan();
    		block();
    	}
    	mustbe(TK.ENDIF);
    }
    // Search In Table 
    public String SearchInScope(String name, int scope2) {
        Stack<HashMap <String, Integer>> stack = table.GetTable();
        int scope = table.GetScope();
        if(scope2 > scope)
            return null;
        int delta;
        if(scope2 == 0)
            delta = 0;
        else
            delta = scope - scope2;
        HashMap<String, Integer> block = stack.elementAt(delta);
        if(block.containsKey(name))
            return name;
        return null;
    }
//Mark: Symbol Table Searches: 
    public String SearchInStack(String name) {
        Stack<HashMap <String, Integer>> stack = table.GetTable();
        ListIterator<HashMap<String, Integer>> blocks = stack.listIterator(stack.size());
        
        while(blocks.hasPrevious()) {
            HashMap<String, Integer> block = blocks.previous();
            if(block.containsKey(name))
                return name;
        }
        return null;
    }
//Mark: Search in Block
    public String SearchInBlock(String name) {
        Stack<HashMap <String, Integer>> stack = table.GetTable();
        HashMap<String,Integer> block = stack.peek();
        if(block.containsKey(name))
            return name;
        return null;
    }
//Given: 
    // is current token what we want?
    private boolean is(TK tk) {
        return tk == tok.kind;
    }
    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
    	if( tok.kind != tk ) {
	    	System.err.println( "mustbe: want " + tk + ", got " +
				    tok);
	    	parse_error( "missing token (mustbe)" );
		}
		scan();
    }
    private void parse_error(String msg) {
    	System.err.println( "can't parse: line "
			    + tok.lineNumber + " " + msg );
    	System.exit(1);
    }
}


