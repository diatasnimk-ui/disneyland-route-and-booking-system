package Disneyland;

public class Node
    //implement each node in queue as their own object
{
    int value;//Stores ID of each attraction
    int priority;//Stores distance from source
    Node next;//pointer to

    public Node(int value, int priority)//constructor to instantiate
    {
        this.value = value;
        this.priority = priority;
    }
    //getter to return priority, value or next of the node
    public int Priority()
    {
        return priority;
    }

    public int Value()
    {
        return value;
    }

    public Node Next()
    {
        return next;
    }

    //setter to set the value/priority/next pointer of a node into given values.

    public void SetPriority( int priority)
    {
        this.priority = priority;
    }

    public void SetValue(int value)
    {
        this.value = value;
    }

    public void setNext(Node next)
    {
        this.next = next;
    }
}
