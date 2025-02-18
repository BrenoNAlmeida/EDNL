/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.atv_01;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author breno
 */
public class Atv_01 {

    static int[][] matriz;
    static int l, c;
    static Node inicio;
    static List<Node> saidas = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        lerMatriz("C:\\Users\\breno\\OneDrive\\Documentos\\NetBeansProjects\\atv_01\\src\\main\\java\\com\\mycompany\\atv_01\\labirinto.dat");

        long inicio_tempo_Dijkstra = System.nanoTime();
        List<Node> caminhoDijkstra = dijkstra();
        long fim_tempo_Dijkstra = System.nanoTime();

        mostrarMatriz(converteMatriz(caminhoDijkstra));

        long inicio_tempo_a_estrela = System.nanoTime();
        List<Node> caminhoAStar = aStar();
        long fim_tempo_a_estrela = System.nanoTime();
        mostrarMatriz(converteMatriz(caminhoAStar));

        System.out.println("Dijkstra: " + (fim_tempo_Dijkstra - inicio_tempo_Dijkstra) / 1e6 + " ms");
        System.out.println("A*: " + (fim_tempo_a_estrela - inicio_tempo_a_estrela) / 1e6 + " ms");
    }

    static void lerMatriz(String filename) throws IOException {
        List<String> linhas = Files.readAllLines(Paths.get(filename));

        l = linhas.size();
        c = linhas.get(0).length();
        matriz = new int[l][c];

        for (int i = 0; i < l; i++) {
            String linha = linhas.get(i);

            for (int j = 0; j < c; j++) {
                matriz[i][j] = Integer.parseInt(String.valueOf(linha.charAt(j)));

                if (matriz[i][j] == 2) {
                    inicio = new Node(i, j, 0, 0, null);
                } else if (matriz[i][j] == 3) {
                    saidas.add(new Node(i, j, 0, 0, null));
                }
            }
        }
    }

    static void mostrarMatriz(int[][] matriz) {
        int l = matriz.length;
        int c = matriz[0].length;
        System.out.println("=============================");
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("=============================");
    }

    static int[][] converteMatriz(List<Node> caminho) {
        // copia a matriz original
        int[][] matrizCaminho = new int[l][c];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                matrizCaminho[i][j] = matriz[i][j];
            }
        }

        // Marca o caminho
        for (Node no : caminho) {
            matrizCaminho[no.x][no.y] = 5;
        }

        return matrizCaminho;
    }

    static List<Node> dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        float[][] distancias = new float[l][c];
        for (int i = 0; i < l; i++) {
            Arrays.fill(distancias[i], Integer.MAX_VALUE);
        }
        distancias[inicio.x][inicio.y] = 0;

        pq.add(inicio);

        // Direções possíveis para movimento (cima, baixo, esquerda, direita)
        //int[] dx = {-1, 1, 0, 0};
        //int[] dy = {0, 0, -1, 1};
        // Direções possíveis: cima, baixo, esquerda, direita, e diagonais
        int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
        int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};

        while (!pq.isEmpty()) {
            Node atual = pq.poll();

            //condição de parada
            if (matriz[atual.x][atual.y] == 3) {
                return reconstruirCaminho(atual);

            }

            // Explora os vizinhos (cima, baixo, esquerda, direita)
            for (int i = 0; i < 8; i++) {
                // Coordenada x do vizinho
                int nx = atual.x + dx[i];
                // Coordenada y do vizinho
                int ny = atual.y + dy[i];

                if (nx >= 0 && nx < l && ny >= 0 && ny < c && matriz[nx][ny] != 1) {
                    float custoMovimento = (i < 4) ? 1 : 1.5f; // 1 para horizontal/vertical, 2 para diagonais
                    float novoCusto = atual.custo + custoMovimento;

                    if (novoCusto < distancias[nx][ny]) {
                        distancias[nx][ny] = novoCusto;
                        Node vizinho = new Node(nx, ny, novoCusto, 0, atual);
                        pq.add(vizinho);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    static List<Node> aStar() {

        PriorityQueue<Node> listaAberta = new PriorityQueue<>();

        inicio.custo = 0;
        inicio.heuristica = calcularManhattan(inicio);
        listaAberta.add(inicio);

        boolean[][] visitado = new boolean[l][c];

        while (!listaAberta.isEmpty()) {

            Node atual = listaAberta.poll();

            if (matriz[atual.x][atual.y] == 3) {
                return reconstruirCaminho(atual);
            }

            visitado[atual.x][atual.y] = true;

            //int[] dx = {-1, 1, 0, 0};
            //int[] dy = {0, 0, -1, 1};
            
            // Direções possíveis: cima, baixo, esquerda, direita, e diagonais
            int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
            int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
            
            for (int i = 0; i < 8; i++) {
                int novoX = atual.x + dx[i];
                int novoY = atual.y + dy[i];

                // Verifica se a posição é válida
                if (novoX >= 0 && novoX < l && novoY >= 0 && novoY < c && matriz[novoX][novoY] != 1 && !visitado[novoX][novoY]) {
                    float custoMovimento = (i < 4) ? 1.0f : 1.5f;
                    float novoCusto = atual.custo + custoMovimento;
                    Node vizinho = new Node(novoX, novoY, novoCusto, calcularManhattan(new Node(novoX, novoY, 0, 0, null)), atual);

                    
                    listaAberta.add(vizinho);
                }
            }
        }

        return new ArrayList<>();
    }
    
    //heurística de Manhattan - h(n)= ∣ x destino​ −x atual​ ∣ + ∣y destino −y atual ∣
    static int calcularManhattan(Node no) {
    int menorDistancia = Integer.MAX_VALUE;

    for (Node saida : saidas) {
        int distancia = Math.abs(no.x - saida.x) + Math.abs(no.y - saida.y);
        if (distancia < menorDistancia) {
            menorDistancia = distancia;
        }
    }
    
    return menorDistancia;
}
    static int calcularEuclidiana(Node no) {
    int menorDistancia = Integer.MAX_VALUE;

    for (Node saida : saidas) {
        int dx = no.x - saida.x;
        int dy = no.y - saida.y;
        int distancia = (int) Math.sqrt(dx * dx + dy * dy);
        menorDistancia = Math.min(menorDistancia, distancia);
    }

    return menorDistancia;
}


    static List<Node> reconstruirCaminho(Node no) {
        List<Node> caminho = new ArrayList<>();
        while (no != null) {
            caminho.add(no);
            no = no.anterior;
        }
        Collections.reverse(caminho);
        return caminho;
    }
}
