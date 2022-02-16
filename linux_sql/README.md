# Linux Cluster Monitoring Agent
# Introduction

Linux Cluster Monitoring Agent is a tool that will allow users to collect, monitor, compare and generate reports of system hardware specifications and performance for a group of Linux systems on a server. The gathered hardware configuration and resource usage(collected by bash script and data extraction from each system stored in a centralized database on the server) provides a user with the ability to plan for future resource distribution and utilization. Collected data is stored within a PostgreSQL database on the server to interpret data for resolving issues.

Technologies and softwares used throughout this process include: Docker, Git, PostgreSQL, IntelliJ IDEA , Bash Scripts, Crontab, and SQL Queries.

# Quick Start

- Start a psql instance using psql_docker.sh <br/>
  `./scripts/psql_docker.sh start <username> <password>` <br/>
- Create tables using ddl.sql <br/>
  `psql -h localhost -U postgres -d host_agent -f sql/ddl.sql` <br/>
- Insert hardware specs data into the DB using host_info.sh <br/>
  `./scripts/host_info.sh psql_port db_name psql_user psql_password` <br/>
- Insert hardware usage data into the DB using host_usage.sh <br/>
  `./scripts/host_usage.sh psql_port db_name psql_user psql_password` <br/>
- Crontab setup <br/>
  `* * * * * bash /home/centos/dev/jarvis_data_eng_Shubhkarman/ linux_sql/scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres" "password" > /tmp/psl_docker.log` <br/>

# Implemenation
1. We set up the required environment and directories  for the project
2. Git and Github administrated
3. Install Docker and setup up PSQL instance using Docker
4. Create DDL script for creation of  tables to store hardware specifications and to    resource usage data
5. Implement bash scripts to extract hardware information and usage and store them in the database.
6. 3 Special PSQl queries code to organize data and to detect host failure
## Architecture
![](/home/centos/dev/jarvis_data_eng_Shubhkarman/linux_sql/assets/sql_linux.drawio.png){assets/sql_linux.drawio.png}

## Scripts

### psql_docker.sh
- Discription <br/>
  Creation of PSQL instance by creating a container of postgres:9.6-alpine image using bash. The script must pass through the required parameters for proper functionality <br/>
- Usage <br/>
  The script below will create the docker container which consists of the Postgres database we will be using throughout the project . <br/>
  [db_username]:- Username for database <br/>
  [db_password]:- Password for  database <br/>
  `./scripts/psql_docker.sh create [db_username] [db_password]` <br/>
  The script below will start the container when executed <br/>
  `./scripts/psql_docker.sh start` <br/>
  The scripts below will stop the container when executed. <br/>
  `./scripts/psql_docker.sh stop` <br/>
### host_info.sh
- Discription <br/>
  This bash script will  run once on each node to collect hardware information using various linux usage commands and then transfer the data into database.This script needs to run before host_usage.sh so that node is registered in database. <br/>
- Usage <br/>
  The script below will gather the extracted hardware specifications, process and store the data in the assigned table in the database. <br/>
  psql_host :- Host name <br/>
  psql_port :- Port number for server instance (mannualy code here "5432") <br/>
  db_name :- Username for database <br/>
  psql_user :- Instance username <br/>
  psql_password:- Instance password <br/>
  Command:-`./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password` <br/>
  Example:-`./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "password"` <br/>
### host_usage.sh
- Discription <br/>
  This bash script will extract resource usage information and store the data in the assigned table in the database and this script will execute, extract immediate data, and store the data on a minute-by-minute basis. <br/>
- Usage <br/>
  This bash script will be executed after host_info.sh and then with the help of crontab run every minute and gather information.<br/>
  psql_host :- Host name <br/>
  psql_port :- Port number for server instance (mannualy code here "5432") <br/>
  db_name :- Username for database <br/>
  psql_user :- Instance username <br/>
  psql_password:- Instance password <br/>
  Command:-`./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password` <br/>
  Example:-`./scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres" "password"` <br/>

### crontab
- Discription <br/>
  This bash script will help host_usage.sh to run minute-by-minute basis and perform its task
- Usage <br/>
  Open crontab editor:- `crontab -e` <br/>
  Add this to your crontab :-`* * * * * bash /home/centos/dev/jarvis_data_eng_Shubhkarman/ linux_sql/scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres" "password" > /tmp/psl_docker.log` <br/>

### queries.sql
- Discription
1.  **Group hosts by hardware info** :- Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group)
2.  **Average memory usage**:-Average used memory in percentage over 5 mins interval for each host.
3.  **Detect host failure**:- As crontab will be insert a new data entry to the `host_usage` table every minute when the server is working properly . When server will fail we assume it inserts less than three data points within 5-min interval. This query will detect host failures.
## Database Modeling
### host_info Table

| Variable         | Data Type | Description                                                        |
|------------------|-----------|--------------------------------------------------------------------|
| id               | SERIAL    | Primary Key; created automatically when entered into the database. |
| hostname         | VARCHAR   | Server name.                                                |
| cpu_number       | INT       | Number of CPUs listed in server.                                   |
| cpu_architecture | VARCHAR   | CPU architecture specifications.                             |
| cpu_model        | VARCHAR   | CPU model.                                                  |
| cpu_mhz          | REAL      | CPU MHz.                                                    |
| L2_cache         | INT       | L2 cache amount (KB).                                        |
| total_mem        | INT       | Total amount of memory (KB).                                 |
| timestamp        | TIMESTAMP | Timestamp, formatted in UTC timezone.

### host_usage Table

| Variable       | Data Type | Description                                     |
|----------------|-----------|-------------------------------------------------|
| timestamp      | TIMESTAMP | Timestamp, formatted in UTC timezone.           |
| host_id        | INT       | Foreign Key; holds ID of the host.              |
| memory_free    | INT       | Total free memory (MB).                      |
| cpu_idle       | INT       | Percentage of idle CPU time.           |
| cpu_kernel     | INT       | Percentage of CPU usage.               |
| disk_io        | INT       | Amount of I/O that is being used.       |
| disk_available | INT       | Amount of available disk space. |

# Test
1. First psql_docker.sh is tested by the "docker container ls" command as it displays all containers currently running
2. Bash scripts host_info.sh and host_usage.sh will be tested by the DML command "Select" directly on the instance's command-line interface tool
3. Pqsl scripts ddl.sql and queries.sql  can be tested directly instance's command-line interface tool

# Deployment
This project is Git Flow-based, so separate branches are made for each feature and when coding and testing are done, the feature-branch merged to develop branch. After this develop-release pull request is made and when senior developer approves it, develop and release get sync and later when the final full request for release-master will be  made and approved by management, program will be delivered.
# Improvements
1.  Add more SQL queries and sample data to test the script and for  better data analytics
2.  Better error detection and correction.
3.  Test this program on an actual network for better simulation
