# Pet-Adoption-App-Tin-Puja
An app where admins can list pets for adoption, and users can adopt those pets

Steps to get the application up and running
1. In the project directory execute command "docker compose up -d", the -d flag means detached, so it will not occupy your terminal instance. This runs a postresql container as well as a pgadmin container
2. We need to create a db in our PostgreSQL container
   1. Start a psql session in the container<br>
docker exec -it postgres_container psql -U admin
   2. Create the pets database in the psql session
      CREATE DATABASE pets;
3. After finished, to clean up docker containers on your system use command <br>
   docker rm $(docker ps -a -q)


