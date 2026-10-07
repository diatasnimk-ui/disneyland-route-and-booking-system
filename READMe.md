# Disneyland Paris Route Finder & Booking System

A console-based Java application for planning a day-trip at Disneyland Paris. It finds the shortest walking route between attractions using **Dijkstra's algorithm**, and manages visitors, bookings, tickets and reviews in a custom **SQLite** database.

![Java](https://img.shields.io/badge/Java-8%2B-orange) ![SQLite](https://img.shields.io/badge/Database-SQLite-blue) ![Status](https://img.shields.io/badge/Status-Complete-green)

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Screenshots](#screenshots)
- [How Route Finding Works](#how-route-finding-works)
- [Database Design](#database-design)
- [Database in Action](#database-in-action)
- [Program Design (UML)](#program-design-uml)
- [Getting Started](#getting-started)
- [Future Improvements](#future-improvements)

## Features

**Route finding**
- Finds the shortest route between any two attractions
- Uses ride numbers, so it works alongside the park map visitors are given

**Bookings**
- Create, update and delete bookings

**Visitors**
- View and update visitor information

**Attractions & tickets**
- View all attractions and ticket information

**Reviews**
- Add and view reviews of attractions

## Tech Stack

| Area | Tool |
|---|---|
| Language | Java |
| Database | SQLite (via JDBC) |
| Algorithm | Dijkstra's shortest path |
| Design | OOP, UML (PlantUML) |
| IDE | IntelliJ IDEA |
| Database viewer | SQLite Studio |

## Screenshots

### Main menu
<img width="735" height="309" alt="Main menu" src="https://github.com/user-attachments/assets/eef9df35-e28c-4354-8160-61ef95dcd54b" />

### Route finding
This assumes visitors are given a map with ride numbers on it, so they enter ride numbers to get their route.
Route consists of list of rides to go through as reference points.

<img width="422" height="229" alt="Route finding output" src="https://github.com/user-attachments/assets/4e8252f5-afc8-4215-9e6e-dab99933e8f2" />

## How Route Finding Works

The park is modeled as a weighted graph: each attraction is a node and each walkable path between attractions is an edge weighted by distance in meters. Dijkstra's algorithm then finds the lowest-cost path from the start attraction to the destination.
The whole graph is stored as hash table of adjacency lists where the keys are the attraction number/node for graph and the values hold an array list of the attraction node the key is connected to.
The array list themselves are stored as linked lists where each element stores the distance from parent node (as edge weight) and pointer to next element.

When a visitor enters a start and end ride number, Dijkstra's algorithm finds the lowest-cost path between them.
It repeatedly picks the unvisited attraction with the smallest known distance and updates its neighbours' distances, until it reaches the destination.
For the routing part, priority queue is used to select the next closest attraction efficiently.

**Why an adjacency list?** A theme park map is sparse, because each attraction only connects to a few nearby ones. 
An adjacency list only stores the connections that exist, so it uses less memory than an adjacency matrix, and the hash map gives fast lookup of any attraction's neighbours.

## Database Design

<img width="786" height="410" alt="Database diagram" src="https://github.com/user-attachments/assets/72da07a3-9e0d-4bf5-9222-e0bd381b2b45" />

- Visitor table stores personal details of the person making the booking; unique identifier is also stored in Bookings table
-   Unique identifier is also stored in Review table for credible reviews 
- Booking table stores details of the type of booking/day trip package, type of tickets differentiated in passes for queue and date
- Purchase table linked to Booking table stores amount, method and purchase date (as form of receipt)
- Review table stores ratings and comments as well date of the review creation to be displayed to users upon request; also contains attraction ID if visitors want reviews of specific attraction before planning their day
- Attraction table stores individual description of the rides, type of ride (family friendly or thrilling) and duration. The unique ID is also used in Route table which is not displayed to user but used by the program.
- Tickets table just holds information about type and price, only to be displayed to user upon request.

## Database in Action

Each table is shown before and after an operation in the app.

### Booking table
| Before | After |
|---|---|
| <img width="100%" alt="Booking table before" src="https://github.com/user-attachments/assets/30abdf51-75cf-4d82-bcad-0544a98455b4"/> | <img width="100%" alt="Booking table after" src="https://github.com/user-attachments/assets/a381c57e-a609-450f-88a1-d4bf932479a4" /> |

### Visitor table
| Before | After |
|---|---|
| <img width="100%" alt="Visitor table before" src="https://github.com/user-attachments/assets/8dbdb9cb-80ec-4d40-92ed-62069e9bb050" /> | <img width="100%" alt="Visitor table after" src="https://github.com/user-attachments/assets/dfd03da1-a4f3-48f2-b56a-63944e16195b" /> |

### Purchase table
| Before | After |
|---|---|
| <img width="100%" alt="Purchase table before" src="https://github.com/user-attachments/assets/49e17c49-11b5-44de-92e3-da249da1deac" /> | <img width="100%" alt="Purchase table after" src="https://github.com/user-attachments/assets/f976c92f-e5b8-40f6-955a-801f734ee813" /> |

## Program Design (UML)

<img width="884" height="539" alt="UML class diagram" src="https://github.com/user-attachments/assets/26b39ac0-e089-461e-90d2-638d262574e9" />

## Running the Project

This project was built in **IntelliJ IDEA**, so that is the easiest way to run it.
Requires Java 8+ and the SQLite JDBC driver. 
Open the project in any Java IDE, add the driver `.jar` to the project libraries, and run `DatabaseConnect.java`.
 

## Future Improvements
- Add a GUI or web front end
- Support multi-stop routes (e.g. visit 5 rides in the shortest order)
- Factor in live queue times when choosing routes
- Secure storage of sensitive visitor data: encrypt personal details at rest, and handle payments through a payment provider instead of storing general details

## Author
Built by Diatasnim Khan as a personal project for coursework.

## Disclaimer
This is an unofficial student project and is not affiliated with or endorsed by Disney or Disneyland Paris. All trademarks belong to their respective owners.
