# BANK ACCOUNT MANAGEMENT API
API for bank accounts management

## Endpoints
The endpoints for consume this API
* create new account: POST /accounts
* transfer money between accounts: POST /accounts/{accountNumber}/transfers
* withdraw money from account: POST /accounts/{accountNumber}/withdraws
* deposit money into account: POST /accounts/{accountNumber}/deposits

## Building the application
    docker build --tag bank-account-management -f devops/Dockerfile .
    
## Running the application
Before running the application in Docker, you must run docker compose on `devops/docker-compose.yml`.
This will run all the dependencies for this application to start.

Now you can run the docker image you have built

    docker run -e PORT=8080 \
              -e DB_HOST=localhost \
              -e DB_USER=postgres \
              -e DB_PASSWORD=bankAccountManagementAdmin \
              -e DEFAULT_SCHEMA=donus \
              -p 8080:8080  \
            bank-account-management