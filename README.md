# 🎮 Arkanoid Game - Object-Oriented Programming Project

## 👥 Author
**Group [Số nhóm]** – Class [Mã lớp]  

- [Họ tên 1] – [MSSV 1]  
- [Họ tên 2] – [MSSV 2]  
- [Họ tên 3] – [MSSV 3]  
- [Họ tên 4] – [MSSV 4]  

**Instructor:** [Tên giảng viên]  
**Semester:** [HK1/HK2 – Năm học]

---

## 🧩 Description
This is a classic **Arkanoid** game developed in **Java** as a final project for the *Object-Oriented Programming* course.  
The project demonstrates the implementation of **OOP principles** and **design patterns**.

### ✨ Key Features
- Developed using **Java 17+** with **JavaFX** for GUI  
- Implements **Encapsulation**, **Inheritance**, **Polymorphism**, and **Abstraction**  
- Applies multiple **Design Patterns**: *Singleton*, *Factory Method*, *Strategy*, *Observer*, and *State*  
- Features **multithreading** for smooth gameplay and responsive UI  
- Includes **sound effects**, **animations**, and **power-up systems**  
- Supports **save/load game** functionality and a **leaderboard system**

---

## 🕹️ Game Mechanics
- Control a paddle to bounce the ball and destroy bricks  
- Collect power-ups for special abilities  
- Progress through multiple levels with increasing difficulty  
- Score points and compete on the leaderboard  

---

## 🧱 UML Diagram
### 📘 Class Diagram
You can use **IntelliJ IDEA** to generate UML diagrams:  
🔗 [Tutorial](https://www.youtube.com/watch?v=yCkTqNxZkbY)

Full diagrams are located in:  
📁 `docs/uml/`

---

## 🧠 Design Patterns Implementation
### 1. **Singleton Pattern**
**Used in:** `GameManager`, `AudioManager`, `ResourceLoader`  
**Purpose:** Ensure only one instance exists throughout the application.

(You can add more sections like *Factory Method*, *Strategy*, etc.)

---

## ⚙️ Multithreading Implementation
The game uses multiple threads for performance:

| Thread | Purpose |
|--------|----------|
| Game Loop Thread | Updates logic at 60 FPS |
| Rendering Thread | Handles graphics on JavaFX Application Thread |
| Audio Thread Pool | Plays sound effects asynchronously |
| I/O Thread | Manages save/load without blocking UI |

---

## 💻 Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/[username]/ArkanoidGame.git
