import java.util.*;

public class HostelManager 
{
    Scanner sc = new Scanner(System.in);
    static RoomOperations roomOps; 
    StudentOperations studentOps;
    PaymentOperations payOps;
    Reports reports;
    Utils utils;
    public static HostelManager managerRef;


    final String RESET = "\u001B[0m";
    final String GREEN = "\u001B[32m";
    final String RED = "\u001B[31m";
    final String YELLOW = "\u001B[33m";
    final String CYAN = "\u001B[36m";


    void initializeRooms()
    {
        System.out.println("Initializing Hostel Rooms....");

        roomOps.roomGenderType = new String[roomOps.TOTAL_ROOMS];
        roomOps.roomTypeByRoom = new String[roomOps.TOTAL_ROOMS];
        roomOps.roomStatus = new String[roomOps.TOTAL_ROOMS];
        roomOps.occupancyCount = new int[roomOps.TOTAL_ROOMS];
        roomOps.isOccupied = new boolean[roomOps.TOTAL_ROOMS];

        roomOps.waitingListStudentIndex = new int[roomOps.MAX_WAITING];
        roomOps.waitingListGender = new String[roomOps.MAX_WAITING];
        roomOps.waitingListRoomType = new String[roomOps.MAX_WAITING];
        roomOps.waitingListCount = 0;

        for (int i = 0; i < roomOps.TOTAL_ROOMS; i++)
        {
            if (i < 250) {
                roomOps.roomGenderType[i] = "Boys";
            } else {
                roomOps.roomGenderType[i] = "Girls";
            }

            int indexInGenderBlock = i % 250;

            if (indexInGenderBlock < 100) {
                roomOps.roomTypeByRoom[i] = "Single";
            } else if (indexInGenderBlock < 200) {
                roomOps.roomTypeByRoom[i] = "Double";
            } else {
                roomOps.roomTypeByRoom[i] = "Triple";
            }

            roomOps.roomStatus[i] = "Available";
            roomOps.occupancyCount[i] = 0;
            roomOps.isOccupied[i] = false;
        }

        System.out.println("Rooms initialized successfully!");
    }

    void showMenu()
    {
        System.out.println("\n==============================================");
        System.out.println(CYAN + "🏡 HOSTEL / PG MANAGEMENT SYSTEM" + RESET);
        System.out.println(YELLOW + "Choose an operation below:" + RESET);
        System.out.println("  Date: " + Utils.getCurrentDate());
        System.out.println("==============================================");

        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Fee Payment");
        System.out.println("4. View Room Availability");
        System.out.println("5. Generate Reports");
        System.out.println("6. Smart Canteen System");
        System.out.println("7. Exit\n");

        System.out.print("Enter your choice: ");
        int choice = -1;
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
        } else {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
        }

        handleUserChoice(choice);
    }
    
    void handleUserChoice(int choice)
    {
        switch(choice)
        {
            case 1:
                System.out.println("\n📋 Adding new student...");
                studentOps.addStudent();
                BackUpManager.saveData();
                System.out.println("💾 Auto-backup completed successfully.");
                break;

            case 2:
                System.out.println("\n👥 Showing all students...");
                studentOps.viewAllStudents();
                break;

            case 3:
                System.out.println("\n💰 Opening payment update section...");
                payOps.updateFeePayment();
                BackUpManager.saveData();
                System.out.println("💾 Auto-backup completed successfully.");
                break;

            case 4:
                System.out.println("\n🏠 Checking room availability...");
                roomOps.viewRoomAvailability();
                break;

            case 5:
                System.out.println("\n📊 Generating reports...");
                System.out.println("1. System Summary");
                System.out.println("2. Pending Payments Report");
                System.out.println("3. Deadline Countdown");
                System.out.println("4. Back to Main Menu");
                System.out.print("Enter choice: ");
                int subChoice = sc.nextInt();
                sc.nextLine();

                switch(subChoice)
                {
                    case 1:
                        reports.generateSystemSummary();
                        break;
                    case 2:
                        reports.pendingPaymentsReport();
                        break;
                    case 3:
                        reports.deadlineCountdown();
                        break;
                    case 4:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("⚠ Invalid choice! Try again.");
                }
                break;

            case 6:
                System.out.println("\n🍽 Opening Smart Canteen System...");
                CanteenSystem canteen = new CanteenSystem();
                canteen.showCanteenMenu();
                BackUpManager.saveData();
                System.out.println("💾 Canteen data saved successfully.");
                break;

            case 7:
                System.out.println(CYAN + "\n💾 Saving all data before exit..." + RESET);
                BackUpManager.saveData();
                System.out.println(GREEN + "✅ Data saved successfully." + RESET);
                System.out.println(GREEN + "👋 Thank you for using Hostel Management System!" + RESET);
                System.exit(0);
                break;

            default:
                System.out.println(YELLOW + "⚠ Invalid choice. Please try again." + RESET);
        }

        System.out.println("\n===== DAILY SUMMARY =====");
        System.out.println("Students in System : " + StudentOperations.studentCount);
        System.out.println("Average Fees Paid  : ₹" + reports.averageFees());
        System.out.println("Most Occupied Room : " + reports.mostOccupiedRoom());
        System.out.println("Generated on       : " + Utils.getCurrentDate());
        System.out.println("==========================\n");

        Utils.pauseScreen();
    }

    public static void main(String[] args) {
        HostelManager manager = new HostelManager();
        managerRef = manager; 

        roomOps = new RoomOperations();
        manager.studentOps = new StudentOperations();
        manager.payOps = new PaymentOperations();
        manager.reports = new Reports();
        manager.utils = new Utils();

        manager.initializeRooms();
        System.out.println("\n" + manager.CYAN + "💠 Welcome to Hostel Management System!" + manager.RESET);
        System.out.println("Loading system data...\n");

        while (true) {
            manager.showMenu();
        }
    }

}
