
import java.util.LinkedList;
import java.util.Queue;



public class AVLTree {

    Node root;

    public AVLTree() {
        this.root = null; // Inicialmente, a árvore está vazia
    }

    
    // Métodos auxiliares para obter a altura e o fator de balanceamento de um nó
    private int altura(Node node) {
        if (node == null) {
            return 0;
        }
        return node.altura;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return altura(node.esquerda) - altura(node.direita);
    }

    private Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node T2 = x.direita;

        // Realiza a rotação
        x.direita = y;
        y.esquerda = T2;

        // Atualiza as alturas
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    // Rotação simples à esquerda
    private Node rotacaoEsquerda(Node x) {
        Node y = x.direita;
        Node T2 = y.esquerda;

        // Realiza a rotação
        y.esquerda = x;
        x.direita = T2;

        // Atualiza as alturas
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    // Rotação dupla à direita (rotação à esquerda seguida por rotação à direita)
    private Node rotacaoDuplaDireita(Node y) {
        y.esquerda = rotacaoEsquerda(y.esquerda);
        return rotacaoDireita(y);
    }

    // Rotação dupla à esquerda (rotação à direita seguida por rotação à esquerda)
    private Node rotacaoDuplaEsquerda(Node x) {
        x.direita = rotacaoDireita(x.direita);
        return rotacaoEsquerda(x);
    }

    // Método para inserir um novo nó na árvore AVL
    public void insert(int valor) {
        this.root = insertNode(this.root, valor);
    }

    // Método auxiliar para inserir um novo nó na árvore AVL
    private Node insertNode(Node node, int valor) {
        // Caso base: se o nó for nulo, criamos um novo nó com a chave fornecida
        if (node == null) {
            return new Node(valor);
        }

        // Inserção na subárvore esquerda
        if (valor < node.valor) {
            node.esquerda = insertNode(node.esquerda, valor);
        }
        // Inserção na subárvore direita
        else if (valor > node.valor) {
            node.direita = insertNode(node.direita, valor);
        }
        // Se a chave já existe na árvore, não fazemos nada
        else {
            return node;
        }

        // Atualiza a altura do nó atual
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));

        // Calcula o fator de balanceamento do nó
        node.balanceamento = getBalanceFactor(node);
        int balanceamento = node.balanceamento;
        

           // Realiza rotações se necessário para manter o balanceamento da árvore
        if (balanceamento > 1 && valor < node.esquerda.valor) {
            return rotacaoDireita(node);
        }
        if (balanceamento < -1 && valor > node.direita.valor) {
            return rotacaoEsquerda(node);
        }
        if (balanceamento > 1 && valor > node.esquerda.valor) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }
        if (balanceamento < -1 && valor < node.direita.valor) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
    }
        return node;
    }

    public void displayStructured() {
        displayStructuredNode(root, 0);
    }

    // Método auxiliar recursivo para exibir os nós da árvore AVL de forma estruturada
    private void displayStructuredNode(Node node, int depth) {
        if (node != null) {
            displayStructuredNode(node.direita, depth + 1);
            System.out.println();
            for (int i = 0; i < depth; i++) {
                System.out.print("\t");
            }
            System.out.print(node.valor + "[" + getBalanceFactor(node) + "]");
            displayStructuredNode(node.esquerda, depth + 1);
        }
    }
    
    // Método auxiliar para encontrar o nó com o menor valor (menor chave) a partir de um nó dado
    private Node minValueNode(Node node) {
        Node current = node;
        while (current.esquerda != null) {
            current = current.esquerda;
        }
        return current;
    }

  // Método para remover um nó com uma determinada chave da árvore AVL
    public void remove(int key) {
        root = removeNode(root, key);
    }
    private Node removeNode(Node node, int key) {
        // Caso base: se o nó for nulo, retorna o próprio nó
        if (node == null) {
            return node;
        }
    
        // Se a chave a ser removida for menor que a chave do nó atual,
        // então ela está na subárvore esquerda
        if (key < node.valor) {
            node.esquerda = removeNode(node.esquerda, key);
        }
        // Se a chave a ser removida for maior que a chave do nó atual,
        // então ela está na subárvore direita
        else if (key > node.valor) {
            node.direita = removeNode(node.direita, key);
        }
        // Se a chave a ser removida for igual à chave do nó atual,
        // então este é o nó a ser removido
        else {
            // Nó com apenas um filho ou sem filhos
            if (node.esquerda == null || node.direita == null) {
                Node temp = (node.esquerda != null) ? node.esquerda : node.direita;
    
                // Caso sem filhos
                if (temp == null) {
                    node = null;
                } else { // Caso com um filho
                    node = temp; // Copia o conteúdo do filho não nulo
                }
            } else {
                // Nó com dois filhos: obter o sucessor in-order (menor na subárvore direita)
                Node temp = minValueNode(node.direita);
    
                // Copiar o valor do sucessor in-order para este nó
                node.valor = temp.valor;
    
                // Remover o sucessor in-order
                node.direita = removeNode(node.direita, temp.valor);
            }
        }
    
        // Se a árvore tinha apenas um nó, então retorna
        if (node == null) {
            return node;
        }
    
        // Atualiza a altura do nó atual
        node.altura = 1 + Math.max(altura(node.esquerda), altura(node.direita));
    
        // Calcula o fator de balanceamento do nó atual
        int balanceamento = getBalanceFactor(node);
    
        // Verifica o balanceamento do nó e realiza rotações, se necessário
        // Nota: se o balanceamento for maior que 1, então o nó está desbalanceado
        // e está inclinado para a esquerda. Se o balanceamento for menor que -1,
        // então o nó está desbalanceado e está inclinado para a direita.
        if (balanceamento > 1 && getBalanceFactor(node.esquerda) >= 0) {
            return rotacaoDireita(node);
        }
        if (balanceamento > 1 && getBalanceFactor(node.esquerda) < 0) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }
        if (balanceamento < -1 && getBalanceFactor(node.direita) <= 0) {
            return rotacaoEsquerda(node);
        }
        if (balanceamento < -1 && getBalanceFactor(node.direita) > 0) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }
    
        return node;
    }


    public Node search(int valor) {
        return searchNode(root, valor);
    }
    
    private Node searchNode(Node node, int valor) {
        // Se o nó atual for nulo ou o valor for encontrado, retorna o nó atual
        if (node == null || node.valor == valor) {
            return node;
        }
    
        // Se o valor for menor que o valor do nó atual, busca na subárvore esquerda
        if (valor < node.valor) {
            return searchNode(node.esquerda, valor);
        }
        // Se o valor for maior que o valor do nó atual, busca na subárvore direita
        else {
            return searchNode(node.direita, valor);
        }
    }
}