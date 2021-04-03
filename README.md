# Jewelry Rent System

Documents management system in Java (Spring framework) and Angular

## Build and run

### Application
- Start application with Docker: `docker-compose up -d`

### Data
- Restore data from backup: `docker exec -i jrs-database mysql -ujrs-server -p12345 < ./database/initial_data.sql`

### Usage
- User interface: http://localhost:4200
- Database management: http://localhost:4201