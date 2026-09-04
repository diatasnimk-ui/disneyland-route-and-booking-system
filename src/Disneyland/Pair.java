package Disneyland;

public class Pair
    //Implement the adjacency list comprised of parent node and linked list of destination nodes
{
    private int sourceNode; //key
    private LinkedList list = new LinkedList();  //value

    public Pair(int sourceNode, LinkedList list)// constructor to instantiate a new pair
    {
        this.list = list;
        this.sourceNode = sourceNode;
    }

    public void SetSourceNode( int sourceNode)
    {
        this.sourceNode = sourceNode;
    } //setter to set a source node of a pair into given source node
    public LinkedList List()
    {
        return list;
    }//getter to return a list of destination nodes of a pair
    public int SourceNode()
    {
        return sourceNode;
    }//getter to return the source node of a pair
    public void SetList(LinkedList list)
    {
        this.list = list;
    }//setter to set the list of a pair
}
