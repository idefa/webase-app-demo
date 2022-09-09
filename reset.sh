docker-compose down
rm -rf ./fisco/nodes/127.0.0.1/node0/data
rm -rf ./fisco/nodes/127.0.0.1/node1/data
rm -rf ./fisco/nodes/127.0.0.1/node2/data
rm -rf ./webase/webase-deploy/webase-front
rm -rf ./webase/webase-deploy/webase-node-mgr
rm -rf ./webase/webase-deploy/webase-sign
rm -rf ./webase/webase-deploy/webase-web/log
rm -rf ./webase/webase-deploy/mysql
cd  ./webase/webase-deploy/
tar vxf mysql.tar
