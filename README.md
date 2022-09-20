# webase-app-demo


bash build_chain.sh -l 172.25.0.2:1,172.25.0.3:1,172.25.0.4:1 -p 30300,20200,8545

tail -f fisco/nodes/172.25.0.2/node0/log/log*  | grep connected
tail -f fisco/nodes/172.25.0.2/node0/log/log*  | grep +++


docker network create -d bridge --subnet=172.25.0.0/16 --gateway=172.25.0.1 web_network