package Disneyland;

import java.util.ArrayList;
public class PriorityQueue //implements Queue
{
    ArrayList<Node>queue;
    Node front;// pointer for first item; queue will remove from front
    Node back;// pointer for last item; queue will add fto back
    int count = 0; //counts number of 'active' elements in array

    //NOTE: Priority of queue goes from lowest (higher priority) distance to highest(low priority)

    public PriorityQueue()// constructor instantiating empty queue of nodes
    {
        //queue = new ArrayList<Integer>();
        this.front = null;
        this.back = null;
    }

    //to add new node in queue
    public void add(int value, int priority)
    {
        Node newNode = new Node(value,priority);// instantiate a new node object
        if (isEmpty()) //if empty, new node is front and back
        {
            front = newNode;
            back = newNode;
        }
        else
        {//check and compare priorities with existing nodes
            if (newNode.Priority() < front.Priority() )// new node with higher priority are placed before the less priority
            {
                newNode.setNext(front);
                front = newNode;
            }
            else
            {
                Node current = front;
                while(current.Next() != null && current.Next().Priority() <= priority)//traverse queue until node with higher priority is found and place there
                {
                    current = current.Next();
                }
                //change/set the next pointers of added node and previous nodes
                newNode.setNext(current.Next());
                current.setNext(newNode);
                if(newNode.Next() == null)
                {
                    back = newNode;
                }
            }
        }
        count++;//increment number of active elements

    }

    //remove and return the front of the queue
    public Node popNode()
    {
        if(isEmpty())
        {
            throw new UnsupportedOperationException();//queue is already empty so cannot remove
        }
        Node toReturn = front;
        if(front == back)
        {
            front = null;// if it was last item in queue, make queue empty by setting pointers to null
            back = null;
        }
        else
        {
            front = front.Next();//change the next pointer to the next node in queue

        }
        count--; //decrement number of active element in queue
        return(toReturn);
    }

    //removes a node from queue
    public void remove(int value)
    {
        if(isEmpty())//check if empty and throw exception
        {
            throw new UnsupportedOperationException();
        }

        if (!contains(value))//check if node is not in queue then throw error/exception
        {
            throw new IllegalArgumentException("Node not in queue");
        }

        Node previous = null;//temporary location to store the node to remove to fix positions/pointers in queue
        Node current = front;
        while(!(current.Value()==(value)))//traverse queue
        {
            previous = current;
            current = current.Next();
            /*if(current == null)//if end of queue is reached, then node is not in queue
            {
                throw new IllegalArgumentException("Node not in queue");
            }*/
        }

        count--;//decrement number of active element
        if(current == front)
        {
            front = current.Next();//set new front pointer to next node
        }
        else
        {
            previous.setNext(current.Next());//modify next pointer of the previous node in queue to the element after the node to be removed
            current.setNext(null);
        }

    }


    //Check if queue is empty by checking front pointers
    public boolean isEmpty()
    {
        if (front == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Node Front(){ return front;}// return front node of queue
    public Node Rear(){ return back;}//return back node of queue

    public void printQueue()
    {
        System.out.println("Your Queue: ");
        Node current = front;
        while(current != null)
        {
            System.out.println(current.Value()+" ");
            current = current.Next();
        }
        System.out.println();
    }

    //check if the given node (value) exists in queue; returns boolean
    public boolean contains(int value)
    {
        boolean found = false;
        for(Node i = front; i != null; i = i.Next()) //traverse queue until the node with given value is found
        {
            if (i.Value()==value)//if it is found
            {
                found = true;
                break;
            }
        }
        return found;
    }

    //returns the full node object of the node with specified value
    public Node getNode(int value)
    {
        if(!contains(value)) // check if node is present in queue
        {
            throw new UnsupportedOperationException("Node does not exist");
        }
        Node current = front;
        while(current != null&&!(current.Value()==value ))//traverse the queue until node is found
        {
            current = current.Next();
        }
        return current;//return the node object
    }


    //changes priority in queue of a given node into the specified priority/distance
    public void changePriority(int value, int newDistance)
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException();
        }
        this.remove(value);
        this.add(value,newDistance);
    }

    //retrieves priority/distance of a node
    public int getDistance(int value)
    {
        return this.getNode(value).Priority();
    }
}