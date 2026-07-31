import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Car> cars = new ArrayList<Car>();
    static ArrayList<Customer> customers = new ArrayList<Customer>();
    static ArrayList<LuxuryCar> luxuryCars = new ArrayList<LuxuryCar>();

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

    public static Car getCar(int id) {
        for (Car car : cars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    public static ArrayList<Car> getCarsByBrand(String brand) {
        ArrayList<Car> selectedCars = new ArrayList<Car>();
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                selectedCars.add(car);
            }
        }
        return selectedCars;
    }

    public static Customer getCustomer(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    public static LuxuryCar getLuxuryCar(int id) {
        for (LuxuryCar luxuryCar : luxuryCars) {
            if (luxuryCar.getId() == id) {
                return luxuryCar;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        final int maxNumberOfCars = 20;
        final int maxNumberOfCustomers = 20;
        double officeIncome = 0;
        int choice = 0;
        do {
            printBanner();
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch (choice) {
                case 1: // add new regular car
                {
                    if (Car.getTotalNumberOfCars() == maxNumberOfCars) {
                        System.out.println("You've already reached the Maximum number of Available Cars");
                    } else {
                        Car car = Car.CreateNewCar();
                        cars.add(car);
                        System.out.println("Car Added Successfully!");
                    }
                    break;

                }
                case 2: //add new luxury car
                {
                    if (Car.getTotalNumberOfCars() == maxNumberOfCars) {
                        System.out.println("You've already reached the Maximum number of Available Cars");
                    } else {

                        LuxuryCar car = LuxuryCar.CreateNewCar();
                        cars.add(car);
                        luxuryCars.add(car);
                        System.out.println("Luxury Car Added Successfully!");
                    }
                    break;
                }
                case 3: {
                    if (Customer.getCount() == maxNumberOfCustomers) {
                        System.out.println("You've already reached the Maximum number of Customers");
                    } else {
                        Customer customer = Customer.createNewCustomer();
                        customers.add(customer);
                        System.out.println("Customer Added Successfully with Id " + customer.getId());
                    }
                    break;
                }
                case 4: {
                    if (Car.getTotalNumberOfCars() == 0) {
                        System.out.println("No Cars Added Yet! ");
                    } else {
                        for (int i = 0; i < cars.size(); i++) {
                            System.out.println(i + 1 + ". " + cars.get(i).getId() + " " + cars.get(i).getBrand() + " " + cars.get(i).getModel());

                        }
                    }
                    break;
                }
                case 5: // available
                {
                    int availableCarsCount = 0;
                    for (int i = 0; i < cars.size(); i++) {
                        if (cars.get(i).isAvailable()) {
                            System.out.println(i + 1 + ". " + cars.get(i).getId() + " " + cars.get(i).getBrand() + " " + cars.get(i).getModel());
                            availableCarsCount++;
                        }
                    }
                    System.out.println("Total number of Available Cars: " + availableCarsCount);
                    break;
                }
                case 6: // rent a car
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

                    Customer customer = getCustomer(customerId);
                    if (customer != null) {
                        if (customer.getRentedCarId() == -1) {
                            Car car = getCar(carId);
                            if (car != null) {
                                if (car.isAvailable()) {
                                    if (rentalDays > 0) {
                                        LuxuryCar luxuryCar = getLuxuryCar(carId);
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
                                    } else {
                                        System.out.println("Rental Days incorrect!");
                                    }
                                } else {
                                    System.out.println("Car unavailable!");
                                }
                            } else {
                                System.out.println("Car not found!");

                            }
                        } else {
                            System.out.println("Cannot Rent a new Car, Customer Has a rented Car!");
                        }
                    } else {
                        System.out.println("Customer not found!");
                    }
                    break;
                }
                case 7: //return car
                {
                    System.out.println("Please enter Customer Id:");
                    int customerId = sc.nextInt();

                    Customer customer = getCustomer(customerId);
                    if (customer != null) {
                        int carId = customer.getRentedCarId();
                        if (carId != -1) {
                            Car car = getCar(carId);
                            if (car != null) {
                                car.setAvailable(true);
                                customer.setRentedDays(0);
                                customer.setRentedCarId(-1);
                                customer.setTotalPaidAmount(0);
                                System.out.println("Car Returned Successfully!\n" +
                                        "Customer Name : " + customer.getName() +
                                        "\n Car Brand : " + car.getBrand() +
                                        "\n Car Model : " + car.getModel());
                            } else {
                                System.out.println("Car not found!");

                            }
                        } else {
                            System.out.println("Customer does not have a rented Car!");
                        }
                    } else {
                        System.out.println("Customer not found!");
                    }
                    break;
                }
                case 8: //search car by Id
                {
                    System.out.println("Please enter Car Id:");
                    int carId = sc.nextInt();
                    Car car = getCar(carId);
                    LuxuryCar luxuryCar = getLuxuryCar(carId);
                    if (car != null) {
                        car.printCarDetails();
                        if (luxuryCar != null) {
                            luxuryCar.printCarDetails();
                        }
                    } else {
                        System.out.println("Car Not Found!");
                    }
                    break;
                }
                case 9: //search car by brand
                {
                    System.out.println("Please enter Car Brand:");
                    String carBrand = sc.nextLine();
                    ArrayList<Car> selectedCars = getCarsByBrand(carBrand);
                    if (!selectedCars.isEmpty()) {
                        for (Car car : selectedCars) {
                            LuxuryCar luxuryCar = getLuxuryCar(car.getId());
                            if (luxuryCar != null) {
                                luxuryCar.printCarDetails();
                            } else {
                                car.printCarDetails();
                            }
                        }
                    } else {
                        System.out.println("Car Not Found!");
                    }
                    break;
                }
                case 10: //display all customers
                {
                    if (!customers.isEmpty()) {
                        for (Customer customer : customers) {

                            customer.printDetails();
                            Car car = getCar(customer.getRentedCarId());
                            if (car != null) {
                                LuxuryCar luxuryCar = getLuxuryCar(car.getId());
                                if (luxuryCar != null) {
                                    luxuryCar.printCarDetails();
                                } else {
                                    car.printCarDetails();
                                }
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
            }
        } while (choice != 0);
    }
}