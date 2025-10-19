/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.vu.accommodationarea;

/**
 *
 * @author VU-STUDENT
 */
import java.util.InputMismatchException;
import java.util.Scanner;

// Base class for shared behavior
class AccommodationArea {
    protected String name;
    protected int occupants;
    protected boolean[] lights = new boolean[3]; // lights 1–3

    public AccommodationArea(String name) {
        this.name = name;
        this.occupants = 0;
    }

    // Add occupants
    public void addOccupants(int n) {
        if (n > 0) occupants += n;
    }

    // Remove occupants (cannot go below zero)
    public void removeOccupants(int n) {
        if (n > 0) occupants = Math.max(0, occupants - n);
    }

    // Switch lights ON or OFF
    public void switchLight(int number, boolean on) {
        if (number >= 1 && number <= 3) {
            lights[number - 1] = on;
        }
    }

    // Display status
    public void reportStatus() {
        System.out.println("\n--- " + name + " Area Status ---");
        System.out.println("Occupants: " + occupants);
        for (int i = 0; i < lights.length; i++) {
            System.out.println("Light " + (i + 1) + ": " + (lights[i] ? "ON" : "OFF"));
        }
        System.out.println("-----------------------------\n");
    }
}

// Specific area types (inherit from base)
class GymArea extends AccommodationArea {
    public GymArea() {
        super("Gym");
    }
}

class SwimmingArea extends AccommodationArea {
    public SwimmingArea() {
        super("Swimming Pool");
    }
}

public class EstateManager {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        AccommodationArea gym = new GymArea();
        AccommodationArea pool = new SwimmingArea();
        AccommodationArea activeArea = gym; // Default active area

        System.out.println("Welcome to Speke Apartments – Accommodation Control System\n");

        char choice;
        do {
            System.out.println("Main Menu:");
            System.out.println("S – Select active area (G = Gym, P = Swimming)");
            System.out.println("W – Add occupants");
            System.out.println("X – Remove occupants");
            System.out.println("Y – Switch ON light (1–3)");
            System.out.println("Z – Switch OFF light (1–3)");
            System.out.println("R – Report status");
            System.out.println("Q – Quit");
            System.out.print("Enter your choice: ");
            choice = Character.toUpperCase(input.next().charAt(0));

            switch (choice) {
                case 'S':
                    System.out.print("Select area (G = Gym, P = Swimming): ");
                    char areaChoice = Character.toUpperCase(input.next().charAt(0));
                    if (areaChoice == 'G') {
                        activeArea = gym;
                        System.out.println("Active area set to GYM.\n");
                    } else if (areaChoice == 'P') {
                        activeArea = pool;
                        System.out.println("Active area set to SWIMMING POOL.\n");
                    } else {
                        System.out.println("Invalid area choice! Try again.\n");
                    }
                    break;

                case 'W':
                    int addCount = getValidInteger(input, "Enter number of occupants to ADD: ");
                    activeArea.addOccupants(addCount);
                    System.out.println(addCount + " occupants added to " + activeArea.name + ".\n");
                    break;

                case 'X':
                    int removeCount = getValidInteger(input, "Enter number of occupants to REMOVE: ");
                    activeArea.removeOccupants(removeCount);
                    System.out.println(removeCount + " occupants removed from " + activeArea.name + ".\n");
                    break;

                case 'Y':
                    int lightOn = getValidLightNumber(input, "Enter light number (1–3) to SWITCH ON: ");
                    activeArea.switchLight(lightOn, true);
                    System.out.println("Light " + lightOn + " switched ON in " + activeArea.name + ".\n");
                    break;

                case 'Z':
                    int lightOff = getValidLightNumber(input, "Enter light number (1–3) to SWITCH OFF: ");
                    activeArea.switchLight(lightOff, false);
                    System.out.println("Light " + lightOff + " switched OFF in " + activeArea.name + ".\n");
                    break;

                case 'R':
                    activeArea.reportStatus();
                    break;

                case 'Q':
                    System.out.println("Exiting system... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid menu choice. Try again.\n");
            }
        } while (choice != 'Q');

        input.close();
    }

    // Method to validate integer input
    private static int getValidInteger(Scanner input, String prompt) {
        int n = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                n = input.nextInt();
                if (n >= 0) valid = true;
                else System.out.println("Please enter a positive integer.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter an integer.");
                input.next(); // clear invalid input
            }
        }
        return n;
    }

    // Method to validate light numbers (1–3)
    private static int getValidLightNumber(Scanner input, String prompt) {
        int light = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                light = input.nextInt();
                if (light >= 1 && light <= 3) valid = true;
                else System.out.println("Invalid light number! Must be 1, 2, or 3.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter an integer (1–3).");
                input.next();
            }
        }
        return light;
    }
}
