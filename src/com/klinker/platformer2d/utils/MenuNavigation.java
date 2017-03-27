package com.klinker.platformer2d.utils;

import com.klinker.engine2d.gui.Button;

import java.util.HashMap;

public class MenuNavigation<T> {

    private HashMap<T, Node> nodes;
    private T selected;
    private OnItemSelectedListener<T> selectedListener;

    public interface OnItemSelectedListener<T> {
        void onItemSelected(T oldItem, T newItem);
    }

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

    public T getLeft() {
        Node node = nodes.get(selected);
        if (node != null) return node.left;
        else return null;
    }

    public T getUp() {
        Node node = nodes.get(selected);
        if (node != null) return node.up;
        else return null;
    }

    public T getRight() {
        Node node = nodes.get(selected);
        if (node != null) return node.right;
        else return null;
    }

    public T getDown() {
        Node node = nodes.get(selected);
        if (node != null) return node.down;
        else return null;
    }

    public void select(T newSelected) {
        if (selectedListener != null) selectedListener.onItemSelected(selected, newSelected);
        this.selected = newSelected;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> listener) {
        this.selectedListener = listener;
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
