# Network-Routing-Scheme
The project was implemented in two parts.  

<b>Part 1: Dijkstra's Single Source Shortest Path Algorithm using Fibonacci Heaps:</b>

  • Implemented Dijkstra's Single Source Shortest Path Algorithm to find and print the shortest path between any two given nodes in an undirected graph 
 
  • The algorithm is optimized in its runtime complexity by making use of Fibonacci Heaps to store the graph  
  
<b>Part 2: Routing Scheme: </b>

• For every router in the network, its shortest path from every other router in the network is calculated with the help of implementation in Part 1 
  
  • Each router’s router table is constructed as sets of (Destination Router, Next hop router) 
  
  • These sets are inserted in a Binary Trie and the packets are forwarded to the next hop router using the Longest Prefix Matching Scheme (Care is taken to remove sub-tries from the Binary trie in which the next hop is the same for all destinations by doing a Post-Order Traversal)
