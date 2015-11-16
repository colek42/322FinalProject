package org.fsu.finalproject;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Heap {

    private Node2[] heapArray;
    private int maxSize;
    private int currentSize;

    public Heap(int mx) {
        maxSize = mx;
        currentSize = 0;
        heapArray = new Node2[maxSize];
    }

    public Node2 remove() {
        Node2 root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        trickleDown(0);
        return root;
    }

    public void trickleDown(int index) {
        int largerChild;
        Node2 top = heapArray[index];
        while (index < currentSize / 2) {
            int leftChild = 2 * index + 1;
            int rightChild = leftChild + 1;
            if (rightChild < currentSize
                    && heapArray[leftChild].getKey()
                    < heapArray[rightChild].getKey()) {
                largerChild = rightChild;
            } else {
                largerChild = leftChild;
            }

            if (top.getKey() >= heapArray[largerChild].getKey()) {
                break;
            }

            heapArray[index] = heapArray[largerChild];
            index = largerChild;

            heapArray[index] = top;
        }

    }

    public void visHeap(String name) {
        Graph graph = new SingleGraph(name);

        graph.addAttribute("ui.stylesheet",
                "node {\n"
                + "    size: 40px, 40px;\n"
                + "    shape: circle;\n"
                + "    fill-color: green;\n"
                + "    stroke-mode: plain;\n"
                + "    stroke-color: black;\n"
                + "    text-style: bold;\n"
                + "    text-size: 12;\n"
                + "}");
        
        graph.display(false);

        int row = 1;
        int itemsPerRow = 1;
        int itemsInRow = 0;
        float currDistance = 0;

        for (int i = 0; i < heapArray.length; i++) {
            if (itemsInRow == 1) {
                currDistance = 1;
            }

            if(row>1){
                currDistance += 1 * (6/row);
            }else{
                currDistance = (float) 3.5;
            }
            int item = heapArray[i].getKey();
            graph = addNext(item, i, row, currDistance, graph);

            if (++itemsInRow == itemsPerRow) {
                itemsPerRow *= 2;
                itemsInRow = 0;
                currDistance = 0;
                row++;

            }

        }

        Node node = graph.addNode("title");
        node.addAttribute("ui.label", name);
        node.addAttribute("y", "-" + Integer.toString(row + 1));
        node.addAttribute("x", Integer.toString(3.5));

    }

    public Graph addNext(int item, int position, int row, float xDist, Graph graph) {

        int parent = (int) Math.floor((position - 1) / 2);
        String parentString = Integer.toString(parent);
        String nodeString = Integer.toString(position);

        Node node = graph.addNode(nodeString);
        node.addAttribute("ui.label", item);
        node.addAttribute("y", "-" + Integer.toString(row));
        node.addAttribute("x", Float.toString(xDist));

        if (row > 1) {
            graph.addEdge(parentString + ":" + nodeString, parentString, nodeString);
        }
        return graph;
    }

    public void displayArray() {
        for (int j = 0; j < maxSize; j++) {
            System.out.print(heapArray[j].getKey() + " ");
        }
        System.out.println("");
    }

    public void insertAt(int index, Node2 newNode) {
        heapArray[index] = newNode;
    }

    public void incrementSize() {
        currentSize++;
    }

}
