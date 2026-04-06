# Pluggable Rate Limiting System

## Problem Overview
In this assignment, I designed a rate limiting system for external resource usage.

Rate limiting is not applied on every incoming API request.
It is applied only when the internal service is about to call the paid external resource.


## Design
I kept the design simple.

- `RateLimiter`
  - Main class used by internal service
  - Stores rules and calls the selected algorithm

- `RateLimiterAlgorithm`
  - Interface for pluggable algorithms

- `FixedWindowAlgorithm`
  - Fixed window counter implementation

- `SlidingWindowAlgorithm`
  - Sliding window counter implementation

- `RateLimitRule`
  - Stores one rule like `5 requests in 60 seconds`

- `InternalService`
  - Runs business logic
  - Calls rate limiter only before external call

- `Main`
  - Shows sample usage
  - Shows switching algorithm without changing business logic

## Class Responsibilities
- `Main`
  - Creates rules
  - Creates rate limiter with different algorithms
  - Runs sample requests

- `InternalService`
  - Simulates business logic
  - Calls external resource only if rate limiter allows

- `RateLimiter`
  - Accepts key and current time
  - Delegates checking to selected algorithm

- `RateLimiterAlgorithm`
  - Common contract for all algorithms

- `FixedWindowAlgorithm`
  - Maintains count per fixed window

- `SlidingWindowAlgorithm`
  - Maintains current and previous window counts

- `RateLimitRule`
  - Represents limit and window size

## Flow
1. Client request comes to API
2. API calls internal service
3. Internal service runs business logic
4. If external call is not needed, no rate limit check is done
5. If external call is needed, rate limiter is checked
6. If allowed, external resource is called
7. If denied, request is handled gracefully

## Why I Chose This Design
- I used one `RateLimiter` class so the internal service has a simple object to call
- I kept the algorithm as an interface so future algorithms can be plugged in easily
- I kept rules in a separate class so multiple limits can be configured together
- I used simple integer time values so the code stays basic and easy to test

## Trade-offs

### Fixed Window Counter
- Easy to implement
- Uses less memory
- Can allow burst traffic at window boundaries

### Sliding Window Counter
- Better than fixed window at boundaries
- Gives smoother rate limiting
- Slightly more complex

## Thread Safety
- `ConcurrentHashMap` is used for storing counters
- Per-key locking is used so concurrent updates are safe

## Extensibility
If I want to add another algorithm like Token Bucket or Leaky Bucket:
1. Create a new class implementing `RateLimiterAlgorithm`
2. Write the logic there
3. Pass that algorithm to `RateLimiter`

No change is needed in `InternalService`.

