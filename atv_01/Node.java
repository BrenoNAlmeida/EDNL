/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.atv_01;



class Node implements Comparable<Node> {
    int x, y;
    float heuristica,custo;
    Node anterior;

    // Construtor correto
    Node(int x, int y, float custo, float heuristica, Node anterior) {
        this.x = x;
        this.y = y;
        this.custo = custo;
        this.heuristica = heuristica;
        this.anterior = anterior;
    }

  @Override
  public int compareTo(Node other) {
      float custo_no_atual = this.custo + this.heuristica;
      float custo_no_proximo = other.custo + other.heuristica;
      
      return Float.compare(custo_no_atual, custo_no_proximo);
  }
}