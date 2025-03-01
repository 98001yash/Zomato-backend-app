# Zomato App Backend

The **Zomato App Backend** is a RESTful API built to support the **Zomato** app, which provides services like restaurant searches, food delivery, and user reviews. The backend is designed to handle the core functionalities of the app, including managing users, restaurants, orders, and reviews.

### Features:
- **User Management:** Register, authenticate, and manage user profiles.
- **Restaurant Management:** Display restaurant details, menus, and filters for cuisine types.
- **Order Management:** Handle user orders, cart management, and delivery status.
- **Reviews & Ratings:** Allow users to leave reviews and rate restaurants.
- **Search & Filtering:** Efficient search functionality to find restaurants based on location, cuisine, or ratings.

### Tech Stack:
- **Backend Framework:** Spring Boot
- **Database:** PostgreSQL (for storing user, restaurant, and order data)
- **Authentication:** JWT (JSON Web Token) for secure user authentication
- **API Documentation:** Swagger for API documentation
- **Caching:** Redis for caching popular restaurant data
- **Others:** JPA/Hibernate, Spring Security, Spring Data

### Setup:
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/zomato-backend.git
