import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Represents a binary tree which is composed of nodes that can hold any object.
 * The implementation is not complete but is sufficient for this calculator.
 *
 * @author Luka Kralj
 * @version 30 March 2018
 */
public class BinaryTree {
    private int size;
    private Node root;

    /**
     * Create new binary tree with the specified root.
     *
     * @param root Root of the new tree.
     */
    public BinaryTree(Node root) {
        size = 0;
        if (root != null) {
            size++;
        }
        this.root = root;
    }

    /**
     * Create a new empty binary tree.
     */
    public BinaryTree() {
        this(null);
    }

    /**
     * Check if node v is internal.
     *
     * @param v Node to check.
     * @return True if v has any children.
     */
    public boolean isInternal(Node v) {
        return hasLeft(v) || hasRight(v);
    }

    /**
     * Check if node v is external.
     *
     * @param v Node to check.
     * @return True if v has no children.
     */
    public boolean isExternal(Node v) {
        return !hasLeft(v) && !hasRight(v);
    }

    /**
     * Check if node v has a left child.
     *
     * @param v Node to check.
     * @return True if v has a left child.
     */
    public boolean hasLeft(Node v) {
        return v.getLeft() != null;
    }

    /**
     * Check if node v has a right child.
     *
     * @param v Node to check.
     * @return True if v has a right child.
     */
    public boolean hasRight(Node v) {
        return v.getRight() != null;
    }

    /**
     * Check if node v is a root of this tree,
     *
     * @param v Node to check.
     * @return True if v is a root of this tree.
     */
    public boolean isRoot(Node v) {
        return v == root;
    }

    /**
     *
     * @return Root node of the tree.
     */
    public Node root() {
        return root;
    }

    /**
     *
     * @return Number of nodes in the tree.
     */
    public int size(){
        return size;
    }

    /**
     * Updates the tree. New root is set according to the parameter. The old root is set
     * as the left child of the new root. If there is a right child of the new root it is also set.
     *
     * @param newRoot New root of the tree.
     * @param rightChild The right child of the new root.
     */
    public void updateTree(@NotNull Node newRoot, @Nullable Node rightChild) {
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
