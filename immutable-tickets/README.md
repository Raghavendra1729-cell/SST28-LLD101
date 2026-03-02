Exercise B — Immutable Classes (Incident Tickets)
------------------------------------------------
Narrative
A small CLI tool called **HelpLite** creates and manages support/incident tickets.
Today, `IncidentTicket` is **mutable**:
- multiple constructors
- public setters
- validation scattered across the codebase
- objects can be modified after being "created", causing audit/log inconsistencies

Refactor the design so `IncidentTicket` becomes **immutable** and is created using a **Builder**.

What you have (Starter)
- `IncidentTicket` has public setters + several constructors.
- `TicketService` creates a ticket, then mutates it later (bad).
- Validation is duplicated and scattered, making it easy to miss checks.
- `TryIt` demonstrates how the same object can change unexpectedly.

Tasks
1) Refactor `IncidentTicket` to an **immutable class**
   - private final fields
   - no setters
   - defensive copying for collections
   - safe getters (no internal state leakage)

2) Introduce `IncidentTicket.Builder`
   - Required: `id`, `reporterEmail`, `title`
   - Optional: `description`, `priority`, `tags`, `assigneeEmail`, `customerVisible`, `slaMinutes`, `source`
   - Builder should be fluent (`builder().id(...).title(...).build()`)

3) Centralize validation
   - Move ALL validation to `Builder.build()`
   - Use helpers in `Validation.java` (add more if needed)
   - Examples:
     - id: non-empty, length <= 20, only [A-Z0-9-] (you can reuse helper)
     - reporterEmail/assigneeEmail: must look like an email
     - title: non-empty, length <= 80
     - priority: one of LOW/MEDIUM/HIGH/CRITICAL
     - slaMinutes: if provided, must be between 5 and 7,200

4) Update `TicketService`
   - Stop mutating a ticket after creation
   - Any “updates” should create a **new** ticket instance (e.g., by Builder copy/from method)
   - Keep the API simple; you can add `toBuilder()` or `Builder.from(existing)`

Acceptance
- `IncidentTicket` has no public setters and fields are final.
- Tickets cannot be modified after creation (including tags list).
- Validation happens only in one place (`build()`).
- `TryIt` still works, but now demonstrates immutability (attempted mutations should not compile or have no effect).
- Code compiles and runs with the starter commands below.

Build/Run (Starter demo)
  cd immutable-tickets/src
  javac com/example/tickets/*.java TryIt.java
  java TryIt

Tip
After refactor, you can update `TryIt` to show:
- building a ticket
- “updating” by creating a new instance
- tags list is not mutable from outside

1. The Problem: Unsafe Mutations (Changing Data)
What was wrong: The original IncidentTicket had public setter methods (setPriority, etc.). Any part of the application could change a ticket's data at any time. This is dangerous in large apps because it causes race conditions (two things updating the ticket at once) and makes it impossible to reliably track the history of a ticket.

How we fixed it: We made the class strictly immutable. We marked every single field as private final, meaning they can only be assigned once. We completely deleted all the setter methods. Once an IncidentTicket is created, it is locked forever.

2. The Problem: The "List Leak"
What was wrong: Even without setters, the original class returned its internal tags list directly. If someone called ticket.getTags().add("HACKED"), it modified the list inside the ticket, breaking our immutability rule.

How we fixed it: We used defensive copying. When the constructor assigns the list, it wraps it like this: this.tags = Collections.unmodifiableList(new ArrayList<>(builder.tags));. Now, if anyone tries to add or remove a tag from the outside, Java throws an error immediately.

3. The Problem: Scattered Validation
What was wrong: The rules for what makes a valid ticket (like checking for a valid email or making sure the title isn't blank) were scattered inside the TicketService. If someone created a ticket somewhere else in the app, they could accidentally bypass those checks and create corrupt data.

How we fixed it: We made the Builder's build() method the ultimate gatekeeper. We moved all the Validation.require... methods directly into the build() step. Now, it is literally impossible for the system to create an invalid IncidentTicket. If the data is bad, the Builder stops it before the object even exists.

4. The Problem: Messy Object Creation
What was wrong: The original class had tons of parameters—some required, some optional. Passing 10 different variables into a standard constructor makes it incredibly easy to put the wrong variable in the wrong spot (like putting the assigneeEmail in the reporterEmail slot).

How we fixed it: We created a public static class Builder. This gave us a fluent, step-by-step way to construct the object (e.g., .id("123").title("Broken PC").build()). It makes the code highly readable and prevents parameter mix-ups.

5. The Problem: How to Update an Immutable Object?
What was wrong: Since we deleted all the setters and made the fields final, we couldn't change the priority of a ticket to "CRITICAL" when we needed to escalate it.

How we fixed it: We built the toBuilder() method. Instead of modifying the existing ticket, toBuilder() creates a carbon copy of the ticket's data inside a fresh Builder. We apply our updates (like .priority("CRITICAL")) to that new Builder, and then stamp out a brand new, updated IncidentTicket. The original remains untouched and safe.
