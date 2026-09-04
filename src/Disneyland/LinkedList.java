package Disneyland;

import java.util.ArrayList;

// This is the linked list containing all the nodes that is connected to a singular node;
public class LinkedList
{
    private Element front;// front pointer of the list

    public Object[] asArray()
    {
        ArrayList<Object> a = new ArrayList<Object>();
        Element e = front;

        while (e != null)
        {//Iterate through elements adding to the array
            a.add(e.DestNode()+":"+e.Weight());
            e = e.Next();
        }
        return a.toArray();
    }

    //Append element to the end of the list
    public void append(int destnode, int weight)
    {
        Element current;//memory location to store current element as list is traversed
        Element tail;//memory location to store last element

        if (front != null)
        {
            current = front;// Start at the front of the LinkedList

            while (current.Next() != null)
            {//Iterate through elements in the LinkedList
                current = current.Next();
            }

            tail = new Element(destnode,weight,null);// Create new tail Element pointing back to the previous Tail
            current.Next(tail);//Update the end of the LinkedList to point to this new Element
        }
        else
        {//Add the front of the linked list
            front = new Element(destnode,weight,null);
        }
    }

    //get all destination nodes of an adjacency list in a linked list
    public ArrayList<Element> getDestNodes()
    {
        ArrayList <Element> nodes = new ArrayList<>();
        Element current = front;
        while(current != null)//traverse through list and add element to the array list
        {
            nodes.add(current);
            current = current.Next();
        }
        return nodes;
    }
}
