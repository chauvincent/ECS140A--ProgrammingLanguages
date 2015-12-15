public class Sequence extends Element{
// 	Linked-List Implementation
	private Element element;
	private Sequence next;
	public Sequence(){ 									element = null; next = null;}
	public Element GetElement(){						return this.element;		}
	public Sequence GetSequence(){						return this.next;			}
	public void SetElement(Element elm){				this.element = elm;			}
	public void SetNextSequence(Sequence node){			this.next = node;			}
	public Element first(){								return this.GetElement();	}
	public Sequence rest(){								return this.GetSequence();	}
	public void InsertToTail(Sequence currentNode, Element elm){
		// Insert to end of linked list
		Sequence newNode = new Sequence();
		newNode.SetElement(elm);
		Sequence temp = newNode;
		int pos = length(), i = 0;
		while(i < (pos-1)){
			currentNode = currentNode.next; 
			i++;
		}
		currentNode.next = newNode; // point last node to new node
	}
	public void TakeItToTheHead(Sequence currentNode, Element elm){ // set current node to head
		currentNode.SetElement(elm);
	}
	public void Swap(Sequence seq1, Sequence seq2){ // swap elements
		Sequence temp = new Sequence();
		temp = seq2.next;
		seq1.element = seq2.element;
		seq2.element = temp.element;
		seq2.next.element = seq1.element;
	}
	public void Print(){ // print
		System.out.print("[ ");
		Sequence temp = new Sequence();
		temp = this;
		for(int i = 0; i < length(); i++){
			temp.GetElement().Print();
			System.out.print(" ");
			temp = temp.next;
		}
		System.out.print("]");
	}	
	public int length(){ // trasverse list, return the length
		int count = 0;
		Sequence temp = new Sequence();
		temp = this;
		while (temp != null){ 
			count++;
			temp = temp.next;
		}
		return count;
	}
	public void add(Element elm, int pos){
		if (pos < 0 || pos > length()){  // if out of bounds
			System.err.println("ERROR: Position is out of bounds"); 
			System.exit(1);
		}
		if(pos >= 0 && pos <= length() && this.element!=null){ // within bounds, and not null
			Sequence newNode = new Sequence();
			newNode.SetElement(elm);
			Sequence currentNode = new Sequence();
			currentNode = this;
			int i = 0;
			while(i < (pos-1)){
				currentNode = currentNode.next;
				i++;
			}
			newNode.next = currentNode.next;
			currentNode.next = newNode;
			if (pos == 0){
				Sequence temp = new Sequence();
				Swap(temp, currentNode);
			}
		}
		if (this.next == null && this.element == null){  // if list is empty
			Sequence temp = new Sequence();
			temp = this;
			TakeItToTheHead(temp, elm);
			return;
		}
		if( (pos - length()) == 0 && this.element!= null){  // insert to the end of list, if not null
			Sequence currentNode = new Sequence();
			currentNode = this;
			InsertToTail(currentNode, elm);
		}
	}
	public void delete(int pos){
		if (pos < 0 || pos >= length()){  // if out of bounds
			return;
		}
		if (pos == 0){  // if delete first node
			Sequence currentNode = new Sequence();
			currentNode = this;
			Sequence tempNode = new Sequence();
			tempNode = currentNode.next;
			currentNode.next = tempNode.next;
			currentNode.element = tempNode.element;
			return;
		}
		Sequence currentNode = new Sequence();
		currentNode = this;
		for (int i = 0; i < pos-1; i++)
			currentNode = currentNode.next;
		
		Sequence tempNode = new Sequence();
		tempNode = currentNode.next;
		currentNode.next = tempNode.next;		
	}
}
	