# Distributed Cache - LLD

This is a simple in-memory distributed cache design in Java.

It supports:
- `get(key)`
- `put(key, value)`

I used multiple cache nodes, and each node stores data in a `HashMap`.
To decide which node should store a key, I used modulo-based distribution.
For eviction, I used LRU.

## Assumption

I assumed that when `put(key, value)` is called, the database is also updated.
I used a simple in-memory database class just for demonstration.

## How data is distributed

When a key comes, the system calculates:

`hash(key) % numberOfNodes`

Based on this result, the key is stored in one cache node.
So the same key always goes to the same node.

## How cache miss is handled

When `get(key)` is called:
- first find the correct node
- check if key is present in that node
- if present, return value
- if not present, fetch value from database
- store that value in cache
- return the value

## How eviction works

Each cache node has fixed capacity.

If the node is full and a new key is inserted, one old key is removed.
For this assignment I used LRU.

LRU means:
- the least recently used key is removed first
- whenever a key is accessed or inserted, it becomes recently used

I implemented LRU using:
- `HashMap`
- custom doubly linked list

This makes access and update efficient.

## Why the design is extensible

I kept two small interfaces:
- `DistributionStrategy`
- `EvictionPolicy`

Because of this:
- distribution can be changed later to consistent hashing or map-based routing
- eviction can be changed later to LFU or MRU

## Main classes

- `DistributedCache` : main class used by client
- `CacheNode` : one cache node
- `DistributionStrategy` : decides which node will store a key
- `ModuloDistributionStrategy` : current distribution logic
- `EvictionPolicy` : decides which key to remove
- `LRUEvictionPolicy` : current eviction logic
- `Database` : database interface
- `InMemoryDatabase` : simple mock database
