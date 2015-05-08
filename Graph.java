import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * This class represent the Graph on which the Dijkstra's SSSP algorithm is run
 */

/**
 * @author PRITI
 *
 */
public class Graph {
	//storing the adjacency list of each individual nodes
	public  final HashMap<Integer, HashMap<Integer, Double>> fullGraphList = new HashMap<Integer, HashMap<Integer, Double>>();
	//no. of nodes in the graph
	int nodeNum;
	
	//parameterized constructor for creating HashMaps for adjacency list of each individual graph node
	Graph(int num)
	{
		this.nodeNum = num;
		
		for(int i=0;i<nodeNum;i++)
			fullGraphList.put(new Integer(i), new HashMap<Integer, Double>());
		
		return;
	}
	
	//this method is called after each line is read from the input file and creates edges between the specified nodes
	//for both the nodes to achieve an undirected graph
	public void addCost(Integer nodeFrom, Integer nodeTo, Double cost)
	{
		if (!fullGraphList.containsKey(nodeFrom) || !fullGraphList.containsKey(nodeTo))
            throw new NoSuchElementException("No such node in the graph");
		fullGraphList.get(nodeFrom).put(nodeTo, cost);
		fullGraphList.get(nodeTo).put(nodeFrom, cost);
	}
	
	//this method return the complete adjacency list representation of the graph for each individual nodes
	HashMap<Integer, HashMap<Integer, Double>> getAdjList()
	{
		return fullGraphList;
	}
	
	//this method prints the calling graph instance
	void print_Graph()
	{
		for(Integer n : fullGraphList.keySet())
		{
			HashMap<Integer, Double> h = fullGraphList.get(n);
			for(Integer n1: h.keySet())
			{
		        System.out.println(n.intValue()+" -> "+n1.intValue() + " " + h.get(n1));
		    }
		}
		
	}

	/**
	 * @return iterator for the adjacency list of the graph
	 */
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return fullGraphList.keySet().iterator();
	}

}
