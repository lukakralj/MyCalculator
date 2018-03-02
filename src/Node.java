public class Node {
    private Object value;
    private Node parent;
    private Node left;
    private Node right;

    public Node(Object value, Node leftChild, Node rightChild) {
        this.value = value;
        parent = null;
        left = leftChild;
        right = rightChild;
        if (leftChild != null) {
            leftChild.setParent(this);
        }
        if (rightChild != null) {
            rightChild.setParent(this);
        }
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

    public Object element(){
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
