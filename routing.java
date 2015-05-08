import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents the actual implementation of the routing
 */

/**
 * @author PRITI
 *
 */

public class routing {


	public static String binIP(String nativeIP)
	{
		StringBuilder bits = new StringBuilder();
		String[] bytes = nativeIP.split("\\.");
		assert bytes.length == 4;
		byte[] byteArray = new byte[4];
		for (int i = 0; i < 4; ++i) 
		{
			byteArray[i] = Integer.valueOf(bytes[i]).byteValue();
			Integer sp =  Integer.parseInt(bytes[i]);
			bits.append(padIt(sp));
		}
		return bits.toString();
	}

	public static String padIt(int num)
	{
		String binaryString = Integer.toBinaryString(num);
		int length = 8 - binaryString.length();
		char[] c = new char[length];
		Arrays.fill(c, '0');
		String toAdd = new String(c);
		binaryString = toAdd + binaryString;

		return binaryString;
	}

	//data-structure to store the router IP addresses
	static HashMap<Integer,String> IP = new HashMap<Integer,String>();

	public static void main(String[] args)
	{

		Integer source = Integer.parseInt(args[2]);
		Integer destination = Integer.parseInt(args[3]);

		Scanner sc;
		try 
		{
			sc = new Scanner(new File(args[0]));

			String line = sc.nextLine();
			int n = Integer.parseInt(line.split(" +")[0]);
			int e = Integer.parseInt(line.split(" +")[1]);

			Graph g = new Graph(n);

			while(sc.hasNextLine())
			{
				String[] fileLine = sc.nextLine().split(" +");
				if(fileLine.length == 3)
					g.addCost(Integer.parseInt(fileLine[0]), Integer.parseInt(fileLine[1]), Double.parseDouble(fileLine[2]));
			}

			sc.close();

			Scanner sc1 = new Scanner(new File(args[1]));
			int routernum =0;

			while(sc1.hasNext())
			{
				String ip = sc1.next();
				String routerStr =""+routernum;
				IP.put(Integer.valueOf(routerStr), ip);
				routernum = routernum+1;
			}
			sc1.close();



			Dijkstra2 d = new Dijkstra2();
			Dijkstra d2 = new Dijkstra();
			HashMap<HashMap<Integer, Double>, HashMap<Integer, Integer>> dijResult = d2.DijkstraPath(g, source, destination);


			for ( Map.Entry<HashMap<Integer, Double>, HashMap<Integer, Integer>> dij1result : dijResult.entrySet()) 
			{
				HashMap<Integer, Double> costResult = dij1result.getKey();
				System.out.println(costResult.get(destination).intValue());
			}

			HashMap<Integer,HashMap<String,Integer>> pairs = new HashMap<Integer, HashMap<String,Integer>>();

			HashMap<Integer,BinTrie> bts = new HashMap<Integer, BinTrie>();

			Map<Integer,String> nodeIPs = new HashMap<Integer,String>();

			Iterator<Integer> it1 = g.iterator();

			while(it1.hasNext()){


				HashMap<String,Integer> pair1 = new HashMap<String,Integer>();



				Integer r1 = it1.next();
				HashMap<Integer, BundleFromDijk> everyRouterDijk = d.DijkstraPath(g, r1);

				nodeIPs.put(r1,  binIP(IP.get(r1)));// puts node num->1s 0s in hashmap

				Iterator<Integer> it2 = g.iterator();

				BinTrie btObj = new BinTrie();
				while(it2.hasNext())
				{
					Integer r2 = it2.next();
					if(r2==r1)
						continue;

					BundleFromDijk bundleObj = everyRouterDijk.get(r2);

					String IP2 = IP.get(r2);

					String nextHop = bundleObj.nextHop();

					String addr = binIP(IP2);

					pair1.put(addr, new Integer(nextHop));
					btObj.insert(addr, nextHop);

				}

				btObj.poMerge();
				pairs.put(r1, pair1);	
				bts.put(r1,btObj);

			}

			String destIP = nodeIPs.get(destination);

			String src = Integer.toString(source);

			boolean check = src.equalsIgnoreCase(Integer.toString(destination)) ;

			while(!src.equalsIgnoreCase(Integer.toString(destination)))
			{
				BinTrie btObj1 = bts.get(Integer.valueOf(src)).poMerge();
				String sMP = btObj1.prefixMatNode(destIP).getResPrefix();
				String nexthop = btObj1.prefixMatNode(destIP).getNValue();
				System.out.print(sMP+" ");
				src = nexthop;
			}
			System.out.println('\n');
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//main ends

}
