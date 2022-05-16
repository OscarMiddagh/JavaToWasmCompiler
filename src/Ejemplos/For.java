package Ejemplos;

public class For{
    public int ciclo1(int n){
        int num = n;
        for(int i = 0; i<num ; i++){
            n = n*2;
        }
        return n;
    }
    public int ciclo2(int m, int n){
        int num = m + n;
        for(int i = 0; i< num; i++){
            for(int j = 0; j< num; j++){
                m++;
            }
            n++;
        }
        return n*m;
    }

}