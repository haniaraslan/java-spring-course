import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static final int maxNumberOfCars = 20;
    static final int maxNumberOfCustomers = 20;
    static HashMap<Integer, Car> cars = new HashMap<>();
    static HashMap<Integer, Customer> customers = new HashMap<>();
    static HashMap<Integer, LuxuryCar> luxuryCars = new HashMap<>();

    private static void printBanner() {
        System.out.println("========================================\n" +
                "SPEEDWAY RENTALS SYSTEM\n" +
                "========================================\n" +
                "1. Add Regular Car\n" +
                "2. Add Luxury Car\n" +
                "3. Add Customer\n" +
                "4. Display All Cars\n" +
                "5. Display Available Cars\n" +
                "6. Rent a Car\n" +
                "7. Return a Car\n" +
                "8. Search Car by ID\n" +
                "9. Search Car by Brand\n" +
                "10. Display All Customers\n" +
                "0. Exit\n" +
                "========================================\n" +
                "Enter your choice:");
    }

    public static ArrayList<Car> getCarsByBrand(String brand) {
        ArrayList<Car> selectedCars = new ArrayList<>();
        for (Car car : cars.values()) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                selectedCars.add(car);
            }
        }
        return selectedCars;
    }

    private static void printCarBasic(Car car, int index) {
        System.out.println(index + ". " + car.getId() + " " + car.getBrand() + " " + car.getModel());
    }

    private static void printCarDetails(Car car) {
        LuxuryCar luxuryCar = luxuryCars.get(car.getId());
        if (luxuryCar != null) luxuryCar.printCarDetails();
        else car.printCarDetails();
    }

    public static void main(String[] args) {
        double officeIncome = 0;
        int choice = 0;
        do {
            printBanner();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: // add new Regular Car
                {
                    if (Car.getTotalNumberOfCars() == maxNumberOfCars) {
                        System.out.println("You've already reached the Maximum number of Available Cars");
                    } else {
                        try {
                            Car car = Car.CreateNewCar();
                            if (cars.containsKey(car.getId())) {
                                System.out.println("Car Cannot be Added, duplicate Id!");
                            } else {
                                cars.put(car.getId(), car);
                                System.out.println("Car Added Successfully!");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e);
                        }
                    }
                    break;
                }
                case 2: // add new Luxury Car
                {
                    if (Car.getTotalNumberOfCars() == maxNumberOfCars) {
                        System.out.println("You've already reached the Maximum number of Available Cars");
                    } else {
                        LuxuryCar car = LuxuryCar.CreateNewCar();
                        if (cars.containsKey(car.getId())) {
                            System.out.println("Car Cannot be Added, duplicate Id!");
                        } else {
                            cars.put(car.getId(), car);
                            luxuryCars.put(car.getId(), car);
                            System.out.println("Luxury Car Added Successfully!");
                        }
                    }
                    break;
                }
                case 3: { // add new Customer
                    if (Customer.getCount() == maxNumberOfCustomers) {
                        System.out.println("You've already reached the Maximum number of Customers");
                    } else {
                        Customer customer = Customer.createNewCustomer();
                        if (customers.containsKey(customer.getId())) {
                            System.out.println("Customer Cannot be Added, duplicate Id!");
                        } else {
                            customers.put(customer.getId(), customer);
                            System.out.println("Customer Added Successfully with Id " + customer.getId());
                        }
                    }
                    break;
                }
                case 4: { // Display All Cars
                    if (Car.getTotalNumberOfCars() == 0) {
                        System.out.println("No Cars Added Yet! ");
                    } else {
                        int index = 1;
                        for (Car car : cars.values()) {
                            printCarBasic(car, index++);
                            printCarDetails(car);
                        }
                    }
                    break;
                }
                case 5: // Display Available Cars
                {
                    int index = 1;
                    int availableCarsCount = 0;
                    for (Car car : cars.values()) {
                        if (car.isAvailable()) {
                            printCarBasic(car, index++);
                            printCarDetails(car);
                            availableCarsCount++;
                        }
                    }
                    System.out.println("Total number of Available Cars: " + availableCarsCount);
                    break;
                }
                case 6: // Rent a Car
                {
                    System.out.println("Please enter Customer Id:");
                    int customerId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Please enter Car Id:");
                    int carId = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Please enter number of rental days:");
                    int rentalDays = sc.nextInt();
                    sc.nextLine();

                    Customer customer = customers.get(customerId);
                    if (customer == null) {
                        System.out.println("Customer not found!");
                        break;
                    }
                    if (customer.getRentedCarId() != -1) {
                        System.out.println("Cannot Rent a new Car, Customer Has a rented Car!");
                        break;
                    }
                    Car car = cars.get(carId);
                    if (car == null) {
                        System.out.println("Car not found!");
                        break;
                    }
                    if (!car.isAvailable()) {
                        System.out.println("Car unavailable!");
                        break;
                    }
                    LuxuryCar luxuryCar = luxuryCars.get(carId);
                    int minimumDays = luxuryCar != null ?
                            luxuryCar.getMinimumDays() : 0;
                    if (rentalDays < minimumDays) {
                        System.out.println("Rental Days incorrect!");
                        break;
                    }
                    double totalCost = rentalDays * car.getPrice() + car.getTax();
                    if (luxuryCar != null && rentalDays <= luxuryCar.getMinimumDays()) {
                        totalCost += luxuryCar.getInsuranceFees();
                    }
                    car.setAvailable(false);
                    customer.setRentedCarId(carId);
                    customer.setRentedDays(rentalDays);
                    customer.setTotalPaidAmount(customer.getTotalPaidAmount() + totalCost);
                    officeIncome += totalCost;
                    System.out.println("Car Rented Successfully!\n" +
                            "Customer Name : " + customer.getName() +
                            "\n Car Brand : " + car.getBrand() +
                            "\n Car Model : " + car.getModel() +
                            "\n Number of Rented Days : " + rentalDays +
                            "\n Final Amount : " + totalCost
                    );
                    break;
                }
                case 7: // Return car
                {
                    System.out.println("Please enter Customer Id:");
                    int customerId = sc.nextInt();

                    Customer customer = customers.get(customerId);
                    if (customer == null) {
                        System.out.println("Customer not found!");
                        break;
                    }
                    int carId = customer.getRentedCarId();
                    if (carId == -1) {
                        System.out.println("Customer does not have a rented Car!");
                        break;
                    }
                    Car car = cars.get(carId);
                    if (car == null) {
                        System.out.println("Car not found!");
                        break;
                    }
                    car.setAvailable(true);
                    customer.resetCustomerRental();
                    System.out.println("Car Returned Successfully!\n" +
                            "Customer Name : " + customer.getName() +
                            "\n Car Brand : " + car.getBrand() +
                            "\n Car Model : " + car.getModel());
                    break;
                }
                case 8: //search car by ID
                {
                    System.out.println("Please enter Car Id:");
                    int carId = sc.nextInt();
                    Car car = cars.get(carId);
                    if (car == null) {
                        System.out.println("Car Not Found!");
                        break;
                    }
                    printCarDetails(car);
                    break;
                }
                case 9: //search car by brand
                {
                    System.out.println("Please enter Car Brand:");
                    String carBrand = sc.nextLine();
                    ArrayList<Car> selectedCars = getCarsByBrand(carBrand);
                    if (selectedCars.isEmpty()) {
                        System.out.println("No Cars with this Brand Found!");
                        break;
                    }
                    for (Car car : selectedCars) {
                        printCarDetails(car);
                    }
                    break;
                }
                case 10: //display all customers
                {
                    if (!customers.isEmpty()) {
                        for (Customer customer : customers.values()) {
                            customer.printDetails();
                            Car car = cars.get(customer.getRentedCarId());
                            if (car != null) {
                                printCarDetails(car);
                            }
                        }
                    } else {
                        System.out.println("No Customers Added!");
                    }
                    break;
                }
                case 0: // Exit
                {
                    System.out.println("Thank you for using Rental Services!");
                    return;
                }
                default: {
                    System.out.println("Invalid Choice!");
                    break;
                }
            }
        } while (choice != 0);
    }
}