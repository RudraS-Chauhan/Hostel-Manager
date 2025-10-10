import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Utils 
{
    static String generateStudentID(int roomNumber, int studentCount, String gender)
    {
        System.out.println("Generating unique student ID...");
        String prefix = (gender.equalsIgnoreCase("M")) ? "B_" :(gender.equalsIgnoreCase("F")) ? "G_" : "O_";
        String formattedRoom = String.format("%03d", roomNumber + 1);
        String formattedCount = String.format("%03d", studentCount + 1);
        String studentID = prefix + formattedRoom + formattedCount;
        System.out.println("Generated Student ID : " + studentID);
        return studentID;
    }


    static int calculateDaysDifference(String date1, String date2)
    {
        System.out.println("Calculating days difference between two dates...");
        try
        {
            SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");

            Date d1 = sd.parse(date1);
            Date d2 = sd.parse(date2);

            long dif = d2.getTime() - d1.getTime();

            long diftoDays = dif/(1000*60*60*24);

            return (int) diftoDays;
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    
    }

    static String getCurrentDate()
    {
        System.out.println("Fetching today's date...");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date now = new Date();
        return sdf.format(now);
    }
    
    static  Boolean validateInput(String input, String type)
    {
        System.out.println("Validating input type...");
        if(type.equalsIgnoreCase("numeric"))
        {
            return input.matches("\\d+");
        }
        else if(type.equalsIgnoreCase("alpha"))
        {
            return input.matches("[A-Za-z]");
        }
        else
            return false;
    }

    static String addMonthsToDate(LocalDate date, int months)
    {
        System.out.println("Adding months to current date...");
        return date.plusMonths(months).format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    static void pauseScreen()
    {
            System.out.println("Pausing screen for user readability...");
            System.out.println("\nPress Enter to continue...");
            new Scanner(System.in).nextLine();
    }

    static String generateRandomIDPrefix(String gender)
    {
        System.out.println("Generating prefix for student ID based on gender...");
        if(gender.equalsIgnoreCase("male"))
        {
            return "B";
        }
        else if(gender.equalsIgnoreCase("female"))
        {
            return "G";
        }
        else
        return "O";
    }
}
