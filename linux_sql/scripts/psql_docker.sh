#! /bin/sh

#capture CLI arguments (please do not copy comments)
cmd=$1
db_username=$2
db_password=$3

#Start docker
#Make sure you understand `||` cmd
sudo systemctl status docker || systemctl start docker

#check container status (try the following cmds on terminal)
container_status=`docker container inspect $db_username`

#User switch case to handle create|stop|start opetions
case $cmd in
  create)

  # Check if the container is already created
  if [ $container_status -ne 0 ]; then
		echo 'Container already exists'
		exit 1
	fi

  #check # of CLI arguments
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  #Create container
  docker pull postgres
	docker volume create pgdata

  docker run --name $db_username -e POSTGRES_PASSWORD=$db_password -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
	exit $?
	;;

  start|stop)
  #check instance status; exit 1 if container has not been created
  if [ $container_status -eq 0 ]; then
  echo 'Container does not exists'
  exit 1
  fi
  #Start or stop the container
	docker container $cmd $db_username
	exit $?
	;;

  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac