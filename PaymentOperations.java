import java.io.*;
import java.util.*;

public class PaymentOperations {

    StudentOperations studentOps = new StudentOperations();
    Scanner sc = new Scanner(System.in);

    PaymentOperations() 
    {
        studentOps = HostelManager.managerRef.studentOps;
    }

    void updateFeePayment() {
        System.out.println("Updating Fee Payment...");
        System.out.print("Enter Student ID to update fees: ");
        String StudID = sc.nextLine();

        int index = -1;
        for (int i = 0; i < StudentOperations.studentCount; i++) {
            if (studentOps.studentIDs[i].equalsIgnoreCase(StudID)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("❌ Student not found.");
            return;
        }

        System.out.println("Current fees paid: ₹" + studentOps.feesPaid[index]);
        System.out.println("Total fees: ₹" + studentOps.totalFees[index]);

        System.out.print("Enter payment amount: ₹");
        int payment = Integer.parseInt(sc.nextLine());
        studentOps.feesPaid[index] += payment;

        int lateFee = calculateLateFees(index);

        if (studentOps.feesPaid[index] < studentOps.totalFees[index])
            System.out.println("⚠ Pending balance remaining.");
        else if (studentOps.feesPaid[index] == studentOps.totalFees[index])
            System.out.println("✅ All fees cleared.");

        System.out.println("Payment date: " + Utils.getCurrentDate());
        BackUpManager.saveData();
        generateReceipt(index, lateFee);
    }

    int calculateLateFees(int studentIndex) {
        String today = Utils.getCurrentDate();
        String dueDate = studentOps.dueDates[studentIndex];

        if (dueDate == null || dueDate.trim().isEmpty()) return 0;
        int dif = Utils.calculateDaysDifference(dueDate, today);
        if (dif > 0) return dif * 100;
        else if (dif < -5) return -100;
        else return 0;
    }

    void generateReceipt(int studentIndex, int lateFee) {
        try {
            File folder = new File("receipts/");
            if (!folder.exists()) folder.mkdirs();

            String filename = "receipts/payment_" + studentOps.studentIDs[studentIndex] + ".txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("===== PAYMENT RECEIPT =====\n");
            bw.write("Date: " + Utils.getCurrentDate() + "\n");
            bw.write("Name: " + studentOps.studentNames[studentIndex] + "\n");
            bw.write("Room: " + studentOps.roomNumbers[studentIndex] + "\n");
            bw.write("Fees Paid: ₹" + studentOps.feesPaid[studentIndex] + "\n");
            bw.write("Total Fees: ₹" + studentOps.totalFees[studentIndex] + "\n");
            bw.write("Late Fee: ₹" + lateFee + "\n");
            bw.write("============================\nThank you!\n");
            bw.close();
            System.out.println("🧾 Receipt saved to " + filename);
        } catch (IOException e) {
            System.out.println("⚠ Error saving receipt: " + e.getMessage());
        }
    }
}
