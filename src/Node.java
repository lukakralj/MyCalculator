public class Node {
    private Object value;
    private Node parent;
    private Node left;
    private Node right;

    public Node(Object value, Node parent, Node leftChild, Node rightChild) {
        this.value = value;
        this.parent = parent;
        left = leftChild;
        right = rightChild;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Object getValue(){
        return value;
    }

    public void setRight(Node v) {
        right = v;
    }

    public void setLeft(Node v) {
        left = v;
    }

    public void setParent(Node v) {
        parent = v;
    }
}
