public class Node {
    protected Object value;
    protected Node pai;
    protected Node filhoEsquerdo;
    protected Node filhoDireito;
    protected int fb;

    public Node(Object value, Node pai, Node filhoEsquerdo, Node filhoDireito, int fb) {
        this.value = value;
        this.pai = pai;
        this.filhoEsquerdo = filhoEsquerdo;
        this.filhoDireito = filhoDireito;
        this.fb = fb;
    }

    public Node() {
    }

    public Object getValue() {
        return value;
    }

    public Node getPai() {
        return pai;
    }

    public Node getFilhoEsquerdo() {
        return filhoEsquerdo;
    }

    public Node getFilhoDireito() {
        return filhoDireito;
    }

    public int getFb() {
        return fb;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setPai(Node pai) {
        this.pai = pai;
    }

    public void setFilhoEsquerdo(Node filhoEsquerdo) {
        this.filhoEsquerdo = filhoEsquerdo;
    }

    public void setFilhoDireito(Node filhoDireito) {
        this.filhoDireito = filhoDireito;
    }

    public void setFb(int fb) {
        this.fb = fb;
    }
    
    
    
}
