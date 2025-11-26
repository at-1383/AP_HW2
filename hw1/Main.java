import java.util.ArrayList;import java.util.Scanner;

public class MyBankSystem {

    private static class Account {
        String user;
        String pass;
        String name;
        String card;
        String phone;
        String mail;
        double money;

        public Account(String u, String p, String n, String c, String ph, String m) {
            user = u;
            pass = p;
            name = n;
            card = c;
            phone = ph;
            mail = m;
            money = 0.0;
        }
    }

    private static ArrayList<Account> list = new ArrayList<>();
    private static Account current = null;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("*** My Bank System ***");

        while (true) {
            System.out.print("$ ");
            String line = in.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");

            switch (parts[0]) {
                case "signup":
                    if (parts.length == 6) create(parts[1], parts[2], parts[3], parts[4], parts[5]);
                    else System.out.println("Usage: signup <user> <pass> <name> <phone> <email>");
                    break;

                case "login":
                    if (parts.length == 3) enter(parts[1], parts[2]);
                    else System.out.println("Usage: login <user> <pass>");
                    break;

                case "balance":
                    showMoney();
                    break;

                case "add":
                    if (parts.length == 2) deposit(parts[1]);
                    else System.out.println("Usage: add <amount>");
                    break;

                case "take":
                    if (parts.length == 2) withdraw(parts[1]);
                    else System.out.println("Usage: take <amount>");
                    break;

                case "send":
                    if (parts.length == 3) send(parts[1], parts[2]);
                    else System.out.println("Usage: send <card> <amount>");
                    break;

                case "exit":
                    System.out.println("Bye Bye!");
                    return;

                case "logout":
                    signOut();
                    break;

                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    private static void create(String u, String p, String n, String ph, String mail) {
        if (findUser(u) != null) {
            System.out.println("This username is taken!");
            return;
        }
        if (!ph.matches("09\\d{9}")) {
            System.out.println("Phone is not valid!");
            return;
        }
        if (!mail.contains("@") || !mail.endsWith("aut.com")) {
            System.out.println("Email should be @aut.com !");
            return;
        }
        if (!checkPass(p)) {
            System.out.println("Weak password!");
            return;
        }

        String card = makeCard();
        list.add(new Account(u, p, n, card, ph, mail));

        System.out.println("Sign up OK!");
        System.out.println("Your card: " + card);
    }

    private static void enter(String u, String p) {
        if (current != null) {
            System.out.println("Someone already logged in!");
            return;
        }
        Account a = findUser(u);
        if (a != null && a.pass.equals(p)) {
            current = a;
            System.out.println("Welcome " + a.name);
        } else System.out.println("Login failed!");
    }

    private static void showMoney() {
        if (!loginCheck()) return;
        System.out.println("Money: " + current.money);
    }

    private static void deposit(String x) {
        if (!loginCheck()) return;
        try {
            double m = Double.parseDouble(x);
            if (m <= 0) { System.out.println("Invalid amount!"); return; }
            current.money += m;
            System.out.println("Done. New balance: " + curre