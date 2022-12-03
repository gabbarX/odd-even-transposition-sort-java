package org.example;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static org.example.Main.cgpaOfStudents;
import static org.example.Main.tmp;

public class Main {
    public static int[] nums = {1,10,100,1000,10000};
    public static ArrayList<Float> cgpaOfStudents = new ArrayList<Float>();
    public static int tmp;
    public static void oddEvenSort(int n)
    {
        boolean isSorted = false; // Initially array is unsorted

        while (!isSorted)
        {
            isSorted = true;
            Float temp;

            // Perform Bubble sort on odd indexed element
            for (int i=1; i<=n-2; i=i+2)
            {
                if (cgpaOfStudents.get(i) < cgpaOfStudents.get(i+1))
                {
                    temp = cgpaOfStudents.get(i);
                    cgpaOfStudents.set(i,cgpaOfStudents.get(i+1));
                    cgpaOfStudents.set(i+1,temp);
                    isSorted = false;
                }
            }

            // Perform Bubble sort on even indexed element
            for (int i=0; i<=n-2; i=i+2)
            {
                if (cgpaOfStudents.get(i) < cgpaOfStudents.get(i+1))
                {
                    temp = cgpaOfStudents.get(i);
                    cgpaOfStudents.set(i,cgpaOfStudents.get(i+1));
                    cgpaOfStudents.set(i+1,temp);
                    isSorted = false;
                }
            }
        }
        return;
    }
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Enter the mode you want to enter in:");
        System.out.println("1. Normal mode");
        System.out.println("2. Parallel mode");

        int choice = sc.nextInt();
        if(choice==1) {
            System.out.println("Welcome to normal mode");
            for(int i=0;i< nums.length;i++)
            {
                int n = nums[i];
                for(int j=0;j<n;j++)
                {
                    float temp = rand.nextFloat(0,10);
                    cgpaOfStudents.add(temp);
                }

                System.out.println(" ");
                System.out.println("Before sorting");
                for (int k=0;k<n;k++){
                    System.out.print(cgpaOfStudents.get(k)+" ");
                }
                System.out.println(" ");
                oddEvenSort(n);

//                System.out.println(" ");
                System.out.println("After sorting");
                for (int k=0;k<n;k++){
                    System.out.print(cgpaOfStudents.get(k)+" ");
                }
                System.out.print("\n");
                cgpaOfStudents.clear();

            }
        }
        else if(choice==2){
            System.out.println("Welcome to parallel mode");
            for(int i=0;i< nums.length;i++){
                int n = nums[i];
                int threadNums = (n+1)/2;
                Thread[] mythreads = new Thread[threadNums];
                for(int j=0;j<n;j++)
                {
                    float temp = rand.nextFloat(0,10);
                    cgpaOfStudents.add(temp);
                }
                System.out.println(" ");
                System.out.println("Before sorting:"+n);
                for (int k=0;k<n;k++){
                    System.out.print(cgpaOfStudents.get(k)+" ");
                }

                if(n>1){
                    for (int j=1;j<=n;j++){
                        if(j%2!=0){
                            tmp = 0;
                            //create threads
                            for(int k=0;k<threadNums;k++)
                            {
                                Thread t = new Thread(new compare(n));
                                mythreads[k] = t;
                                t.start();
                            }
                            for(int k=0;k<threadNums;k++){
                                mythreads[k].join();
                            }
                        }
                        else {
                            tmp = 1;
                            for(int k=0;k<threadNums-1;k++){
                                Thread t = new Thread(new compare(n));
                                mythreads[k] = t;
                                t.start();
                            }
                            for(int k=0;k<threadNums-1;k++){
                                mythreads[k].join();
                            }
                        }
                    }
                }

                System.out.println(" ");
                System.out.println("After sorting:"+n);
                for (int k=0;k<n;k++){
                    System.out.print(cgpaOfStudents.get(k)+" ");
                }

                cgpaOfStudents.clear();
            }

        }
        else {
            System.out.println("Invalid choice");
        }
    }
}

class compare implements Runnable{
    int n;

    public compare(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        int index = tmp;
        tmp = tmp+2;
        Float temp;
        if (cgpaOfStudents.get(index) < cgpaOfStudents.get(index+1) )
        {
            temp = cgpaOfStudents.get(index);
            cgpaOfStudents.set(index,cgpaOfStudents.get(index+1));
            cgpaOfStudents.set(index+1,temp);
        }
    }
}
