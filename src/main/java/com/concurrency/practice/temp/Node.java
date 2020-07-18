package com.concurrency.practice.temp;

/**
 * @author altenchen
 * @time 2020/7/18
 * @description 功能
 */
public class Node {
    
    private int i;
    private Node n;
    
    Node(int i) {
        this.i = i;
    }
    
    Node(Node n, int i) {
        this.i = i;
        this.n = n;
    }
    
    @Override
    public String toString() {
        return i + "";
    }
    
    public static void main(String[] args) {
        Node nn = new Node(0);
        Node n1 = new Node(nn, 1);
        Node n2 = n1;
    
//        n1.n = n1 = new Node(nn, 3);
        n1 = new Node(nn, 3);
        n1.n = n1;
        
        System.out.println("n1: " + n1.toString() + n1.n.toString());
        System.out.println("n2: " + n2.toString() + n2.n.toString());
    }
}
