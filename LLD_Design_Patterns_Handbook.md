# 📘 SDE-2 LLD Design Patterns Handbook (Java Notes)

This handbook summarizes **must-know design patterns** for LLD interviews, with minimal Java client usage examples.

---

## 1. Singleton
**What it is**: Ensure only one instance of a class exists.  
**When to use**: Global config, logging, shared resources.  
**Interview Examples**: Logger, CacheManager, DB connection.

```java
// Client usage
Logger logger1 = Logger.getInstance();
Logger logger2 = Logger.getInstance();
System.out.println(logger1 == logger2); // true (same instance)
```

---

## 2. Factory Method / Abstract Factory
**What it is**: Centralizes object creation, hides `new`.  
**When to use**: Varying products, decoupling creation from usage.  
**Interview Examples**: Payment gateways, parsers, UI toolkit.

```java
// Client usage
Payment p1 = PaymentFactory.getPayment("UPI");
p1.pay(100);

Payment p2 = PaymentFactory.getPayment("CARD");
p2.pay(500);
```

---

## 3. Builder
**What it is**: Step-by-step construction of complex objects.  
**When to use**: Many optional fields, readable object creation.  
**Interview Examples**: User profiles, HttpRequest, Pizza order.

```java
// Client usage
User user = new User.Builder("Alice")
                  .email("alice@mail.com")
                  .phone("12345")
                  .build();
```

---

## 4. Observer (Publisher–Subscriber)
**What it is**: One-to-many dependency. Observers auto-update on subject change.  
**When to use**: Event-driven systems.  
**Interview Examples**: Notification system, stock prices, chatrooms.

```java
// Client usage
Subject stock = new Stock();
Observer mobileApp = new MobileApp();
Observer webApp = new WebApp();

stock.addObserver(mobileApp);
stock.addObserver(webApp);

stock.setPrice(100); // both observers notified
```

---

## 5. Strategy
**What it is**: Encapsulates interchangeable algorithms.  
**When to use**: Replaceable behaviors at runtime.  
**Interview Examples**: Payment methods, sorting, routing.

```java
// Client usage
PaymentStrategy upi = new UpiPayment();
PaymentContext ctx = new PaymentContext(upi);
ctx.pay(200);

ctx.setStrategy(new CardPayment());
ctx.pay(500);
```

---

## 6. Decorator
**What it is**: Add functionality dynamically without altering base class.  
**When to use**: Add layers (logging, auth, compression).  
**Interview Examples**: Java I/O streams, API middleware, Coffee toppings.

```java
// Client usage
Coffee coffee = new BasicCoffee();
coffee = new MilkDecorator(coffee);
coffee = new SugarDecorator(coffee);

System.out.println(coffee.getCost());   // base + milk + sugar
```

---

## 7. Adapter
**What it is**: Converts one interface into another.  
**When to use**: Integrating legacy/third-party systems.  
**Interview Examples**: Wrapping new API, payment gateways, power plugs.

```java
// Client usage
NewSmsApi api = new NewSmsApi();
NotificationService service = new SmsAdapter(api);

service.send("Hello from Adapter!");
```

---

## 8. Command
**What it is**: Encapsulate actions as objects (undo, queue, macro).  
**When to use**: Logging, scheduling, undo/redo.  
**Interview Examples**: Text editor, remote control, job queue.

```java
// Client usage
Command insert = new InsertTextCommand(editor, "Hello");
Command delete = new DeleteTextCommand(editor);

Invoker invoker = new Invoker();
invoker.execute(insert);
invoker.execute(delete);

invoker.undo(); // undo last command
```

---

## 9. Prototype
**What it is**: Clone existing objects instead of building from scratch.  
**When to use**: Expensive object creation.  
**Interview Examples**: Document templates, game character cloning.

```java
// Client usage
Document doc1 = new Document("Original");
Document doc2 = doc1.clone(); // clone via prototype
```

---

# 📝 How to Revise
For each pattern:
1. Recall **abstract idea** (what problem it solves).  
2. Recall **when to use** (scenario).  
3. Recall **client usage** (how it looks in code).  
