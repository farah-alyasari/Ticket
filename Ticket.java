package ticket;
/**
 * 
 */

import java.io.*;

/**
 * @author Farah Al Yasari
 * This program performs the following operations:
 * 1) Given the flight number generates a unique ticket number
 * 2) Tracks the number of tickets that were added during this session
 * 3) Stores the ticket and flight number in a text file
 */
public class Ticket {

    //variables needed for this program
    //only the ticket number and the flight number are instance variables
    //the remaining are class variables to operate the program.
    public static int ticketsCounter = 0;
    private static int userChoice = 1;
    private int ticketNumber;
    private int flightNumber;
    private static String fileName = "tickets.txt";
    private static boolean newFile = true;

    //The flight number must be provided by either the user or another program
    //to instantiate and initialize a ticket object
    Ticket(int flightNumber) {
        this.ticketNumber = Ticket.generateTicketNumber();
        this.flightNumber = flightNumber;
        addTicketToFile(this.flightNumber, this.ticketNumber);
        Ticket.ticketsCounter++;
    }

    //Generates a unique ticket number in the range of 1 to 999999
    private static int generateTicketNumber() {
        int ticketNumber = (int)(Math.random() * (999999 - Ticket.ticketsCounter + 1) + Ticket.ticketsCounter);

        while (newFile == false && isInFile(ticketNumber) == true) {
            ticketNumber = (int)(Math.random() * (999999 - Ticket.ticketsCounter + 1) + Ticket.ticketsCounter);
        }

        return ticketNumber;
    }

    //Adds a ticket to a local file, if the file is not present, it will make it
    public void addTicketToFile(int flightNumber, int ticketNumber) {
        boolean fileCreated = createFile(Ticket.fileName);
        if (fileCreated == true) {
            try {
                //write to the existing file
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                writer.append(flightNumber + "," + ticketNumber + "\n");
                System.out.println("ticket successfully added ");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error creating the file");
        }
    }

    //checks if a file is present with the specified file name
    //if a file is not present it will make a file in the root repository of the project
    private static boolean createFile(String fileName) {
        boolean fileCreated = true;
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                fileCreated = true;
            }
        } catch (Exception e) {
            System.out.println("Error creating the file");
        }
        return fileCreated;
    }

    //checks that the given randomly generated number has not been used for a prior ticket
    public static boolean isInFile(int num) {
        boolean found = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader(Ticket.fileName));
            String line;
            while ((line = br.readLine()) != null && found == false) {
                // process the line.
                String[] values = line.split(",");
                if (Integer.parseInt(values[1]) == num) {
                    found = true;
                }
            }
            br.close();
        } catch (Exception exception) {
            System.out.println("Error the file is not present");
        }
        return found;
    }

}