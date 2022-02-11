#!/bin/bash

  # assign arguemnt  to variable
  psql_host=$1
  psql_port=$2
  db_name=$3
  psql_user=$4
  psql_password=$5

  # checking arguments
  if [ $# -ne  5 ]; then
    echo "Please check you arguments"
    echo "Correct way:- host_info.sh psql_host psql_port db_name psql_user psql_password "
    exit 1
  fi

  # Cpu info
  lscpu_out=`lscpu`

  host_name=$(hostname -f)
  cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
  cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
  cpu_model=$(echo "$lscpu_out"  | egrep "^Model name:" | cut -c 12-   | xargs)
  cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
  L2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | xargs)
  L2_cache=${L2_cache%?};

  mem_out=`cat /proc/meminfo`

  total_mem=$(echo "$mem_out"  | egrep "^MemTotal:" | awk '{print $2}' | xargs)
  timestamp=$(date +"%Y-%m-%d %T")

  #special variable for insert statement
  insert_stmt="INSERT INTO host_info(host_name,cpu_number,cpu_architecture,cpu_model,cpu_mhz,L2_cache,total_mem,timestamp) VALUES('$host_name','$cpu_number','$cpu_architecture','$cpu_model','$cpu_mhz','$L2_cache','$total_mem','$timestamp');"

  # running psql command
  psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$insert_stmt"
