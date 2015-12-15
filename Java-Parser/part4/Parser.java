/* *** This file is given as part of the programming assignment. *** */
import java.util.*;
public class Parser {
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    String lastTok;
    Boolean printFlag = false;
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
        System.out.print("}\n");

    }
    private void program() {
        System.out.print("#include <stdio.h>\n" +
                "int main()\n");
        System.out.println("{");
        block();
        System.out.print("  return 0;\n");

    }
    private void block(){

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
        Boolean flag = false;

        mustbe(TK.DECLARE);
        // HEREREERERERER
        System.out.print("int " + "_" + tok.string);

        if( is(TK.ID) ) {
            if( SearchInBlock(tok.string) == null ) {
                System.out.print("; \n");
                table.InsertToTable(tok.string);  // Insert to hash 
            }     
            else{
                System.out.print("2; \n");
                System.err.println("redeclaration of variable " + tok.string);
            }
        }
        mustbe(TK.ID);
        while( is(TK.COMMA) ) {
            scan();
            if( is(TK.ID) ) {
                if( SearchInBlock(tok.string) == null ) {
                    table.InsertToTable(tok.string);  // HERERERERE   
                    lastTok = tok.string;
                    System.out.print("int " + "_" + tok.string);  
                }
                else{
                    //System.out.print("2; \n");
                    System.err.println("redeclaration of variable " + tok.string);
                }
            }            
            mustbe(TK.ID);
                System.out.print(";\n");
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
    private void assignment (){
    // Syntax: First(Assignment) = { ~, id} PREDICT: = expr 
        printFlag = false;
        ref_id();
        mustbe(TK.ASSIGN);
        System.out.print("= ");
        expr();
        System.out.print(";\n");
    }
    // Syntax: First(ref_id)  OPTIONAL: { ~, number} PREDICT: id,
    private void ref_id() {
        Boolean isGlobalScope = false;
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
 //table.SearchInScope()
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
                    if(scopeNumber == 0) {
                        System.err.println("no such variable ~" + tok.string +
                            " on line " + tok.lineNumber);
                        System.exit(1);
                    }else{
                        System.err.println("no such variable ~" + scopeNumber + tok.string +
                            " on line " + tok.lineNumber);
                        System.exit(1);
                    }
                }
// PRNT GLOBAL SCOPE VARIABLE t42
                Integer temp =  GetFromScope(tok.string, scopeNumber);
                if(temp != null && printFlag == true){
                    isGlobalScope = true;
                    System.out.print(temp);
                }else{
                  //  System.exit(1);
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
// Global checkk
            if(isGlobalScope == false)
                System.out.print("_" + tok.string + " ");
        }
        lastTok = tok.string;
        mustbe(TK.ID);
    }      
    private void expr(){
    // Syntax: First(expr) = term Optional: +/- term
        term();
        while(is(TK.PLUS) || is(TK.MINUS)){
            if(is(TK.PLUS)){
                System.out.print(" + ");
            }else if(is(TK.MINUS)){
              System.out.print(" - ");
                
            }
            scan();
            term();
        }
       // System.out.print(";\n");
    }
    private void term(){
    // Syntax: First(term) = { factor: ’(’ expr ’)’, ref_id(must have id), number }  OPTIONAL: *, /, (, ref_id, number }
        if(is(TK.LPAREN)){
            System.out.print("(");
            scan();
            expr();
            mustbe(TK.RPAREN);
            System.out.print(")");
        }
        else if(is(TK.TILDE) || is(TK.ID)){
            ref_id();
        }
        else if(is(TK.NUM)){ 
            //table.put(lastTok, )  //// hash table insert here 
            if(printFlag==false)
              table.SetValueForKey(lastTok, Integer.parseInt(tok.string));
          //    System.out.println("//CHECK: " + lastTok + " " + Integer.parseInt(tok.string));
            System.out.print(tok.string);
            scan();
        }
        // OPTIONAL *, /, (, ref_id, number
        while(is(TK.TIMES) || is(TK.DIVIDE)){
            
            if(is(TK.TIMES)){
                System.out.print(" * ");
            }else if(is(TK.DIVIDE)){
              System.out.print(" / ");   
            }
               scan(); 

            if(is(TK.LPAREN)){ // t14 added
                System.out.print("(");
                scan();
                expr();
                mustbe(TK.RPAREN);
                System.out.print(")");
            }
            else if(is(TK.TILDE) || is(TK.ID)){
                ref_id();
            }
            else{ 
                System.out.print(tok.string);
                mustbe(TK.NUM);

            }
        }
    }
    private void print() {
    // Syntax: First(print) = ! PREDICT = expr
        if(is(TK.PRINT)){
            printFlag = true;
            System.out.print("  printf(\"%d\\n\",");
            scan();
            expr(); 
            System.out.println(");\n");
        }
    }
    private void dooDoo (){
    // Syntax: First(do) = < PREDICT: Guarded_command (, [, number } : block >
        mustbe(TK.DO);
        System.out.print("while(");
        guarded_command();
        mustbe(TK.ENDDO);
    }
    private void guarded_command(){
    // Syntax: First(guarded_command) = { (, [, number } : block 
        expr();
        System.out.print(" <= 0");
        System.out.print(")\n{\n");
        mustbe(TK.THEN);
        block();
        System.out.print("}\n");
    }
    private void if2(){
    // Syntax: First(if) = [ predict: Guarded_command OPTIONAL: | Guarded_comand OPTION: % block MUST ]
        System.out.print("if (");
        mustbe(TK.IF);
        guarded_command();

        Boolean flag = true;
        // OPTIONAL
        while(is(TK.ELSEIF)){
            System.out.print("else if(");
            scan();
            guarded_command();
            flag = true;
        }
        if(is(TK.ELSE)){
            System.out.print("else {\n");
            scan();
            block();
            flag = false;
        }
        mustbe(TK.ENDIF);
        
        if(flag!= true){
            System.out.print("} // HEREEEEEEE\n");
            flag = false;
        }
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
    public Integer GetFromScope(String name, int scope2) {
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
            return block.get(name);
        return null;
    }
}