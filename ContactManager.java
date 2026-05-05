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
}

public class ContactManager {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        ArrayList<Contact> contacts = new ArrayList<>();
        
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
        }
        
        System.out.println("\nContacts:");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
        
        kb.close();
    }
}