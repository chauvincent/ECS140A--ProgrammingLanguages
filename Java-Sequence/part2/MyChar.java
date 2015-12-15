public class MyChar extends Element{
	private char c;
	public MyChar(){ c = '0';							}
	public char Get(){ return this.c;					}
	public void Set(char val) {this.c = val;			}
	public void Print(){System.out.print("'" + c + "'");}
}
