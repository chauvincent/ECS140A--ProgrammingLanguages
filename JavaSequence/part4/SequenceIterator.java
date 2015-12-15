public class SequenceIterator {
	private Sequence seq;
	public SequenceIterator(){																		  seq = null; }
	public Boolean equal (SequenceIterator other) { 		if (this.seq == other.seq) return true; return false; }
	public SequenceIterator advance(){								   		set(seq.GetSequence()); return this;  }
	public Element get(){																 return seq.GetElement(); }
	public void set(Sequence s){															 		    seq = s;  }	
}
