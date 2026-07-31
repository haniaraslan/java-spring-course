import java.util.Scanner;

public class Car {

    private static int count = 0;
    private final float tax = 0.14F;
    private final int id;
    private final String brand;
    private final String model;
    private final int year;
    private boolean available = false;
    private double price;

    public Car(int id, String brand, String model, int year, double price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.count++;
    }

    public static int getTotalNumberOfCars() {
        return count;
    }

    public static Car CreateNewCar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter car Id:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Please enter car Brand:");
        String brand = sc.nextLine();
        System.out.println("Please enter car Model:");
        String model = sc.nextLine();
        System.out.println("Please enter car year:");
        int year = sc.nextInt();
        if (year > 2026 || year < 1990) {
            throw new IllegalArgumentException("Year must be between 1990 and 2026!");
        }
        sc.nextLine();
        double price = 0;
        do {
            System.out.println("Please enter car Price Per Day:");
            price = sc.nextDouble();
        }
        while (price < 0);
        return new Car(id, brand, model, year, price);

    }

    public void addCar() {
        this.count++;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public float getTax() {
        return tax;
    }

    public void printCarDetails() {
        System.out.println(
                "Car Id : " + id +
                        "\n Car Brand : " + brand +
                        "\n Car Model : " + model +
                        "\n Car Year : " + year +
                        "\n Car Price : " + price +
                        "\n Car Tax : " + tax
        );
    }

}
