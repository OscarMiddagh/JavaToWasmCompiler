package Ejemplos;

public class TablaDeVerdad {
    public boolean and(boolean p, boolean q){
        return p&&q;
    }
    public boolean or(boolean p, boolean q){
        return p||q;
    }
    public boolean neg(boolean p){
        return !p;
    }
    public boolean igual(int p, int q){
        return p==q;
    }
    public boolean noIgual(int p, int q){
        return p!=q;
    }
    public boolean mayor(int p, int q){
        return p>q;
    }
    public boolean mayorIgual(int p, int q){
        return p>=q;
    }
    public boolean menor(int p, int q){
        return p<q;
    }
    public boolean menorIgual(int p, int q){
        return p<=q;
    }
}
