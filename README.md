# SpaceX Dragon Rockets Repository

This project is a simple, lightweight library written in pure Java designed to manage SpaceX missions and rockets.

## Business Rules the System Strictly Protects

To prevent data chaos and keep everything consistent, all business logic is encapsulated directly within the domain objects. The domain independently guards the
following rules:

- One mission at a time - A rocket can be assigned to a maximum of one mission at any given time.
- Status reflects reality - A rocket cannot be "in space" (`IN_SPACE`) or "in repair" (`IN_REPAIR`) without an actively assigned mission. Conversely, a
  rocket with an `ON_GROUND` status must have no mission attached to it.
- Mission completion releases gear - When a mission changes its status to `ENDED`, all assigned rockets automatically return to the ground (`ON_GROUND`) and
  are instantly released from that mission.
- Mission status is reactive - A mission doesn't just change its state randomly. Its status (`Scheduled`, `Pending`, `In Progress`) automatically reacts to
  whatever is happening with the rockets assigned to it.

## Architecture – Why this setup?

Instead of taking the easy way out and dumping everything into a single massive service, I chose Clean Architecture combined with a Rich Domain Model.

### 1. Rich Domain Model vs. Anemic Classes

The `Mission` and `Rocket` classes aren't dumb data holders with getters and setters. They are the actual "brains" of the system. If any Use Case tries to
modify a rocket's status in an illegal way, the domain will immediately throw an exception. This makes the application bulletproof.

### 2. Use Cases and Single Level of Abstraction

Every operation has its own dedicated Use Case class.

This gave us incredible scalability. If tomorrow someone decides to turn this into a Spring Boot web app and connect a PostgreSQL database, the domain code and
Use Cases will remain untouched. We would only need to swap out the external infrastructure layer.

### 3. In-Memory Store

As per the guidelines, data is kept in memory using standard Java collections, but the entire mechanism is cleanly hidden behind repository interfaces to keep
things loosely coupled.

### 4. Multi-Layer Validation & Framework Readiness

The exception architecture is built using a cohesive hierarchy under a base `SpaceXManagementException`. This supports a robust, two-tier validation approach:

- First Line of Defense - Validation happens immediately at the system entrance inside the Java Records (Commands) using compact constructors. If data is
  syntactically invalid, a `CommandValidationException` is thrown before it even hits the application flow.
- Second Line of Defense - State transitions are validated directly within the aggregates, which throw a `BusinessValidationException` if any
  business rules are breached.
- Spring Integration Readiness - If integrated into Spring
  Boot, a single `@RestControllerAdvice` listening globally for `SpaceXManagementException` can intercept these errors. It would seamlessly map both validation
  and business issues into consistent, clean HTTP API responses alongside localized error messaging fetched from the `ErrorMessage` enum.

## How I Collaborated with AI

In line with the assignment instructions, here is a transparent breakdown of how I used advanced language models during development. I took a pragmatic
approach: the chat was a fantastic, high-speed assistant, but I retained full control over the architecture and code quality.

### My Role

- Architecture - I defined the overall structure of the project, the split into Use Cases, and the choice to lock the business logic inside the domain.
- Code Review - I actively rejected early, overly simplistic AI suggestions that tried to write "anemic" code (pushing logic into application services) or
  mixed technical infrastructure code with pure business logic.
- TDD Driver - I defined the test cases first and ensured they verified realistic business flows and edge cases.

### AI's Role

- Writing Boilerplate - The chat was perfect for generating records, Mapper objects, and test data factories.
- Refactoring Support - It helped me quickly break down larger technical methods into smaller, readable pieces with a single responsibility.
- Speeding up Assertions - It accelerated the test suite creation by generating repetitive test blocks for various combinations of entity states and
  statuses.

This split allowed me to combine strict adherence to SOLID and Clean Code principles with a very fast delivery pace.

---

P.S. To be honest, AI helped me write this post, too :D