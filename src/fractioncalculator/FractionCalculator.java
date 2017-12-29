package fractioncalculator;

import java.util.Scanner;
import java.util.Arrays;
public class FractionCalculator {
    public static class Fraction {
        private int numerator;
        private int denominator;
        public Fraction (int num, int den) {
            if (den != 0) {
                if (den < 0) {
                    this.numerator = -num;
                    this.denominator = -den;   
                }
                else {
                    this.numerator = num;
                    this.denominator = den; 
                }
            }
            else {
                throw new IllegalArgumentException("Error: denominator is zero.");
            }
        }
        public Fraction (int wholeNum) {
            this.numerator = wholeNum;
            this.denominator = 1;
        }
        public Fraction() {
            this.numerator = 0;
            this.denominator = 1;
        }
        public int getNumerator() {return this.numerator;}
        public int getDenominator() {return this.denominator;}
        public String toString () {return numerator + " / " + denominator;}
        public double toDouble() {return (double)numerator/denominator;}
        public static int gcd(int num, int den) {
            while (num != 0 && den != 0) {
                int remainder = num % den;
                num = den;
                den = remainder;
            }
            return num;
        }
        public void toLowestTerms() {
            int divisor = gcd(numerator, denominator);
            numerator = numerator/divisor;
            denominator = denominator/divisor;
        }
        public boolean equals(Fraction F2) {
            int GCD1 = gcd(numerator,denominator);
            int GCD2 = gcd(F2.numerator,F2.denominator);
            if (numerator/GCD1 == F2.numerator/GCD2 &&
                    denominator/GCD1 == F2.denominator/GCD2) {
                return true;
            }
            else {return false;}     
        }
        public Fraction multiply(Fraction F2) {
            int num = numerator * F2.numerator;
            int den = denominator * F2.denominator;
            int GCD = gcd(num,den);
            return new Fraction(num/GCD, den/GCD);
        }
        public Fraction reciprocal() {return new Fraction(denominator,numerator);}
        public Fraction divide(Fraction F2) {
            if (F2.numerator == 0) {
                throw new IllegalArgumentException("Error: denominator is zero.");
            }
            else {return multiply(F2.reciprocal());}                    
        }
        public Fraction add(Fraction F2) {
            int num = numerator * F2.denominator + F2.numerator * denominator;
            int den = denominator * F2.denominator;
            int GCD = gcd(num,den);
            return new Fraction(num/gcd(num,den), den/gcd(num,den));
        }
        public Fraction subtract(Fraction F2) {
            Fraction minusF2 = new Fraction(-1).multiply(F2);
            return add(minusF2);
        }  
    }
    static String operation;       //initializing variables to which values will be assigned in subordinate clauses
    static String numString = "";  
    static String denString = "";
    static int numInteger = 0;
    static int denInteger = 0; 
    static boolean toContinue = true;
    static boolean validInput = true;
    static boolean inputComplete = false;
    static Fraction myFrac;
    static Fraction myFrac1;
    static Fraction myFrac2;
    static Fraction resultFrac;
    
    public static void main(String[] args) {
        System.out.println("This program is a fraction calculator.\n"
                + "It will add, subtract, multiply, and divide fractions until you type Q to quit.\n"
                + "Please enter your fractions in the form a/b, where a and b are integers.");
        String[] operations = {"+", "-", "/", "*", "="}; 
        Scanner input = new Scanner(System.in);
        while (toContinue == true) {
            System.out.println("Please enter an operation (+, -, *, /, =), or Q to quit:");
            operation = input.nextLine();
            if (operation.equals("Q") || operation.equals("q")) {
                toContinue = false;
            } 
            else if (!(Arrays.asList(operations).contains(operation))) {
                System.out.println("Invalid input.");   
            }
            else {
                inputComplete = false;
                while (inputComplete == false) {
                    parseFraction("first");
                    if (inputComplete == true) {
                        myFrac1 = myFrac;
                    }
                }
                inputComplete = false;
                while (inputComplete == false) {
                    parseFraction("second");
                    if (inputComplete == true) {
                        myFrac2 = myFrac;
                    }
                }
                if (operation.equals("+")) {
                    resultFrac = myFrac1.add(myFrac2);
                    resultPrint();   
                }
                if (operation.equals("-")) {
                    resultFrac = myFrac1.subtract(myFrac2);
                    resultPrint();
                }
                if (operation.equals("*")) {
                    resultFrac = myFrac1.multiply(myFrac2);
                    resultPrint();
                }
                if (operation.equals("/")) {
                    resultFrac = myFrac1.divide(myFrac2);
                    resultPrint();
                }
                if (operation.equals("=")) {
                    myFrac1.equals(myFrac2);
                    String equalityResult;
                    if (myFrac1.equals(myFrac2)) {equalityResult = "equals";}
                    else {equalityResult = "is not equal to";}
                    System.out.print("(" + myFrac1.numerator + "/" + myFrac1.denominator + ") " + equalityResult + " (");
                    System.out.println(myFrac2.numerator + "/" + myFrac2.denominator + ").");   
                } 
            }
        }            
    }
    public static void parseFraction(String s) {  
        System.out.println("Please enter the " + s + " fraction (a/b or integer a):");
        Scanner input = new Scanner(System.in);        
        String inFraction = input.nextLine();
        int slashIndex = inFraction.indexOf("/");
        validInput = true;
        if (slashIndex == -1) {
            numString = inFraction;
            try {
                numInteger = Integer.valueOf(numString);
                denInteger = 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                    validInput = false;
                    }
        }
        else if (slashIndex == 0 && slashIndex == inFraction.length() - 1) {
            validInput = false;
            System.out.println("Invalid input.");
        }
        else {
            numString = inFraction.substring(0,slashIndex);
            denString = inFraction.substring(slashIndex + 1, inFraction.length());
            try {
                numInteger = Integer.valueOf(numString);
                denInteger = Integer.valueOf(denString);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                    validInput = false;
                    }
        }
        if (validInput == true) {
            inputComplete = true; 
            myFrac = new Fraction(numInteger, denInteger);  
            }  
        }
    public static void resultPrint() {
        System.out.print("(" + myFrac1.numerator + "/" + myFrac1.denominator + ") " + operation + " (");
        System.out.print(myFrac2.numerator + "/" + myFrac2.denominator + ") = ("); 
        System.out.println(resultFrac.numerator + "/" + resultFrac.denominator + ")");  
    }
}
