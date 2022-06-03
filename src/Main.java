public class Main {

    public static int getMax(MyTree<Integer>.Node node){
        return (node.left == null ? node.value: getMax(node.left));
    }
    public static void main(String[] args) {
        MyTree<Integer> tree = new MyTree<>();
        tree.addNode(10);
        tree.addNode(9);
        tree.addNode(8);
        tree.addNode(10);
        tree.addNode(12);
        tree.addNode(20);
        tree.addNode(11);

        MyTree tree1 = tree.clone();
        tree.print();
        tree1.print();
        System.out.println(tree1 == tree);
        System.out.println(tree1.equals(tree)); //=> алгоритм работает

    }
}
