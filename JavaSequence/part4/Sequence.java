public class Sequence extends Element{
// 	Linked-List Implementation
	private Element element;
	public Sequence next;
	public Sequence(){ 									element = null; next = null;}
	public Element GetElement(){						return this.element;		}
	public Sequence GetSequence(){						return this.next;			}
	public void SetElement(Element elm){				this.element = elm;			}
	public void SetNextSequence(Sequence node){			this.next = node;			}
	public Element first(){								return this.GetElement();	}
	public Sequence rest(){								return this.GetSequence();	}
	public void advance(){ Sequence currentSeq = this; currentSeq = currentSeq.next;}
	public void InsertToTail(Sequence currentNode, Element elm){
		// Insert to end of linked list
		Sequence newNode = new Sequence();
		newNode.SetElement(elm);
		Sequence temp = newNode;
		int pos = length(), i = 0;
		while(i < (pos-1)){
			currentNode.advance();
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
	public Element index(int pos){
		if (pos < 0 || pos > length()){  // if out of bounds
			System.err.println("ERROR: Position is out of bounds"); 
			System.exit(1);
		}
		Sequence currentNode = new Sequence();
		currentNode = this;
		for (int i = 0; i < pos; i++)
			currentNode = currentNode.next;
		return currentNode.element;
	}
	public Sequence flatten(){
		Sequence flatSequence = new Sequence();
		Sequence currentNode = new Sequence();
		for(currentNode = this;currentNode!=null;	currentNode = currentNode.next){
			if(currentNode.element instanceof MyChar || currentNode.element instanceof MyInteger){
				flatSequence.add(currentNode.element, flatSequence.length());
			}
			if(currentNode.element instanceof Sequence){
				Sequence subSequence = new Sequence();
				subSequence = ((Sequence)currentNode.first()).flatten();
				for(;subSequence!=null;subSequence=subSequence.next)
					flatSequence.add(subSequence.first(), flatSequence.length());
			}
		}
		return flatSequence; 
	}
	public Sequence copy(){	
			Sequence newSequence = new Sequence();
			for(Sequence currentNode = this; currentNode!=null;currentNode = currentNode.rest()){
				if (currentNode.element instanceof Sequence){ // if is sequence
					Sequence element = (Sequence)currentNode.first();
					Sequence temp = new Sequence();   
					temp = element.copy();
					newSequence.add(temp, newSequence.length()); // deep copy
				}
				if(currentNode.element instanceof MyChar){ // if is char
					MyChar element = (MyChar)currentNode.first();
					MyChar temp = new MyChar();
					MyChar myChar = new MyChar();
					myChar.Set(element.Get());
					temp = myChar;
					newSequence.add(temp, newSequence.length()); // deep copy
				}
				if(currentNode.element instanceof MyInteger){ // if is integer
					MyInteger element = (MyInteger)currentNode.first();
					MyInteger temp = new MyInteger();
					MyInteger myInteger = new MyInteger();
					myInteger.Set(element.Get());
					temp = myInteger;
					newSequence.add(temp, newSequence.length()); // deep copy
				}
			}
			return newSequence; 
	}
	public SequenceIterator begin(){
		SequenceIterator itr = new SequenceIterator();
		Sequence current = new Sequence(); 
		current = this; 
		itr.set(current); // set begin
		return itr;
	}
	public SequenceIterator end(){
		SequenceIterator itr = new SequenceIterator();
		Sequence currentSequence = new Sequence();
		for(currentSequence = this; currentSequence.next!= null; currentSequence = currentSequence.next); //find end
		itr.set(currentSequence.next); // set end
		return itr;
	}
}