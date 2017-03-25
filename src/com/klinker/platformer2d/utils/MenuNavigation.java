package com.klinker.platformer2d.utils;

import java.util.HashMap;

public class MenuNavigation<T> {

    private HashMap<T, Node> nodes;

    public MenuNavigation() {
        this.nodes = new HashMap<T, Node>();
    }

    public void insertNode(T item, T left, T up, T right, T down) {
        // Add this node.
        Node node = new Node(left, up, right, down);
        nodes.put(item, node);

        // Add the other nodes if necessary
        if (!nodes.containsKey(left)) nodes.put(left, null);
        if (!nodes.containsKey(up)) nodes.put(up, null);
        if (!nodes.containsKey(right)) nodes.put(right, null);
        if (!nodes.containsKey(down)) nodes.put(down, null);
    }

    public T getLeft(T item) {
        Node node = nodes.get(item);
        if (node != null) return node.left;
        else return null;
    }

    public T getUp(T item) {
        Node node = nodes.get(item);
        if (node != null) return node.up;
        else return null;
    }

    public T getRight(T item) {
        Node node = nodes.get(item);
        if (node != null) return node.right;
        else return null;
    }

    public T getDown(T item) {
        Node node = nodes.get(item);
        if (node != null) return node.down;
        else return null;
    }

    private class Node {
        public T left, up, right, down;
        public Node(T left, T up, T right, T down) {
            this.left = left;
            this.up = up;
            this.right = right;
            this.down = down;
        }
    }

}
