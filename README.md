# Card Game - 24 (JavaFX)

CSC311 Advanced Programming – Module 4, Assignment 2

Four random playing cards are dealt. Enter an arithmetic expression that uses each
card's value exactly once and evaluates to **24**.

| Card | Value |
|------|-------|
| Ace | 1 |
| 2–10 | face value |
| Jack | 11 |
| Queen | 12 |
| King | 13 |

Allowed: `+  -  *  /` and parentheses, e.g. `(6+6)*12/6`.

## Features
- **Refresh** – deals four new random cards.
- **Find a Solution** – shows an expression that makes 24, or "No solution".
- **Verify** (or press Enter) – checks that the expression is valid, uses exactly the
  four card values, and equals 24. The result is shown in a dialog box.

## Project structure
```
src/main/java/org/example/csc311_mod4_assigment2/
  Launcher.java             entry point (main method)
  CardGameApplication.java  loads the FXML + CSS and shows the window
  CardGameController.java   button handlers and validation logic
  Card.java                 a playing card (rank + suit)
  Deck.java                 52-card deck, deals random hands
  CardView.java             draws a card with JavaFX controls
  ExpressionEvaluator.java  recursive-descent parser/evaluator
  Solver.java               recursive search for a 24 solution
src/main/resources/org/example/csc311_mod4_assigment2/
  card-game.fxml            UI layout
  styles.css                UI styling
src/test/java/.../GameLogicTest.java  JUnit tests for the evaluator and solver
```

## Running
- IntelliJ: run `Launcher`.
- Command line: `mvnw javafx:run` (tests: `mvnw test`).

Requires JDK 24 (JavaFX 21 is downloaded by Maven).
