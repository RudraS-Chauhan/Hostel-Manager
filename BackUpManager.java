import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackUpManager 
{
    static final String STUDENT_DATA_FILE = "data/student.txt";
    static final String ROOM_DATA_FILE = "data/rooms.txt";
    static final String BACKUP_FOLDER = "backup/";

    static boolean autoBackUpEnabled = true;
    
    static void saveData() {
    try {
        File studentFile = new File(STUDENT_DATA_FILE);
        File roomFile = new File(ROOM_DATA_FILE);

    
        if (!studentFile.getParentFile().exists()) studentFile.getParentFile().mkdirs();
        if (!roomFile.getParentFile().exists()) roomFile.getParentFile().mkdirs();


        StudentOperations studOps = HostelManager.managerRef.studentOps;
        RoomOperations roomOps = HostelManager.roomOps;

        BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENT_DATA_FILE));
        for (int i = 0; i < StudentOperations.studentCount; i++) {
            String line = studOps.studentNames[i] + "," +
                          studOps.studentIDs[i] + "," +
                          studOps.genders[i] + "," +
                          studOps.roomNumbers[i] + "," +
                          studOps.roomTypes[i] + "," +
                          studOps.feesPaid[i] + "," +
                          studOps.contactNumbers[i];
            bw.write(line);
            bw.newLine();
        }
        bw.close();
        System.out.println("✅ Student data saved successfully.");

        BufferedWriter bwRoom = new BufferedWriter(new FileWriter(ROOM_DATA_FILE));
        for (int i = 0; i < roomOps.TOTAL_ROOMS; i++) {
            String line = (roomOps.roomTypeByRoom[i] != null ? roomOps.roomTypeByRoom[i] : "-") + "," +
                          (roomOps.roomGenderType[i] != null ? roomOps.roomGenderType[i] : "-") + "," +
                          (roomOps.isOccupied[i] ? "Occupied" : "Vacant");
            bwRoom.write(line);
            bwRoom.newLine();
        }
        bwRoom.close();
        System.out.println("✅ Room data saved successfully.");

        if (autoBackUpEnabled) backupData();

    } catch (IOException e) {
        System.out.println("❌ Error saving data: " + e.getMessage());
    }
}

    static void backupData()
    {
        try
        {
            File folder = new File(BACKUP_FOLDER);
            if (!folder.exists()) folder.mkdirs();

            String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
            copyTextFile(STUDENT_DATA_FILE, BACKUP_FOLDER + "students_backup_" + timeStamp + ".txt");
            copyTextFile(ROOM_DATA_FILE, BACKUP_FOLDER + "rooms_backup_" + timeStamp + ".txt");

            System.out.println("💾 Backup completed successfully at " + timeStamp);
        }
        catch (Exception e)
        {
            System.out.println("⚠ Backup failed: " + e.getMessage());
        }
    }

    static void copyTextFile(String source, String dest)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(source));
            BufferedWriter bw = new BufferedWriter(new FileWriter(dest));
            String line;
            while ((line = br.readLine()) != null)
            {
                bw.write(line);
                bw.newLine();
            }
            br.close();
            bw.close();
        }
        catch (IOException e)
        {
            System.out.println("⚠️ Error copying file: " + e.getMessage());
        }
    }
}