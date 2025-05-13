
import java.util.LinkedList;
import java.util.Queue;



public class AVLTree {
    protected Node raiz;
    
    public Node atualizafb (Node no, String metodo){
        if (no.getPai() == null) {
            return no;
        }

        Node pai = no.getPai();

        if (metodo.equals("insercao")) {
            if (pai.getFilhoDireito() != null && pai.getFilhoDireito().getValue() == no.getValue()) {
                pai.setFb(pai.getFb() - 1);
            } else if (pai.getFilhoEsquerdo() != null && pai.getFilhoEsquerdo().getValue() == no.getValue()) {
                pai.setFb(pai.getFb() + 1);
            }
        } else if (metodo.equals("remocao")) {
            if (pai.getFilhoDireito() != null && pai.getFilhoDireito().getValue() == no.getValue()) {
                pai.setFb(pai.getFb() + 1);
            } else if (pai.getFilhoEsquerdo() != null && pai.getFilhoEsquerdo().getValue() == no.getValue()) {
                pai.setFb(pai.getFb() - 1);
            }
        }
        if (pai.getFb() != 0) {
            return atualizafb(pai, metodo);
        }
        return pai;
    }
    
