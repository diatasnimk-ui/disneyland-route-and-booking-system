package Disneyland;

public class Element
{
    //to be stored in linked list of destination nodes and their distance from their parent node (making the adjacency list)
    private int destnode;//ID of destination node
    private int weight;//distance from source
    private Element next;//pointer to next element in linked list

    public Element(int destnode, int weight, Element next)//constructor to instantiate an element for linked list
    {
        this.destnode = destnode;
        this.weight = weight;
        this.next = next;
    }

    //get the value of destination nodes/distance(weight)/next pointer
    public int DestNode()
    {
        return destnode;
    }
    public int Weight()
    {
        return weight;
    }
    public Element Next()
    {
        return next;
    }

    //sets the attribute to specified values
    public void DestNode(int destnode)
    {
        this.destnode = destnode;
    }
    public void Weight(int weight)
    {
        this.weight = weight;
    }
    public void Next(Element destnode)
    {
        next = destnode;
    }
}