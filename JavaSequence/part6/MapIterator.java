public class MapIterator {
	private Map map;
	public MapIterator(){						map = null;        }
	public void advance(){						map = map.rest();  } 
	public void set(Map begin){                 map = begin;       }
	public Pair get(){                          return map.first();}
	public Map getMap(){						return map;        }
	public boolean equal(MapIterator itr)
	{
		Map map2 = itr.getMap();
		PairList list = map2.node;
		PairList queryNode = list.getNext(); 		
		PairList thisNode = getMap().node;
		if(  (thisNode == queryNode) || get() == null )
				return true;
		return false; 
	}
}
