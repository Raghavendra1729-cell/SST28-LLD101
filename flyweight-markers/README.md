Flyweight — Deduplicate Map Marker Styles (Refactoring)
------------------------------------------------------
Narrative (Current Code)
A CLI tool called **GeoDash** renders a large list of map markers (pins).
Right now, every `MapMarker` stores its own style fields (shape, color, size, filled).
When rendering thousands of markers, we end up creating thousands of duplicate style objects → memory blow-up.

Your Task
1) Extract an immutable `MarkerStyle` (shape, color, size, filled) as **intrinsic state**.
2) Implement `MarkerStyleFactory` that caches and returns shared `MarkerStyle` instances by key.
3) Modify `MapMarker` to hold:
   - `MarkerStyle` (intrinsic)
   - marker-specific fields (extrinsic): `lat`, `lng`, `label`
4) Update `MapDataSource` (marker creation pipeline) to obtain styles via the factory
   (no `new MarkerStyle(...)` during marker creation).

Acceptance Criteria
- Same rendering “cost” as before (same number of markers rendered, same output format).
- Identical style configurations reuse the same `MarkerStyle` instance
  (see `QuickCheck` — it should report a small number of unique styles).
- `MarkerStyle` is immutable (all fields final, no setters).
- `MapMarker` stores only extrinsic state plus a reference to shared `MarkerStyle`.

Hints
- Use a `Map<String, MarkerStyle>` cache in the factory.
- Key suggestion: `"PIN|RED|12|F"` (shape|color|size|filledFlag)

Build & Run
  cd flyweight-markers/src
  javac com/example/map/*.java
  java com.example.map.App

Repo intent
This is a **refactoring assignment**: the starter code is intentionally wasteful.
Students should refactor to Flyweight without changing the external behavior.



The original structure had every MapMarker creating and owning its own MarkerStyle object, which meant thousands of duplicate style objects were stored in memory. This led to a massive memory blow-up when rendering a large number of markers with identical visual properties. We fixed this using the Flyweight pattern by extracting the style into a separate immutable class and using a MarkerStyleFactory to cache and share a single instance for each unique combination of style fields.

In this refactored design, MarkerStyle contains the intrinsic state (shared data like shape, color, size, and filled status), while MapMarker holds the extrinsic state (unique data like latitude, longitude, and label). We implemented this by modifying MapMarker to store a reference to a shared MarkerStyle object rather than creating its own. The MapDataSource now obtains these shared styles from the factory, ensuring that even with 30,000 markers, only a few dozen unique style objects exist in memory.