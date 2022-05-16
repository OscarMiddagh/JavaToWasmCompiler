package Ejemplos;

public class While{
    public int loop1(int n, int m){
        while(n>m){
            m += 2;
        }
        return m;
    }
    public int loop2(int n, int m, int o){

        while(n>m){
            while(m>o){
                o +=3;
            }
            m+=2;
        }
        return m*o;
    }
}