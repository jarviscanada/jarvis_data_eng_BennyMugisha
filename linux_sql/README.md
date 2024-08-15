# Linux Cluster Monitoring Agent

## Introduction
This Linux Cluster Monitoring Agent project is designed for users to track hardware specifications and the Linux system's monitoring infrastructure automatically. The agent uses bash scripts to start docker which installs Postgres with a given username and password. The host information script aims to collect the CPU, memory, and hostname information saved into a database. Using this information, another script is programmed to run and provide details on the memory, CPU, and disk usage while recording the timestamp. These two scripts are given the database host, port, username, password, and an insert statement to save the recorded information referencing the hostname of the Linux machine. In the end, the host usage script is automated using crontab for Realtime data collection. To use and implement this Monitoring Agent please follow the instructions below:

## Quick Start
Quick-start commands and working with the scripts
- Clone the repository and navigate to the project
```
git clone https://github.com/jarviscanada/jarvis_data_eng_BennyMugisha.git
cd linux_sql
```
- Start a psql instance using psql_docker.sh
```
./scripts/psql_docker.sh create <db_username> <db_password>
```
- Create tables using ddl.sql
```
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```
- Insert hardware specs data into the DB using host_info.sh
```
./scripts/host_info.sh <psql_host> <psql_port> <db_name> <psql_user> <psql_password>
```
- Insert hardware usage data into the DB using host_usage.sh
```
bash scripts/host_usage.sh <psql_host> <psql_port> <db_name> <psql_user> <psql_password>
# Example
bash scripts/host_usage.sh localhost 5432 host_agent postgres password
```
- Crontab setup
```
# edit crontab jobs
bash> crontab -e

# add this to crontab
# make sure you are using the correct file location for your script by replacing <your_path>
* * * * * bash <your_path>/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
```

# Implemenation
The following describes the project implementation with more depth on what each script is running in the background
## Architecture
A cluster diagram with three Linux hosts, a DB, and agents <br>
![Linux Cluster Architecture](assets/"System Architecture".png)
## Scripts
Shell script description and usage.
- __psql_docker.sh:__ a script that facilitates the installation of the psql docker container. It can also start and stop the container. Available commands are start|stop|create
- __host_info.sh:__ an agent collecting information from the CPU, memory, and hostname and saves it to the psql database
- __host_usage.sh:__ a monitor script that automatically records the usage of the hardware with a timestamp of the memory, CPU, and disk usage capture.
- __crontab:__ helps automate and deploy the host_usage.sh using the scheduling syntax. The initial standard used in this project is to run the script every 1 minute.

## Database Modeling
The Database name used is the `host_agent` which consist of two tables `host_info` and `host_usage`
- `host_info`

  `id`: a unique id auto-incremented for each record

  `hostname`: a full hostname of the machine running the script

  `cpu_number`: the CPU number of the computer

  `cpu_architecture`: the CPU architecture type

  `cpu_model`: the CPU model showing the name/manufacturer and the clock speed

  `cpu_mhz`: the CPU Clock speed rate

  `l2_cache`: the L2 cache size in kB

  `total_mem`: the total memory size in kB

  `timestamp`: current time in UTC


- `host_usage`

  `timestamp`: recorded time in UTC

  `host_id`: Unique ID with reference from the host_info.id

  `memory_free`: Available memory size on the computer in MB

  `cpu_idle`: the CPU idle in percentage

  `cpu_kernel`: the  CPU kernel of the system in percentage

  `disk_io`: number reads and writes in the process

  `disk_available`: available size in the disk root directory in MB


# Test
The following scripts were tested using the debugging option bash -x <script.sh> command, the results were different at times until the scripts exited at the expected output.
### psql_docker.sh
The test for this script starts by checking if the program is given the right or available commands `start|stop|create` otherwise the user gets an error that they have given an illegal command. The next texting is when creating the container, the script checks if the container exists or the parameters are not equal to three then returns an error otherwise it proceeds to the container creation. The last test is on the `start|stop` command to check if the container was created otherwise returns an error.
### host_info.sh
In the first stage, the program is tested on the number of parameters since it requires to have all five parameters to operate. The second stage of testing was the insert statement since we need to make sure the CPU and hardware specifications are saved to the database correctly by returning the `psql` command output to the CLI.
### host_usage.sh
Same as the above script, the process starts with testing the five parameter inputs and checking the correct message output. The second test was to verify that the `SELECT` statement is giving the right information for the `host_id`. Then the last test is for the insert statement using the `psql` command to save the system usage to the database by checking if we have the correct output on the CLI from the `echo $?` command.

# Deployment
The application is deployed using Docker and Postgres containers. For more information on the automation using crontab, it can be referenced in the Quick Start section of this document.
After cloning this project from GitHub and navigating to the `linux_sql` directory, when the `psql_docker.sh` is executed the command `docker run --name jrvs-psql -e POSTGRES_PASSWORD=$db_password -d -v pgdta:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine` will create the necessary Postgres image on port 5432. For the deployment of the `host_usge.sh` follow the crontab setup instructions:
- Crontab setup
```
bash> crontab -e

# add this to crontab
# make sure you are using the correct file location for your script by replacing <your_path>
* * * * * bash <your_path>/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
```

# Improvements
The project itself can be improved to give users more flexibility and clarity about what the program is operating. The following is information that we can give to improve this project:
- Checking for different Linux distributions since this was developed using the Rocky Linux 9 version.
- Refactor and add unit tests for each of the scripts.
- Implement additional error checking and give hints to the user with accurate and easy-to-understand information.
