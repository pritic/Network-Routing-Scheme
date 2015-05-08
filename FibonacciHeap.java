import java.util.ArrayList;

/**
 * This class represent the Fibonacci heap used by Dijkstra's SSSP algorithm
 */

/**
 * @author PRITI
 *
 */
public class FibonacciHeap {

	//the min pointer of the heap
	FibHeapNode min = null;

	//this method inserts and returns a new node into the heap with specified parameters
	public FibHeapNode insertNode(int val, double dist) 
	{
		FibHeapNode fnode = new FibHeapNode(val, dist); //making a singleton tree
		min = insertActual(fnode, min); //adjusting the min pointer of the heap which could get the new value owing to a new insertion
		return fnode;
	}

	//performs the actual insertion of the node into the heap
	//adds to the root level list; 
	//gives the final pointer after two heaps and/or a node have been merged
	public FibHeapNode insertActual(FibHeapNode fnode1, FibHeapNode fnode2)
	{
		//if either of the nodes have a null value then the other node is returned
		//if both the nodes have null value then null is returned
		//for both non-null nodes, their pointers(prev/next) are adjusted to include both in a circular doubly linked list
		if(fnode1==null)
		{
			if(fnode2==null)
				return null;
			else
				return fnode2;
		}
		else
		{
			if(fnode2==null)
				return fnode1;
			else
			{
				FibHeapNode temp = fnode1.next;
				fnode1.next = fnode2.next;
				fnode1.next.prev = fnode1;
				fnode2.next = temp;
				fnode2.next.prev = fnode2;
				//finding the appropriate min pointer
				if(fnode1.distance < fnode2.distance)
					return fnode1;

				return fnode2;
			}
		}
	}

	//this method merges two heaps into one and returns the new combined heap with the updated min pointer of the resultant heap
	public FibonacciHeap mergeTwoHeaps(FibonacciHeap fh1, FibonacciHeap fh2)
	{
		FibonacciHeap finalHeap = new FibonacciHeap();
		finalHeap.min = insertActual(fh1.min, fh2.min);
		return finalHeap;
	}

	//this method deletes a node from the heap
	public void delete(FibHeapNode fnode)
	{
		decreaseKey(fnode, Double.NEGATIVE_INFINITY);
		deleteMin();
	}

	//this method deletes and returns the min pointer from the heap and updated the new min pointer
	public FibHeapNode deleteMin() {
		if(min==null)
		{
			System.out.println("Error! The heap is empty and has no min to be deleted.");
			return null;
		}

		FibHeapNode minOld = min; //storing the old node

		//checking for siblings and isolating the old min node; assigning new min for the heap
		if (min.next == min) //means no siblings
			min = null;
		else //siblings are present and therefore their pointers are updated to isolate the min node
		{ 
			min.prev.next = min.next;
			min.next.prev = min.prev;
			min = min.next;
		}
		//checking for children of the old min, if any; parent nullified and children nodes added to root level list
		if(minOld.child!=null)
		{
			FibHeapNode temp = minOld.child;
			do
			{
				temp.parent=null;
				temp=temp.next;
			}while(temp!=minOld.child);
		}
		min = insertActual(min, minOld.child); //puts the children in the root level list
		//if there was only one node in the heap, that is deleted and returned as output
		if(min==null)
			return minOld;
		else
		{
			//maintaining the degree table of the heap for pairwise pairing of the same degree root-level sub-trees
			ArrayList<FibHeapNode> degreeList = new ArrayList<FibHeapNode>();
			ArrayList<FibHeapNode> pair = new ArrayList<FibHeapNode>();

			//finding the eligible sub-trees for pairing
			for (FibHeapNode f1 = min; pair.isEmpty() || pair.get(0) != f1; f1 = f1.next)
				pair.add(f1);

			for (FibHeapNode f1: pair) 
			{
				while (true) 
				{
					while (f1.degree >= degreeList.size())
						degreeList.add(null);

					if (degreeList.get(f1.degree) == null) 
					{
						degreeList.set(f1.degree, f1);
						break;
					}

					FibHeapNode temp1 = degreeList.get(f1.degree);
					degreeList.set(f1.degree, null); 

					//finding the min and max based on the distances 
					FibHeapNode min1 = (temp1.distance < f1.distance)? temp1 : f1;
					FibHeapNode max = (temp1.distance < f1.distance)? f1  : temp1;

					//isolating the max node
					max.next.prev = max.prev;
					max.prev.next = max.next;
					//making it a singleton tree/sub-tree
					max.next = max.prev = max;
					min1.child = insertActual(min1.child, max);
					//making the max a child of the min
					max.parent = min1;
					//updating its child cut value
					max.ccValue = false;
					//since one node gets added as the child of the min, its degree is increased by one
					min1.degree=min1.degree+1;

					f1 = min1;
				}
				
				if (f1.distance <= min.distance) min = f1;
			}
			return minOld;
		}

	}

	//this method decrease the distance of a fibonacci node to a new distance value; used by Dijkstra's algorithm to decrease distances from infinity to double values
	public void decreaseKey(FibHeapNode fnode, double newDist) 
	{
		fnode.distance = newDist;
		//checking if the heap property is violated due to decrease in the key value
		if(fnode.parent!=null && fnode.distance<=fnode.parent.distance)
			cascadingCut(fnode);
		//updating the new min pointer if any change happens due to decrease in the key value
		if(fnode.distance<min.distance)
			min = fnode;

	}

	//this method is called to restore the heap to its standard structure when decreasekey violates the heap property
	public void cascadingCut(FibHeapNode fnode) 
	{
		//setting the child cut value of the node to false
		fnode.ccValue = false;
		
		//if no parents
		if (fnode.parent == null) return;


		//cut ties from siblings if any
		if(fnode.next!=fnode)
		{
			fnode.next.prev = fnode.prev;
			fnode.prev.next = fnode.next;
		}

		//if the node has a parent 
		if(fnode.parent.child==fnode)
		{
			//if the node has siblings; isolated the node and make its parent point to another child(sibling of the fnode)
			if(fnode.next!=fnode)
			{
				fnode.parent.child=fnode.next;
			}
			else 
			{
				//make the parent pointer null
				fnode.parent.child=null;
			}
		}

		//since the node is cute from its parent, its degree is reduced by one
		fnode.parent.degree = fnode.parent.degree-1;
		//making a single circular doubly linked list with the node
		fnode.next = fnode.prev = fnode;
		//updating the min pointer of the heap
		min = insertActual(min,fnode);
		//repeat the cascading cut if the parent had lost a child in the future and now has again lost one above
		if(fnode.parent.ccValue==true)
			cascadingCut(fnode.parent);
		else
			//else set the parent's child cut value to true due to child loss
			fnode.parent.ccValue=true;
		//nullify the node's pointer towards its parent
		fnode.parent=null;
	}

}
