package Disneyland;

import java.util.ArrayList;

public class Graph
{
    Hashing graph = new Hashing();// graph is made from hashtable

    //add all nodes and edges into hash table to make the graph
    public void add(int sourceNode, int destNode, int weight)
    {
        if(!graph.contains(sourceNode))//if node isn't already in hash table
        {
            graph.add(sourceNode, new LinkedList());//add pair of the node and empty linked list to hash table

        }
        graph.item(sourceNode).append(destNode,weight);//add destination nodes and weight in linked list of the source/parent node
    }



    //return number of item in hashtable/graph
    public int len()
    {
        return graph.length();
    }

    //gets all parent/source nodes in an array
    public ArrayList<Integer> getNodes()
    {
        ArrayList<Integer> nodes = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < graph.length(); i++) //iterate through and only add source node to array list
        {
            Pair pair = graph.getHashtable()[j];
            if (pair != null) {
                nodes.add(pair.SourceNode());
            }
            else
            {
                i--;
            }
            j++;
        }
        return nodes;
    }

    //returns the list of destination nodes of a parent node
    public ArrayList<Element> getDestNodes(int value)
    {
        return graph.getDestNodes(value);
    }
}