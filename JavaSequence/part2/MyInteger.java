public class MyInteger extends Element {
	private int a;

	public MyInteger(){ a = 0;				}
	public int Get() { return this.a; 		}
	public void Set(int val){ a = val;		}
	public void Print(){System.out.print(a);}
}
