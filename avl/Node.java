
class Node {
    int valor;
    int altura; // Altura do nó
    int balanceamento; // Fator de balanceamento do nó
    Node esquerda;
    Node direita;

    public Node(int key) {
        this.valor = key;
        this.altura = 1; // Por padrão, um novo nó tem altura 1
        this.balanceamento = 0; // Por padrão, um novo nó tem fator de balanceamento 0
        this.esquerda = null;
        this.direita = null;
    }
}