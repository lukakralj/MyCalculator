import com.sun.istack.internal.NotNull;

public class BinaryTree {
    private int size;
    private Node root;

    public BinaryTree(Node root) {
        size = 0;
        if (root != null) {
            size++;
        }
        this.root = root;
    }

    public BinaryTree() {
        this(null);
    }

    public boolean isInternal(Node v) {
        return hasLeft(v) || hasRight(v);
    }

    public boolean isExternal(Node v) {
        return !hasLeft(v) && !hasRight(v);
    }

    public boolean hasLeft(Node v) {
        return v.getLeft() != null;
    }

    public boolean hasRight(Node v) {
        return v.getRight() != null;
    }

    public boolean isRoot(Node v) {
        return v == root;
    }

    public Node root() {
        return root;
    }

    public Node parent(Node v) {
        return v.getParent();
    }

    public int size(){
        return size;
    }

    public void updateTree(@NotNull Node newRoot, Node rightChild) {
        if (root == null) {
            root = newRoot;
        }
        else {
            root.setParent(newRoot);
            newRoot.setLeft(root);
            root = newRoot;
        }
        size++;

        if (rightChild != null) {
           root.setRight(rightChild);
           size++;
        }

    }
}
