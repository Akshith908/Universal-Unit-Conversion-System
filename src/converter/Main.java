
package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println(" Universal Unit Conversion System");
        System.out.println("Choose interface:");
        System.out.println("1) Console version");
        System.out.println("2) GUI version");
        System.out.print("Enter choice: ");

        String choice = sc.nextLine().trim();

        if (choice.equals("1")) {
            System.out.println("Launching console version...\n");
            UnitConverterApp.main(args);
        } else if (choice.equals("2")) {
            System.out.println("Launching GUI version...\n");
            CategoryMenu.main(args);
        } else {
            System.out.println("Invalid choice.");
        }

        sc.close();
    }
}
