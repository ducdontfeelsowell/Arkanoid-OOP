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
| 🔫   | Laser Gun       | Shoots lasers to destroy bricks               |
|  🕸   | Safety Net      | Prevents ball from dropping out              |
|❤️|Extra Life| Grants +1 life|
|🔴|Multi-Ball| x3 current balls|
|✪|Coin| Grants coins|

---

## Scoring System

- **Normal Brick:** 50 points  
- **Strong Brick:** 100 points  
- **Explosive Brick:** 50 points + nearby bricks 

---

## Demo

### Screenshots

#### Login
![login](https://github.com/user-attachments/assets/8c46b5d7-37d5-40c3-81bc-83ea12ffcea6)

#### Main Menu
![arkanoid](https://github.com/user-attachments/assets/c95c4c03-561c-4699-9428-b93268042fb4)

#### Map
![map2](https://github.com/user-attachments/assets/8163c4ee-cb26-466c-ad09-a83a83ae7e87)
![map](https://github.com/user-attachments/assets/1b925cdd-b5ea-4e07-a1b6-247741eff613)

#### Power-ups in Action
![powerup](https://github.com/user-attachments/assets/f0780de6-4612-4a8e-a00b-127826247ded)

#### Scoreboard
![scoreboard](https://github.com/user-attachments/assets/ae95765a-8b0b-4438-9ee7-62cc68ba202c)

#### Help
![help](https://github.com/user-attachments/assets/2a683ac6-1f03-4a38-9c84-b451c32b9dad)

#### Setting
![setting](https://github.com/user-attachments/assets/f0982415-532c-4a32-9d26-76a0c58d3f09)

#### Shop
![shop](https://github.com/user-attachments/assets/bb40ac1c-56eb-45d2-b848-b19b87b13215)

#### Demo
Found in:
demo/demogame.mp4

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
