import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Set up required variables
        String url = "https://www.cia.gov/the-world-factbook/";
        String rootURL = "https://www.cia.gov/";
        Questions q = new Questions(rootURL, url);
        Scanner reader = new Scanner(System.in);

        System.out.println("There are the following 8 questions that can be answered:\n");

        System.out.println("1. List all countries with flags containing both red and green colors. \n" +
                "2. What is the lowest point in the Atlantic Ocean? \n" +
                "3. Find the largest country in Africa in terms of Electricity Production. \n" +
                "4. Norway  is  notoriously  known  for  having  the  largest  coastline  in  Europe  despite  its" +
                "  small  land " +
                "area. Which country in Europe has the largest coastline to land area ratio? \n" +
                "5. What is the population of the country in South America with the highest mean elevation? \n" +
                "6. Many  islands  rely  on  imports  from  larger  countries.  Which  countries  are  the  Imports" +
                "  Partners " +
                "for the third largest island (by total area) in the Caribbean? \n" +
                "7. Provide a list of all countries starting with the letter “D”, sorted by total area, smallest" +
                " to largest. \n" +
                "8. What country in East and Southeast Asia with the color green in their flag has the most internet users?\n");

        System.out.println("Which question would you like answered? (enter a number): ");
        int n = Integer.parseInt(reader.nextLine());

        // Question 1 specifics:
        if (n == 1) {
            System.out.println("You chose the question: List all countries with flags containing both ____ and" +
                    " ____ colors.");

            System.out.println("Enter the first color you would like to search for:");
            String colorOne = reader.nextLine();

            System.out.println("Enter the second color you would like to search for:");
            String colorTwo = reader.nextLine();

            ArrayList<String> temp = q.questionOne(colorOne, colorTwo);

            for (String c : temp) {
                System.out.println(c);
            }

        }

        // Question 2 specifics:
        else if (n == 2) {
            System.out.println("You chose the question: What is the lowest point in the ______ Ocean?");

            System.out.println("Enter the ocean you would like to search for:");
            String ocean = reader.nextLine();

            System.out.println("The lowest point of the " + ocean + " ocean is: -" + q.questionTwo(ocean));

        } else if (n == 3) {
            System.out.println("You chose the question: Find the largest country in ______ in terms of Electricity" +
                    " Production.");

            System.out.println("Enter the continent you would like to search for:");
            String continent = reader.nextLine();

            System.out.println("The largest country in " + continent + " in terms of Electricity production is: " +
                    q.questionThree(continent));

        } else if (n == 4) {
            System.out.println("You chose the question: Norway  is  notoriously  known  for  having  the  largest" +
                    " coastline  in  Europe  despite  its  small  land " +
                    "area. Which country in _______ has the largest coastline to land area ratio?");

            System.out.println("Enter the continent you would like to search for:");
            String continent = reader.nextLine();

            System.out.println("The country in " + continent + " with the largest coastline to land area ratio is " + q.questionFour(continent));

        } else if (n == 5) {
            System.out.println("You chose the question: What is the population of the country in ______ with" +
                    " the highest mean elevation?");

            System.out.println("Enter the continent you would like to search for:");
            String continent = reader.nextLine();

            System.out.println("The population of the country with the highest mean elevation in " + continent +
                    " is: " + q.questionFive(continent));

        } else if (n == 6) {
            System.out.println("You chose the question: Many  islands  rely  on  imports  from  larger  countries." +
                    " Which  countries  are  the  Imports Partners for the third largest *country* (by total area)" +
                    " in the _______?");

            System.out.println("Enter the region you would like to search for (e.g. Caribbean, Southern Africa, etc.):");

            String region = reader.nextLine();

            System.out.println("The Imports Partners for the third largest country in " + region + " are as" +
                    " follows: " + q.questionSix(region));

        } else if (n == 7) {
            System.out.println("You chose the question: Provide a list of all countries starting with the letter" +
                    " '_', sorted by total area, smallest to largest.");

            System.out.println("Enter the letter you would like to search for:");
            String startingLetter = reader.nextLine();

            System.out.println("The list of all countries starting with the letter " + startingLetter + " sorted by total" +
                    " area, smallest to largest, are: ");

            ArrayList<String> temp = q.questionSeven(startingLetter);

            for (String c : temp) {
                System.out.println(c);
            }

        } else if (n == 8) {
            System.out.println("You chose the question: What country in _____ with the color ____ in their flag has" +
                    " the most internet users?");

            System.out.println("Enter the continent you would like to search for:");
            String continent = reader.nextLine();

            System.out.println("Enter the color you would like to search for:");
            String color = reader.nextLine();

            System.out.println("The country in " + continent + " with the color " + color + " in their flag that has" +
                    " the most internet users is: " + q.questionEight(continent, color));

        } else {
            System.out.println("You did not enter a valid question number.");
        }

        reader.close();
    }
}
