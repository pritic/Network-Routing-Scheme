/**
 * This class represent the Fibonacci node
 */

/**
 * @author PRITI
 *
 */
public class FibHeapNode {
	//four pointers
	FibHeapNode next = null; //sibling node
	FibHeapNode prev = null; //sibling node
	FibHeapNode parent = null; //parent node
	FibHeapNode child = null; //child node
	
	boolean ccValue = false; //default child-cut value
	
	int degree, key; //no. of children and node value respectively
	
	double distance; //distance of the node from the source
	
	FibHeapNode(int k, double dist) //constructor
	{
		this.key = k;
		next = prev = this;
		this.degree = 0;
		this.distance = dist;
	}
	
	public boolean getccValue()
	{
		return this.ccValue;
	}
	
	//sets the child cut value of the calling instance to val
	public void setccValue(boolean val)
	{
		this.ccValue = val;
	}
	
	public int getKey()
	{
		return this.key;
	}
	

}
