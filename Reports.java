public class Reports {
    RoomOperations roomOps;
    StudentOperations studentOps;

    Reports() {
        roomOps = HostelManager.roomOps;
        studentOps = HostelManager.managerRef.studentOps;
    }

    void generateSystemSummary() {
        System.out.println("===== SYSTEM SUMMARY =====");
        int totalStudents = StudentOperations.studentCount;
        int totalRooms = roomOps.TOTAL_ROOMS;

        int occupiedRooms = 0;
        for (int i = 0; i < totalRooms; i++) {
            if (roomOps.isOccupied != null && roomOps.isOccupied[i]) occupiedRooms++;
        }

        int availableRooms = totalRooms - occupiedRooms;
        float avgFees = averageFees();

        System.out.printf("Total Students   : %d%n", totalStudents);
        System.out.printf("Total Rooms      : %d%n", totalRooms);
        System.out.printf("Occupied Rooms   : %d%n", occupiedRooms);
        System.out.printf("Available Rooms  : %d%n", availableRooms);
        System.out.printf("Average Fees     : ₹%.2f%n", avgFees);
        Utils.pauseScreen();
    }

    int mostOccupiedRoom() {
        int maxIndex = 0, maxCount = 0;
        if (roomOps.occupancyCount == null) return -1;

        for (int i = 0; i < roomOps.TOTAL_ROOMS; i++) {
            if (roomOps.occupancyCount[i] > maxCount) {
                maxCount = roomOps.occupancyCount[i];
                maxIndex = i;
            }
        }

        String prefix = (roomOps.roomGenderType[maxIndex] != null &&
                        roomOps.roomGenderType[maxIndex].equalsIgnoreCase("Boys")) ? "B_" : "G_";
        String roomNo = prefix + String.format("%03d", maxIndex % 250 + 1);
        System.out.println("Most occupied room: " + roomNo + " with " + maxCount + " student(s).");
        return maxIndex;
    }

    void pendingPaymentsReport() {
        System.out.println("\n===== Pending Payments Report =====");

        boolean hasPending = false;
        if (studentOps == null || studentOps.studentNames == null) {
            System.out.println("⚠ No student data found.");
            return;
        }

        for (int i = 0; i < studentOps.studentCount; i++) {
            float due = studentOps.totalFees[i] - studentOps.feesPaid[i];
            if (due > 0.01f) {
                hasPending = true;
                int roomIndex = studentOps.roomNumbers[i];
                String prefix = (roomOps.roomGenderType != null &&
                                roomOps.roomGenderType[roomIndex] != null &&
                                roomOps.roomGenderType[roomIndex].equalsIgnoreCase("Boys"))
                                ? "B_" : "G_";
                String roomNo = prefix + String.format("%03d", roomIndex % 250 + 1);

                System.out.printf("ID: %-8s | Name: %-15s | Room: %-6s | Pending: ₹%.2f%n",
                        studentOps.studentIDs[i],
                        studentOps.studentNames[i],
                        roomNo,
                        due);
            }
        }

        if (!hasPending)
            System.out.println("🎉 All clear! No pending payments.");

        Utils.pauseScreen();
    }

    void deadlineCountdown() {
        System.out.println("\n===== Payment Deadline Countdown =====");

        boolean hasUpcomingDeadlines = false;
        String today = Utils.getCurrentDate();

        if (studentOps == null || studentOps.dueDates == null) {
            System.out.println("⚠ No student data found.");
            return;
        }

        for (int i = 0; i < studentOps.studentCount; i++) {
            String dueDate = studentOps.dueDates[i];
            if (dueDate == null || dueDate.trim().isEmpty()) continue;

            int daysLeft = Utils.calculateDaysDifference(today, dueDate);

            if (daysLeft >= 0 && daysLeft <= 5) {
                hasUpcomingDeadlines = true;
                System.out.println("⚠ " + studentOps.studentNames[i] + " → Due in " + daysLeft + " days");
            }
        }

        if (!hasUpcomingDeadlines)
            System.out.println("🎉 All dues clear for now!");

        Utils.pauseScreen();
    }

    float averageFees() {
        float sum = 0;
        for (int i = 0; i < studentOps.studentCount; i++)
            sum += studentOps.feesPaid[i];
        return (studentOps.studentCount == 0) ? 0 : sum / studentOps.studentCount;
    }
}
