class Design {
    public static void main(String[] args) {
        System.out.println("patterns for low-level-design");
    }
}

// # Singleton ----------------------------------------------------

class LazySingleton {
    // The single instance, initially null
    private static LazySingleton instance;

    // Private constructor to prevent instantiation
    private LazySingleton() {}

    // Public method to get the instance
    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}

// When a thread enters the synchronized method, it acquires a lock on the class object. 
// Other threads must wait until the method is executed.
class ThreadSafeSingleton {
    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {}

    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }

        return instance;
    }
}

// inherently thread-safe without needing explicit synchronization.
// JVM guarantees that the instance will be created before any thread access instance variable
class EagerSingleton {
    // The single instance, created immediately
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return instance;
    }
}

// inner class is not loaded into memory until it's referenced for the first time in
// thread-safe without requiring explicit synchronization
// perfect balance of lazy initialization, thread safety, and performance
class BillPughSingleton {
    private BillPughSingleton() {}

    // Static inner class that holds the instance
    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}

// ability to handle exceptions during instance creation
class StaticBlockSingleton {
    private static StaticBlockSingleton instance;

    private StaticBlockSingleton() {}

    // Static block for initialization
    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    // Public method to get the instance
    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}

// singleton is declared as an enum rather than a class.
// only one instance of an enum value is created, even in a multithreaded environment
enum EnumSingleton {
    INSTANCE;

    // Public method
    public void doSomething() {
        // Add any singleton logic here
    }
}


// # Factory Method --------------------------------------------------------------------------------
// an interface for creating objects in a superclass, 
// but allows subclasses to alter the type of objects that will be created.

// naive 
class NotificationService {
    public void sendNotification(String type, String message) {
        if (type.equals("EMAIL")) {
            EmailNotification email = new EmailNotification();
            email.send(message);
        } else if (type.equals("SMS")) {
            SMSNotification sms = new SMSNotification();
            sms.send(message);
        } else if (type.equals("Push")) {
            PushNotification sms = new PushNotification();
            sms.send(message);
        } else if (type.equals("Slack")) {
            SlackNotification sms = new SlackNotification();
            sms.send(message);
        } else if (type.equals("WhatsApp")) {
            WhatsAppNotification sms = new WhatsAppNotification();
            sms.send(message);
        }
    }
}

// factory method 
// 1.product interface
interface Notification {
    public void send(String message);
}
// 2. concrete products 
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}
// 3. abstract creator 
abstract class NotificationCreator {
    // Factory Method
    public abstract Notification createNotification();

    // Common logic using the factory method
    public void send(String message) {
        Notification notification = createNotification();
        notification.send(message);
    }
}

// 4. concrete creators 
class EmailNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}

// 5. client usage 
public class FactoryMethodDemo {
    public static void main(String[] args) {
        NotificationCreator creator;

        // Send Email
        creator = new EmailNotificationCreator();
        creator.send("Welcome to our platform!");

        // Send SMS
        creator = new SMSNotificationCreator();
        creator.send("Your OTP is 123456");
    }
}


