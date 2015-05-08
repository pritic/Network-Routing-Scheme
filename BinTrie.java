public class BinTrie
{
    private BinTrieNode root;
    
	//Searching a word
    public boolean search(String str1)
    {
        BinTrieNode present = root;  
        for (char ch : str1.toCharArray() )
        {
            if (present.getC(ch) == null)
                return false;
            else
                present = present.getC(ch);
        }      
        if (present.isEnd() == true) 
            return true;
        return false;
    }
    
 
     /* Constructor */
    public BinTrie()
    {
        root = new BinTrieNode(' ',"ROOT","ROOT"); 
        
    }
    
    //Searching a word
      public String prefixMatch(String word)
      {
          BinTrieNode present = root;  
          for (char ch : word.toCharArray() )
          {
              if (present.getC(ch) == null)
                  return present.resPrefix;
              else
                  present = present.getC(ch);
          }      
           
              return present.resPrefix;
          
      }
      
      public BinTrieNode prefixMatNode(String word)
      {
          BinTrieNode present = root;  
          for (char ch : word.toCharArray() )
          {
              if (present.getC(ch) == null)
                  return present;
              else
                  present = present.getC(ch);
          }      
           
              return present;
          
      }
        
     /* Function to insert word */
    public void insert(String key,String value)
    {	
    	boolean foo = false;
    	BinTrieNode prevNode = null;

        if (search(key) == true) 
            return;        
   
        StringBuilder match = new StringBuilder();
        
        BinTrieNode current = root; 
        for (char ch : key.toCharArray() )
        {
        	match.append(ch);
            
        	BinTrieNode child = current.getC(ch);
         
        	if (child != null){
            	if(!child.isEnd()){
            		current = child;
            	}
            	else{
            		prevNode = child;
            		foo=true;
            		current.addC(ch,key,value);
                    current = current.getC(ch);
                    current.setNValue("");
            	}
                
            }
            else 
            {
                 current.addC(ch,key,value);
                
                 current = current.getC(ch);
                 
                 break;
            }
            current.count++;
        }
        current.setEnd(true);
        if("".equalsIgnoreCase(current.getNValue()))
        	current.setNValue(value);
        
        if(foo==true){
        	  insert(prevNode.getNKey(),prevNode.getNValue());
        }
    }
    
    
    public BinTrie poMerge(){
    	actualMerge(root);
    	return this;
    	
    }
 
    
    private BinTrieNode actualMerge(BinTrieNode node) {
    	
    	if(node==null || node.isEnd() ){
    		return node;
    	}
		
    	BinTrieNode leftChild = actualMerge(node.getLeftC());
    	BinTrieNode rightChild = actualMerge(node.getRightC());
    	
    	if(leftChild!=null && rightChild!= null && leftChild.isEnd() && rightChild.isEnd() && leftChild.getNValue().equalsIgnoreCase(rightChild.getNValue())){
    		node.setNValue(rightChild.getNValue());
    		node.setLeftC(null);
    	    node.setRightC(null);
    	    node.setEnd(true);
    	    
    	}// if left child is not null but right child is null then delete left child and pass its value to parent
    	else if(leftChild!= null && rightChild == null && leftChild.isEnd()){
    		node.setNValue(leftChild.getNValue());
    		node.setLeftC(null);
    	   // node.setOneChild(null);
    	    node.setEnd(true);
    	}// if right child is not null but left child is null then delete left child and pass its value to parent
    	else if(leftChild == null && rightChild != null && rightChild.isEnd()){
    		node.setNValue(rightChild.getNValue());
    		node.setLeftC(null);
    	    node.setRightC(null);
    	    node.setEnd(true);
    	}
    	
    	return node;
	} 
   
}    
 