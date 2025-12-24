初始化
```shell
docker-compose up -d
docker exec -it mysql-db /bin/bash
mysql -u root -proot tw < /docker-entrypoint-initdb.d/l1jdb_Taiwan.sql

```

編譯
```shell
docker exec -it mysql-db /bin/sh
cd /app 
sh compile.sh

```

啟動server
```shell
docker exec -it mysql-db /bin/sh
cd /app 
sh StartServer.sh

```