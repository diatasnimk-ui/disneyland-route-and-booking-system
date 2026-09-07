# Disneyland-route-and-booking-system
Console-based Disneyland Paris route finder &amp; reservation system in Java, using Dijkstra's algorithm and a custom SQLite database (visitors, bookings, tickets, reviews).

Features:
- Finds the shortest route between attractions 
- Make/update/delete bookings
- View/update visitor info
- View attractions and ticket info
- Add and view reviews

  MENU SCREENSHOT:
<img width="735" height="309" alt="Screenshot 2026-09-04 211159" src="https://github.com/user-attachments/assets/eef9df35-e28c-4354-8160-61ef95dcd54b" />



ROUTE FINDING (This is assuming the visitors are given a map with ride numbers on it):

<img width="422" height="229" alt="image" src="https://github.com/user-attachments/assets/4e8252f5-afc8-4215-9e6e-dab99933e8f2" />


DATABASE DESIGN:

<img width="786" height="410" alt="DisneyDBDiagram" src="https://github.com/user-attachments/assets/72da07a3-9e0d-4bf5-9222-e0bd381b2b45" />



DATABASE AT WORK: <br>

Booking table <br>
| **before**                 |                         **after** | <br>

<img width="45%" height="50%" alt="Screenshot 2026-09-07 125726" src="https://github.com/user-attachments/assets/30abdf51-75cf-4d82-bcad-0544a98455b4"/> | <img width="45%" height="65%" alt="image" src="https://github.com/user-attachments/assets/a381c57e-a609-450f-88a1-d4bf932479a4" /> <br>



Visitor table <br>
| **before**                 |                         **after** | <br>

<img width="45%" height="50%" alt="Screenshot 2026-09-07 125833" src="https://github.com/user-attachments/assets/8dbdb9cb-80ec-4d40-92ed-62069e9bb050" />
 | <img width="45%" height="50%" alt="image" src="https://github.com/user-attachments/assets/dfd03da1-a4f3-48f2-b56a-63944e16195b" /> <br>




Purchase table <br>
| **before**                 |                         **after** | <br>

<img width="45%" height="50%" alt="Screenshot 2026-09-07 125930" src="https://github.com/user-attachments/assets/49e17c49-11b5-44de-92e3-da249da1deac" /> | <img width="45%" height="50%" alt="Screenshot 2026-09-07 130655" src="https://github.com/user-attachments/assets/f976c92f-e5b8-40f6-955a-801f734ee813" /> <br>


PROGRAM'S PUML DIAGRAM: <br>

<img width="884" height="539" alt="OOP DIAGRAM FINAL" src="https://github.com/user-attachments/assets/26b39ac0-e089-461e-90d2-638d262574e9" /> <br>

IMPLEMENTATION DETAILS:<br>





 
