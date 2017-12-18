package segment.bymatch.forward;


class BinaryNode {
    public Comparable element;
    public BinaryNode left;
    public BinaryNode right;

    BinaryNode (Comparable theElement){
        element = theElement;
        left = right = null;
    }
}

public class BinarySearchTree {
    protected BinaryNode root;

    public BinarySearchTree() {
        root = null;
    }


    /**
    * 插入一个子树的内部方法
    * @param x the item to insert
    * @param t the node that roots the tree
    * @return the new root
    * @throws DuplicateItemException if x is already present
    * */

    protected BinaryNode insert (Comparable x, BinaryNode t){
        if (t == null)
            t = new BinaryNode(x);
        else if (x.compareTo(t.element) < 0)
            t.left = insert(x,t.left);
        else if (x.compareTo(t.element) > 0)
            t.right = insert(x,t.right);
        else
            throw new DuplicateItemException(x.toString());

        return t;
    }

    /**
    * 插入树
    * @param x the item to insert
    * @throws DuplicateItemException if x is already present
     * */
    public void insert (Comparable x) {
        root = insert (x, root);
    }

    /**
     * Find an item in the tree
     * @param x the item to search for
     * @return the matching item or null if not found
     */
    public Comparable find(Comparable x){
        return elementAt(find (x, root));
    }

    /**
     * Internal method to get element field
     * @param t the node
     * @return the element field or null if t is null
     * */

    private Comparable elementAt (BinaryNode t){
        return t == null ? null:t.element;
    }

    /**
     * Internal method to find an item in a subtree
     * @param x is item to search for
     * @param t the node that roots the tree
     * @return node containing the matched item
     * */
    private BinaryNode find(Comparable x,BinaryNode t){
        while ( t != null){
            if (x.compareTo(t.element) < 0)
                t = t.left;
            else if (x.compareTo(t.element) > 0)
                t = t.right;
            else
                return t;
        }

        return null;
    }

}


/**
 * Exception class for duplicate item errors
 * in search tree insertions.
 * @author Mark Allen Weiss
 */
class DuplicateItemException extends RuntimeException
{
    /**
     * Construct this exception object.
     */
    public DuplicateItemException( )
    {
        super( );
    }
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public DuplicateItemException( String message )
    {
        super( message );
    }
}