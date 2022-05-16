package Ejemplos;

public class Recursividad{
    public int factorial(int fact) {
        if (fact>0) {
            int valor= fact * factorial(fact-1);
            return valor;
        } else
            return 1;
    }

}