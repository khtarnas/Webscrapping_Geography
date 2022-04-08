import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Set up required variables
        String url = "https://www.cia.gov/the-world-factbook/";
        String rootURL = "https://www.cia.gov/";
        Questions q = new Questions(rootURL, url);
        Scanner reader = new Scanner(System.in);

        System.out.println("Which question would you like answered? (enter a number): ");
        int n = reader.nextInt(); // Scans the next token of the input as an int.

        // Question 1 specifics:
        if (n == 1) {
            System.out.println("You chose the question: List all countries with flags containing both ____ and" +
                    " ____ colors.");

            System.out.println("Enter the first color you would like to search for:");
            String colorOne = reader.next();

            System.out.println("Enter the second color you would like to search for:");
            String colorTwo = reader.next();

            ArrayList<String> temp = q.questionOne(colorOne, colorTwo);

            for (String c : temp) {
                System.out.println(c);
            }

        }

        // Question 2 specifics:
        else if (n == 2) {
            System.out.println("You chose the question: What is the lowest point in the ______ Ocean?");

            System.out.println("Enter the ocean you would like to search for:");
            String ocean = reader.next();

            System.out.println("The lowest point of the " + ocean + " ocean is: -" + q.questionTwo(ocean));

        } else if (n == 3) {
            System.out.println("You chose the question: Find the largest country in ______ in terms of Electricity" +
                    " Production.");

            System.out.println("Enter the continent you would like to search for:");
            String continent = reader.next();

            System.out.println("The largest country in " + continent + " in terms of Electricity production is: " +
                    q.questionThree(continent));

        } else if (n == 4) {
            System.out.println("You chose the question: Norway  is  notoriously  known  for  having  the  largest" +
                    " coastline  in  Europe  despite  its  small  land " +
                    "area. Which country in _______ has the largest coastline to land area ratio?");

            System.out.println("Enter the continent you would like to search for:");
            String continent = reader.next();

            q.questionFour(continent);

        } else if (n == 5) {
            System.out.println("You chose the question: What is the population of the country in South America with" +
                    " the highest mean elevation?");

            System.out.println("Enter the continent you would like to search for:");
            String colorOne = reader.next();

            // go to south america page
            // for each country, get the country name, the mean elevation and population (both listed on country page) -- onyl save the largest, replace if find larger (by pop)

        } else if (n == 6) {
            System.out.println("You chose the question: Many  islands  rely  on  imports  from  larger  countries." +
                    " Which  countries  are  the  Imports Partners for the third largest *country* (by total area)" +
                    " in the _______?");

            System.out.println("Enter the region you would like to search for (e.g. Carribean):");
            String colorOne = reader.next();

            // go through all countries, if it says under location "carribean" then save in list (also their link and also their size)
            // go through all saved links and check third largest for import partners

        } else if (n == 7) {
            System.out.println("You chose the question: Provide a list of all countries starting with the letter" +
                    " '_', sorted by total area, smallest to largest.");

            System.out.println("Enter the letter you would like to search for:");
            String startingLetter = reader.next();

            // Go through all countries
            // If starts with D then add it to a list
            // go through D list and get area
            // sort lists from smaller to largest

        } else if (n == 8) {
            System.out.println("You chose the question: WILD CARD.");

            System.out.println("Enter the continent you would like to search for:");
            String colorOne = reader.next();

        } else {
            System.out.println("You did not enter a valid question number.");
        }

        reader.close();
    }
}
