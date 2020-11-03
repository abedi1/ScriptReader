package com.favor;

import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here

        // print languages
        try {
            LocalMaryInterface marytts = new LocalMaryInterface();
            System.out.println(marytts.getAvailableVoices(Locale.UK));
            System.out.println("U.S Voices");
            System.out.println(marytts.getAvailableVoices(Locale.US));
        } catch (MaryConfigurationException e) {
            e.printStackTrace();
        }


        Voice voice = new Voice("dfki-obadiah-hsmm");
        File file = new File("C:\\Users\\Aman\\IdeaProjects\\scriptReader\\src\\main\\scripts\\1-1.txt.txt");
        ArrayList<String> scene1 = new ArrayList<>();
        String character = "TRINA"; // use this variable to determine which lines the individual will be saying


        try {
            Scanner scanner = new Scanner(file);
            Scanner keyboard = new Scanner(System.in);
            boolean save = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(character)) {
                    System.out.println("your lines now");
                    System.out.println("press enter when you are finished with your lines");
                    String value = keyboard.nextLine();
                    while (!value.equals("")) {
                        // wait or do nothing
                        value = keyboard.nextLine();
                    }
                    System.out.println("your lines were");
                    save = true;
                } else if (allCaps(line)) {
                    save = false;

                    for (int i = 0; i < scene1.size(); i++) {
                        System.out.println(scene1.get(i)); // print out the lines you were supposed to say
                    }
                    System.out.println();
                    scene1.clear();

                } else if (line.contains(character)) {
                    // for the joint lines reset the state back to true.
                    save = true;
                }
                if (save == false) {
                    voice.say(line);
                } else {
                    scene1.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public static boolean allCaps(String s) {
        boolean allCaps = true;
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "x", "y", "z"};
        for (int i = 0; i < letters.length; i++) {
            if (allCaps == false) {
                break;
            } else {
                if (s.contains(letters[i])) {
                    allCaps = false;
                }
            }
        }
        return allCaps;
    }
}
