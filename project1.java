/*
* Alexa Tang
* Project 1
* CS 3310
* */



package Matrix_Multiplication;
import java.lang.Math;
import java.util.Random;
import java.util.Arrays;


class Matrix_Generator
{
    public int [][] Matrix_Generator(int size)
    {
        int n = size;
        Random rand = new Random();
        int [][] Matrix = new int[n][n];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                Matrix[i][j] =  rand.nextInt(101); // numbers from 0 to 100
            }
        }
        return Matrix;
    }
}
class Iterative_Matrix_Multiplication
{
    public int[][] Iterative_Matrix_Multiplication(int n, int[][] A, int[][] B, int[][] C)
    {
        int i, j, k;
        //int [][] C;
        for(i = 0; i < n; i++)
        {
            for(j = 0; j < n; j++)
            {
                C[i][j] = 0;
                for(k = 0; k < n; k++)
                {
                    C[i][j] = C[i][j] + A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
}

class Divide_and_Conquer
{

    public int [][] MatrixAddition(int[][] A, int[][] B, int[][] C)
    {

        int size = C.length;
        for(int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }


    public int [][] Divide_and_Conquer(int[][] A, int[][]B, int n)
    {

        //result nxn C[][]
        int[][] C = new int [n][n];
        int size = n;
        //base case where matrix is 1x1
        if (n == 1)
        {
            C[0][0] = A[0][0] * B[0][0];
        }
        else
        {
            //parition matrices int n/2 x n/2
            int newSize = n/2;
            //A[][] quadrants
            int [][] A11 = new int[newSize][newSize];
            int [][] A12 = new int[newSize][newSize];
            int [][] A21 = new int[newSize][newSize];
            int [][] A22 = new int[newSize][newSize];
            //B[][] quadrants
            int [][] B11 = new int[newSize][newSize];
            int [][] B12 = new int[newSize][newSize];
            int [][] B21 = new int[newSize][newSize];
            int [][] B22 = new int[newSize][newSize];
            //C[][] quadrants
            int [][] C11 = new int[newSize][newSize];
            int [][] C12 = new int[newSize][newSize];
            int [][] C21 = new int[newSize][newSize];
            int [][] C22 = new int[newSize][newSize];

            for(int i = 0; i < newSize; i++)
            {
                for (int j = 0; j < newSize; j++)
                {

                    A11[i][j] = A[i][j]; //top left
                    A12[i][j] = A[i][j + newSize]; //top right
                    A21[i][j] = A[i + newSize][j];//bottom left
                    A22[i][j] = A[i+ newSize][j + newSize]; // bottom right

                    B11[i][j] = B[i][j]; //top left
                    B12[i][j] = B[i][j + newSize]; //top right
                    B21[i][j] = B[i + newSize][j];//bottom left
                    B22[i][j] = B[i+ newSize][j + newSize];//bottom right

                }
            }

            // solving for each quadrant of resultant matrix
            C11 = MatrixAddition(Divide_and_Conquer(A11,B11,newSize), Divide_and_Conquer(A12,B21,newSize), C11);
            C12 = MatrixAddition(Divide_and_Conquer(A11,B12,newSize), Divide_and_Conquer(A12,B22,newSize), C12);
            C21 = MatrixAddition(Divide_and_Conquer(A21,B11,newSize), Divide_and_Conquer(A22,B21,newSize), C21);
            C22 = MatrixAddition(Divide_and_Conquer(A21,B12,newSize), Divide_and_Conquer(A22,B22,newSize), C22);

            //combining resultant quadrants
            int[][] C11_21 = new int [size][newSize]; // vertically left side stacked 2x2
            int[][] C12_22 = new int [size][newSize];
            // Concat top left and bottom left in column
            System.arraycopy(C11, 0, C11_21, 0, newSize);
            System.arraycopy(C21, 0, C11_21, newSize, newSize);

            // Concat top right and bottom right in column
            System.arraycopy(C12, 0, C12_22, 0, newSize);
            System.arraycopy(C22, 0, C12_22, newSize, newSize);



            //copying left stack C11_21
            for (int i = 0; i < size; i ++)
            {
                for (int j = 0; j < newSize; j++)
                {
                    C[i][j] = C11_21[i][j];
                }
            }

            //copying right stack C12_22
            for (int i = 0; i < size; i ++)
            {
                for (int j = 0; j < newSize; j++)
                {
                    C[i][j+newSize] = C12_22[i][j];
                }
            }

        }
        return C;
    }
}

class Strassen
{
    public int [][] MatrixAddition(int[][] A, int[][] B, int[][] C)
    {

        int size = C.length;
        for(int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    public int [][] MatrixSubtraction(int[][] A, int[][] B, int[][] C)
    {

        int size = C.length;
        for(int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    public int[][] Strassen(int size, int[][] A, int[][] B, int[][] C)
    {
        int n = size;
        if (size == 2)
        {
            Iterative_Matrix_Multiplication Matrix_Mult = new Iterative_Matrix_Multiplication();
            Matrix_Mult.Iterative_Matrix_Multiplication(n, A, B, C);
        }
        else
        {
            //parition matrices int n/2 x n/2
            int newSize = n/2;
            //A[][] quadrants
            int [][] A11 = new int[newSize][newSize];
            int [][] A12 = new int[newSize][newSize];
            int [][] A21 = new int[newSize][newSize];
            int [][] A22 = new int[newSize][newSize];
            //B[][] quadrants
            int [][] B11 = new int[newSize][newSize];
            int [][] B12 = new int[newSize][newSize];
            int [][] B21 = new int[newSize][newSize];
            int [][] B22 = new int[newSize][newSize];
            //C[][] quadrants
            int [][] C11 = new int[newSize][newSize];
            int [][] C12 = new int[newSize][newSize];
            int [][] C21 = new int[newSize][newSize];
            int [][] C22 = new int[newSize][newSize];

            for(int i = 0; i < newSize; i++)
            {
                for (int j = 0; j < newSize; j++)
                {

                    A11[i][j] = A[i][j]; //top left
                    A12[i][j] = A[i][j + newSize]; //top right
                    A21[i][j] = A[i + newSize][j];//bottom left
                    A22[i][j] = A[i+ newSize][j + newSize]; // bottom right

                    B11[i][j] = B[i][j]; //top left
                    B12[i][j] = B[i][j + newSize]; //top right
                    B21[i][j] = B[i + newSize][j];//bottom left
                    B22[i][j] = B[i+ newSize][j + newSize];//bottom right

                }
            }
            int[][] P = new int[newSize][newSize];
            int[][] Q = new int[newSize][newSize];
            int[][] R = new int[newSize][newSize];
            int[][] S = new int[newSize][newSize];
            int[][] T = new int[newSize][newSize];
            int[][] U = new int[newSize][newSize];
            int[][] V = new int[newSize][newSize];
            int[][] resultA = new int[newSize][newSize];
            int[][] resultB = new int[newSize][newSize];

            //P = (A11 + A22)(B11+B22)
            Strassen(newSize, MatrixAddition(A11, A22, resultA), MatrixAddition(B11, B22, resultB), P);

            //Q = (A21 + A22)(B11)
            Strassen(newSize, MatrixAddition(A21, A22, resultA), B11, Q);

            //R = (A11)(B12 - B22)
            Strassen(newSize, A11, MatrixSubtraction(B12, B22, resultB), R);

            //S = (A22)(B21 - B11)
            Strassen(newSize, A22, MatrixSubtraction(B21, B11, resultB), S);

            //T = (A11+ A12)(B22)
            Strassen(newSize, MatrixAddition(A11, A12, resultA), B22, T);

            //U = (A21 - A11)(B11 + B12)
            Strassen(newSize, MatrixSubtraction(A21, A11, resultA), MatrixAddition(B11, B12, resultB), U);

            //V = (A12 - A22)(B21 + B22)
            Strassen(newSize, MatrixSubtraction(A12, A22, resultA), MatrixAddition(B21, B22, resultB), V);



            //C11 = P + S - T + V
            resultA = MatrixAddition(P, S, resultA); //P + S = resultA
            resultB = MatrixSubtraction(resultA, T, resultB); // resultA - T = resultB
            C11 = MatrixAddition(resultB, V, C11); //resultB + V = C11

            //C12 = R + T
            C12 = MatrixAddition(R, T, C12);

            //C21 = Q + S
            C21 = MatrixAddition(Q, S, C21);

            //C22 = P + R - Q + U
            resultA = MatrixAddition(P, R, resultA); //P + R = resultA
            resultB = MatrixSubtraction(resultA, Q, resultB); // resultA - Q = resultB
            C22 = MatrixAddition(resultB, U, C22); //resultB + U = C22


            //combining resultant quadrants
            int[][] C11_21 = new int [size][newSize]; // vertically left side stacked 2x2
            int[][] C12_22 = new int [size][newSize];
            // Concat top left and bottom left in column
            System.arraycopy(C11, 0, C11_21, 0, newSize);
            System.arraycopy(C21, 0, C11_21, newSize, newSize);

            // Concat top right and bottom right in column
            System.arraycopy(C12, 0, C12_22, 0, newSize);
            System.arraycopy(C22, 0, C12_22, newSize, newSize);


            //copying left stack C11_21
            for (int i = 0; i < size; i ++)
            {
                for (int j = 0; j < newSize; j++)
                {
                    C[i][j] = C11_21[i][j];
                }
            }

            //copying right stack C12_22
            for (int i = 0; i < size; i ++)
            {
                for (int j = 0; j < newSize; j++)
                {
                    C[i][j+newSize] = C12_22[i][j];
                }
            }
        }
        return C;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Matrix_Generator Rand_Matrix = new Matrix_Generator();
        int[][]  A, B, C;
        int n = 1; // 2^n
        int x = 0;

        ////////// ITERATIVE METHOD /////////////
        System.out.println("************Iterative method**************");


        Iterative_Matrix_Multiplication Iterative_Method = new Iterative_Matrix_Multiplication();
        while (n <= 9)// 2^n = 512 being the greatest
        {
            while (x <= 1000) // Number of random matrices generated for each size
            {
                int size = (int) Math.pow(2, n); // 2^n

                //A[][]
                A = Rand_Matrix.Matrix_Generator(size);
                //B[][]
                B = Rand_Matrix.Matrix_Generator(size);
                //result
                C = new int[size][size];
                int count = 0;
                if(x == 0)
                {
                    System.out.println("Runtimes for " + size + "x" + size + "\n");
                }
                //running the same matrix calculation 20 times
                while (count != 20)
                {
                    //start counting run time
                    long totalTime;
                    long startTime = System.nanoTime();

                    C = Iterative_Method.Iterative_Matrix_Multiplication(size, A, B, C);

                    long endTime = System.nanoTime();
                    totalTime = endTime - startTime;

                    System.out.println( totalTime );
                    count++;
                }
                count = 0; //resetting counter for inner while loop
                x++; // counter for generating 2000 sets
            }
            x = 0; // reset for next matrix size
            n++; // incrementing rows, columns of matrix
        }


        ////////// DIVIDE AND CONQUER METHOD /////////////
        System.out.println("*********Divide and conquer method**********");
        Divide_and_Conquer Divide_Conquer = new Divide_and_Conquer();
        //resetting counters
        n = 1; // 2^n
        x = 0;
        //int[][]  A, B, C;
        while (n <= 9)// 2^n = 512 being the greatest
        {
            while (x <= 1000) // Number of random matrices generated for each size
            {
                int size = (int) Math.pow(2, n); // 2^n

                //A[][]
                A = Rand_Matrix.Matrix_Generator(size);
                //B[][]
                B = Rand_Matrix.Matrix_Generator(size);
                //result
                C = new int[size][size];
                int count = 0;
                if(x == 0)
                {
                    System.out.println("Runtimes for " + size + "x" + size + "\n");
                }
                //running the same matrix calculation 20 times
                while (count != 20)
                {
                    //start counting run time
                    long totalTime;
                    long startTime = System.nanoTime();

                    C = Divide_Conquer.Divide_and_Conquer(A,B, size);
                    //System.out.println(" C " + Arrays.deepToString(C));
                    long endTime = System.nanoTime();
                    totalTime = endTime - startTime;

                    System.out.println( totalTime );
                    count++;
                }
                count = 0; //resetting counter for inner while loop
                x++; // counter for generating 2000 sets of A B matrices
            }
            x = 0; // reset for next matrix size
            n++; // incrementing rows, columns of matrix
        }



        ////////// Strassen's METHOD /////////////
        System.out.println("************Strassen's method**************");


        Strassen Strassens_Method = new Strassen();
        while (n <= 1)// 2^n = 512 being the greatest
        {
            while (x <= 1000) // Number of random matrices generated for each size
            {
                int size = (int) Math.pow(2, n); // 2^n

                //A[][]
                A = Rand_Matrix.Matrix_Generator(size);
                //B[][]
                B = Rand_Matrix.Matrix_Generator(size);
                //result
                C = new int[size][size];
                int count = 0;
                if(x == 0)
                {
                    System.out.println("Runtimes for " + size + "x" + size + "\n");
                }
                //running the same matrix calculation 20 times
                while (count != 20)
                {
                    //start counting run time
                    long totalTime;
                    long startTime = System.nanoTime();

                    C = Strassens_Method.Strassen(size, A, B, C);

                    long endTime = System.nanoTime();
                    totalTime = endTime - startTime;

                    System.out.println( totalTime );
                    count++;
                }
                count = 0; //resetting counter for inner while loop
                x++; // counter for generating 2000 sets
            }
            x = 0; // reset for next matrix size
            n++; // incrementing rows, columns of matrix
        }



    }
}
