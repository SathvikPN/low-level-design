class Design {
    public static void main(String[] args) {
        System.out.println("patterns for low-level-design");
    }
}

// #1 Singleton ----------------------------------------------------

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


// #2 Factory Method --------------------------------------------------------------------------------
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

// 2.1.product interface
interface Notification {
    public void send(String message);
}
// 2.2. concrete products 
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}
// 2.3. abstract creator 
abstract class NotificationCreator {
    // Factory Method
    public abstract Notification createNotification();

    // Common logic using the factory method
    public void send(String message) {
        Notification notification = createNotification();
        notification.send(message);
    }
}

// 2.4. concrete creators 
class EmailNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}

// 2.5. client usage 
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


// #3 Abstract Factory 
// The Abstract Factory Pattern provides an interface for creating families of related or dependent objects 
// without specifying their concrete classes.

// naive 
public class App {
    public static void main(String[] args) {
        String os = System.getProperty("os.name");

        if (os.contains("Windows")) {
            WindowsButton button = new WindowsButton();
            WindowsCheckbox checkbox = new WindowsCheckbox();
            button.paint();
            checkbox.paint();
        } else if (os.contains("Mac")) {
            MacOSButton button = new MacOSButton();
            MacOSCheckbox checkbox = new MacOSCheckbox();
            button.paint();
            checkbox.paint();
        }
    }
}


// 3.1 abstract Factory Method
// abstract product interface 
interface Button {
    void paint();
    void onClick();
}

// 3.2 concrete product classes
class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Painting a Windows-style button.");
    }

    @Override
    public void onClick() {
        System.out.println("Windows button clicked.");
    }
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Painting a Windows-style checkbox.");
    }

    @Override
    public void onSelect() {
        System.out.println("Windows checkbox selected.");
    }
}

class MacOSButton implements Button {
    @Override
    public void paint() {
        System.out.println("Painting a macOS-style button.");
    }

    @Override
    public void onClick() {
        System.out.println("MacOS button clicked.");
    }
}

// 3.3 abstract factory 
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// 3.4 concrete factories
class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacOSFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}

// 3.5 client usage - abstract interfaces only 
class Application {
    private final Button button;
    private final Checkbox checkbox;

    public Application(GUIFactory factory) {
        this.button = factory.createButton();
        this.checkbox = factory.createCheckbox();
    }

    public void renderUI() {
        button.paint();
        checkbox.paint();
    }
}

// 3.6 application entry point 
public class AppLauncher {
    public static void main(String[] args) {
        // Simulate platform detection
        String os = System.getProperty("os.name");
        GUIFactory factory;

        if (os.contains("Windows")) {
            factory = new WindowsFactory();
        } else {
            factory = new MacOSFactory();
        }

        Application app = new Application(factory);
        app.renderUI();
    }
}


// # 4 Builder 
// lets you construct complex objects step-by-step, 
// separating the construction logic from the final representation.

// naive 
class HttpRequestTelescoping {
    private String url;
    private String method;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private String body;
    private int timeout;

    public HttpRequestTelescoping(String url) {
        this(url, "GET");
    }

    public HttpRequestTelescoping(String url, String method) {
        this(url, method, null);
    }

    public HttpRequestTelescoping(String url, String method, Map<String, String> headers) {
        this(url, method, headers, null);
    }

    public HttpRequestTelescoping(String url, String method, Map<String, String> headers,
                                  Map<String, String> queryParams) {
        this(url, method, headers, queryParams, null);
    }

    public HttpRequestTelescoping(String url, String method, Map<String, String> headers,
                                  Map<String, String> queryParams, String body) {
        this(url, method, headers, queryParams, body, 30000);
    }

    public HttpRequestTelescoping(String url, String method, Map<String, String> headers,
                                  Map<String, String> queryParams, String body, int timeout) {
        this.url = url;
        this.method = method;
        this.headers = headers == null ? new HashMap<>() : headers;
        this.queryParams = queryParams == null ? new HashMap<>() : queryParams;
        this.body = body;
        this.timeout = timeout;

        System.out.println("HttpRequest Created: URL=" + url +
            ", Method=" + method +
            ", Headers=" + this.headers.size() +
            ", Params=" + this.queryParams.size() +
            ", Body=" + (body != null) +
            ", Timeout=" + timeout);
    }

    // Getters could be added here
}

// client 
public class HttpAppTelescoping {
    public static void main(String[] args) {
        HttpRequestTelescoping req1 = new HttpRequestTelescoping("https://api.example.com/data");

        HttpRequestTelescoping req2 = new HttpRequestTelescoping(
            "https://api.example.com/submit",
            "POST",
            null,
            null,
            "{\"key\":\"value\"}"
        );

        HttpRequestTelescoping req3 = new HttpRequestTelescoping(
            "https://api.example.com/config",
            "PUT",
            Map.of("X-API-Key", "secret"),
            null,
            "config_data",
            5000
        );
    }
}

// Builder Method 

// 4.1 product class 
class HttpRequest {
    // Required
    private final String url;

    // Optional
    private final String method;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final String body;
    private final int timeout;

    // Private constructor
    private HttpRequest(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.queryParams = builder.queryParams;
        this.body = builder.body;
        this.timeout = builder.timeout;
    }

    // 4.1.2 Builder Class (nested in product class)
    public static class Builder {
        private final String url; // required
        private String method = "GET";
        private Map<String, String> headers = new HashMap<>();
        private Map<String, String> queryParams = new HashMap<>();
        private String body;
        private int timeout = 30000;

        public Builder(String url) {
            this.url = url;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder addQueryParam(String key, String value) {
            this.queryParams.put(key, value);
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}

// 4.2 client usage 
public class HttpAppBuilderPattern {
    public static void main(String[] args) {
        HttpRequest request1 = new HttpRequest.Builder("https://api.example.com/data")
            .build();

        HttpRequest request2 = new HttpRequest.Builder("https://api.example.com/submit")
            .method("POST")
            .body("{\"key\":\"value\"}")
            .timeout(15000)
            .build();

        HttpRequest request3 = new HttpRequest.Builder("https://api.example.com/config")
            .method("PUT")
            .addHeader("X-API-Key", "secret")
            .addQueryParam("env", "prod")
            .body("config_payload")
            .timeout(5000)
            .build();

        System.out.println(request1);
        System.out.println(request2);
        System.out.println(request3);
    }
}

