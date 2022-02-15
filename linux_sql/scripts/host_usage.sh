#!/bin/bash

  # assign arguemnt  to variable
  psql_host=$1
  psql_port=$2
  db_name=$3
  psql_user=$4
  psql_password=$5
  export PGPASSWORD=$psql_password
  # checking arguments
    if [ $# -ne  5 ]; then
      echo "Please check you arguments"
      echo "Correct way:- host_usage.sh psql_host psql_port db_name psql_user psql_password "
      exit 1
    fi

    host_name=$(hostname -f)

    vm_t=`vmstat -t --unit M`

    memory_free=$(echo "$vm_t" | awk '{print $4}'| tail -n1 | xargs)
    cpu_idle=$(echo "$vm_t"|awk '{print $15}'|tail -1|xargs)
    cpu_kernel=$(echo "$vm_t"|awk '{print $12}'|tail -1|xargs)
    vm_d=`vmstat -d`
    disk_io=$(echo "$vm_d"| awk '{print $10}'|tail -1|xargs)

    da_out=`df -BM /`
    disk_available=$(echo "$da_out"| awk '{print $4}'|tail -1|xargs)
    disk_available=${disk_available%?};

    host_id_C="(SELECT id FROM host_info WHERE host_name='$host_name')";
    host_id_V=$(psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$host_id_C")
    host_id=$(echo $host_id_V |awk '{print $3}')

    timestamp=$(date +"%Y-%m-%d %T")

    #special variable for insert statement
    insert_stmt="INSERT INTO host_usage(timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available) VALUES('$timestamp','$host_id','$memory_free','$cpu_idle','$cpu_kernel','$disk_io','$disk_available');"

      # running psql command
      psql -h $psql_host -p $psql_port -U $psql_user -d $db_name -c "$insert_stmt"
