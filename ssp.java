import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author PRITI
 *
 */
public class ssp {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		try{
			BufferedReader in;
			in = new BufferedReader(new FileReader(args[0]));
			Integer source = Integer.parseInt(args[1]);
			Integer destination = Integer.parseInt(args[2]);
			String line = null;
			String fileLine[];
			line = in.readLine();
			//splitting the file arguments
			fileLine = line.split(" ");
			//n=no. of graph nodes
			int n = Integer.parseInt(fileLine[0]);
			//edges=no. of graph edges
			int edges = Integer.parseInt(fileLine[1]);

			//Making a new Graph instance with the given no. of nodes
			Graph g1 = new Graph(n);
			//reading the file and making corresponding graph entries and weights/cost
			while ((line = in.readLine()) != null) 
			{
				fileLine = line.split(" ");
				if(fileLine.length==3)
				{
					Integer v1 = new Integer(Integer.parseInt(fileLine[0]));
					Integer v2 = new Integer(Integer.parseInt(fileLine[1]));
					Double cost = new Double(Double.parseDouble(fileLine[2]));
					g1.addCost(v1,v2,cost);
				}
			}

			in.close();
			//making a new Dijkstra instance
			Dijkstra d = new Dijkstra();
			//calling the DijkstraPath method and storing the output in result
			HashMap<HashMap<Integer, Double>, HashMap<Integer, Integer>> result = d.DijkstraPath(g1, source, destination);

			HashMap<Integer, Integer> pathResult = null;
			//printing the shortest path cost retrieved from Dijkstra's algorithm to the screen
			for ( Map.Entry<HashMap<Integer, Double>, HashMap<Integer, Integer>> entry : result.entrySet()) 
			{
				HashMap<Integer, Double> costResult = entry.getKey();
				pathResult = entry.getValue();
				System.out.println(costResult.get(destination).intValue());
			}
			
			//printing the shortest path from source node to destination node as given by Dijkstra's algorithm to the screen
			String path = "";
			Integer i = destination;
			while (true)
			{
				path=path+" "+i;
				i = pathResult.get(i);
				if(i==null)
					break;
			}
			System.out.println(new StringBuffer(path).reverse().toString());
		} //try block ends
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}//main ends

}//ssp class ends
