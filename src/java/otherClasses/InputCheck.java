/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherClasses;

import java.io.PrintWriter;

/**
 *
 * @author Tom
 */
public class InputCheck {

    private static char[] validNums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static char[] validLetters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's','t', 'u', 'v', 'w', 'x', 'y', 'z',' '};
    
    private PrintWriter p;
    
    public InputCheck(PrintWriter p){
        this.p = p;
    }

    public boolean checkNumber(String number, int length) {

        char[] ca = number.toLowerCase().toCharArray();
        outer:
        for (char c : ca) {
            for (char c2 : validNums) {
                if (c2 == c) {
                    continue outer;
                }

            }
            p.println(number);
            return false;
        }
        
        return number.length() == length || length == -1;
    }

    public boolean checkString(String s, int length) {

        char[] ca = s.toLowerCase().toCharArray();
        outer:
        for (char c : ca) {
            if (c == ',' || c == '.') {
                continue;
            }
            for (char c2 : validLetters) {
                if (c2 == c) {
                    continue outer;
                }

            }
            for (char c2 : validNums) {
                if (c2 == c) {
                    continue outer;
                }
            }
            p.println(s);
            return false;
        }

        return s.length() <= length;
    }

    public boolean checkEmail(String email) {
        boolean hasAt = false;
        char[] ca = email.toLowerCase().toCharArray();
        outer:
        for (char c : ca) {

            if (c == '@') {
                hasAt = true;
                continue;
            }

            if (c == '.') {
                continue;
            }
            for (char c2 : validNums) {
                if (c2 == c) {
                    continue outer;
                }
            }
            for (char c2 : validLetters) {
                if (c2 == c) {
                    continue outer;
                }
            }
            p.println(email);
            return false;
        }

        return email.length() <= 48 && hasAt;
    }

}
