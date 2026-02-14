import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    String name;
    String phone;
    String email;

    Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

public class Main {

    static ArrayList<Contact> contacts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    static void addContact() {
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Phone: ");
        String phone = sc.next();
        System.out.print("Enter Email: ");
        String email = sc.next();

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added successfully!");
    }

    static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("\n--- Contact List ---");
        for (int i = 0; i < contacts.size(); i++) {
            Contact c = contacts.get(i);
            System.out.println(i + ". " + c.name + " | " + c.phone + " | " + c.email);
        }
    }

    static void searchContact() {
        System.out.print("Enter name to search: ");
        String searchName = sc.next();
        boolean found = false;

        for (Contact c : contacts) {
            if (c.name.equalsIgnoreCase(searchName)) {
                System.out.println("Found: " + c.name + " | " + c.phone + " | " + c.email);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    static void updateContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter index to update: ");
        int index = sc.nextInt();

        if (index >= 0 && index < contacts.size()) {
            System.out.print("Enter new name: ");
            contacts.get(index).name = sc.next();
            System.out.print("Enter new phone: ");
            contacts.get(index).phone = sc.next();
            System.out.print("Enter new email: ");
            contacts.get(index).email = sc.next();
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Invalid index!");
        }
    }

    static void deleteContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter index to delete: ");
        int index = sc.nextInt();

        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Invalid index!");
        }
    }

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== CONTACT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1: addContact(); break;
                case 2: viewContacts(); break;
                case 3: searchContact(); break;
                case 4: updateContact(); break;
                case 5: deleteContact(); break;
                case 6: System.out.println("Thank you for using Contact Management System!"); break;
                default: System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
