import java.time.LocalDate;
import java.util.*;
public class StudentOperations 
{
    final int MAX_STUDENTS = 500;
    final int MAX_WAIT = 50;
    final int TOTAL_ROOMS = 500;

    String[] studentNames = new String[MAX_STUDENTS];
    String[] studentIDs = new String[MAX_STUDENTS];
    String[] genders = new String[MAX_STUDENTS];
    int[] roomNumbers = new int[MAX_STUDENTS];
    String[] roomTypes = new String[MAX_STUDENTS];
    int[] feesPaid = new int[MAX_STUDENTS];
    int[] totalFees = new int[MAX_STUDENTS];
    String[] dueDates = new String[MAX_STUDENTS];
    String[] lockInEndDates = new String[MAX_STUDENTS];
    String[] contactNumbers = new String[MAX_STUDENTS];
    String[] guardianContacts = new String[MAX_STUDENTS];
    String[] addressIDs = new String[MAX_STUDENTS];

    boolean[] isOccupied = new boolean[TOTAL_ROOMS + 1];
    String[] roomTypeByRoom = new String[TOTAL_ROOMS + 1];
    String[] roomGenderByRoom = new String[TOTAL_ROOMS + 1];

    String[] waitNames = new String[MAX_WAIT];
    String[] waitDesiredRoomTypes = new String[MAX_WAIT];
    String[] waitGenders = new String[MAX_WAIT];
    String[] waitContactNumbers = new String[MAX_WAIT];
    int waitFront=0;
    int waitRear=-1;
    int waitCount=0;
    static int studentCount = 0;

    Scanner sc = new Scanner(System.in);

    void addStudent() 
    {
        System.out.println("Adding a new student...");
        System.out.println("Enter name of the student:");
        studentNames[studentCount] = sc.nextLine();

        System.out.println("Enter Contact Number of the student:");
        String input = sc.nextLine();
        contactNumbers[studentCount] = input;
        Utils.validateInput(input, "numeric");

        System.out.println("Enter Contact Number of the Gaurdian:");
        guardianContacts[studentCount] = sc.nextLine();   

        System.out.println("Enter Address ID of the student:");
        addressIDs[studentCount] = sc.nextLine();

        while (true) {
            System.out.print("Enter gender (M/F/O): ");
            String inp = sc.nextLine().trim().toUpperCase();
            if(inp.equals("M") || inp.equals("F") || inp.equals("O")) 
            {
                genders[studentCount] = inp;
                break;
            }
            else
            System.out.println("Invalid input. Please enter M, F, or O.");
            }
        
            System.out.println("Enter lock-in period (months):");
        int lockInMonths = Integer.parseInt(sc.nextLine().trim());

        sc.nextLine();

        if (lockInMonths != 6 && lockInMonths != 12 && lockInMonths != 24) 
        {
            System.out.println("Invalid lock-in period. Allowed values: 6, 12, or 24 months.");
            return;
        }

        LocalDate currentDate = LocalDate.now();
        lockInEndDates[studentCount] = Utils.addMonthsToDate(currentDate, lockInMonths);

        
        System.out.println("Choose room type:");
        System.out.println("1) Single - AC - Common Washroom (₹14000)");
        System.out.println("2) Single - AC - Attached Washroom (₹16000)");
        System.out.println("3) Single - Non-AC - Common Washroom (₹10000)");
        System.out.println("4) Single - Non-AC - Attached Washroom (₹12000)");
        System.out.println("5) Double - AC - Common Washroom (₹9000)");
        System.out.println("6) Double - AC - Attached Washroom (₹10000)");
        System.out.println("7) Double - Non-AC - Common Washroom (₹6500)");
        System.out.println("8) Double - Non-AC - Attached Washroom (₹7000)");
        System.out.print("Enter option (1-8): ");
        int roomOption = Integer.parseInt(sc.nextLine().trim());

        String selectedRoomType = " ";
        int selectedFee = 0;

        switch (roomOption) {
            case 1:
                selectedRoomType = "Single - AC - Common Washroom";
                selectedFee = 14000;
                break;
            case 2:
                selectedRoomType = "Single - AC - Attached Washroom";
                selectedFee = 16000;
                break;
            case 3:
                selectedRoomType = "Single - Non-AC - Common Washroom";
                selectedFee = 10000;
                break;
            case 4:
                selectedRoomType = "Single - Non-AC - Attached Washroom";
                selectedFee = 12000;
                break;
            case 5:
                selectedRoomType = "Double - AC - Common Washroom";
                selectedFee = 9000;
                break;
            case 6:
                selectedRoomType = "Double - AC - Attached Washroom";
                selectedFee = 10000;
                break;
            case 7:
                selectedRoomType = "Double - Non-AC - Common Washroom";
                selectedFee = 6500;
                break;
            case 8:
                selectedRoomType = "Double - Non-AC - Attached Washroom";
                selectedFee = 7000;
                break;
            default:
                System.out.println("Invalid option selected. Please choose between 1 and 8.");
                return;
        }

        roomTypes[studentCount] = selectedRoomType;
        totalFees[studentCount] = selectedFee;

        System.out.println("Selected Room Type: " + selectedRoomType);
        System.out.println("Total Fees: ₹" + selectedFee);

        System.out.println("Enter initial fees paid:");
        feesPaid[studentCount] = Integer.parseInt(sc.nextLine());

        if (feesPaid[studentCount] < 0 || feesPaid[studentCount] > totalFees[studentCount]) {
            System.out.println("Invalid fees amount. It must be between 0 and " + totalFees[studentCount]);
            return;
        } else if (feesPaid[studentCount] < totalFees[studentCount]) {
            System.out.println("Fees pending.");
        } else {
            System.out.println("Fees paid recorded: " + feesPaid[studentCount]);
        }

        RoomOperations roomOps = HostelManager.roomOps;
        int roomAssigned = roomOps.findAvailableRoom(selectedRoomType, genders[studentCount]);

        if (roomAssigned != -1) {
            roomOps.allocateRoom(studentCount, genders[studentCount], selectedRoomType);
            roomNumbers[studentCount] = roomAssigned;
            System.out.println("Room allocated: Room " + (roomAssigned+1));
        } 
        else {
            roomNumbers[studentCount] = -1;
    
            waitRear = (waitRear + 1) % MAX_WAIT;
            waitNames[waitRear] = studentNames[studentCount];
            waitDesiredRoomTypes[waitRear] = selectedRoomType;
            waitGenders[waitRear] = genders[studentCount];
            waitContactNumbers[waitRear] = contactNumbers[studentCount];
            waitCount++;
            System.out.println("No rooms available. Added to waiting list.");
        }

        studentIDs[studentCount] = Utils.generateStudentID(roomNumbers[studentCount] + 1, studentCount, genders[studentCount]);


        System.out.println("\n====================================");
        System.out.println("🎉 Student registration completed successfully!");
        System.out.println("Student ID        : " + studentIDs[studentCount]);
        System.out.println("Name              : " + studentNames[studentCount]);
        System.out.println("Room Type         : " + roomTypes[studentCount]);
        System.out.println("Room Number       : " + (roomNumbers[studentCount] != -1 ? roomNumbers[studentCount] : "Waiting List"));
        System.out.println("Lock-in Ends On   : " + lockInEndDates[studentCount]);
        System.out.println("Total Fees        : ₹" + totalFees[studentCount]);
        System.out.println("Fees Paid         : ₹" + feesPaid[studentCount]);
        System.out.println((roomNumbers[studentCount] != -1) ? 
            "🏠 Your room has been allocated successfully!" : 
            "⌛ You have been added to the waiting list. We'll contact you once a room is available.");
        System.out.println("====================================\n");
        System.out.println("Thank you for registering with us. If you have any questions, feel free to contact the office.");
        System.out.println("====================================\n");

        studentCount++;
        BackUpManager.saveData();



    }
    void searchStudent() 
    {
        System.out.println("Searching for a student...");
        System.out.println("1. Name");
        System.out.println("2. ID");
        System.out.println("3. Room Number");
        System.out.println("Enter choice :");
        int choice = sc.nextInt();
        sc.nextLine(); 

        boolean found = false;

        switch(choice)
        {
            case 1:
            System.out.println("Enter name to search:");
            String nameToSearch = sc.nextLine().trim().toLowerCase();
            if (nameToSearch.isEmpty()) {
                    System.out.println("Name cannot be empty.");
                } else {
                    System.out.println("Searching by name: " + nameToSearch);
                }

            break;

            case 2:
            System.out.println("Enter ID to search:");
            String idToSearch = sc.nextLine().trim();
            if (idToSearch.isEmpty()) {
                    System.out.println("ID cannot be empty.");
                } else {
                    System.out.println("Searching by ID: " + idToSearch);
                }
                break;

            case 3:
            System.out.println("Enter Room Number to search:");
            int roomToSearch = sc.nextInt();
            sc.nextLine();
            if (roomToSearch < 0) {
                    System.out.println("Room number must be positive.");
                } else {
                    System.out.println("Searching by Room Number: " + roomToSearch);
                }
                break;

            default:
            System.out.println("Invalid choice.");
        }
    }
    void viewAllStudents() 
    {
        if(studentCount == 0) {
            System.out.println("No students to display.");
            return;
        }

        System.out.println("\n==================== 📋 STUDENT RECORDS ====================");
        System.out.printf("%-8s | %-20s | %-9s | %-30s | %-10s | %-11s | %-10s | %-15s | %-6s\n", 
                      "ID", "Name", "Room No", "Room Type", "Fees Paid", "Total Fees", "Due Date", "Lock-in End", "Gender");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        int roomsOccupied = 0;
        int waitingCount = 0;

        for(int i = 0; i<studentCount; i++)
        {
            if(studentNames[i]==null || studentNames[i].isEmpty()) {
                continue; 
            }
                System.out.println(studentIDs[i] + " | " +studentNames[i] + " | " + roomNumbers[i] + " | " +roomTypes[i] + " | " +feesPaid[i] + " | " +totalFees[i] + " | " +(dueDates[i] != null ? dueDates[i] : "-") + " | " +(lockInEndDates[i] != null ? lockInEndDates[i] : "-") + " | " +genders[i]);

                if(roomNumbers[i] != -1) {
                    roomsOccupied++;
                } else {
                    waitingCount++;
                }
            System.out.println("📊 Summary:");
            System.out.println("👥 Total Students Registered : " + studentCount);
            System.out.println("🏠 Rooms Currently Occupied  : " + roomsOccupied);
            System.out.println("⏳ Students on Waiting List  : " + waitCount);
            System.out.println("✅ End of records.");
            System.out.println("==============================================================================================================\n");
            }
        }    
    void removeStudent() 
    {
        System.out.println("Removing a student...");

        if(studentCount == 0) {
            System.out.println("No students to remove.");
            return;
        }

        System.out.println("Students List:");
        for (int i = 0; i < studentCount; i++) {
            System.out.println(studentIDs[i] + " - " + studentNames[i]);
        }


        System.out.println("Enter Student ID to remove:");
        String idToRemove = sc.nextLine().trim();
        int removeIndex = -1;


        for(int i=0; i<studentCount; i++)
        {
            if(studentIDs[i] != null && studentIDs[i].equalsIgnoreCase(idToRemove)){
                removeIndex = i;
                break;
            }
        }

        if(removeIndex == -1) {
            System.out.println("Student ID not found.");
            return;
        }
        System.out.println("Are you sure you want to remove " + studentNames[removeIndex] + "? (Y/N)");
        String confirm = sc.nextLine().trim().toUpperCase();
        if(!confirm.equals("Y")) {
            System.out.println("Removal cancelled.");
            return;
        }   

        int freedRoom = roomNumbers[removeIndex];
        if(freedRoom != -1) {
            isOccupied[freedRoom] = false;
            roomGenderByRoom[freedRoom] = null;
            System.out.println("Freed up Room " + freedRoom);
        }

        boolean replacedFromWaitlist = false;

        if(waitCount > 0 && freedRoom != -1) {
            String desiredType = waitDesiredRoomTypes[waitFront];
            String desiredGender = waitGenders[waitFront];

            if(desiredType != null && desiredGender != null) {
                studentNames[studentCount] = waitNames[waitFront];
                genders[studentCount] = desiredGender;
                contactNumbers[studentCount] = waitContactNumbers[waitFront];
                roomTypes[studentCount] = desiredType;
                roomNumbers[studentCount] = freedRoom;

                roomTypeByRoom[freedRoom] = desiredType;
                roomGenderByRoom[freedRoom] = desiredGender;
                isOccupied[freedRoom] = true;

                RoomOperations roomOps = HostelManager.roomOps;
                roomOps.allocateRoom(studentCount, desiredGender, desiredType);

                studentIDs[studentCount] = Utils.generateStudentID(freedRoom + 1, studentCount, genders[studentCount]);


                studentCount++;

                waitFront = (waitFront + 1) % MAX_WAIT;
                waitCount--;

                replacedFromWaitlist = true;
            }
        }

            for (int i = removeIndex; i < studentCount - 1; i++) {
            studentNames[i] = studentNames[i + 1];
            studentIDs[i] = studentIDs[i + 1];
            genders[i] = genders[i + 1];
            roomNumbers[i] = roomNumbers[i + 1];
            roomTypes[i] = roomTypes[i + 1];
            feesPaid[i] = feesPaid[i + 1];
            totalFees[i] = totalFees[i + 1];
            dueDates[i] = dueDates[i + 1];
            lockInEndDates[i] = lockInEndDates[i + 1];
            contactNumbers[i] = contactNumbers[i + 1];
            guardianContacts[i] = guardianContacts[i + 1];
            addressIDs[i] = addressIDs[i + 1];
        }

        studentCount--;


            System.out.println("\n✅ Student \"" + idToRemove + "\" has been removed successfully.");
            System.out.println("-----------------------------------------------------");
            System.out.println("📦 Updated Stats:");
            System.out.println("👥 Total Students Now     : " + studentCount);
            System.out.println("🏠 Room " + (freedRoom != -1 ? freedRoom : "[None]") + " freed and " +
                            (replacedFromWaitlist ? "assigned to a waitlisted student." : "not reassigned."));
            System.out.println("⏳ Students Remaining in Waiting List: " + waitCount);
            System.out.println("-----------------------------------------------------");
            System.out.println("📁 All changes have been saved successfully.\n");

        BackUpManager.saveData();
    }
}

