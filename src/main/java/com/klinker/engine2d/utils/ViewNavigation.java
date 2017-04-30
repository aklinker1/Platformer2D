package com.klinker.engine2d.utils;

import com.klinker.engine2d.gui.View;

import java.util.HashMap;

public class ViewNavigation {

    private HashMap<View, Node> nodes;
    private View selected;
    private OnItemSelectedListener selectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(View oldItem, View newItem);
    }

    public ViewNavigation() {
        this.nodes = new HashMap<View, Node>();
    }

    public void insertNode(View item, View left, View up, View right, View down) {
        // Add this node.
        Node node = new Node(left, up, right, down);
        nodes.put(item, node);

        // Add the other nodes if necessary
        if (!nodes.containsKey(left)) nodes.put(left, null);
        if (!nodes.containsKey(up)) nodes.put(up, null);
        if (!nodes.containsKey(right)) nodes.put(right, null);
        if (!nodes.containsKey(down)) nodes.put(down, null);
    }

    private View getLeft() {
        Node node = nodes.get(selected);
        if (node != null) return node.left;
        else return null;
    }

    private View getUp() {
        Node node = nodes.get(selected);
        if (node != null) return node.up;
        else return null;
    }

    private View getRight() {
        Node node = nodes.get(selected);
        if (node != null) return node.right;
        else return null;
    }

    private View getDown() {
        Node node = nodes.get(selected);
        if (node != null) return node.down;
        else return null;
    }

    public void left() {
        View left = getLeft();
        if (left != null) {
            select(left);
        }
    }

    public void up() {
        View up = getUp();
        if (up != null) {
            select(up);
        }
    }

    public void right() {
        View right = getRight();
        if (right != null) {
            select(right);
        }
    }

    public void down() {
        View down = getDown();
        if (down != null) {
            select(down);
        }
    }

    public void select(View newSelected) {
        if (selectedListener != null) selectedListener.onItemSelected(selected, newSelected);
        this.selected = newSelected;
        this.selected.selected();
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.selectedListener = listener;
    }

    private class Node {
        public View left, up, right, down;
        public Node(View left, View up, View right, View down) {
            this.left = left;
            this.up = up;
            this.right = right;
            this.down = down;
        }
    }

}
