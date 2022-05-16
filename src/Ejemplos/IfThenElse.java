package Ejemplos;

public class IfThenElse {
    public int condicional1(int n){
        if(n==0){
            n = n+2;
        }
        return n;
    }
    public int codicional2(int n){
        if(n==0){
            n = n+2;
        }else{
            n = n+3;
        }
        return n;
    }
    public int condicional3(int n){
        if(n==0){
            n = n+2;
        }else if(n == 1){
            n = n+3;
        }else{
            n = n+4;
        }
        n = n+2;
        return n;
    }
}
