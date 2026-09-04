package Disneyland;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class DatabaseConnect
{
    private static Connection conn = null;
    public static void main(String args[]) {
        System.out.println("Hiya, pal! Welcome to our Route and Reservation System designed to make your Disneyland Paris adventure as easy as pie!");
        System.out.println("Just tell us where you’d like to go and what you’d like to do, and we’ll help you plan your most magical day ever.");
        System.out.println("Oh boy, let’s get started!");

        DatabaseConnect conn = new DatabaseConnect();
        Scanner in = new Scanner(System.in);
        boolean exit = false; // Flag to keep the menu running

        while (!exit) { // Loop to keep program running until asked to exit
            // Display menu options
            System.out.println("================= MENU ===================");
            System.out.println("{1} Display Visitor Details");
            System.out.println("{2} Display Booking Information");
            System.out.println("{3} Display Attractions and Reviews");
            System.out.println("{4} Display Ticket Types and Prices");
            System.out.println("{5} Make a new Booking");
            System.out.println("{6} Add a Review");
            System.out.println("{7} Find path from a ride to another.");
            System.out.println("{8} Update booking");
            System.out.println("{9} Update visitor details");
            System.out.println("{10} Delete visitor or booking information");
            System.out.println("{11} Exit");
            System.out.println("===========================================");
            System.out.print("Please select an option (1-11): ");

            if (in.hasNextInt()) {
                int choice = in.nextInt();
                in.nextLine(); // Consume newline character

                if (choice >= 1 && choice <= 11) {
                    switch (choice) {
                        case 1:
                            conn.displayVisitor();
                            break;
                        case 2:
                            conn.displayBooking();
                            break;
                        case 3:
                            conn.displayAverageReviews();
                            break;
                        case 4:
                            conn.displayTicketPrices();
                            break;
                        case 5:
                            conn.AddBooking();
                            break;
                        case 6:
                            conn.AddReview();
                            break;
                        case 7:
                            conn.DjikstraPathAndDistance();
                            break;
                        case 8:
                            conn.updateBooking();
                            break;
                        case 9:
                            conn.updateVisitor();
                            break;
                        case 10:
                            conn.deleteRecord();
                            break;
                        case 11:
                            exit = true;
                            break;
                    }
                    if (!exit) {
                        System.out.println("Would you like to use another service? (y or n)");
                        String input = in.nextLine().toLowerCase();

                        if (input.equals("n")) {
                            exit = true;
                        } else if (!input.equals("y")) {
                            System.out.println("Invalid choice. Returning to main menu.");
                        }
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 11.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                in.nextLine(); // Clear the invalid input
            }
            System.out.println(); // Empty line for better formatting
        }

        System.out.println("Thank you for using our service! Have a jolly good day! :)");
        in.close(); // Close the input before exiting
    }

    public boolean isNumeric(String str)//CHECKS WHETHER STRING IS NUMBER OR NOT
    {
        try
        {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e)
        {
            return false;
        }
    }

    public DatabaseConnect()// to connect to named database
    {
        try
        {
            //connecting to the database
            Class.forName("org.sqlite.JDBC");//Specify the SQLite Java driver
            conn = DriverManager.getConnection("jdbc:sqlite:DisneyDatabase.db");//Specify the database, since relative in the main project folder
            conn.setAutoCommit(false);// Important as you want control of when data is written
            System.out.println("Opened database successfully");
        } catch (Exception e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public void close() 
    {
        //closing database when the procedure is called
        try
        {
            conn.close();
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //to validate every booking ID entered (for update/deletion or display)
    public int validateBookingID() {
        Scanner in = new Scanner(System.in);
        int bookingID = -1;

        // Loop for valid BookingID input (3 tries max)
        for (int attempts = 1; attempts <= 3; attempts++)
        {
            System.out.println("Please enter your Booking ID: ");
            String bookingInput = in.nextLine();

            // Validate if the BookingID is numeric
            if (isNumeric(bookingInput))
            {
                bookingID = Integer.parseInt(bookingInput);

                // Check if the BookingID exists in the database
                try (Statement stmt = conn.createStatement()) {
                    String checkBookingQuery = "SELECT * FROM Booking WHERE BookingID = " + bookingID;
                    ResultSet rs = stmt.executeQuery(checkBookingQuery);

                    if (rs.next())
                    {
                        // Booking ID exists, break out of the loop
                        System.out.println("Booking found.");
                        return bookingID;  // Return valid booking ID
                    }
                    else
                    {
                        System.out.println("Booking ID not found in the database. Please try again.");
                    }
                }
                catch (SQLException e)
                {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            }
            else
            {
                System.out.println("Booking ID must be numeric. Please try again.");
            }

            if (attempts == 3)
            {
                System.out.println("You have entered invalid Booking ID 3 times.");
                System.out.println("Mickey is suspicious...");
                return -1;  // Return -1 if validation fails after 3 attempts
            }
        }
        return bookingID;
    }


    //To validate every visitor ID entered (for update/deletion or display)
    public int validateVisitorID()
    {
        Scanner in = new Scanner(System.in);
        int visitorID = -1;


        // Loop for valid VisitorID input (3 tries max)
        for (int attempts = 1; attempts <= 3; attempts++)
        {
            System.out.println("Please enter your Visitor ID: ");
            String visitorInput = in.nextLine();

            // Validate if the VisitorID is numeric
            if (isNumeric(visitorInput))
            {
                visitorID = Integer.parseInt(visitorInput);

                // Check if the VisitorID exists in the database
                try (Statement stmt = conn.createStatement())
                {
                    String checkVisitorQuery = "SELECT VisitorID FROM Visitor WHERE VisitorID = " + visitorID;
                    ResultSet rs = stmt.executeQuery(checkVisitorQuery);

                    if (rs.next())
                    {
                        // Visitor ID exists, break out of the loop
                        System.out.println("Visitor found.");
                        return visitorID;  // Return valid visitor ID
                    }
                    else
                    {
                        System.out.println("Visitor ID not found in the database. Please try again.");
                    }
                }
                catch (SQLException e)
                {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                }
            }
            else
            {
                System.out.println("Visitor ID must be numeric. Please try again.");
            }

            if (attempts == 3)
            {
                System.out.println("You have entered invalid Visitor ID 3 times.");
                System.out.println("Mickey is suspicious...");
                return -1;  // Return -1 if validation fails after 3 attempts
            }
        }
        return visitorID;
    }

    //Route Finder
    public boolean DjikstraPathAndDistance() {
        System.out.println("Let's find the shortest route to fun!");
        System.out.println();
        Scanner in = new Scanner(System.in);
        Statement stmt = null;
        int destId = -1;
        boolean bFound = false;

        while (true)
        {
            System.out.println("Please enter the ride name/ID you are trying to go to: ");
            String name1 = in.nextLine();
            String name = name1.toLowerCase(); // Convert to lowercase to avoid confusion

            // Check if the input is numeric
            if (isNumeric(name)) {
                destId = Integer.parseInt(name); // Convert to int if it's numeric
                if (destId >= 1 && destId <= 16) { // Check if ID is within range
                    break;
                } else {
                    System.out.println("ID must be between 1 and 16 (inclusive). Please try again.");
                }
            } else {
                // If the input is a name, look up the ID in the database
                try {
                    stmt = conn.createStatement();
                    String sql = "SELECT AttractionID FROM Attraction WHERE AttractionName = '" + name + "'";
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        destId = rs.getInt("AttractionID");
                        break; // Exit the loop once a valid ID is found
                    } else {
                        System.out.println("Ride name not found. Please try again.");
                    }
                    rs.close();
                } catch (SQLException e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    return false; // Exit on SQL error
                }
            }
        }

        int source = -1;

        while (true)
        {
            System.out.println("Please enter the ride name/ID you are trying to go from: ");
            String name1 = in.nextLine();
            String name = name1.toLowerCase(); // Convert to lowercase to avoid confusion

            // Check if the input is numeric
            if (isNumeric(name)) {
                source = Integer.parseInt(name); // Convert to int if it's numeric
                if (source >= 1 && source <= 16) { // Check if ID is within range
                    break;
                } else {
                    System.out.println("ID must be between 1 and 16 (inclusive). Please try again.");
                }
            } else {
                // If the input is a name, look up the ID in the database
                try {
                    stmt = conn.createStatement();
                    String sql = "SELECT AttractionID FROM Attraction WHERE AttractionName = '" + name + "'";
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        source = rs.getInt("AttractionID");
                        break; // Exit the loop once a valid ID is found
                    } else {
                        System.out.println("Ride name not found. Please try again.");
                    }
                    rs.close();
                } catch (SQLException e) {
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    return false; // Exit on SQL error
                }
            }
        }
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Route";//retrieve all data from route table for dijkstra
            ResultSet rs = stmt.executeQuery(sql);
            Graph graph = new Graph();
            while (rs.next()) {
                int sourceNode = rs.getInt("SourceID");
                int destNode = rs.getInt("NextID");
                int distance = rs.getInt("Distance");
                graph.add(sourceNode, destNode, distance);//add all the nodes and distance to graph

            }
            Djikstra djikstra = new Djikstra();
            System.out.println("Here is a list of rides to go through to arrive at your destination");
            System.out.println("ROUTE = "+djikstra.Path(destId, graph, source));//display route of nodes
            System.out.println("DISTANCE = "+djikstra.pathLength(destId, graph, source)+ " meters");//display total distance to travel

            rs.close();
            stmt.close();
            bFound = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bFound;

    }



    //display visitor details of visitor with given ID
    public boolean displayVisitor()
    {
        System.out.println("Let's see your details pal!");
        System.out.println();
        //Select query
        boolean bSelect = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner in = new Scanner(System.in);

        int vID = validateVisitorID();//input the ID and validate

        if(vID != -1)
        {
            try
            {
                stmt = conn.createStatement();
                String query = "SELECT * FROM Visitor WHERE VisitorID = " + vID;//query to retrieve and display details of the visitor
                rs = stmt.executeQuery(query);

                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String PhoneNumber = rs.getString("PhoneNumber");
                String email= rs.getString("email");

                System.out.println("ID = " + vID);
                System.out.println("FIRSTNAME = " + FirstName);
                System.out.println("LASTNAME = " + LastName);
                System.out.println("PHONE = " + PhoneNumber);
                System.out.println("EMAIL = " + email);
                System.out.println();

                rs.close();
                stmt.close();
                bSelect = true;
            }
            catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                return false;
            }
        }
        else
        {
            System.out.println("Try again later...");//after 3 attempt, program ends.
        }
        return bSelect;
    }

    //to display a specific booking detial with given ID and visitor ID
    public boolean displayBooking()
    {
        System.out.println("Let's see your booking details!");
        System.out.println();
        boolean bSelect = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner in = new Scanner(System.in);

        try
        {
            //input and validate both IDs
            int bID = validateBookingID();
            int vID = validateVisitorID();

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Booking WHERE BookingID = '"+ bID+"' AND VisitorID = '"+ vID+ "'");

            if (rs.next())//retrieve all bk=ooking record with given ID and given visitor ID
            {
                int BookingID = rs.getInt("BookingID");
                int VisitorID = rs.getInt("VisitorID");
                String BookingType = rs.getString("BookingType");
                String TicketType = rs.getString("TicketType");
                String Arrival = rs.getString("Arrival");
                String BookingDate = rs.getString("BookingDate");

                System.out.println("BOOKING ID = " + BookingID);
                System.out.println("VISITOR ID = " + VisitorID);
                System.out.println("BOOKING TYPE = " + BookingType);
                System.out.println("TICKETS TYPE = " + TicketType);
                System.out.println("ARRIVAL = " + Arrival);
                System.out.println("BOOKING DATE = " + BookingDate);
                System.out.println();


                String choice;//initialisation

                //loop validation to only write y OR n
                while(true)
                {
                    //to display payment details of the booking
                    System.out.println("Would you like to see your payment details? y or n:");
                    choice = in.nextLine();
                    if (choice.equals("y") || choice.equals("n"))//validation
                    {
                        if (choice.equals("y")) {displayPurchase(BookingID);}//method to display details
                        else
                        {
                            break;
                        }
                        break;
                    }
                    else
                    {
                        System.out.println("Please enter only 'y' for yes or 'n' for no. ");
                    }

                }

            }
            else
            {
                System.out.println("Oh boy! It appears the wrong set of IDs have been input. No worries! Check you have the right details and try again.");
            }

            rs.close();
            stmt.close();
            bSelect = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bSelect;
    }

    //EVERY PURCHASE HAS A BOOKING
    //to display full payment details of a booking
    public boolean displayPurchase(int BookingID)
    {
        System.out.println("Displaying payment details...");
        System.out.println();
        boolean bSelect = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner in = new Scanner(System.in);

        try
        {
            //Query to display purchase, filtered by given booking ID
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PurchaseID, BookingID, TotalAmount, Method, PurchaseDate FROM Purchase WHERE BookingID = '"+ BookingID+ "'");

            while (rs.next())
            {
                int PurchaseID = rs.getInt("PurchaseID");
                float TotalAmount = rs.getInt("TotalAmount");
                String amount = String.format("%.2f", TotalAmount);//to 2 decimal place
                String Method = rs.getString("Method");
                String PurchaseDate = rs.getString("PurchaseDate");


                System.out.println("TOTAL AMOUNT = € " + amount);
                System.out.println("PAYMENT METHOD = " + Method);
                System.out.println("PURCHASE DATE = " + PurchaseDate);
                System.out.println();
            }

            rs.close();
            stmt.close();
            bSelect = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bSelect;
    }

    //display attractions
    public boolean displayAttractions(String Type)  //Type: thrill or family INPUT BY THE USER
    {
        System.out.println("Here are descriptions of all the exciting rides for your booking!");
        System.out.println();
        //Select query
        boolean bSelect = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner in = new Scanner(System.in);

        try
        {
            //query to display attractions filtered by the group type given during booking
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Attraction WHERE Type = '"+ Type+ "'");

            while (rs.next())
            {
                int AttractionID = rs.getInt("AttractionID");
                String AttractionName = rs.getString("AttractionName");
                String Description = rs.getString("Description");
                int Duration = rs.getInt("Duration");


                System.out.println("ID = " + AttractionID);
                System.out.println("NAME = " + AttractionName);
                System.out.println("DESCRIPTION = " + Description);
                System.out.println("DURATION = " + Duration);
                System.out.println();
            }

            String choice;//initialisation

            //validation to only write y OR n
            while(true)
            {
                System.out.println("Would you like to see reviews? y or n:");
                choice = in.nextLine();
                if (choice.equals("y") || choice.equals("n"))
                {
                    if (choice.equals("y"))
                    {
                        displayReviews(Type );
                    }
                    else
                    {
                        break;
                    }
                    break;
                }
                else
                {
                    System.out.println("Please enter only 'y' for yes or 'n' for no. ");
                }
            }

            rs.close();
            stmt.close();
            bSelect = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bSelect;
    }

    //display all ticket prices from tickets table
    public boolean displayTicketPrices()
    {
        System.out.println("TICKET PRICES:");
        System.out.println();
        //Select query
        boolean bSelect = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner in = new Scanner(System.in);

        try
        {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Tickets ");

            while (rs.next())
            {
                String Type = rs.getString("Type");
                float Price = rs.getFloat("Price");

                System.out.println("Type = " + Type);
                System.out.println("Price = € " + Price);
                System.out.println();
            }

            rs.close();
            stmt.close();
            bSelect = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bSelect;
    }

    public int AddVisitor()
    {
        //This is to be used in the addBooking method as before adding the customer, the visitor details must be added to
        // get the visitorID which links both booking and visitor table
        // this function return the visitorID but can be changed to boolean (bInsert) to only add visitor.
            //boolean bInsert = false;
            int VisitorID = -1;
            Statement stmt = null;
            ResultSet rs = null;
            Scanner input = new Scanner(System.in);

            try
            {
                //inputs from the user
                System.out.println("Hiya, new pal! Gosh, I'm so excited you're planning to visit Disneyland Paris! \n Could you tell me a bit about you and your group? I just need some teeny-tiny details.\n Oh boy, we're gonna have so much fun!");
                System.out.println("Visitor Details:");
                System.out.println();
                //enter names of visitor booking
                System.out.println("Please enter first name:");
                String FirstName = input.nextLine();

                System.out.println("Please enter last name:");
                String LastName = input.nextLine();


                String phone = validNumber();
                // method that includes validation that it is a number and has 11 digits


                String email = validEmail();
                //method that validates  in form STRING@STRING.STRING


                stmt = conn.createStatement();
                //query to add the inputted data into table
                String sql = "INSERT INTO Visitor (FirstName,LastName,PhoneNumber,email) " +
                        "VALUES ('"+FirstName+"', '"+ LastName+"', '"+ phone+"', '"+ email+"' );";

                //code to generate new primary key/ ID based on previous existing IDs.
                int affectedRows = stmt.executeUpdate(sql);
                //Retrieve the generated key (primary key)
                if (affectedRows > 0) {
                    rs = stmt.executeQuery("SELECT last_insert_rowid() AS VisitorID");
                    if (rs.next()) {
                        VisitorID = rs.getInt("VisitorID");  // Assuming the primary key is of type long
                        System.out.println("Please note down your visitor ID to view/update your booking or visitor details in the future.");
                        System.out.println("VisitorID: " + VisitorID);

                        System.out.println("VISITOR DETAILS SAVED!!!.");
                    }
                }
                conn.commit();

                //extra try and catch exception to be safe
            }

            catch (SQLException e)
            {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }

            return VisitorID;
    }

    public boolean AddBooking()
    {
        displayTicketPrices();//to display ticket prices before booking
        System.out.println();

        boolean bInsert = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner input = new Scanner(System.in);
        boolean ExistingVisitor = false;
        try
        {
            // Ask whether the visitor is new or existing
            while (true)
            {
                System.out.println("Are you an existing visitor? (y/n):");
                String visitorType = input.nextLine().toLowerCase();
                if (visitorType.equals("y"))
                {
                    ExistingVisitor = true;
                    break;
                }
                else if (visitorType.equals("n"))
                {
                    ExistingVisitor = false;
                    break;
                }
                else
                {
                    System.out.println("Please enter 'y' for yes or 'n' for no.");
                }
            }

            int VisitorID = -1;//initialise vairable ot get ID
            if (ExistingVisitor) // existing visitors do not need to add their details again
            {
                VisitorID = validateVisitorID();
            }
            else //new visitor need to enter their details to be stored
            {
                VisitorID = AddVisitor();// Call the method to get the VisitorID
            }
            int BookingID = -1;
            if (VisitorID == -1)
            {
                throw new SQLException("Failed to add visitor's details");
            }
            //inputs from the user
            System.out.println("Booking Details:");
            System.out.println("Hot dog! Let's make some magic happen!\n Can you let me know when you’d like to visit and which of our wonderful attractions you’d like to see?\n Oh, and don’t forget to share any special wishes you have for your visit!");
            System.out.println();
            String BookingType = validType();//Visitor chooses the type of group they are booking for(+validation)

            String TicketType = VticketType();//Visitor chooses the type of ticket they want(+validation)

            String Arrival;
            while(true)
            {
                System.out.println("Please enter the date of arrival (format YYYY-MM-DD):");
                Arrival = input.nextLine();
                if(Arrival.matches("^\\d{4}-\\d{2}-\\d{2}$"))//regex to compare the format
                {
                    break;
                }
                else
                {
                    System.out.println("Please enter the data in YYYY-MM-DD format.");
                }
            }

            //add today's date for booking date in right format
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String BookingDate = today.format(formatter);

            //query to add all input data into table
            stmt = conn.createStatement();

            String sql = "INSERT INTO Booking (VisitorID,BookingType,TicketType,Arrival,BookingDate) " +
                    "VALUES ('"+VisitorID+"', '"+ BookingType +"', '"+ TicketType+"', '"+ Arrival +"', '" +BookingDate+"');";
            //generates new primary key for Booking (BookingID)
            int affectedRows = stmt.executeUpdate(sql);
            //Retrieve the generated key (primary key)
            if (affectedRows > 0) {
                rs = stmt.executeQuery("SELECT last_insert_rowid() AS BookingID");
                if (rs.next()) {
                    BookingID = rs.getInt("BookingID");  // Assuming the primary key is of type long
                    System.out.println("Please note down your booking ID to view/update your booking or visitor details in the future.");
                    System.out.println("BookingID: " + BookingID);

                    bInsert = true;
                    AddPayment(BookingID,TicketType);
                    System.out.println("BOOKING COMPLETE AND SAVED!!!");
                }
            }
            //ADD QUERY TO DISPLAY ATTRACTIONS OF THEIR TYPE AND ASK IF THEY WANT SEE REVIEWS
            String choice;

            //validation to only write y OR n
            while(true)
            {
                System.out.println("Would you like the attractions in you're booking? y or n:");
                choice = input.nextLine();
                if (choice.equals("y") || choice.equals("n"))
                {
                    if (choice.equals("y"))
                    {
                        displayAttractions(BookingType);
                    }
                    else
                    {
                        break;
                    }
                    break;
                }
                else
                {
                    System.out.println("Please enter only 'y' for yes or 'n' for no. ");
                }

            }
            conn.commit();

        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
            return bInsert;

    }

    public boolean updateVisitor()
    {
        //method to modify a customer's details; similar can be used to change other details
        // remember to implement the other method using this.
        System.out.println("Let's update your details haha!:");
        System.out.println();
        boolean bUpdated = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner input = new Scanner(System.in);

        try {
            // Input visitor's ID to update their visitor details
            int vID = validateVisitorID();

            String Uchoice;
                while(true)//validate their choice
                {
                    //choose
                    System.out.println("Please select what you would like to modify: 1.Firstname" +
                        " 2.Lastname     3. Phone Number     4. Email        5.Exit");
                    Uchoice = input.nextLine();

                    if (!isNumeric(Uchoice))
                    {
                        System.out.println("Please enter a number between 1 and 5.");
                        continue;
                    }

                    int choice = Integer.parseInt(Uchoice);//convert validated string into integer
                    if (choice>= 1 && choice <= 5)
                    {
                        try {
                            switch (choice)//update details depending on user choice
                            {
                            case 1:
                                System.out.println("Enter your First Name: ");
                                String firstname = input.nextLine();
                                stmt = conn.createStatement();
                                String sql = "UPDATE Visitor set FirstName = '"+firstname+"' where VisitorID='"+vID+"';";
                                stmt.executeUpdate(sql);
                                stmt.close();
                                conn.commit();
                                break;
                            case 2:
                                System.out.println("Enter your Last Name: ");
                                String lastname = input.nextLine();
                                stmt = conn.createStatement();
                                String sqltwo = "UPDATE Visitor set LastName = '"+lastname+"' where VisitorID='"+vID+"';";
                                stmt.executeUpdate(sqltwo);
                                stmt.close();
                                conn.commit();
                                break;
                            case 3:

                                String number = validNumber();//validate format
                                stmt = conn.createStatement();
                                String sqlthree = "UPDATE Visitor set PhoneNumber = '"+number+"' where VisitorID='"+vID+"';";
                                stmt.executeUpdate(sqlthree);
                                stmt.close();
                                conn.commit();
                                break;
                            case 4:
                                String email = validEmail();//validate format
                                stmt = conn.createStatement();
                                String sqlfour = "UPDATE Visitor set email = '"+email+"' where VisitorID='"+vID+"';";
                                stmt.executeUpdate(sqlfour);
                                stmt.close();
                                conn.commit();
                                break;

                            default:
                                System.out.println("Bye bye :)");
                                break;
                            }
                        }
                            catch (Exception e) {
                                System.out.println("Error updating field: " + e.getMessage());
                                conn.rollback();
                            }
                            break;
                    }
                    else
                    {
                        System.out.println("Please enter the NUMBER corresponding your choice from 1-5: ");
                    }
                }
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bUpdated;
    }

    public boolean updateBooking()
    {
        //method to modify a customer's details; similar can be used to change other details
        // remember to implement the other method using this.
        System.out.println("Let's update your booking !");
        System.out.println();
        boolean bUpdated = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner input = new Scanner(System.in);

        try {
            int bID = validateBookingID();
            String Uchoice;
            while(true)//loop to validate choice
            {
                System.out.println("Please select what you would like to modify: 1.Booking Type" +
                        " 2.Ticket type     3. Arrival Date     4.Exit");
                Uchoice = input.nextLine();

                if (!isNumeric(Uchoice))
                {
                    System.out.println("Please enter a number between 1 and 4.");
                    continue;
                }

                int choice = Integer.parseInt(Uchoice);//convert choice into integer
                if (choice>= 1 && choice <= 4)
                {
                    try {
                        switch (choice)//update a detail depending on choice
                        {
                            case 1:

                                String BookingType = validType();//valdiate format
                                //Validate to be onl 1 or 2
                                stmt = conn.createStatement();
                                String sql = "UPDATE Booking set BookingType = '"+BookingType+"' where BookingID='"+bID+"';";
                                stmt.executeUpdate(sql);
                                stmt.close();
                                conn.commit();
                                break;

                            case 2:

                                String TicketType = VticketType();//validate format
                                stmt = conn.createStatement();
                                String sqltwo = "UPDATE Booking set TicketType = '"+TicketType+"' where BookingID='"+bID+"';";
                                stmt.executeUpdate(sqltwo);
                                stmt.close();
                                conn.commit();
                                break;

                            case 3:

                                String Arrival;
                                while(true)
                                {
                                    System.out.println("Please enter the date of arrival (format YYYY-MM-DD):");
                                    Arrival = input.nextLine();
                                    if(Arrival.matches("^\\d{4}-\\d{2}-\\d{2}$"))//validate format
                                    {
                                        break;
                                    }
                                    else
                                    {
                                        System.out.println("Please enter the data in YYYY-MM-DD format.");
                                    }
                                }
                                stmt = conn.createStatement();
                                String sqlthree = "UPDATE Booking set Arrival = '"+Arrival+"' where BookingID='"+bID+"';";
                                stmt.executeUpdate(sqlthree);
                                stmt.close();
                                conn.commit();
                                break;

                            default:
                                System.out.println("Have a magical day :)");
                                break;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Error updating field: " + e.getMessage());
                        conn.rollback();
                    }
                    break;
                }
                else
                {
                    System.out.println("Please enter the NUMBER corresponding your choice from 1 to 4: ");
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bUpdated;
    }

    public String validType()//Validates the booking type
    {
        Scanner input = new Scanner(System.in);
        String in;
        String BookingType;
        //Validate to be only 1 or 2
        while(true)
        {
            System.out.println("Please choose whether you would like to book for: 1. Thrill Group  2. Family");
            in = input.nextLine();

           if(isNumeric(in))
           {
               if (in.equals("1") || in.equals("2"))
               {
                   break;
               }
               else
               {
                   System.out.println("Invalid choice. Please input only 1 or 2.");
               }
           }
           else
           {
               System.out.println("Please enter your numeric choice.");
           }
        }
        if (in.equals("1"))
        {
            BookingType = "Thrill";
        }
        else
        {
            BookingType = "Family";
        }
        return BookingType;
    }

    public String VticketType()//Validates the ticket type
    {
        Scanner input = new Scanner(System.in);
        String choice2;
        int c;
        String TicketType;
        while(true) //user chooses type of ticket (price is dependable) and also validates it to correct format
        {
            System.out.println("Please enter what type of tickets you would like to book for (ONLY 1-3): 1.Standard    2.Premier Access One    3.Premier Access Ultimate ");
            choice2 = input.nextLine();

            if(isNumeric(choice2))
            {
                c = Integer.parseInt(choice2);
                if (c <= 3 && c >= 1)
                {
                    break;
                }
                else
                {
                    System.out.println("Hiya Pal! Could you please input only 1, 2 or 3.");
                }
            }
            else
            {
                System.out.println("Please enter your numeric choice.");
            }
        }
        if (c == 1)
        {
            TicketType = "Standard";
        }
        else if (c == 2)
        {
            TicketType = "PremierAccess";
        }
        else
        {
            TicketType = "UltimateAccess";
        }
        return TicketType;
    }



    public String validNumber()//VALIDATION OF NUMBER FORMAT - must be 11 digits
    {
        Scanner input = new Scanner(System.in);
        String number;
        while(true)
        {
            System.out.println("Enter your phone number: ");
            number = input.nextLine();
            if(number.length()== 11 && isNumeric(number))
            {
                break;
            }
            else
            {
                System.out.println("Phone number must be 11 DIGITS pal.");
            }
        }
        return number;
    }

    public String validEmail()//Validate email format to STRING@EMAIL.COM
    {
        Scanner input = new Scanner(System.in);
        String email;
        while(true)
        {
            System.out.println("Enter your email address: ");
            email = input.nextLine();
            if(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))//
            {
                break;
            }
            else
            {
                System.out.println("Email address must be in the format: string@string.com");
            }
        }
        return email;
    }

    public boolean deleteRecord()//delete all visitor or specific booking
    {
        System.out.println("Oh no! Are you sure you want to cancel your magical plans?\n If something’s come up, we understand, and we’ll be here to help you book again anytime.\n Just let us know what you need!");
        System.out.println();

        boolean bDelete = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner input = new Scanner(System.in);

        try {
            String table;// stores what where user wants to delete record from
            //validation to input only 1 or 2
            while(true)
            {
                System.out.println("Please enter whether you would like to  1.delete Visitor records or 2.cancel a Booking (1 or 2):");
                table = input.nextLine();
                if (isNumeric(table) && (table.equals("1")|| table.equals("2")))
                {
                    break;
                }
                else
                {
                    System.out.println("Please enter 1 or 2 as your choice.");
                }
            }

            if (table.equals("1")) // If visitor needs to be deleted, all associated bookings and purchases must also be deleted.(entity relationship)
            {
                //Creating list of integer to store all booking and purchase IDs associated with the visitor
                List<Integer> bookingIDs = new ArrayList<>();
                List<Integer> purchaseIDs = new ArrayList<>();

                int VisitorID = validateVisitorID(); // Retrieve the Visitor ID
                stmt = conn.createStatement();
                String query ="SELECT Booking.BookingID, Purchase.PurchaseID FROM Booking " +
                        "LEFT JOIN Purchase ON Booking.BookingID = Purchase.BookingID " +
                        "WHERE Booking.VisitorID = " + VisitorID;
                // Get all bookings and purchases for the visitor
                ResultSet resultSet = stmt.executeQuery(query);

                // Iterate through the results and add all IDs to the array
                while (resultSet.next())
                {
                    int bookingID = resultSet.getInt("BookingID");
                    int purchaseID = resultSet.getInt("PurchaseID");

                    bookingIDs.add(bookingID);
                    purchaseIDs.add(purchaseID);
                }
                resultSet.close();
                stmt.close();

                // Delete associated Purchase records
                stmt = conn.createStatement();
                for (int purchaseID : purchaseIDs)
                {
                    String deletePurchaseQuery = "DELETE FROM Purchase WHERE PurchaseID = " + purchaseID;
                    stmt.executeUpdate(deletePurchaseQuery);
                }

                //Delete associated Booking records
                for (int bookingID : bookingIDs)
                {
                    String deleteBookingQuery = "DELETE FROM Booking WHERE BookingID = " + bookingID;
                    stmt.executeUpdate(deleteBookingQuery);
                }

                // Delete the visitor records
                String deleteVisitorQuery = "DELETE FROM Visitor WHERE VisitorID = " + VisitorID;
                stmt.executeUpdate(deleteVisitorQuery);

                bDelete = true;
                System.out.println("Visitor and all associated bookings and purchases records deleted successfully. Adios:)");
                conn.commit();
            }

            else // if only booking needs to be deleted, only the associated purchase must be deleted. (entity relationship)
            {
                int BookingID = validateBookingID();//validate booking ID
                stmt = conn.createStatement();
                String query = "SELECT PurchaseID FROM Purchase WHERE Purchase.BookingID = '" + BookingID + "'";
                int purchaseID = 0;
                ResultSet resultSet = stmt.executeQuery(query);
                if (resultSet.next()) {
                    purchaseID = resultSet.getInt("PurchaseID");
                }
                resultSet.close();

                // query to delete assoicated purchase, and booking record
                String deleteQuery = "DELETE FROM Purchase WHERE PurchaseID = " + purchaseID;
                stmt.executeUpdate(deleteQuery);

                String deleteBookQuery ="DELETE FROM Booking WHERE BookingID = " + BookingID;
                stmt.executeUpdate(deleteBookQuery);

                bDelete = true;
                System.out.println("Booking records deleted successfully.:)");
                conn.commit();

            }

        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bDelete;
    }


    //add payment details for eac new booking into purchase table
    public boolean AddPayment(int BookingID, String TicketType)
    {
        boolean bInsert = false;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner input = new Scanner(System.in);

        try
        {
            //input details from user
            System.out.println("Please enter your payment method(credit, debit or cash):");
            String Method = "";//initialization

            //Validation of the method input
            while (true)//loop validating the payment method
            {
                String method = input.next();
                Method = method.toLowerCase();
                if(Method.equals("credit"))
                {
                    break;
                }
                if(Method.equals("cash"))
                {
                    break;
                }
                if(Method.equals("debit"))
                {
                    break;
                }
                else
                {
                    System.out.println("Please enter one of the method options above.");
                }
            }

            //set purchase date to today's date
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String PurchaseDate = today.format(formatter);

            //to calculate the total amount due
            System.out.println("Please enter the number of adult tickets you would like to purchase: ");
            int number = input.nextInt();
            System.out.println("Please enter the number of Child tickets you would like to purchase: ");
            int numberC = input.nextInt();

            String Adultticket;
            String ChildTicket;

            if(TicketType.equals("Standard"))
            {
                Adultticket = "Standard Adult";
                ChildTicket = "Standard Child";

            }
            else if (TicketType.equals("PremierAccess"))
            {
                Adultticket = "Premier Access Adult";
                ChildTicket = "Premier Access Child";
            }
            else
            {
                Adultticket = "Ultimate Access Adult";
                ChildTicket = "Ultimate Access Child";
            }


            // query to find the price of adult associated with the provided ticketType
            stmt = conn.createStatement();
            String query = "SELECT Price FROM Tickets WHERE Type = '" + Adultticket+"'";
            float adult = 0; // Initialize with a default value
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                adult = resultSet.getFloat("Price");
            }
            resultSet.close();

            // query to find the price of child associated with the provided ticketType
            stmt = conn.createStatement();
            String queryTwo = "SELECT Price FROM Tickets WHERE Type = '" + ChildTicket+"'";
            float child = 0;// Initialize with a default value
            ResultSet resultSet2 = stmt.executeQuery(queryTwo);
            resultSet2 = stmt.executeQuery(queryTwo);
            if (resultSet2.next()) {
                child = resultSet2.getFloat("Price");
            }
            resultSet2.close();

            //calculate total price
            float TotalAmount = adult*number + child*numberC;
            String amount = String.format("%.2f", TotalAmount);
            System.out.println("Amount to be paid for "+ number+ " Adults and "+ numberC+ " Children  at "+ TicketType+" price :" + amount);

            // query to add data into table
            stmt = conn.createStatement();
            String sql = "INSERT INTO Purchase (BookingID,TotalAmount,Method,PurchaseDate) " +
                    "VALUES ('"+BookingID+"', '"+ TotalAmount +"', '"+ Method+"', '"+ PurchaseDate+"');";

            // generating new primary key (PurchaseID)
            int affectedRows = stmt.executeUpdate(sql);
            //Retrieve the generated key (primary key)
            if (affectedRows > 0) {
                rs = stmt.executeQuery("SELECT last_insert_rowid() AS PurchaseID");
                if (rs.next()) {
                    int PurchaseID = rs.getInt("PurchaseID");  // Assuming the primary key is of type long
                    System.out.println("PurchaseID: " + PurchaseID);
                    bInsert = true;
                }
            }
            conn.commit();

        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return bInsert;
    }

    //to add a new r=review to review table
    public boolean AddReview()
    {
        System.out.println("Hiya, pal! Gosh, we’d love to hear about your magical adventure!\nYour review helps us sprinkle even more pixie dust for future visits. \nThanks a bunch—you’re the best!");
        System.out.println();
        boolean bAdd = false;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Here are the attraction with their IDs for you to refer to :)");
            stmt = conn.createStatement();
            //to view attractions with id and number for reference when writing review
            result = stmt.executeQuery("SELECT * FROM ViewAttractions");

            while (result.next())
            {
                int AttractionID = result.getInt("AttractionID");
                String AttractionName = result.getString("AttractionName");

                System.out.println("ID = " + AttractionID);
                System.out.println("NAME = " + AttractionName);
                System.out.println();
            }

            result.close();
            //ask and validate IDs (safety feature)
            int vID = validateVisitorID();
            int bID = validateBookingID();

            if(vID != -1 && bID != -1)
            {
                int attractID = -1;
                while (true) //validate to be only between 1-16
                {
                    System.out.println("Please enter the Attraction ID (1-16) you want to review: ");
                    String attractionInput = input.nextLine();

                    if (isNumeric(attractionInput)) {
                        attractID = Integer.parseInt(attractionInput);
                        if (attractID >= 1 && attractID <= 16) {
                            break; // Valid attraction ID, exit loop
                        } else {
                            System.out.println("Attraction ID must be between 1 and 16. Please try again.");
                        }
                    } else {
                        System.out.println("Please enter a valid numeric Attraction ID.");
                    }
                }

                int rating = -1;
                while (true) //validate rating 1-5
                {
                    System.out.println("Please enter your rating (1-5): ");
                    String ratingInput = input.nextLine();
                    if (isNumeric(ratingInput)) {
                        rating = Integer.parseInt(ratingInput);
                        if (rating >= 1 && rating <= 5) {
                            break; // Valid rating, exit loop
                        } else {
                            System.out.println("Rating must be between 1 and 5. Please try again.");
                        }
                    } else {
                        System.out.println("Please enter a valid numeric rating.");
                    }
                }

                //add comment
                System.out.println("Please enter any comment you may have?: ");
                String comment = input.nextLine();

                //set the review to today's date
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String RDate = today.format(formatter);

                // query to add data into table
                stmt = conn.createStatement();
                String sql = "INSERT INTO Review (VisitorID,AttractionID,Rating,Comment,RDate) " +
                        "VALUES ('" + vID + "', '" + attractID + "', '" + rating + "', '" + comment + "', '" + RDate + "');";

                // generating new primary key (ReviewID)
                int affectedRows = stmt.executeUpdate(sql);
                //Retrieve the generated key (primary key)
                if (affectedRows > 0) {
                    rs = stmt.executeQuery("SELECT last_insert_rowid() AS ReviewID");
                    if (rs.next()) {
                        int ReviewID = rs.getInt("ReviewID");  // Assuming the primary key is of type long
                        System.out.println("ReviewID: " + ReviewID);
                        bAdd = true;
                    }
                }
                conn.commit();
           }

        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bAdd;
    }

    //display reviews filtered by group type(family/thrill)
    public boolean displayReviews(String Type)
    {
        System.out.println("Here’s what folks are saying about their adventures at Disneyland Paris!");
        System.out.println("We hope their stories inspire your next trip to be just as amazing");
        System.out.println();
        boolean bView = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            //query to reviews from view base don type
            String query = "SELECT AttractionID,AttractionName,Rating,Comment, DaysSinceLastReview FROM viewAllReviews WHERE Type = '" + Type+"'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next())//while there is data
            {
                int AttractionID = rs.getInt("AttractionID");
                String AttractionName = rs.getString("AttractionName");
                int Rating = rs.getInt("Rating");
                String Comment = rs.getString("Comment");
                int Days = rs.getInt("DaysSinceLastReview");

                System.out.println("ID =" + AttractionID);
                System.out.println("NAME =" + AttractionName);
                System.out.println("RATING = " + Rating);
                System.out.println("COMMENT = " +Comment);
                System.out.println("DAYS SINCE LAST REVIEW = "+ Days +" days ago");
                System.out.println();
            }
            rs.close();
            stmt.close();
            bView = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bView;
    }

    //display average review for each attraction
    public boolean displayAverageReviews()
    {
        System.out.println("Hot dog! Look at all these magical memories our pals have shared! Here’s what folks thought of their adventures at Disneyland Paris:");
        System.out.println();
        boolean bView = false;
        Statement stmt = null;
        ResultSet rs = null;

        try
        {
            String query = "SELECT * FROM ViewAverageReviews";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next())//while there is data
            {
                int AttractionID = rs.getInt("AttractionID");
                String AttractionName = rs.getString("AttractionName");
                String type = rs.getString("Type");
                float rating = rs.getFloat("AverageRating");
                String Rating = String.format("%.2f", rating);
                int Num = rs.getInt("NumberOfReviews");

                System.out.println("ID = " + AttractionID);
                System.out.println("NAME = " + AttractionName);
                System.out.println("TYPE = "+ type);
                System.out.println("AVERAGE RATING = " + Rating);
                System.out.println("NUMBER OF REVIEWS = "+ Num);
                System.out.println();
            }
            rs.close();
            stmt.close();
            bView = true;
        }
        catch (SQLException e)
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return bView;
    }
}
