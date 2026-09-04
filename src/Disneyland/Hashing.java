package Disneyland;

import java.util.ArrayList;
import java.util.Arrays;

public class Hashing
{
    private final int MAX_SIZE = 50;//set size of hash table
    private int length = 0;//keeps track of the number of items in table
    private Pair hashtable[] = new Pair[MAX_SIZE];//create hash table (array of pairs) of that size
    private int[] parentNode = new int[MAX_SIZE];//stores all parent nodes

    public String asString()
    {
        StringBuilder adjList = new StringBuilder("");
        for(int i = 0;i < MAX_SIZE;i++)
        {
            if(hashtable[i]!= null)
            {
                adjList.append(hashtable[i].SourceNode()).append(": ").append(Arrays.toString(hashtable[i].List().asArray())).append("\n");
            }
        }
        return adjList.toString();
    }

    //add a new adjacency list(pair of source node and linked list of destination nodes with distance) to the hashtable
    public void add(int sourceNode, LinkedList list)
    {
        Pair newPair = new Pair(sourceNode,list);//instantiate new pair
        int address = Math.abs(sourceNode % MAX_SIZE);//calculate address
        int origAddress = address;//stores address calculated in case of collisions
        if(contains(sourceNode) == true)//check if node exists in hash table
        {
            throw new IllegalArgumentException("Node is already added on the list");
        }
        if(length == MAX_SIZE)//check if hash table is full
        {
            throw new UnsupportedOperationException("No space available");
        }


        boolean emptySpace = false;
        while(emptySpace == false)
        {
            if(hashtable[address] == null)
            {
                hashtable[address] = newPair;//if the space in hashtable is empty, store the pair at that address
                length++;
                break;
            }
            else//otherwise iterate through the list until empty space found
            {
                address++;
                if(address == MAX_SIZE)
                {
                    for(address = 0; address < origAddress; address++)
                    {
                        if(hashtable[address] == null)
                        {
                            emptySpace = true;
                            break;
                        }
                    }
                    if(emptySpace == true)
                    {
                        hashtable[address] = newPair;
                        length++;
                        break;
                    }
                }
            }
        }

    }


     //returns the linked list of dest nodes of a parent node
    public LinkedList item(int sourceNode)
    {
        if (contains(sourceNode)==false)
        {
            throw new UnsupportedOperationException("Key does not exist.");
        }
        boolean found = false;
        //same method to calculate address
        int address = Math.abs(sourceNode % MAX_SIZE);
        int origAddress = address;
        //same method to deal with collision by iterating through hashtable until the pair with given source node is found
        while (found == false)
        {
            if (hashtable[address] != null && hashtable[address].SourceNode() == sourceNode)
            {
                found = true;
                break;
            }
            else
            {
                address++;
                if(address == MAX_SIZE)
                {
                    for(address = 0; address< origAddress; address++)
                    {
                        if(hashtable[address].SourceNode()==(sourceNode))
                        {
                            found = true;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        LinkedList value = null;
        if(found == true)
        {
            value = hashtable[address].List();
        }
        return value;
        //return the list
    }

   /* public void delete(int sourceNode)
    {
        if(contains(sourceNode) == false)
        {
            throw new IllegalArgumentException("Node does not exist");
        }
        int address = Math.abs(sourceNode % MAX_SIZE);
        int origAddress = address;
        boolean found = false;
        while(found == false)
        {
            if(hashtable[address].SourceNode()==(sourceNode))
            {
                hashtable[address]= null;
                length--;
                break;
            }
            else
            {
                address++;
                if(address == MAX_SIZE)
                {
                    for(address = 0;address < origAddress; address++)
                    {
                        if(hashtable[address].SourceNode()==(sourceNode))
                        {
                            hashtable[address]= null;
                            length--;
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }*/


    //check if a pair exists based on given source node
    public boolean contains(int sourceNode)
    {
        //same method to calculate address and iterate through table until the given node is found
        int address = Math.abs(sourceNode % MAX_SIZE);
        int origAddress = address;
        boolean found = false;
        while (found == false) {
            Pair item = hashtable[address];
            if (item == null)
            {
                for(address = 0;address < origAddress;address++)
                {
                    item = hashtable[address];
                    if(item!= null && item.SourceNode()==(sourceNode))
                    {
                        found = true;
                        break;
                    }
                }
                break;
            }
            else if (item.SourceNode()==(sourceNode)) {
                found = true;
                break;
            }
            else {
                address++;
                if (address == MAX_SIZE) {
                    for(address = 0;address < origAddress;address++)
                    {
                        item = hashtable[address];
                        if(item != null && item.SourceNode()==(sourceNode))
                        {
                            found = true;
                            break;
                        }
                    }
                    break;

                }
            }

        }
        //true if it is found, false if not found
        return found;
    }

    //returns length/ number of pairs in hashtable
    public int length()
    {
        return length;
    }

    public Pair[] getHashtable()
    {
        return hashtable;

    }

    //checks if hash table is empty
    public boolean isEmpty()
    {
        if(length == 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    /*public Pair getPair(int sourceNode) {
        int address = Math.abs(sourceNode % MAX_SIZE);
        int origAddress = address;

        while (true)
        {
            if (hashtable[address] != null && hashtable[address].SourceNode()==(sourceNode)) {
                return hashtable[address];
            }

            address++;
            if (address == MAX_SIZE) {
                address = 0;
            }
            if (address == origAddress) {
                break;
            }
        }

        return null;
    }*/

    //get all the dest nodes in one array of element(same as in graph)
    public ArrayList<Element> getDestNodes(int value)
    {
        ArrayList<Element> nodes = new ArrayList<>();
        int address = Math.abs(value % MAX_SIZE);
        int origAddress = address;
        while (true) {
            if (hashtable[address] != null && hashtable[address].SourceNode()==(value))
            {
                return hashtable[address].List().getDestNodes();
            }

            address++;
            if (address == MAX_SIZE) {
                address = 0;
            }
            if (address == origAddress) {
                break;
            }
        }
        nodes = hashtable[address].List().getDestNodes();
        return nodes;
    }

    //set parent nodes/source node
    public void setParentNode(int key, int parent)
    {
        int position = Math.abs(key %MAX_SIZE);
        parentNode[position] = parent;
    }

    //get parent node/source node
    public int getParent(int key)
    {
        int position = Math.abs(key %MAX_SIZE);
        return parentNode[position];
    }

}