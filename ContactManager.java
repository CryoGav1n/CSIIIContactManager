import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    String firstName;
    String lastName;
    String email;
    String phone;

    Contact(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = formatPhone(phone);
    }

    private String formatPhone(String phone) {
        String clean = phone.replaceAll("[^\\d]", "");
        if (clean.length() == 10) {
            return clean.substring(0, 3) + "-" + clean.substring(3, 6) + "-" + clean.substring(6);
        } else {
            return clean;
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " - " + email + " - " + phone;
    }

    String toDataLine() {
        return firstName + "|" + lastName + "|" + email + "|" + phone;
    }

    static Contact fromDataLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length != 4) {
            return null;
        }
        return new Contact(parts[0], parts[1], parts[2], parts[3]);
    }
}

public class ContactManager {
    private static final String CONTACTS_FILE = "contacts.txt";

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        ArrayList<Contact> contacts = loadContacts();

        while (true) {
            System.out.print("Enter first name (or 'quit' to exit): ");
            String firstName = kb.nextLine();
            if (firstName.equalsIgnoreCase("quit")) break;

            System.out.print("Enter last name: ");
            String lastName = kb.nextLine();

            System.out.print("Enter email: ");
            String email = kb.nextLine();

            System.out.print("Enter phone number: ");
            String phone = kb.nextLine();

            contacts.add(new Contact(firstName, lastName, email, phone));
            System.out.println("Contact added.\n");
        }

        saveContacts(contacts);

        System.out.println("\nContacts:");
        contacts.sort((c1, c2) -> {
            int lastCompare = c1.lastName.compareToIgnoreCase(c2.lastName);
            if (lastCompare != 0) {
                return lastCompare;
            }
            return c1.firstName.compareToIgnoreCase(c2.firstName);
        });
        for (Contact contact : contacts) {
            System.out.println(contact);
        }

        kb.close();
    }

    private static ArrayList<Contact> loadContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        File file = new File(CONTACTS_FILE);
        if (!file.exists()) {
            return contacts;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Contact contact = Contact.fromDataLine(line);
                if (contact != null) {
                    contacts.add(contact);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load contacts from " + CONTACTS_FILE + ".");
        }

        return contacts;
    }

    private static void saveContacts(ArrayList<Contact> contacts) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(CONTACTS_FILE)))) {
            for (Contact contact : contacts) {
                writer.println(contact.toDataLine());
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save contacts to " + CONTACTS_FILE + ".");
        }
    }
}
