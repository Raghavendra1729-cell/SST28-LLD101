# Adapter — Payments (Refactoring)

## Narrative (Current Code)
OrderService directly depends on two mismatched SDKs (`FastPayClient`, `SafeCashClient`), uses a string `provider` switch, and duplicates glue logic.

## Your Task
Introduce an **Adapter** so `OrderService` depends only on a `PaymentGateway` interface. Create:
- `PaymentGateway` (target interface): `String charge(String customerId, int amountCents)`
- `FastPayAdapter` and `SafeCashAdapter` mapping to their respective SDKs
- A simple map-based registry in `App` to select the gateway

Refactor `OrderService` to accept a `PaymentGateway` and remove provider branching.

## Acceptance Criteria
- `OrderService` calls **only** `PaymentGateway`
- Adding a new provider requires no change to `OrderService`
- Running `App` prints transaction IDs for both providers

## Hints
- Use constructor injection or a `Map<String, PaymentGateway>`
- Keep adapters stateless
- Use `Objects.requireNonNull` to validate inputs

## Build & Run
```bash
cd adapter-payments/src
javac com/example/payments/*.java
java com.example.payments.App
```



1. The Flow (How it should work)
The OrderService handles checkouts. It wants to be lazy and treat every payment provider exactly the same way.

It expects to use a standard PaymentGateway interface with one simple method: charge(customerId, amount).

2. The Problem (Mismatched Plugs)
The third-party payment SDKs don't fit the interface!

FastPay uses a method called payNow().

SafeCash requires a weird two-step process: createPayment() followed by confirm().

Because they don't implement PaymentGateway, the OrderService crashes trying to use them, and Java won't even let you put them in the registry Map.

3. The Solution (Adapter Pattern)
The Translators: We created two "wrapper" classes (FastPayAdapter and SafeCashAdapter).

The Fit: Both adapters implement the PaymentGateway interface, so the OrderService is happy to use them.

The Translation: When OrderService calls the standard .charge() method, the adapter intercepts it and translates it into the messy, specific SDK calls (like .payNow() or .createPayment().confirm()) behind the scenes.
