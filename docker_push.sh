# dependency jq
# apt-get install -y jq

json=$( cat docker_config.json )

version=$( echo $json | jq -r .version )
next_version=$(($version + 1))
docker_hub_username=$( echo $json | jq -r .docker_hub_username )
docker_hub_repository=$( echo $json | jq -r .docker_hub_repository )

echo $json | jq --arg next_version "${next_version}" '.version = $next_version' > docker_config.json
docker build -t $docker_hub_username/$docker_hub_repository:$next_version -t $docker_hub_username/$docker_hub_repository:latest .
docker push $docker_hub_username/$docker_hub_repository:$next_version
docker push $docker_hub_username/$docker_hub_repository:latest
docker rmi $docker_hub_username/$docker_hub_repository:$next_version