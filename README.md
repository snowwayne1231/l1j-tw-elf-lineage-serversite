初始化
```shell
docker-compose up -d
docker exec -it mysql-db /bin/bash
mysql -u root -proot tw < /docker-entrypoint-initdb.d/l1jdb_Taiwan.sql

mysql -uroot -proot
ALTER USER 'root'@'%' IDENTIFIED BY '[NEW_PASSWORD]';
ALTER USER 'root'@'localhost' IDENTIFIED BY '[NEW_PASSWORD]';
FLUSH PRIVILEGES;
EXIT;

```

編譯
```shell
docker exec -it l1j-server /bin/sh
cd /app 
sh compile.sh

```

啟動server
```shell
docker exec -it l1j-server /bin/sh
cd /app 
sh StartServer.sh

```


安裝 Debian linux
```shell
sudo apt update
sudo apt install ca-certificates curl gnupg lsb-release

sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

```shell
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
# Check version
docker --version
docker compose version
sudo ln -s /usr/libexec/docker/cli-plugins/docker-compose /usr/local/bin/docker-compose

cd /
mkdir app
cd app
git clone https://github.com/snowwayne1231/l1j-tw-elf-lineage-serversite.git
```