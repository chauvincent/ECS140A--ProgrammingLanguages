class PairList {
	private PairList next; 
	public Element pair; 
	public PairList(Element p){						next = null; pair = p;		  }
	public Element getPair(){						return pair;                  }
	public void setPair(Element p){         		pair = p;             		  }
	public PairList getNext(){                      return next;                  }
	public void setNext(PairList nextValue){        next = nextValue;             }
}
public class Map {
	private MapIterator mapIterator, mapIterator2; 
	public PairList node;
	public Map(){	
		Element elm = null;
		node = new PairList(elm); 
		mapIterator2 = new MapIterator(); 
	}
	public void setHead(PairList n){ //set node
		node = n;
	}
	public MapIterator begin(){ //set map to beginning itr
		mapIterator = new MapIterator();  
		mapIterator.set(this);
		return this.mapIterator;  
	}
	public MapIterator end(){ //set map to end itr
		MapIterator s = mapIterator2;
		s.set(this);
		for (int pos  = 0; pos < this.length(); pos++)
			s.advance();
		return s;  
	}
	public Pair first(){ //get first pair
		PairList currentNode = node; 
		if(this.length() > 0){
			PairList nextNode = currentNode.getNext();
			Element val = null;
			val = nextNode.getPair();
			Pair firstPair = (Pair) val;
			return firstPair;
		}
		return null;
	}
	public Map rest(){ //set next
		Map newMap = new Map(); 
		newMap.setHead(node.getNext());
		return newMap; 
	}	
	public void add(Pair inval){ //add to list
		PairList newNode = new PairList(inval);  
		PairList currentNode = node; 
		MyChar queryObject = inval.getKey();
		char query = queryObject.Get(); //get query key
		while(currentNode.getNext() != null){
			PairList nextNode = currentNode.getNext();
			Element elm = null;
			elm = nextNode.getPair();
			MyChar currentKey = ((Pair)elm).getKey();
			char currentPair = currentKey.Get();
			//if current greater than query put to the right
			if(currentPair > query &&  currentPair != query){
				PairList rightNode = currentNode.getNext();
				newNode.setNext(rightNode);  
				currentNode.setNext(newNode);  
				break;
			}
			else
				currentNode = currentNode.getNext(); 
		}
		//if next is last
		if(currentNode.getNext() == null){
			PairList nextNode = currentNode.getNext();
			newNode.setNext(nextNode);
			currentNode.setNext(newNode);  
		}
	}	
	public int length(){ //get length of list
		int size = 0;
		PairList currentNode = node.getNext();
		while (currentNode != null){
			currentNode = currentNode.getNext();
			size++;
		}
		return size;
	}
	public MapIterator find(MyChar key){ //find mapitr equal to query
		char query = key.Get();
		MapIterator start = this.begin();
		MyChar keyChar = new MyChar();
		while(!start.equal(this.end())){
			Pair currentPair = new Pair(null, null);
			currentPair = start.get();
			keyChar = currentPair.getKey();
			char currentChar = keyChar.Get();
			if(query == currentChar)
				return start;
			start.advance();
		}
		return start; 
	}
	public void Print(){
		PairList currentNode = node.getNext(); 
		System.out.print("[ "); 
		while (currentNode != null){
			Pair pair = new Pair(null,null); 		
			pair = (Pair)currentNode.getPair();	
			System.out.print("(");
			pair.getKey().Print(); 
			System.out.print(" ");
			pair.getVal().Print(); 
			System.out.print(")");
			currentNode = currentNode.getNext();
			if(currentNode != null)
				System.out.print(" ");
		}
		System.out.print(" ]"); 	
	}	
}