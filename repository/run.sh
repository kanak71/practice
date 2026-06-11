declare dc_infra=docker-compose-db.yml
declare cd_app=docker-compose-app.yml

#IDE도구에서 사용하지 않고 CLI에서 애플리케이션 빌드 명령어
function build_app(){
    cd bookmarker-api
    ./mvnw clean package -DskipTests
    cd ..
}

# 데이터베이스 인프라(PostgreSQL) 시작 -f(--follow 실시간 로그를 스트리밍)
function start_infra(){
    echo "Start DB Docker containers...."
    docker-compose -f ${dc_infra} up -d
    docker-compose -f ${dc_infra} logs -tail=200 -f
} 

# 데이터베이스 인프라(PostgreSQL) 중지
function stop_infra(){
    echo "Stop DB Docker containers...."
    docker-compose -f ${dc_infra} stop
    docker-compose -f ${dc_infra} rm -f
}

# 모든 서비스 시작
function start(){
    echo "Start DB and Application Docker containers...."
    docker-compose -f ${dc_infra} -f ${dc_app} up -d
    docker-compose -f ${dc_infra} -f ${dc_app} logs -f
}

#모든 서비스 중지
function stop(){
    echo "Stop DB and Application Docker containers...."
    docker-compose -f ${dc_infra} -f ${dc_app} stop
    docker-compose -f ${dc_infra} -f ${dc_app} rm
}

# 모두 서비스 재시작
function restart(){
    stop
    sleep 5
    start
}


# 기본동작 , ./run sh start_infra : 명령어 인자
action="start"

if [[ "$#" != 0 ]]
then
  action=$@
fi

$action

