package com.bigc.gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public final class Password {

    private final StringBuilder PASSWORD;    
    private final ArrayList<Character> OPTIONS;
    private final int SIZE;
    private final boolean ISUNIQUE;
    private final Random RAND;
    private static final HashMap<Character, Character[]> CHARACTERS = new HashMap<>() {{
        put('A', new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'});
        put('N', new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
        put('S', new Character[]{'~','`','!',' ','@','#','$','%','^','&','*','(',')','_','-','+','=','{','[','}',']','|','\\',':',';','"','\'','<',',','>','.','?','/'});
    }};    

    public static ArrayList<PasswordHistory> history = new ArrayList<>();

    public Password(int size, ArrayList<Character> options, boolean isUnique) {
        this.OPTIONS = options;
        this.SIZE = size;
        this.ISUNIQUE = isUnique;
        this.PASSWORD = new StringBuilder(size);
        this.RAND = new Random();
    }

    private int getRandomIndex(int len) {
        return RAND.nextInt(len);
    }

    private char getRandomChar() {  
        char option = OPTIONS.get(getRandomIndex(OPTIONS.size()));
        Character[] c;
        if(option == 'L' || option == 'U') {
            c = CHARACTERS.get('A');
        } else {
            c = CHARACTERS.get(option);
        }
        char cc = c[getRandomIndex(c.length)];
        if(option == 'L')
            return Character.toLowerCase(cc);
        return cc;
    }

     /**
     * Checks whether a character exists in the generated password for Uniqueness.
     * 
     * @param character takes a Character as String.
     * @return true if exists, else false
     */
    private boolean available(char character) {
        return PASSWORD.indexOf(Character.toString(character)) != -1;
    }
    
    public void process() {
        for (int i = 0; i < SIZE; i++) {
            char c = getRandomChar();
            while(ISUNIQUE && available(c)){
                c = getRandomChar();
            }
            PASSWORD.append(c);
        }
    }

    public static int getMaxSize(ArrayList<Character> options) {
        int maxSize = 0;
        for (char c : options) {
            if (c == 'L' || c == 'U')
                c = 'A';
            maxSize += CHARACTERS.get(c).length;
        }
        return maxSize;
        
    }
    private boolean isValid() {
        if(!ISUNIQUE)
            return true;        
        return (getMaxSize(OPTIONS) >= SIZE);
    }
    
    public String getPassword() {
        if (OPTIONS.isEmpty() || !isValid())
            return null;
        process();
        String password = PASSWORD.toString();
        history.add(new PasswordHistory(password, new Date()));
        return password;
    }
    
    private class PasswordHistory {
        private final String password;
        private final Date generatedDate;

        public PasswordHistory(String password, Date generatedDate) {
            this.password = password;
            this.generatedDate = generatedDate;
        }

        public String getPassword() {
            return password;
        }

        public Date getGeneratedDate() {
            return generatedDate;
        }

        @Override
        public String toString() {
            return "PasswordHistory{" + "password=" + password + ", generatedDate=" + generatedDate + '}';
        }
    }
}
