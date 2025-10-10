import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class CanteenSystem {

    // ===== MENUS =====
    String[][] breakfastItems = {
        {"Poha", "Aloo Paratha", "Masala Omelette", "Idli-Sambar", "Bread Butter & Jam"},
        {"Upma", "Vegetable Daliya", "Chole Bhature", "Paneer Sandwich", "Aloo Puri"},
        {"Medu Vada & Sambar", "Egg Bhurji with Toast", "Vegetable Sandwich", "Chana Dal Pancake", "Sabudana Khichdi"},
        {"Dosa with Coconut Chutney", "Egg Curry with Paratha", "Moong Dal Chilla", "Vegetable Poha", "Tomato Omelette"},
        {"Puri with Aloo Sabzi", "Masala Omelette", "Vegetable Upma", "Bread Pakora", "Sprout Salad"},
        {"Idli with Sambar", "Aloo Paratha", "Vegetable Dosa", "Corn Sandwich", "Chana Dal Pancake"},
        {"Paneer Paratha", "Egg Bhurji", "Vegetable Uttapam", "Masala Omelette", "Bread Butter & Jam"}
    };

    float[][] breakfastPrices = {
        {25, 30, 35, 30, 20},
        {25, 30, 40, 35, 30},
        {30, 35, 30, 25, 30},
        {35, 40, 30, 25, 35},
        {30, 35, 25, 20, 25},
        {25, 30, 30, 30, 25},
        {35, 40, 30, 35, 20}
    };

    String[][] lunchItems = {
        {"Chicken Curry", "Dal Tadka", "Jeera Rice", "Mixed Veg Sabzi", "Roti"},
        {"Rajma Masala", "Vegetable Biryani", "Curd", "Baingan Bharta", "Roti"},
        {"Chicken Curry", "Dal Makhani", "Steamed Rice", "Paneer Butter Masala", "Roti"},
        {"Mutton Rogan Josh", "Chole", "Jeera Rice", "Aloo Gobi", "Roti"},
        {"Egg Curry", "Palak Dal", "Vegetable Pulao", "Bhindi Masala", "Roti"},
        {"Vegetable Khichdi", "Cabbage Sabzi", "Curd", "Moong Dal", "Roti"},
        {"Chicken Biryani", "Dal Fry", "Mixed Veg Curry", "Roti", "Raita"}
    };

    float[][] lunchPrices = {
        {90, 40, 30, 50, 10},
        {60, 80, 20, 45, 10},
        {95, 50, 30, 90, 10},
        {110, 40, 30, 45, 10},
        {80, 35, 50, 40, 10},
        {70, 40, 20, 35, 10},
        {95, 45, 50, 10, 15}
    };

    String[][] dinnerItems = {
        {"Butter Chicken", "Dal Fry", "Jeera Rice", "Mixed Veg", "Roti"},
        {"Palak Paneer", "Vegetable Pulao", "Dal Tadka", "Roti", "Curd"},
        {"Mutton Curry", "Dal Makhani", "Steamed Rice", "Aloo Matar", "Roti"},
        {"Chicken Curry", "Rajma", "Jeera Rice", "Vegetable Sabzi", "Roti"},
        {"Egg Curry", "Dal Fry", "Vegetable Biryani", "Bhindi Masala", "Roti"},
        {"Vegetable Khichdi", "Moong Dal", "Curd", "Aloo Gobi", "Roti"},
        {"Chicken Curry", "Dal Tadka", "Jeera Rice", "Mixed Vegetable", "Roti"}
    };

    float[][] dinnerPrices = {
        {110, 40, 30, 50, 10},
        {70, 50, 40, 10, 20},
        {120, 60, 30, 40, 10},
        {100, 40, 30, 45, 10},
        {80, 40, 60, 40, 10},
        {70, 35, 20, 40, 10},
        {110, 40, 30, 50, 10}
    };

    // ===== ORDER DETAILS =====
    String studentName, studentID;
    String[] orderedItems = new String[10];
    int[] orderedQty = new int[10];
    float[] orderedPrice = new float[10];
    int orderCount = 0;
    float totalAmount = 0;

    // ===== MAIN MENU =====
    void showCanteenMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== SMART CANTEEN SYSTEM =====");
            System.out.println("Date : " + getCurrentDate());
            System.out.println("1. View Food Menu");
            System.out.println("2. Place an Order");
            System.out.println("3. View Bill / Generate Receipt");
            System.out.println("4. Give Meal Feedback");
            System.out.println("5. View Daily Special");
            System.out.println("6. Return to Main Menu");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: chooseMealAndDisplayMenu(); break;
                case 2: takeOrder(); break;
                case 3: generateBill(); break;
                case 4: collectFeedback(); break;
                case 5: showDailySpecial(); break;
                case 6: System.out.println("👋 Returning to main menu..."); break;
                default: System.out.println("⚠ Invalid choice. Try again.");
            }
        } while (choice != 6);
    }

    // ===== MENU DISPLAY BASED ON MEAL =====
    void chooseMealAndDisplayMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nSelect Meal Type:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        System.out.print("Enter choice: ");
        int type = sc.nextInt();
        displayMenuByType(type);
    }

    void displayMenuByType(int type) {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal = Calendar.getInstance();
        int dayIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;

        System.out.println("\n===== TODAY'S MENU =====");
        System.out.println("Date : " + getCurrentDate());
        System.out.println("Day  : " + days[dayIndex]);
        System.out.println("-----------------------------------");

        String[] items = null;
        float[] prices = null;

        switch (type) {
            case 1 -> { items = breakfastItems[dayIndex]; prices = breakfastPrices[dayIndex]; }
            case 2 -> { items = lunchItems[dayIndex]; prices = lunchPrices[dayIndex]; }
            case 3 -> { items = dinnerItems[dayIndex]; prices = dinnerPrices[dayIndex]; }
            default -> { System.out.println("Invalid meal type!"); return; }
        }

        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + ". " + items[i] + " - ₹" + prices[i]);
        }
        System.out.println("-----------------------------------");
    }

    // ===== TAKE ORDER =====
    void takeOrder() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student Name: ");
        studentName = sc.nextLine();
        System.out.print("Enter Student ID: ");
        studentID = sc.nextLine();

        System.out.println("\nSelect Meal Type to Order:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        System.out.print("Enter choice: ");
        int type = sc.nextInt();

        int dayIndex = LocalDate.now().getDayOfWeek().getValue() % 7;
        String[] items;
        float[] prices;
        if (type == 1) { items = breakfastItems[dayIndex]; prices = breakfastPrices[dayIndex]; }
        else if (type == 2) { items = lunchItems[dayIndex]; prices = lunchPrices[dayIndex]; }
        else { items = dinnerItems[dayIndex]; prices = dinnerPrices[dayIndex]; }

        displayMenuByType(type);
        System.out.print("\nHow many items would you like to order? ");
        orderCount = sc.nextInt();

        totalAmount = 0;
        for (int i = 0; i < orderCount; i++) {
            System.out.print("\nEnter item number (1–5): ");
            int itemNo = sc.nextInt();
            System.out.print("Enter quantity: ");
            int qty = sc.nextInt();

            orderedItems[i] = items[itemNo - 1];
            orderedQty[i] = qty;
            orderedPrice[i] = prices[itemNo - 1] * qty;
            totalAmount += orderedPrice[i];
        }

        System.out.println("\n===== ORDER SUMMARY =====");
        for (int i = 0; i < orderCount; i++) {
            System.out.printf("%-25s x%d = ₹%.2f%n", orderedItems[i], orderedQty[i], orderedPrice[i]);
        }
        System.out.println("-----------------------------------");
        System.out.println("Total Amount: ₹" + totalAmount);

        System.out.print("\nWould you like to confirm your order? (Y/N): ");
        char confirm = sc.next().charAt(0);
        if (confirm == 'Y' || confirm == 'y') {
            System.out.println("✅ Order confirmed! Enjoy your meal, " + studentName + "!");
        } else {
            System.out.println("❌ Order cancelled.");
        }
    }

    // ===== BILL GENERATION =====
    void generateBill() {
        if (orderCount == 0) {
            System.out.println("⚠ No orders found! Please place an order first.");
            return;
        }

        System.out.println("\n========== CANTEEN BILL ==========");
        System.out.println("Student: " + studentName + " (" + studentID + ")");
        System.out.println("Date   : " + getCurrentDate());
        System.out.println("-----------------------------------");

        float total = 0;
        for (int i = 0; i < orderCount; i++) {
            System.out.printf("%-20s x%d = ₹%.2f%n", orderedItems[i], orderedQty[i], orderedPrice[i]);
            total += orderedPrice[i];
        }

        System.out.println("-----------------------------------");
        System.out.println("Total Amount: ₹" + total);
        System.out.println("===================================\n");

        try {
            File folder = new File("receipts/");
            if (!folder.exists()) folder.mkdirs();

            String filename = "receipts/" + studentID + "_" + getCurrentDate().replace("-", "") + ".txt";
            PrintWriter pw = new PrintWriter(new FileWriter(filename));
            pw.println("Canteen Receipt for " + studentName);
            pw.println("Date: " + getCurrentDate());
            pw.println("-----------------------------------");
            for (int i = 0; i < orderCount; i++) {
                pw.println(orderedItems[i] + " x" + orderedQty[i] + " = ₹" + orderedPrice[i]);
            }
            pw.println("-----------------------------------");
            pw.println("Total: ₹" + total);
            pw.println("Thank you!");
            pw.close();

            System.out.println("🧾 Receipt saved: " + filename);
        } catch (IOException e) {
            System.out.println("⚠ Error saving receipt: " + e.getMessage());
        }
    }

    // ===== FEEDBACK =====
    void collectFeedback() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Rate your meal (1–5): ");
        int rating = sc.nextInt();
        System.out.println("Thanks for rating us " + rating + "/5 ⭐");
    }

    // ===== DAILY SPECIAL =====
    void showDailySpecial() {
        String[] specials = {
            "Butter Chicken with Garlic Naan",
            "Paneer Biryani with Raita",
            "Masala Dosa with Coconut Chutney",
            "Veg Thali Deluxe",
            "Chole Bhature Combo",
            "Grilled Sandwich with Coffee",
            "Dal Makhani with Jeera Rice"
        };
        Calendar cal = Calendar.getInstance();
        int dayIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println("\n🌟 Today's Special: " + specials[dayIndex] + " 🌟");
    }

    // ===== DATE HELPER =====
    String getCurrentDate() {
        LocalDate date = LocalDate.now();
        return date.getDayOfMonth() + "-" + date.getMonthValue() + "-" + date.getYear();
    }
}
