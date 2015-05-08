/**
 * 
 */

/**
 * @author PRITI
 *
 */
class BinTrieNode 
{
	String nKey;
	String nValue;
	
	String resPrefix;
	
    char chr; 
    boolean flag1; 

    int count;  
    BinTrieNode leftC;
    
   
	BinTrieNode rightC;
	
	BinTrieNode parent;
	
	
    public BinTrieNode getRightC() {
		return rightC;
	}
	public void setRightC(BinTrieNode ch1) {
		this.rightC = ch1;
	}
	
    
   // TrieNode[] childList; 
    
	 public BinTrieNode getLeftC() {
			return leftC;
		}
	 
	public void setLeftC(BinTrieNode zeroChild) {
			this.leftC = zeroChild;
		}
 
    /* Constructor */
    public BinTrieNode(char c,String key,String value)
    {
        setEnd(true);
        chr = c;
        count = 0;
        leftC = null;
        rightC = null;
        nKey = key;
        nValue = value;
        resPrefix = "";
    }  
    public BinTrieNode getC(char c)
    {
    		switch (c) {
    		case '0' : return leftC;
    		case '1' : return rightC;
    		}
    		
    		if(c=='0')
    			return leftC;
    		else 
    			return rightC;
    }
    
    public void addC(char c,String key, String value){
    	BinTrieNode newChild = new BinTrieNode(c,key,value);
    	newChild.setParent(this);
    	newChild.setResPrefix(resPrefix+c);
    	
    	String parentValue = value;

    	if(c=='0'){
    		
    		this.leftC =newChild;		
    	}
    	else{
    		this.rightC = newChild;
    	}
    	
    	if(this.isEnd()==true ){
    		this.setEnd(false);
    	}
    		
    }

	public BinTrieNode getParent() {
		return parent;
	}

	public void setParent(BinTrieNode parent) {
		this.parent = parent;
	}
	public String getNValue() {
		return nValue;
	}
	public void setNValue(String nodeValue) {
		this.nValue = nodeValue;
	}
	public String getNKey() {
		return nKey;
	}
	public void setNKey(String nodeKey) {
		this.nKey = nodeKey;
	}
	public String getResPrefix() {
		return resPrefix;
	}
	public void setResPrefix(String matchedPrefix) {
		this.resPrefix = matchedPrefix;
	}
	public boolean isEnd() {
		return flag1;
	}
	public void setEnd(boolean isEnd) {
		this.flag1 = isEnd;
	}
}
