import java.util.*;

public class RoomOperations {

    boolean[] isOccupied;
    int[] occupancyCount;
    String[] roomGenderType;
    String[] roomStatus;
    String[] roomTypeByRoom;
    int MAX_B = 250;
    int MAX_G = 250;
    int TOTAL_ROOMS = 500;

    int[] waitingListStudentIndex;
    String[] waitingListGender;
    String[] waitingListRoomType;
    int waitingListCount;
    int MAX_WAITING = 50;

    Scanner sc = new Scanner(System.in);

    int findAvailableRoom(String roomType, String gender) {
        int start = gender.equalsIgnoreCase("M") ? 0 : MAX_B;
        int end = gender.equalsIgnoreCase("M") ? MAX_B : TOTAL_ROOMS;

        for (int i = start; i < end; i++) {
            if (roomGenderType[i] != null && roomTypeByRoom[i] != null && 
                !roomStatus[i].equalsIgnoreCase("Under Maintenance!") &&
                occupancyCount[i] < allowedCapacityFor(roomType)) {
                return i;
            }
        }
        return -1;
    }

    void allocateRoom(int studentIndex, String gender, String roomType) {
        int roomIndex = findAvailableRoom(roomType, gender);

        if (roomIndex == -1) {
            System.out.println("⚠ No room available of requested type. Added to waiting list.");
            return;
        }

        isOccupied[roomIndex] = true;
        occupancyCount[roomIndex]++;

        if (occupancyCount[roomIndex] == allowedCapacityFor(roomType))
            roomStatus[roomIndex] = "Occupied";
        else
            roomStatus[roomIndex] = "Partially Occupied";

        System.out.println("✅ Room " + roomIndex + " allocated successfully.");
    }

    void viewRoomAvailability() {
        int occupied = 0, available = 0;
        System.out.println("\n===== ROOM STATUS =====");
        System.out.printf("%-6s | %-6s | %-12s | %-15s%n", "No.", "Gender", "Type", "Status");
        System.out.println("---------------------------------------------");

        for (int i = 0; i < TOTAL_ROOMS; i++) {
            String status = roomStatus[i];
            if (status.equalsIgnoreCase("Available")) available++;
            else if (status.equalsIgnoreCase("Occupied")) occupied++;
            System.out.printf("%-6d | %-6s | %-12s | %-15s%n",
                i+1, roomGenderType[i], roomTypeByRoom[i], roomStatus[i]);
        }
        System.out.println("---------------------------------------------");
        System.out.println("Total: " + TOTAL_ROOMS + " | Occupied: " + occupied + " | Available: " + available);
    }

    int allowedCapacityFor(String roomType) {
        if (roomType.toLowerCase().contains("single")) return 1;
        if (roomType.toLowerCase().contains("double")) return 2;
        return 1;
    }
}
