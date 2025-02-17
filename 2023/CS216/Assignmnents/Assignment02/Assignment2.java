package CS216.Assignmnet02;
import java.util.Scanner;


public class Assignment2 {
    
    //Main method that gathers user information
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //user could be more than one im just lazy
        User[] users = new User[1];

        //for loop that will go through each part of the object
        for (int i = 0; i < 1; i++) {
            System.out.println("Enter user " + (i+1) + " information:");
            System.out.print("Age: ");
            int age = scanner.nextInt();
            System.out.print("Height (in): ");
            int height = scanner.nextInt();
            System.out.print("Weight: ");
            int weight = scanner.nextInt();
            System.out.print("Gender: ");
            String gender = scanner.next();
            System.out.print("Activity Type: ");
            String activityType = scanner.next();
            System.out.print("MPH: ");
            double mph = scanner.nextDouble();
            System.out.print("Minutes: ");
            int minutes = scanner.nextInt();
            System.out.println();

            users[i] = new User(age, height, weight, gender, activityType, mph, minutes);
        }

        //for loop that prints all info to the terminal
        for (int i = 0; i < 1; i++) {
            System.out.println("User " + (i+1) + " information:");
            users[i].printUser();
            System.out.println();
        }

        scanner.close();
    }
}
