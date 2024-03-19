public class main {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        // Inserção de nós na árvore AVL
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(2);
        tree.insert(8);
        tree.insert(22);

        // Exibição da árvore AVL em pré-ordem
        System.out.println("Preorder traversal of constructed tree is:");
        tree.exibirArvore();
        System.err.println("\n----------------------------");
        tree.insert(25);
        tree.exibirArvore();
        System.err.println("\n----------------------------");
        tree.remove(5);
        tree.exibirArvore();
        System.err.println("\n----------------------------");
        Node no = tree.search(15);
        System.out.println(no.valor);
    }
}
