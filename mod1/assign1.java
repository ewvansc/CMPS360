import java.util.Scanner;

public class assign1 {

    
    private static final int TOTAL_SUBJECTS = 5;
    private static final int DAYS_PER_WEEK = 7;
    private static final int WEEKS_PER_SEMESTER = 16;
    private static final int CREDITS_TO_GRADUATE = 120;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Student Information System!\n");

        String name = readName(sc, "Enter your name: ");
        int age = readPositiveInt(sc, "Enter your age: ", "Invalid age. Please enter a positive integer.");
        double gpa = readDoubleInRange(sc, "Enter your GPA (0.0 - 4.0): ",
                0.0, 4.0, "Invalid GPA. Please enter a value between 0.0 and 4.0.");
        int completedCredits = readNonNegativeInt(sc, "Enter the number of completed credits: ",
                "Invalid credits. Please enter a non-negative integer.");

        double[] weeklyHours = new double[TOTAL_SUBJECTS];
        double totalWeeklyHours = 0.0;
        for (int i = 0; i < TOTAL_SUBJECTS; i++) {
            weeklyHours[i] = readNonNegativeDouble(
                    sc,
                    "Enter the hours you study per week for subject " + (i + 1) + ": ",
                    "Invalid hours. Please enter a non-negative number."
            );
            totalWeeklyHours += weeklyHours[i];
        }

        // Calculations
        double avgHoursPerDay = totalWeeklyHours / DAYS_PER_WEEK;
        double totalHoursPerSemester = totalWeeklyHours * WEEKS_PER_SEMESTER;
        int remainingCredits = Math.max(0, CREDITS_TO_GRADUATE - completedCredits);

        // Output
        System.out.println("\n--- Student Summary ---");
        System.out.printf("%-20s %s%n", "Name:", name);
        System.out.printf("%-20s %d%n", "Age:", age);
        System.out.printf("%-20s %.2f%n", "GPA:", gpa);
        System.out.printf("%-20s %d%n", "Completed Credits:", completedCredits);
        System.out.printf("%-20s %d%n", "Remaining Credits:", remainingCredits);

        System.out.println();
        System.out.printf("%-35s %.2f%n", "Average Study Hours Per Day:", avgHoursPerDay);
        System.out.printf("%-35s %.0f%n", "Total Study Hours Per Semester:", totalHoursPerSemester);

        sc.close();
    }

    

    private static String readName(Scanner sc, String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine();
        while (line.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            System.out.print(prompt);
            line = sc.nextLine();
        }
        return line;
    }

    private static int readPositiveInt(Scanner sc, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int val = sc.nextInt();
                sc.nextLine(); 
                if (val > 0) return val;
            } else {
                sc.nextLine(); 
            }
            System.out.println(errorMsg);
        }
    }

    private static int readNonNegativeInt(Scanner sc, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int val = sc.nextInt();
                sc.nextLine(); 
                if (val >= 0) return val;
            } else {
                sc.nextLine(); 
            }
            System.out.println(errorMsg);
        }
    }

    private static double readDoubleInRange(Scanner sc, String prompt, double min, double max, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                double val = sc.nextDouble();
                sc.nextLine(); 
                if (val >= min && val <= max) return val;
            } else {
                sc.nextLine(); 
            }
            System.out.println(errorMsg);
        }
    }

    private static double readNonNegativeDouble(Scanner sc, String prompt, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                double val = sc.nextDouble();
                sc.nextLine(); 
                if (val >= 0.0) return val;
            } else {
                sc.nextLine(); 
            }
            System.out.println(errorMsg);
        }
    }
}
