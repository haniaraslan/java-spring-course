import java.util.Scanner;

public class LuxuryCar extends Car {
    private final int minimumDays = 3;
    private final double insuranceFees;

    public LuxuryCar(int id, String brand, String model, int year, double price, double insuranceFees) {
        super(id, brand, model, year, price);
        this.insuranceFees = insuranceFees;
    }

    public static LuxuryCar CreateNewCar() {
        Scanner sc = new Scanner(System.in);
        Car baseCar = Car.CreateNewCar();
        System.out.println("Please enter Insurance Fees:");
        double insuranceFees = sc.nextDouble();
        return new LuxuryCar(
                baseCar.getId(),
                baseCar.getBrand(),
                baseCar.getModel(),
                baseCar.getYear(),
                baseCar.getPrice(),
                insuranceFees
        );
    }

    public double getInsuranceFees() {
        return insuranceFees;
    }

    public int getMinimumDays() {
        return minimumDays;
    }

    @Override
    public void printCarDetails() {
        super.printCarDetails();
        System.out.println(
                "Car Insurance fees : " + insuranceFees +
                        "\n Car Minimum dats : " + minimumDays);
    }

}
