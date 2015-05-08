import java.util.HashMap;
import java.util.Map;

/**
 * This class implements Dijkstra's Single Source Shortest Path algorithm using Fibonacci Heaps utilized by the ssp.java file and routing.java file
 */

/**
 * @author PRITI
 *
 */
public class Dijkstra2 {

	//this method is the Dijkstra's algorithm
	public HashMap<Integer, BundleFromDijk> DijkstraPath(Graph graph, Integer source)
	{
		//creating a FibonacciHeap instance
		FibonacciHeap fHeap = new FibonacciHeap();
		//data-structure to maintain mapping of graph nodes to Fibonacci heap nodes
		Map<Integer, FibHeapNode> fNodes = new HashMap<Integer, FibHeapNode>();

		//setting the initial distance of all Fibonacci heap nodes to infinity
		for(Integer n:graph.fullGraphList.keySet())
			fNodes.put(n, fHeap.insertNode(n.intValue(), Double.POSITIVE_INFINITY));
			
		//data-structure to store the bundled result returned after the Dijkstra's algorithm has been run on the input graph
		HashMap<Integer, BundleFromDijk> combinedResult = new HashMap<Integer, BundleFromDijk>();
		//data-structure to store the shortest path from the source to every other node
		HashMap<Integer, Double> result = new HashMap<Integer, Double>();
		//data-structure to store the shortest path from source node to destination nodes
		HashMap<Integer, Integer> previous = new HashMap<Integer, Integer>();

		//updating the distance of source node to itself as 0
		fHeap.decreaseKey(fNodes.get(source), 0.0);
		Integer fmin1;
		Map<Integer, Double> h;
		//updating the path for source node
		previous.put(source, null);
		
		while (fHeap.min!=null) 
		{
			//deleting/retrieving the minimum from the Fibonacci heap
			FibHeapNode fmin = fHeap.deleteMin();
			//type casting the min to Integer
			fmin1 = new Integer(fmin.key);
			//bundling up the result in BundleFromDijk instance
			BundleFromDijk bundle = new BundleFromDijk(fmin1, fmin.distance, previous);
			//storing the min in the result data-structure
			result.put(fmin1, fmin.distance);
			//pushing the outputs for the node in combinedResult
			combinedResult.put(fmin1, bundle);

			h = graph.fullGraphList.get(fmin1);
			
			//for every node in the Fibonacci heap, updating its distance from the source node from infinity to its path cost
			for(Map.Entry<Integer, Double> n: h.entrySet())
			{
				//if the distance for a node is already found as a result of previous iterations, the loop continues without any 
				//calculation on the node
				if(result.containsKey(n.getKey())) 
					continue;
				//else the new distance is found 
				double pathCost = fmin.distance + n.getValue();

				FibHeapNode dest1 = fNodes.get(n.getKey());
				//if the new distance is found to be less than the current one, update the node's distance value and
				//maintain the shortest path in the previous data-structure
				if(pathCost<dest1.distance)
				{
					fHeap.decreaseKey(dest1, pathCost);
					previous.put(new Integer(dest1.key), fmin1);
				}
			}	
		}
		//return the combinedResult
		return combinedResult;
	}
}
