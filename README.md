# Space Visualization & Simulation

**Author:** Zdeněk Vavřička    
**Date:** May 2022    
**Course:** Úvod do počítačové grafiky (**KIV/UPG**)    

---

## Overview
This project is a dynamic 2D visualization of a space simulation based on N-body physics. The application simulates the gravitational interaction between various celestial objects—such as planets, comets, and rockets—and visualizes their movement, trajectories, and physical properties in real-time.

## Physics & Simulation
The core of the application is a physics engine implementing **Newton's law of universal gravitation**. 

- **N-Body Simulation:** The acceleration of each object is calculated based on the mass and distance of all other objects in the system.
- **Integration Algorithm:** To ensure high accuracy, the simulation uses a multi-step velocity and position update (leapfrog integration), calculating positions at the "midpoint" of the time step $\Delta t$.
- **Collisions:** When two objects collide, they merge into a single object, conserving both mass and momentum while maintaining a consistent density.



## Features
- **Dynamic Animation:** Real-time simulation where 1 second of real time corresponds to a predefined simulation step.
- **Interactive GUI:** - Scale-independent visualization that fills the window while maintaining the aspect ratio.
    - The "center of the universe" is always anchored to the center of the application window.
- **Velocity Graphs:** Real-time plotting of object velocity (converted to $km/h$) over the last 30 seconds of simulation.
- **Trajectories:** Visual paths showing the movement of objects, which gradually fade over time.
- **Data Driven:** All simulation parameters (G constant, time step) and initial object states are loaded from UTF-8 encoded CSV files.

## Project Structure & Input
The application expects a CSV file with the following structure:
1. **Header:** Gravitational constant $G$, Time step $\Delta t$.
2. **Data rows:** `Name, Type, Pos X, Pos Y, Vel X, Vel Y, Mass`.

## Compilation and Usage
The application is launched via command-line script:

```bash
Run.cmd path/to/data.csv
