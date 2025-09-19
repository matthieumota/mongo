docker compose up -d

docker compose run --rm mongo-setup /setup.sh
docker compose run --rm mongo-setup /setup-config.sh
docker compose run --rm mongo-setup /setup-shard.sh
