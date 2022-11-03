
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class CiscBST<E extends Comparable<E>> implements CiscCollection<E> {

    private BSTNode<E> root;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return contains(root, (E) o);
    }

    private boolean contains(BSTNode<E> node, E value) {
        if (node == null) {
            return false;
        } else if (node.data.compareTo(value) == 0) {
            return true;
        } else if (node.data.compareTo(value) < 0) {
            return contains(node.right, value);
        } else {
            return contains(node.left, value);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CiscBSTIterator(root);
    }

    @Override
    public Object[] toArray() {
        ArrayList<E> arr = new ArrayList<>(size());
        return toArray(root, arr);
    }

    private Object[] toArray(BSTNode<E> node, ArrayList<E> list) {
        if (node == null) {
            return new Object[0];
        }

        toArray(node.left, list);
        list.add(node.data);
        toArray(node.right, list);

        return list.toArray();
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    public void add(E value) {
        root = add(root, value);
    }

    private BSTNode<E> add(BSTNode<E> node, E value) {
        if (node == null) {
            size++;
            return new BSTNode<E>(value);
        } else if (node.data.compareTo(value) == 0) {
            return node;
        } else if (node.data.compareTo(value) < 0) {
            node.right = add(node.right, value);
            return node;
        } else {
            node.left = add(node.left, value);
            return node;
        }
    }

    public void remove(E value) {
        this.root = remove(root, value);
    }

    public BSTNode<E> remove(BSTNode<E> node, E value) {
        if (node == null) {
            return null;
        } else if (node.data.compareTo(value) > 0) {
            node.left = remove(node.left, value);
        } else if (node.data.compareTo(value) < 0) {
            node.right = remove(node.right, value);
        } else {
            if (node.right == null) {
                size--;
                return node.left;
            } else if (node.left == null) {
                size--;
                return node.right;
            } else {
                // both children
                node.data = getMax(node.left);
                node.left = remove(node.left, node.data);
            }
        }
        return node;
    }

    private E getMax(BSTNode node) {
        if (node.right == null) {
            return (E) node.data;
        } else {
            return getMax(node.right);
        }
    }

    private static class BSTNode<E> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        public BSTNode(E data) {
            this(data, null, null);
        }

        public BSTNode(E data, BSTNode<E> left, BSTNode<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    private class CiscBSTIterator implements Iterator<E> {
        private Stack<BSTNode<E>> stack;

        CiscBSTIterator(BSTNode<E> node) {
            stack = new Stack<BSTNode<E>>();
            if (node != null) {
                while (node.left != null) {
                    stack.push(node.left);
                    node = node.left;
                }
                stack.push(node);
                while (node.right != null) {
                    stack.push(node.left);
                    node = node.right;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !(stack.empty());
        }

        @Override
        public E next() {
            return stack.pop().data;
        }
    }

}
