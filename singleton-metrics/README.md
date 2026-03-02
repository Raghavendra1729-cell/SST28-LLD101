Exercise A — Singleton Refactoring (Metrics Registry)
----------------------------------------------------
Narrative
A CLI tool called **PulseMeter** collects runtime metrics (counters) and exposes them globally
so any part of the app can increment counters like `REQUESTS_TOTAL`, `DB_ERRORS`, etc.

The current implementation is **not a real singleton**, **not thread-safe**, and is vulnerable to
**reflection** and **serialization** breaking the singleton guarantee.

Your job is to refactor it into a **proper, thread-safe, lazy-initialized Singleton**.

What you have (Starter)
- `MetricsRegistry` is *intended* to be global, but:
  - `getInstance()` can return different objects under concurrency.
  - The constructor is not private.
  - Reflection can create multiple instances.
  - Serialization/deserialization can produce a new instance.
- `MetricsLoader` incorrectly uses `new MetricsRegistry()`.

Tasks
1) Make `MetricsRegistry` a proper, **thread-safe singleton**
   - **Lazy initialization**
   - **Private constructor**
   - Thread safety: pick one approach (recommended: static holder or double-checked locking)

2) Block reflection-based multiple construction
   - If the constructor is called when an instance already exists, throw an exception
   - (Hint: use a static flag/instance check inside the constructor)

3) Preserve singleton on serialization
   - Implement `readResolve()` so deserialization returns the same singleton instance

4) Update `MetricsLoader` to use the singleton
   - No `new MetricsRegistry()` anywhere in code

Acceptance
- Single instance across threads within a JVM run.
- Reflection cannot construct a second instance.
- Deserialization returns the same instance.
- Loading metrics from `metrics.properties` works.
- Values are accessible via:
  - `increment(key)`
  - `getCount(key)`
  - `getAll()`

Build/Run (Starter)
  cd singleton-metrics/src
  javac com/example/metrics/*.java
  java com.example.metrics.App

Useful Demo Commands (after you fix it)
- Concurrency check:
  java com.example.metrics.ConcurrencyCheck
- Reflection attack check:
  java com.example.metrics.ReflectionAttack
- Serialization check:
  java com.example.metrics.SerializationCheck

Note
This starter is intentionally broken. Some of these checks will "succeed" in breaking the singleton
until you fix the implementation.

Summary:
Problem 1: Anyone could create new instances
The Problem: The MetricsRegistry class had a regular constructor, meaning other classes (like MetricsLoader) could just write new MetricsRegistry() and create as many instances as they wanted.

The Solution: We made the constructor private. We also went into MetricsLoader.java and changed it to use MetricsRegistry.getInstance() instead of the new keyword.

Problem 2: It was not Thread-Safe (Concurrency Issue)
The Problem: If 80 threads called getInstance() at the exact same millisecond, the basic if (INSTANCE == null) check would let multiple threads slip through and create multiple separate instances.

The Solution: We implemented Double-Checked Locking.

We added the volatile keyword to the INSTANCE variable so all threads see its memory state instantly.

We added a synchronized (MetricsRegistry.class) block inside getInstance() with a second null check. This forces threads to line up and guarantees only the first thread creates the object.

Problem 3: Vulnerable to Reflection Attacks
The Problem: Advanced Java code (Reflection) can bypass the private keyword using constructor.setAccessible(true) to force the creation of a second object.

The Solution: We built a trap inside the private constructor. We added if (INSTANCE != null) throw new IllegalStateException(...). The first time the object is created, it passes. If reflection tries to force a second creation, it hits the trap and crashes.

Problem 4: Vulnerable to Serialization
The Problem: When Java saves an object to bytes and reads it back (deserialization), it automatically creates a brand-new object in memory, bypassing your private constructor completely.

The Solution: We added the @Serial protected Object readResolve() method. This is a special hidden method in Java. When Java finishes building the unauthorized clone from the byte data, this method tells Java to throw the clone in the trash and return the true getInstance() instead.