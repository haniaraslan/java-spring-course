import java.util.Scanner;

public class Customer {
    private static int count = 0;
    private final int id;
    private final String name;
    private final String phone;
    private int rentedCarId;
    private int rentedDays;
    private double totalPaidAmount;

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.rentedCarId = -1;
        count++;
    }

    public Customer(int id, String name, String phone, int rentedCarId, int rentedDays) {
        this(id, name, phone);
        this.rentedCarId = rentedCarId;
        this.rentedDays = rentedDays;
    }

    public static int getCount() {
        return count;
    }

    public static Customer createNewCustomer() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter Customer Id:");
        int id = Integer.parseInt(sc.nextLine());   // safe input

        System.out.println("Please enter Customer Name:");
        String name = sc.nextLine();

        System.out.println("Please enter Customer Phone:");
        String phone = sc.nextLine();

        return new Customer(id, name, phone);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void setRentedCarId(int rentedCarId) {
        this.rentedCarId = rentedCarId;
    }

    public int getRentedDays() {
        return rentedDays;
    }

    public void setRentedDays(int rentedDays) {
        this.rentedDays = rentedDays;
    }

    public double getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(double totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public void printDetails() {
        System.out.println(
                "Customer Id : " + id +
                        "\n Customer Name : " + name +
                        "\n Customer Phone : " + phone);

        if (rentedCarId == -1) {
            System.out.println(
                    "\n Customer rented Car id : " + rentedCarId +
                            "\n Customer rented days : " + rentedDays +
                            "\n Customer paid amount : " + totalPaidAmount);
        } else {
            System.out.println(
                    "\n Rented Car: None");
        }
    }

}
