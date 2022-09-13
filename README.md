# Robot

## Functionality

As for the basic project but with multiple rooms and robots within each room

- Multiple users
- Caching
- GraphQL endpoint
- Deployment with Docker
- Deployment to AWS
- CI/CD Pipeline
- Use Terraform for infrastructure

REST Endpoints

- GET /rooms
    - returns a list of all available rooms for the user
- POST /rooms
    - creates a new room
    - fields
        - length
        - width
        - unique name?
- PUT /rooms/{id}
    - updates an existing room
    - fields
        - length
        - width
- DELETE /rooms/{id}
    - deletes an existing room
- GET /rooms/{id}/robots
    - gets all the robots in the room
- POST /rooms/{id}/robots
    - creates a new robot
    - fields
        - x
        - y
        - orientation
        - unique name
- PUT /rooms/{id}/robots/{id}
    - gives instructions to the specified robot
    - fields
        - instruction list
- DELETE /rooms/{id}/robots/{id}
    - deletes the specified robot
