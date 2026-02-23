# 🏢 Facility Logistics & Management Engine
**A pure Java CLI architecture for facility operations and resource allocation.**

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![CLI](https://img.shields.io/badge/CLI-Terminal-4D4D4D?style=for-the-badge)

This is a comprehensive, console-based facility management system built entirely with core Java (JDK 8+). It was engineered to demonstrate modular backend design, custom data persistence, and complex algorithmic constraints without relying on external databases or GUI frameworks.

---

## 🚀 Core Architecture & Sub-Systems

* **Algorithmic Resource Allocation:** Dynamic entity onboarding with strict, attribute-based routing constraints (e.g., automated gender-based sector separation).
* **Integrated POS (Point of Sale):** A functioning digital canteen sub-system that tracks itemized orders and aggregates total running costs dynamically.
* **Automated Invoice Generation:** Transactional logic that compiles entity data, room allocations, and POS totals into automated, formatted receipt outputs.
* **Persistent State Management:** Custom File I/O architecture utilizing local `.txt` storage to maintain system state and entity records across execution cycles.

## 🧠 Technical Implementation

- **Object-Oriented Design:** Highly modular codebase separating facility constraints, the POS engine, and file handling into distinct, manageable classes.
- **Search & Retrieval:** Custom query functions to retrieve entity records via unique ID or string matching.
- **Dependency-Free:** Built strictly using the Java Standard Library to ensure maximum portability and core language mastery.

## 🖥️ Execution Guide

### Prerequisites
- Java JDK 8 or higher

### Initialization
1. Clone the repository:
   ```bash
   git clone [https://github.com/RudraS-Chauhan/HostelManager.git](https://github.com/RudraS-Chauhan/HostelManager.git)
