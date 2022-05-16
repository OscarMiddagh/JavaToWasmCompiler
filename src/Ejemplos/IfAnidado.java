package Ejemplos;

public class IfAnidado{
    public int ifAnidado(int n, int m){
        if(n%2==0){
            n = n + m;
            if(n%2==0){
                m = 0;
            }
            else{
                m++;
            }
            n = n*n;
        }
        else{
            n++;
        }
        return n*m;
    }
}