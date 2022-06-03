import org.w3c.dom.Node;

import java.util.Objects;

public class MyTree<T extends Comparable<T>> {
    int size;
    Node root;

    public Node search(T value) {
        return search(value, root);
    }

    private Node search(T value, Node currentNode) {
        if (currentNode == null) return null;
        if (currentNode.value.equals(value)) {
            return currentNode;
        }
        return search(value, (value.compareTo(currentNode.value) >= 0) ? currentNode.right : currentNode.left);
    }

    public void addNode(T value) {
        if (root == null) {
            root = new Node(value);
        } else root.addNode(value);
        size++;
    }

    private Node getMinNodeParent(Node node) {
        Node parent = root;
        while (parent.left != null) {
            parent = parent.left;
        }
        return parent;
    }

    public T remove(T value) {
        if (value.compareTo(root.value) > 0) {
            return remove(root.right, root, value);
        }
        return remove(root.left, root, value);
    }

    private T remove(Node node, Node nodeParent, T value) {
        if (node == null) {
            return null;
        }
        int cmp = value.compareTo(node.value);
        if (cmp == 0) {
            T oldValue = node.value;
            if (node.left != null && node.right != null) {
                Node minParent = getMinNodeParent(node.right);
                if (minParent == null) {
                    node.value = node.right.value;
                    node.right = node.right.right;
                } else {
                    node.value = minParent.left.value;
                    minParent.left = minParent.left.right;
                }
            } else {
                Node child = (node.left != null) ? node.left : node.right;
                if (nodeParent == null) {
                    root = child;
                } else if (nodeParent.left == node) {
                    nodeParent.left = child;
                } else nodeParent.right = child;
            }
            size--;
            return oldValue;
        }
        return remove((cmp < 0) ? node.left : node.right, node, value);
    }

    public T put(T value) {
        return put(root, value);
    }

    private T put(Node node, T value) {
        int cmp = value.compareTo(node.value);
        if (cmp == 0) {
            T oldValue = node.value;
            node.value = value;
            return oldValue;
        } else {
            if (cmp < 0) {
                if (node.left == null) {
                    node.left = new Node(value);
                    size++;
                    return null;
                }
                return put(node.left, value);
            } else {
                if (node.right == null) {
                    node.right = new Node(value);
                    size++;
                    return null;
                }
                return put(node.right, value);
            }
        }
    }


    public MyTree<T> clone() {
        if(this.root == null){
            return null;
        }
        MyTree<T> newTree = new MyTree<>();
        class Inner {
            Node recursionClone(Node node) {
                Node currNode = new Node(node.value);
                if (node.left != null) {
                    currNode.left = recursionClone(node.left);
                }
                if (node.right != null) {
                    currNode.right = recursionClone(node.right);
                }
                newTree.size ++;
                return currNode;
            }
        }
        newTree.root = new Inner().recursionClone(root);
        return newTree;
    }


    public void print() {
        class Inner {
            void printSortedTree(Node node) {
                if (node == null) {
                    return;
                }
                printSortedTree(node.left);
                System.out.print(node.value + " ");
                printSortedTree(node.right);
            }
        }
        new Inner().printSortedTree(root);
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyTree<?> myTree = (MyTree<?>) o;
        return size == myTree.size && Objects.equals(root, myTree.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, root);
    }

    @Override
    public String toString() {
        return root + "";
    }

    protected class Node implements Comparable<Node> {
        T value;
        Node left;
        Node right;

        public Node(T value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(T value) {
            this(value, null, null);
        }
        public Node() {
        }


        void addNode(T value) {
            if (this.value.compareTo(value) > 0) {// >= Если нужно, чтобы равный элемент был справа
                if (left == null) {
                    left = new Node(value);
                } else left.addNode(value);
            } else {
                if (right == null) {
                    right = new Node(value);
                } else right.addNode(value);
            }
        }


        @Override
        public int compareTo(Node node) {
            return this.value.compareTo(node.value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(value, node.value) && Objects.equals(left, node.left) && Objects.equals(right, node.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, left, right);
        }

        @Override
        public String toString() {
//            return value + "";
//            return value + ((left !=null) ?  "," + left.toString() : "")  + ((right !=null) ?  "," + right.toString()  : "");
            return value + ((left != null) ? "(" + left.toString() + ")" : "") + ((right != null) ? ",(" + right.toString() + ")" : "");
        }
    }
}
