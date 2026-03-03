SOLID Principles Refactoring
1. Ex1 - Single Responsibility Principle (SRP): Student Onboarding
The Problem: Sir, in the original code, the OnboardingService was a "God Class". It was doing everything inside a single method: parsing the raw input string, validating the business rules, generating a student ID, saving to the database, and printing the output. This violated SRP because any change to validation, printing, or storage would require modifying this one file.

How I Fixed It: I broke the monolithic method into dedicated services. I created an InputService for parsing, a ValidationService for rule checking, and a PrintingService for console output. I also abstracted the database using a PersistenceDb interface and created a HashMapDb implementation. Now, OnboardingService simply acts as an orchestrator.

UML Diagram:

Code snippet
classDiagram
    class OnboardingService
    class InputService
    class ValidationService
    class PrintingService
    class PersistenceDb { <<interface>> }
    class HashMapDb
    
    OnboardingService --> InputService
    OnboardingService --> ValidationService
    OnboardingService --> PrintingService
    OnboardingService --> PersistenceDb
    HashMapDb ..|> PersistenceDb
2. Ex2 - Single Responsibility Principle (SRP): Cafeteria Billing
The Problem: The CafeteriaSystem was tightly coupled. The checkout method manually calculated taxes and discounts using hard-coded if/else blocks based on the customer type, while simultaneously building the receipt string and saving it to the file system.

How I Fixed It: I extracted the formatting logic into a PrinterService and the calculation orchestration into an InvoiceService. More importantly, I extracted the business rules into their own interfaces: taxRules and DiscountRule. I created specific strategy classes like StudentTaxRules and StaffDiscountRules. Now, the main class just wires these together.

UML Diagram:

Code snippet
classDiagram
    class CafeteriaSystem
    class InvoiceService
    class PrinterService
    class taxRules { <<interface>> }
    class DiscountRule { <<interface>> }
    class StudentTaxRules
    class StaffDiscountRules
    
    CafeteriaSystem --> InvoiceService
    CafeteriaSystem --> PrinterService
    StudentTaxRules ..|> taxRules
    StaffDiscountRules ..|> DiscountRule
3. Ex3 - Open/Closed Principle (OCP): Placement Eligibility
The Problem: The EligibilityEngine used a massive if/else if chain to check things like CGR, attendance, and credits. Every time the placement team wanted to add a new rule, we had to modify the core engine class, risking bugs and violating the Open/Closed Principle.

How I Fixed It: I introduced an EligibilityRule interface. I then created separate classes for each rule: AttendanceRule, CgrRule, CreditsRule, and DisciplinaryRule. The engine now accepts a List<EligibilityRule> and simply loops through them. If we need a new rule tomorrow, I just add a new class without touching the engine.

UML Diagram:

Code snippet
classDiagram
    class EligibilityEngine
    class EligibilityRule { <<interface>> }
    class AttendanceRule
    class CgrRule
    class CreditsRule
    
    EligibilityEngine o-- EligibilityRule
    AttendanceRule ..|> EligibilityRule
    CgrRule ..|> EligibilityRule
    CreditsRule ..|> EligibilityRule
4. Ex4 - Open/Closed Principle (OCP): Hostel Fee Calculator
The Problem: The fee calculator relied on a giant switch statement for room types and sequential if statements for add-ons (Mess, Laundry, etc.). Adding a new room type meant diving into the core logic and adding another case.

How I Fixed It: I utilized polymorphism. I created a Rooms interface and an ExtraPricingForAddons interface. I passed maps of these implementations into the HostelFeeCalculator. The calculator now dynamically fetches the prices from the maps. We can add infinite room types and add-ons without changing the calculator's code.

UML Diagram:

Code snippet
classDiagram
    class HostelFeeCalculator
    class Rooms { <<interface>> }
    class SingleRoom
    class ExtraPricingForAddons { <<interface>> }
    class LaundryAddOn
    
    HostelFeeCalculator --> Rooms
    HostelFeeCalculator --> ExtraPricingForAddons
    SingleRoom ..|> Rooms
    LaundryAddOn ..|> ExtraPricingForAddons
5. Ex5 - Liskov Substitution Principle (LSP): File Exporters
The Problem: The subclasses broke the base Exporter contract. PdfExporter unexpectedly threw exceptions if the string was too long (tightening preconditions), and CsvExporter silently stripped commas, changing the data's meaning.

How I Fixed It: I implemented the Template Method pattern in the base Exporter class. The base class now handles all common validations (like null checks and max length enforcement). The subclasses now only implement generateExport() and define their specific limits via getters. Now, any subclass can safely replace the base class without surprises.

UML Diagram:

Code snippet
classDiagram
    class Exporter {
        +export()
        #generateExport()*
        +getMaxContentLength()*
    }
    class PdfExporter
    class CsvExporter
    
    PdfExporter --|> Exporter
    CsvExporter --|> Exporter
6. Ex6 - Liskov Substitution Principle (LSP): Notification Senders
The Problem: Similar to the exporters, our notification channels were breaking trust. EmailSender silently truncated bodies, and WhatsAppSender crashed if the phone number lacked a country code.

How I Fixed It: I pulled the constraint checking up into the abstract NotificationSender class. The base class now validates phone numbers and truncates lengths based on properties provided by the subclasses (requiresPlusInPhone(), getMaxLen()). The subclasses implement doSend(), ensuring all channels behave predictably.

UML Diagram:

Code snippet
classDiagram
    class NotificationSender {
        +send()
        #doSend()*
    }
    class EmailSender
    class WhatsAppSender
    class SmsSender
    
    EmailSender --|> NotificationSender
    WhatsAppSender --|> NotificationSender
    SmsSender --|> NotificationSender
7. Ex7 - Interface Segregation Principle (ISP): Smart Classroom
The Problem: We had one massive SmartClassroomDevice interface containing methods like setTemperatureC and scanAttendance. As a result, the Projector class was forced to implement temperature controls with dummy, blank methods.

How I Fixed It: I broke the fat interface into small, atomic capabilities: Power, Brightness, Temperature, Attendance, and ConnectInput. Now, the AirConditioner only implements Power and Temperature. The controller interacts strictly with the interfaces it needs, keeping the code clean and logical.

UML Diagram:

Code snippet
classDiagram
    class Power { <<interface>> }
    class Temperature { <<interface>> }
    class Brightness { <<interface>> }
    
    class AirConditioner
    class LightsPanel
    
    AirConditioner ..|> Power
    AirConditioner ..|> Temperature
    LightsPanel ..|> Power
    LightsPanel ..|> Brightness
8. Ex8 - Interface Segregation Principle (ISP): Club Admin Tools
The Problem: The ClubAdminTools interface forced all club officials (Secretaries, Treasurers) to implement every administrative action. The SecretaryTool was forced to have dummy implementations for finance methods like addIncome().

How I Fixed It: I segregated the interface by roles: FinanceAdmin, EventAdmin, and MinutesAdmin. Now, TreasurerTool implements only FinanceAdmin, and SecretaryTool implements only MinutesAdmin. No more dummy methods.

UML Diagram:

Code snippet
classDiagram
    class FinanceAdmin { <<interface>> }
    class MinutesAdmin { <<interface>> }
    class TreasurerTool
    class SecretaryTool
    
    TreasurerTool ..|> FinanceAdmin
    SecretaryTool ..|> MinutesAdmin
9. Ex9 - Dependency Inversion Principle (DIP): Evaluation Pipeline
The Problem: The EvaluationPipeline was highly coupled to concrete, low-level classes. Inside the evaluate method, it was literally using new CodeGrader() and new PlagiarismChecker(). This made it impossible to test or swap out grading strategies.

How I Fixed It: I created abstractions (Checker, Grader, Writer). I then used Constructor Injection to pass these dependencies into the EvaluationPipeline. Now, the high-level pipeline depends entirely on abstractions, not concretes.

UML Diagram:

Code snippet
classDiagram
    class EvaluationPipeline
    class Checker { <<interface>> }
    class Grader { <<interface>> }
    class CodeGrader
    class PlagiarismChecker
    
    EvaluationPipeline --> Checker
    EvaluationPipeline --> Grader
    CodeGrader ..|> Grader
    PlagiarismChecker ..|> Checker
10. Ex10 - Dependency Inversion Principle (DIP): Campus Transport
The Problem: Just like Ex9, the TransportBookingService was instantiating new DistanceCalculator() and new PaymentGateway() directly inside its core logic. It was untestable and rigidly tied to specific infrastructure.

How I Fixed It: I extracted IDistanceCalculator, IDriverAllocator, and IPaymentGateway interfaces. I updated the booking service to request these dependencies via its constructor. The Main file handles wiring the concrete implementations, keeping the service clean.

UML Diagram:

Code snippet
classDiagram
    class TransportBookingService
    class IDistanceCalculator { <<interface>> }
    class IPaymentGateway { <<interface>> }
    class PaymentGateway
    class DistanceCalculator
    
    TransportBookingService --> IDistanceCalculator
    TransportBookingService --> IPaymentGateway
    PaymentGateway ..|> IPaymentGateway
    DistanceCalculator ..|> IDistanceCalculator
🛠️ Design Patterns
11. Adapter Pattern (Payments)
The Problem: Our OrderService needed to charge customers, but the third-party SDKs (FastPayClient, SafeCashClient) had entirely different method names and flows. OrderService was forced to use messy switch statements to figure out how to call them.

How I Fixed It: I defined a standard PaymentGateway interface with a charge() method. I then created FastPayAdapter and SafeCashAdapter wrappers. These adapters catch the standard charge() call and translate it into the specific SDK methods (like .payNow() or .createPayment()). OrderService now only talks to PaymentGateway.

UML Diagram:

Code snippet
classDiagram
    class OrderService
    class PaymentGateway { <<interface>> }
    class FastPayAdapter
    class FastPayClient
    
    OrderService --> PaymentGateway
    FastPayAdapter ..|> PaymentGateway
    FastPayAdapter --> FastPayClient
12. Flyweight Pattern (Map Markers)
The Problem: We were rendering 30,000 map markers, and each MapMarker object was creating its own MarkerStyle object (color, shape, size). This caused massive duplication and memory bloat because thousands of markers shared the exact same visual style.

How I Fixed It: I separated the intrinsic state (the shared styling) into an immutable MarkerStyle class, and left the extrinsic state (coordinates and label) inside MapMarker. I built a MarkerStyleFactory that caches styles using a string key. Now, 30,000 markers share just a few unique style objects in memory.

UML Diagram:

Code snippet
classDiagram
    class MapMarker {
        -lat
        -lng
        -MarkerStyle style
    }
    class MarkerStyleFactory {
        -cache: Map
        +get()
    }
    class MarkerStyle {
        -shape
        -color
    }
    
    MapMarker --> MarkerStyle
    MarkerStyleFactory --> MarkerStyle
13. Immutable Objects & Builder (Incident Tickets)
The Problem: IncidentTicket was highly mutable. It had public setters, its internal tags list was leaked, and it had scattered validation. Any part of the code could accidentally or maliciously modify a ticket after it was created.

How I Fixed It: I made IncidentTicket strictly immutable by making all fields private final and removing all setters. I used defensive copying (Collections.unmodifiableList) to protect the tags array. I introduced a Builder Pattern to handle the complex creation process and centralized all validation inside the build() method. If we need to update a ticket, we use toBuilder() to stamp out a brand new instance.

UML Diagram:

Code snippet
classDiagram
    class IncidentTicket {
        -final id
        -final title
        -final tags
        -IncidentTicket(Builder)
        +toBuilder()
    }
    class Builder {
        +id()
        +title()
        +build() IncidentTicket
    }
    
    IncidentTicket ..> Builder
    Builder ..> IncidentTicket
14. Proxy Pattern (Reports)
The Problem: ReportFile had no security (anyone could view any report) and it executed a heavy simulated "disk load" every single time a user requested to view it, even if they had just opened it.

How I Fixed It: I introduced a Report interface. I kept the heavy lifting in RealReport, and created a ReportProxy. The proxy acts as a bouncer: it first checks AccessControl to see if the user has the right role. If they do, the proxy lazily loads the RealReport only once, and caches it for future calls.

UML Diagram:

Code snippet
classDiagram
    class Report { <<interface>> }
    class ReportProxy {
        -RealReport realReport
        +display(User)
    }
    class RealReport {
        +display(User)
    }
    class AccessControl
    
    ReportProxy ..|> Report
    RealReport ..|> Report
    ReportProxy --> RealReport
    ReportProxy --> AccessControl
15. Singleton Pattern (Metrics Registry)
The Problem: The MetricsRegistry was supposed to be a global counter, but the constructor was public, allowing anyone to make new instances. The getInstance() method wasn't thread-safe, and advanced Java features like Reflection and Serialization could easily clone it.

How I Fixed It: I made the constructor private. To fix thread safety, I used the Double-Checked Locking technique with a volatile instance variable and a synchronized block. I blocked Reflection by adding an if (INSTANCE != null) check inside the constructor that throws an exception. Finally, I implemented the hidden readResolve() method so Deserialization returns the existing singleton rather than building a new one.

UML Diagram:

Code snippet
classDiagram
    class MetricsRegistry {
        -static volatile INSTANCE
        -MetricsRegistry()
        +static getInstance()
        #readResolve()
    }