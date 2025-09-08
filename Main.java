// Demo OOP concepts
public class Main {
    public static void main(String[] args) {
        // 1. Creating objects of the Car class
        Car corolla = new Car("Toyota", "Corolla");
        Car mustang = new Car("Ford", "Mustang");
        corolla.accelerate(20);
        mustang.accelerate(40);
        // Displaying status of each car
        corolla.displayStatus();
        mustang.displayStatus();

        // 2. Enums usage
        Coin dime = Coin.DIME;
        System.out.println("ENUM: dime = " + dime + (dime == Coin.DIME) + " Coin.QUARTER.getValue() = " + Coin.QUARTER.getValue());

        // 3. Interfaces and Loose Coupling 
        PaymentGateway stripeGateway = new StripePayment();
        CheckoutService service = new CheckoutService(stripeGateway);
        service.checkout(120.50);  // Output: Processing payment via Stripe: $120.5

        PaymentGateway razorpayGateway = new RazorpayPayment(); // Switch to Razorpay
        service.setPaymentGateway(razorpayGateway);
        service.checkout(150.50);  // Output: Processing payment via Razorpay: ₹150.5 


        // 4. Encapsulation 
        PaymentProcessor payment = new PaymentProcessor("1234567812345678", 250.00);
        payment.processPayment();

        // 5. Abstraction & Inheritance
        Truck truck = new Truck("Mahindra");
        truck.displayBrand(); // inherited method
        truck.start(); // abstract method from superclass implemented in subclass
        truck.broadcastLocation(); // new own method of subclass
    }
}

// 1. Classes and Objects aka Blueprint 
public class Car {
    // Attributes
    private String brand;
    private String model;
    private int speed;

    // Constructor
    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
        this.speed = 0;
    }

    // Method to accelerate
    public void accelerate(int increment) {
        speed += increment;
    }

    // Method to display info
    public void displayStatus() {
        System.out.println(brand + " is running at " + speed + " km/h.");
    }
}

// 2. Enums
public enum Coin {
    PENNY(1),
    NICKEL(5),
    DIME(10),
    QUARTER(25);

    private final int value;

    Coin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}


// 3. Interfaces and Polymorphism (Can-Do Relationship)
public interface PaymentGateway {
    void initiatePayment(double amount);
}

public class StripePayment implements PaymentGateway {
    @Override
    public void initiatePayment(double amount) {
        System.out.println("Processing payment via Stripe: $" + amount);
    }
}

public class RazorpayPayment implements PaymentGateway {
    public void initiatePayment(double amount) {
        System.out.println("Processing payment via Razorpay: ₹" + amount);
    }
}

// Interfaces for Loose Coupling (polymorphism)
public class CheckoutService {
    private PaymentGateway paymentGateway;

    public CheckoutService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void checkout(double amount) {
        paymentGateway.initiatePayment(amount);
    }
}


// 4. Encapsulation 
class PaymentProcessor {
    private String cardNumber;
    private double amount;

    public PaymentProcessor(String cardNumber, double amount) {
        this.cardNumber = maskCardNumber(cardNumber);
        this.amount = amount;
    }

    private String maskCardNumber(String cardNumber) {
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }

    public void processPayment() {
        System.out.println("Processing payment of " + amount + " for card " + cardNumber);
    }
}

// 5. Abstraction & Inheritance (Is-A relationship)
abstract class Vehicle {
    String brand;
    
    // Constructor
    Vehicle(String brand) {
        this.brand = brand;
    }
    
    // Abstract method (must be implemented by subclasses)
    abstract void start();
    
    // Concrete method (can be inherited)
    void displayBrand() {
        System.out.println("Brand: " + brand);
    }
}

// Subclass implementing the abstract method
class Truck extends Vehicle {
    Truck(String brand) {
        super(brand);  // parent constructor (superclass)
    }
    
    @Override // abstract method from superclass
    void start() {
        System.out.println(brand + " Truck is starting...");
    }

    void broadcastLocation() {
        System.out.println("Broadcasting Location for " + brand + " truck ...");
    }
}

// NOTE-1: abstract class vs interfaces 
// Abstract Class: The "Is-A" Relationship (An Incomplete Blueprint) 
//     need to provide common data and default implementation for some methods while 
//     forcing subclasses to provide their own for some methods.
// Interface: The "Can-Do" Relationship (A Contract for Behavior)
//     define a common set of behaviors for unrelated classes
//     public api contract without any implementation 

