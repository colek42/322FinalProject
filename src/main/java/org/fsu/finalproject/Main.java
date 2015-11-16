package org.fsu.finalproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        int size, j;

        System.out.print("Enter number of items: ");
        size = getInt();
        Heap theHeap = new Heap(size);

        for (j = 0; j < size; j++) {
            int random = (int) (java.lang.Math.random() * 100);
            Node2 newNode = new Node2(random);
            theHeap.insertAt(j, newNode);
            theHeap.incrementSize();

        }

        System.out.print("Random: ");
        theHeap.visHeap("Input");
        theHeap.displayArray();

        for (j = size / 2 - 1; j >= 0; j--) {
            theHeap.trickleDown(j);

        }

        theHeap.visHeap("Heap");

        for (j = size - 1; j >= 0; j--) {
            Node2 biggestNode = theHeap.remove();
            theHeap.insertAt(j, biggestNode);
        }

        System.out.print("Sorted: ");
        theHeap.displayArray();

        theHeap.visHeap("Sorted");
    }

    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    public static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }

}
