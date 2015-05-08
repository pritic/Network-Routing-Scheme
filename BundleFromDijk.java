import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class represent the bundle results from Dijkstra's algorithm to be used in routing.java file
 */

/**
 * @author PRITI
 *
 */
public class BundleFromDijk 
{
	
	//shortest path map to reach the destination toNode
	HashMap<Integer, Integer> path = new HashMap<Integer, Integer>(); 
	Integer toNode;
	//path cost to reach toNode
	Double cost;
	
	//parameterized constructor
	BundleFromDijk(Integer toNode1, Double cost1, HashMap<Integer, Integer> path1)
	{
		this.toNode = toNode1;
		this.cost = cost1;
		this.path = path1;
	}

//this method gives the next hop node from the source
public String nextHop()
{
		
	Integer destination = this.toNode;
	Integer x = destination;
	
	LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.addFirst(destination);
		
		do {
			Integer i = path.get(destination);
			if(i==null) break;
			queue.addFirst(i);
			x = destination;
			destination = i;
			
		} while (destination!=x);

		queue.remove();
		return Integer.toString(queue.remove());
	
	}
}
