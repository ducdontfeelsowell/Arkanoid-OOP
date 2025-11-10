# Arkanoid Game - Object-Oriented Programming Project

## Author
**Group [??]** – Class [INT2204 - 1]  

- [Hoàng Văn Thắng] – [24020307]  
- [Phí Dương Đạt] – [24020064]  
- [Vũ Tiến Mạnh] – [24020226]  
- [Vũ Gia Anh Đức] – [24020082]  

**Instructor:** [Kiều Văn Tuyên]  
**Semester:** [HK1 – 2025 - 2026]

---

## Description
This is a classic **Arkanoid** game developed in **Java** as a final project for the *Object-Oriented Programming* course.  
The project demonstrates the implementation of **OOP principles** and **design patterns**.

### Key Features
- Developed using **Java 17+** with **JavaFX** for GUI  
- Implements **Encapsulation**, **Inheritance**, **Polymorphism**, and **Abstraction**  
- Applies some **Design Patterns**: *Singleton*, *Factory Method* 
- Features **multithreading** for smooth gameplay and responsive UI  
- Includes **sound effects**, **animations**, and **power-up systems**  
- Supports **save/load game** functionality and a **scoreboard system**

---

## Game Mechanics
- Control a paddle to bounce the ball and destroy bricks  
- Collect power-ups for special abilities  
- Progress through multiple levels with increasing difficulty  
- Score points and compete on the leaderboard  

---

## UML Diagram
### Class Diagram
You can use **IntelliJ IDEA** to generate UML diagrams:  
[Tutorial](https://www.youtube.com/watch?v=yCkTqNxZkbY)

Full diagrams are located in:  
`docs/uml/`

---

## Design Patterns Implementation
### 1. **Singleton Pattern**
**Used in:** `SoundManager`  
**Purpose:** Ensure only one instance exists throughout the application.
### 2. **Factory Method**
**Used in:** `Brick`
**Purpose:** Creating different types of brick

---

## Multithreading Implementation
The game uses multiple threads for performance:

| Thread | Purpose |
|--------|----------|
| Game Logic Thread | Updates logic at 60 UPS |
| Rendering Thread | Handles graphics on JavaFX Application Thread |

---

## Installation

1. Clone the project from the repository.
2. Open the project in your preferred IDE.
3. Run the project.

---

## Usage

### Controls

| Key       | Action                        |
|-----------|-------------------------------|
| ← or A    | Move paddle left              |
| → or D    | Move paddle right             |
| SPACE     | Launch ball / Shoot laser     |
| ESC       | Pause game                    |

### How to Play

1. **Start the game:** Click "Play" from the main menu, select level and difficulty.  
2. **Control the paddle:** Use arrow keys to move left and right.  
3. **Launch the ball:** Press SPACE to launch the ball from the paddle.  
4. **Destroy bricks:** Bounce the ball to hit and destroy bricks.  
5. **Collect power-ups:** Catch falling power-ups for special abilities.  
6. **Avoid losing the ball:** Keep the ball from falling below the paddle.  
7. **Complete the level:** Destroy all destructible bricks to advance.  

---

## Power-ups

| Icon | Name           | Effect                                         |
|------|----------------|------------------------------------------------|
| 🟦   | Expand Paddle   | Increases paddle width                        |
| 🟥   | Shrink Paddle   | Decreases paddle width                        |
| 🐌   | Slow Ball       | Decreases ball speed by 50%                   |
| 🎯   | Multi Ball      | Spawns 2 additional balls                     |
| 🔫   | Laser Gun       | Shoot lasers to destroy bricks                |

---

## Scoring System

- **Normal Brick:** 50 points  
- **Strong Brick:** 100 points  
- **Explosive Brick:** 50 points + nearby bricks 

---

## Demo

### Screenshots

#### Main Menu
![Main Menu](docs/screenshots/main_menu.png)

#### Gameplay
![Gameplay](docs/screenshots/gameplay.png)

#### Power-ups in Action
![Power-ups](docs/screenshots/powerups.png)

#### Leaderboard
![Leaderboard](docs/screenshots/leaderboard.png)

### Video Demo
Full gameplay video is available at:  
`docs/demo/gameplay.mp4`

---

## Future Improvements

### Planned Features

**Additional game modes**
- Endless mode

**Enhanced gameplay**
- Boss battles at end of worlds  
- More power-up varieties (freeze time, shield wall, etc.)  
- Achievements system  

**Technical improvements**
what
- Migrate to LibGDX or JavaFX for better graphics  
- Add particle effects and advanced animations  
- Implement AI opponent mode  
- Add online leaderboard with database backend  

---

## Technologies Used

| Technology | Version  | Purpose               |
|------------|---------|----------------------|
| Java       | 17+     | Core language         |
| JavaFX     | 19.0.2  | GUI framework         |
| Maven      | 3.9+    | Build tool            |

---

## License

This project is developed for educational purposes only.  

**Academic Integrity:** This code is provided as a reference. Please follow your institution's academic integrity policies.

**Notes:**  
- The game was developed as part of the Object-Oriented Programming with Java course curriculum.  
- All code is written by group members with guidance from the instructor.  
- Some assets (images, sounds) may be used for educational purposes under fair use.  
- The project demonstrates practical application of OOP concepts and design patterns.  

**Last updated:** [10/11/2025]
