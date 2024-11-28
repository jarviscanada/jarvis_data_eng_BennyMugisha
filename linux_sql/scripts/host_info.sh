#! /bin/bash

# Setup the variables from args
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# check the number # of agrs 
if [ "$#" -ne 5 ]; then
	echo "Illegal number of parameters"
	exit 1
fi

# setup variable to get hardware info
lscpu_out=`lscpu`
cpuinfo_out=`cat /proc/cpuinfo`
meminfo_out=`cat /proc/meminfo`

# hardware info
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | awk '{print substr($0, index($0, $3))}' | xargs)
cpu_mhz=$(echo "$cpuinfo_out"  | egrep "^cpu MHz" | awk 'NR==1 {print $4}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | xargs)
# (to review) 
#total_mem= $(vmstat --unit M | tail -1 | awk '{print $4}')
total_mem=$(echo "$meminfo_out" | egrep "^MemTotal:" |  awk '{print $2}' | xargs)
timestamp=$(date '+%Y-%m-%d %H:%M:%S') # current timestamp in `2019-11-26 14:40:19` format; use `date` cmd

# insert statement for the hardware info
insert_stmt="INSERT INTO host_info (
  	            id, hostname, cpu_number, cpu_architecture,
  		          cpu_model, cpu_mhz, l2_cache, timestamp,
  		          total_mem
	            )
	            VALUES
  		          (
                  DEFAULT, '$hostname', '$cpu_number', '$cpu_architecture',
                  '$cpu_model', $cpu_mhz, $l2_cache, '$timestamp',
                  $total_mem
                );
            "

#set up env var for pql cmd
export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
