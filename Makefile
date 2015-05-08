JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	FibonacciHeap.java \
	FibHeapNode.java \
	BinTrieNode.java \
	BinTrie.java \
	Graph.java \
	BundleFromDijk.java \
	Dijkstra.java \
	Dijkstra2.java \
	ssp.java \
	routing.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
