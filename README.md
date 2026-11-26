# Darwin World — Evolutionary Simulation (`PO_2025_WT1830_RODZINKA_GAWEDA`)

An interactive evolutionary ecosystem simulation built in Java and JavaFX as a team project for the Object-Oriented Programming course at AGH University of Krakow.

Animals roam a 2D grid map with a central jungle zone, graze on grass to gain energy, reproduce by combining and mutating their directional genomes, and—when energized enough—can charge across multiple tiles in a single step. The GUI lets you configure simulation parameters, control playback, and track live population statistics.

## Tech Stack

- **Java** (Gradle toolchain configured for Java 25)
- **JavaFX 21** (FXML + Canvas rendering)
- **Gradle** (Gradle Wrapper included in `oolav/`)
- **JUnit 5** (unit tests)

## Features

- **Configurable Simulation Setup**: Customize map size, jungle height and grass spawn probability, initial/daily grass count, starting animal population and energy, reproduction thresholds, mutation limits, genome length, and charge mechanics.
- **Real-Time JavaFX Visualization**: Canvas-based map rendering with pause/resume controls and configurable FPS.
- **Live Statistics**: Tracks animal count, grass count, free map slots, average lifespan, average number of children, average energy, and the dominant genome.

## Authors

- **Krzysztof Rodzinka** (`krzysiuro`)
- **Adam Gawęda** (`adamgaweda`)

Built collaboratively as a two-person team project.

## How to Run

The Gradle project is located in the `oolav/` directory.

### Linux / macOS
```bash
cd oolav
./gradlew run
```

### Windows (PowerShell / CMD)
```powershell
cd oolav
.\gradlew.bat run
```

To run the test suite:
```bash
cd oolav
./gradlew test
```
