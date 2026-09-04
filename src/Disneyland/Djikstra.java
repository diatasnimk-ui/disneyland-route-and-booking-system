package Disneyland;
import java.util.ArrayList;

public class Djikstra {

    //Class that uses djikstra’s shortest path algorithm in order to return the list of nodes in the shortest route as well as value of the shortest distance.

    //Method to find and return list of nodes to go through from specified source node, to specified end node in specified graph.
    public String Path(int endnode, Graph graph, int sourceNode)
    {
        PriorityQueue pq = new PriorityQueue();//instantiate priority queue to aid with traversing the graph(closest first)
        Hashing parentNodes = new Hashing();
        ArrayList<Integer> graphNodes = graph.getNodes();//array list of all source nodes
        for(int i = 0; i< graph.len(); i++)//iterate through graph
        {
            if(!(graphNodes.get(i).equals(sourceNode)))
            {
                //add every node in created array list and set their distance from given sourceNode to infinity
                pq.add(graphNodes.get(i), Integer.MAX_VALUE);
            }
            else
            {
                //if it is the source node, the distance to itself is zero
                pq.add(graphNodes.get(i), 0);
            }
            parentNodes.setParentNode(graphNodes.get(i),graphNodes.get(i));
        }

        ArrayList<Integer> visited = new ArrayList<>();//create list to keep track of visited nodes
        while(!(pq.isEmpty()))
        {
            Node node = pq.popNode();//pops front of queue (node closest to source)
            ArrayList<Element> elements = graph.getDestNodes(node.Value());
            //itterate through all the elements in the linked list of destination nodes
            for(int i = 0; i< elements.size(); i++)
            {
                //calculate distance and update queue if priority/distance changes and if it is shorter than previous node
                if(!(visited.contains(elements.get(i).DestNode())))
                {
                    int oldDist = pq.getDistance(elements.get(i).DestNode());
                    int newDistance = node.Priority() + elements.get(i).Weight();
                    if(newDistance < oldDist)
                    {
                        pq.changePriority(elements.get(i).DestNode(), newDistance);
                        parentNodes.setParentNode(elements.get(i).DestNode(),node.Value());
                    }
                }

            }
            //end of iteration, the node is fully visited and added to list of visited nodes
            visited.add(node.Value());

        }

        // Collect nodes in the path
        ArrayList<Integer> nodesInPath = new ArrayList<>();
        nodesInPath.add(endnode);
        int previousNode = parentNodes.getParent(endnode);
        while (previousNode != sourceNode) {
            nodesInPath.add(previousNode);
            previousNode = parentNodes.getParent(previousNode);
        }
        nodesInPath.add(sourceNode);

        // Build the string in reverse order
        StringBuilder path = new StringBuilder();
        for (int i = nodesInPath.size() - 1; i >= 0; i--) {
            path.append(nodesInPath.get(i));
            if (!(i == 0))
            { // Add a comma and space if not the last element for formatting
                path.append(", ");
            }
        }
        //To display the route
        return path.toString();
    }

    public int pathLength(int endnode, Graph graph, int sourceNode)
    {
        //uses same process as Path method but keeps tracks and adds all shortest distance from source to destination
        int shortestD = 0;//shortest distance form source to dest initalised
        PriorityQueue pq = new PriorityQueue();

        ArrayList<Integer> graphNodes = graph.getNodes();
        //iterate and calculate through graph (same process as above)
        for(int i = 0; i< graph.len(); i++)
        {
            if(!graphNodes.get(i).equals(sourceNode))
            {
                pq.add(graphNodes.get(i), Integer.MAX_VALUE);
            }
            else
            {
                pq.add(graphNodes.get(i), 0);
            }
        }
        ArrayList<Integer> visited = new ArrayList<>();

        while(!(pq.isEmpty()))
        {
            Node node = pq.popNode();
            ArrayList<Element> elements = graph.getDestNodes(node.Value());
            for(int i = 0; i< elements.size(); i++)
            {
                if(!(visited.contains(elements.get(i).DestNode())))
                {
                    int oldDist = pq.getDistance(elements.get(i).DestNode());
                    int newDistance = node.Priority() + elements.get(i).Weight();
                    if(newDistance < oldDist)
                    {
                        pq.changePriority(elements.get(i).DestNode(), newDistance);
                        if(elements.get(i).DestNode()==(endnode))//once destination is reached
                        {
                            shortestD = newDistance;
                        }
                    }
                }

            }
            visited.add(node.Value());

        }
        return shortestD;//output final total shortest distance
    }
}