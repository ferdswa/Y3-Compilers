package uk.ac.nott.cs.comp3012.coursework.util;

import java.util.ArrayList;

public class Node {
    Node parent;
    String name;
    ArrayList<Node> children;
    public Node(Node parent, String name){
        this.parent = parent;
        this.name = name;
        this.children = new ArrayList<>();
    }
    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Node> getChildren() {
        return children;
    }
    public void addNewChild(String child){
        Node childNode = new Node(child);
        childNode.setName(child);
        childNode.setParent(this);
        this.children.add(childNode);
    }
    public void assignChild(Node child){
        child.setParent(this);
        this.children.add(child);
    }
}
