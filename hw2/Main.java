import java.util.ArrayList;import java.util.List;

abstract class Human {
    private String fullName;
    private String phone;

    public Human(String fullName, String phone) {
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void updateName(String name) {
        this.fullName = name;
    }

    public String getPhone() {
        return phone;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    public abstract String showInfo();
}

class Client extends Human {
    private String code;
    private int score;
    private static int counter = 100;

    public Client(String fullName, String phone) {
        super(fullName, phone);
        this.code = "CL-" + counter++;
        this.score = 0;
    }

    public void addScore(double money) {
        if (money > 1200000) score += 2;
        else if (money > 600000) score++;
    }

    public double discountPercent() {
        if (score >= 6) return 0.12;
        if (score >= 3) return 0.06;
        return 0;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String showInfo() {
        return "[" + code + "] " + getFullName() + " | Phone: " + getPhone() +
                " | Score: " + score;
    }
}

class Worker extends Human {
    private String code;
    private String role;
    private double baseSalary;
    private int hours;
    private static int countW = 10;

    public Worker(String fullName, String phone, String role, double baseSalary) {
        super(fullName, phone);
        this.code = "WK-" + countW++;
        this.role = role;
        this.baseSalary = baseSalary;
    }

    public void addHours(int h) {
        hours += h;
    }

    public double calcSalary() {
        if (hours <= 160) return baseSalary;
        int extra = hours - 160;
        return baseSalary + extra * ((baseSalary / 160) * 2);
    }

    @Override
    public String showInfo() {
        return "[" + code + "] " + getFullName() +
                " | Role: " + role + " | Hours: " + hours;
    }
}

abstract class Product {
    private String code;
    private String title;
    private double price;

    public Product(String code, String title, double price) {
        this.code = code;
        this.title = title;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public abstract String showDetails();
}

class Dish extends Product {
    private String heat;
    private int cookTime;

    public Dish(String code, String title, double price, String heat, int cookTime) {
        super(code, title, price);
        this.heat = heat;
        this.cookTime = cookTime;
    }

    @Override
    public String showDetails() {
        return getTitle() + " | Price: " + (int)getPrice() +
                " | Heat: " + heat + " | Time: " + cookTime;
    }
}

class Drink extends Product {
    private String size;

    public Drink(String code, String title, double price, String size) {
        super(code, title, price);
        this.size = size;
    }

    @Override
    public String showDetails() {
        return getTitle() + " | Price: " + (int)getPrice() +
                " | Size: " + size;
    }
}

class Ticket {
    private Client client;
    private List<Product> items = new ArrayList<>();
    private double total;
    private static int ordNum = 1;
    private String id = "ORD-" + ordNum++;

    public Ticket(Client client) {
        this.client = client;
    }

    public void add(Product p) {
        items.add(p);
    }

    public void checkout() {
        double sum = 0;
        for (Product p : items) sum += p.getPrice();
        client.addScore(sum);
        total = sum - sum * client.discountPercent();
    }

    public String summary() {
        StringBuilder sb = new StringBuilder();
        for (Product p : items) sb.append(p.getTitle()).append(", ");
        if (sb.length() > 1) sb.setLength(sb.length() - 2);