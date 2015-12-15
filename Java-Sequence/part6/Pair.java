public class Pair extends Element{
	private MyChar key;
	private Element value;
	public Pair (MyChar c, Element d){			key = c; value = d;}
	public MyChar getKey(){						return key;        }
	public Element getVal(){					return value;      }
	public void Print(){
		System.out.print("(");
		key.Print(); 
		System.out.print(" ");
		value.Print(); 
		System.out.print(")");
	}
}
