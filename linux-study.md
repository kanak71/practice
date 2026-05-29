# 63일차 - Ubuntu / Tomcat / Jenkins 환경 구축

## 오늘 한 것

### 1. Ubuntu 및 Linux 명령어 학습

* WSL Ubuntu 접속
* root 권한 사용 (`sudo -i`)
* `apt update`
* `clear`, `ctrl + l`
* `vim` 사용법 (`i`, `:wq!`)
* Linux 환경변수 확인 (`echo $JAVA_HOME`)

---

## 2. OpenJDK 21 설치

```bash
apt install openjdk-21-jdk
```

### 확인

```bash
java -version
```

### 설치 경로 확인

```bash
find / -name java
```

---

## 3. JAVA_HOME 환경변수 설정

`/etc/profile` 수정

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export PATH=$PATH:$JAVA_HOME/bin
```

### 적용

```bash
source /etc/profile
```

### 확인

```bash
echo $JAVA_HOME
```

---

## 4. Tomcat 10 설치 및 설정

### 다운로드

* Apache Tomcat 10 tar.gz 파일 다운로드

### 압축 해제

```bash
tar xzvf apache-tomcat-10.1.55.tar.gz
```

### 이동

```bash
mv apache-tomcat-10.1.55 /usr/local/tomcat10
```

### 권한 설정

```bash
chmod -R 755 /usr/local/tomcat10/
```

---

## 5. Tomcat 환경설정

### CATALINA_HOME 설정

`/etc/profile`

```bash
export CATALINA_HOME=/usr/local/tomcat10
```

### 관리자 계정 설정

* `tomcat-users.xml`

### 외부 접근 허용

* `context.xml`

### 업로드 용량 설정

* `web.xml`

---

## 6. Tomcat 실행

```bash
./startup.sh
```

### 확인

```bash
localhost:8080
```

---

## 7. Linux Service(systemd) 학습

### tomcat 계정 및 그룹 생성

```bash
groupadd tomcat
useradd -s /sbin/nologin -g tomcat -d /usr/local/tomcat10 tomcat
```

### 소유권 변경

```bash
chown -PR tomcat:tomcat /usr/local/tomcat10
```

### 서비스 등록

* `/etc/systemd/system/tomcat10.service`

### 실행

```bash
systemctl daemon-reload
systemctl enable tomcat10.service
systemctl start tomcat10.service
```

### 상태 확인

```bash
systemctl status tomcat10.service
```

---

## 8. Jenkins 설치 및 실행

### Jenkins 설치

* Jenkins 공식 문서 참고

### 문제 해결

* Tomcat과 8080 포트 충돌 발생
* Jenkins 포트를 8181로 변경

```bash
HTTP_PORT=8181
```

### 서비스 실행

```bash
systemctl start jenkins.service
systemctl status jenkins.service
```

### 초기 관리자 비밀번호 확인

```bash
cat /var/lib/jenkins/secrets/initialAdminPassword
```

---

## 오늘 배운 핵심

* Linux 서버 환경 구성
* Java 실행 환경(OpenJDK)
* 환경변수 설정
* WAS(Tomcat) 설치 및 운영
* systemd 서비스 관리
* Jenkins를 통한 CI 환경 구축 기초
* 서버/배포/DevOps 흐름 이해
