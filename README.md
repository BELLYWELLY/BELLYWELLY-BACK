# BellyWelly-BE
[캡스톤디자인과창업프로젝트] 17. 출발드림팀 <br>
BellyWelly 백엔드 레포지토리
<br>

![image](https://github.com/BellyWelly/BellyWelly-BE/assets/121334671/a495a745-c481-4934-9822-e459c845da1a)



## 🌐 배포 주소
배포된 서버 URL 주소: https://api.bellywelly.kro.kr

<br>

## 💡 프로젝트 개요
https://github.com/BellyWelly

<br>

## 💻 백엔드 기술 스택
<div>
  <img alt="java" src="https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white" />
  <img alt="spring boot" src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=springboot&logoColor=white" />
  <img alt="jwt" src="https://img.shields.io/badge/JWT-282828?style=flat&logo=jsonwebtokens&logoColor=white" />
  <img alt="spring security" src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=springsecurity&logoColor=white" />
  <img alt="swagger" src="https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=swagger&logoColor=white" />
</div>
<div>
  <img alt="mysql" src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" />
  <img alt="hibernate" src="https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=hibernate&logoColor=white" />
</div>
<div>
  <img alt="nginx" src="https://img.shields.io/badge/NGINX-009639?style=flat&logo=nginx&logoColor=white" />
  <img alt="docker" src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=docker&logoColor=white" />
    <img alt="github actions" src="https://img.shields.io/badge/Github Actions-2088FF?style=flat&logo=githubactions&logoColor=white" />
</div>
<div>
  <img alt="EC2" src="https://img.shields.io/badge/AWS EC2-FF9900?style=flat&logo=amazonec2&logoColor=white" />
  <img alt="RDS" src="https://img.shields.io/badge/AWS RDS-527FFF?style=flat&logo=amazonrds&logoColor=white" />
    <img alt="S3" src="https://img.shields.io/badge/AWS S3-569A31?style=flat&logo=amazons3&logoColor=white" />
</div>
<br>

## 🔧 설치 및 실행 방법

### (1) 레포지토리 클론
```shell
git clone https://github.com/BellyWelly/BellyWelly-BE.git
```

### (2) 설정 파일 작성

BellyWelly 프로젝트를 실행하기 위해서는 application.yml을 작성해야 합니다. <br>
`BellyWelly-BE/src/main/resources/` 에 application.yml을 생성하고, 아래 코드를 복사하여 붙여넣습니다. <br>
마스킹된 부분은 본인의 실행 환경에 맞게 넣어줍니다.

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://{ ENDPOINT }:3306/{ DB_NAME }?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: { USERNAME }
    password: { PASSWORD }
  jpa:
    generate-ddl: true
    hibernate:
      ddl-auto: update
    database: mysql
    database-platform: org.hibernate.dialect.MySQLDialect

server:
  error:
    include-message: always

kakao:
  client-id: { CLIENT_ID }
  client-secret: { CLIENT_SECRET }

jwt:
  secret: { SECRET_KEY }

aws:
  s3:
    access-key: { ACCESS_KEY }
    secret-key: { SECRET_KEY }
    region: { REGION }
    bucket: { BUCKET }
```

### (3) 프로젝트 빌드
```shell
cd BellyWelly-BE
./gradlew build -x test
```

### (4) JAR 실행
```shell
cd build/libs
java -jar bellywelly-0.0.1-SNAPSHOT.jar
```
<br>

백그라운드에서 실행하고 싶다면 아래 명령어를 입력합니다.
```shell
nohup java -jar bellywelly-0.0.1-SNAPSHOT.jar &
```

### (5) 브라우저에서 접속
`{IPv4}:8080/{path}`
<br>

로컬에서 실행했다면 `localhost:8080/home` 과 같이 입력하여 접속할 수 있습니다.

<br>

## ⚙️ 시스템 아키텍쳐 
![image](https://github.com/BellyWelly/BellyWelly-BE/assets/121334671/58a2a335-9a7c-4eb5-a5c9-a601b28b601e)

백엔드는 Spring Boot를 사용하여 RESTful API를 개발하였고, 이를 Docker 컨테이너화하여 AWS EC2 상에서 애플리케이션을 실행하였습니다. <br>
EC2에 Nginx를 설치하여 80번 포트로 들어오는 요청을 8080번 포트로 리다이렉트하며, HTTPS도 설정하였습니다. <br>
서버의 DB로는 MySQL을 사용하고, 이를 RDS로 관리하였으며, RDS는 EC2와 연결하여 3306번 포트로 요청을 전달하였습니다.<br>
서비스 내에서 사용자가 업로드하는 이미지는 AWS S3를 이용하여 관리하였습니다.<br>


### AWS Configuration
- EC2: Ubuntu 22.04(arm64) t4g.small
- RDS: db.t4g.micro MySQL Community

<br>

## ⚙️ 사용한 오픈소스
- Java 17, Spring Boot 3.2.0으로 애플리케이션 개발
- 데이터베이스로 MySQL 8 사용, ORM으로 Spring Data JPA, Hibernate 사용
- Spring Security와 JWT 도입으로 보안 구축
- Spring Cloud AWS S3 이용하여 S3에 이미지 업로드
- Springdoc OpenAPI로 Swagger 생성하여 API 문서 배포
- Github Actions, Docker를 이용한 CI/CD 구축
- Nginx를 이용하여 웹서버 구축
- Certbot을 이용하여 SSL 인증서 발급 후 HTTPS 연결

<br>

## ✏️ ERDiagram
![image](https://github.com/BellyWelly/BellyWelly-BE/assets/121334671/685e013a-715a-4e82-8344-41ba2d94025e)
- member: 회원 정보를 담는 테이블
- diet: 식단 기록 정보를 담는 테이블
- diet_meal: diet와 meal의 중간 테이블
- meal: 음식 정보를 담는 테이블
- defecation: 배변 기록 정보를 담는 테이블
- stress: 스트레스 기록 정보를 담는 테이블
- report: 레포트 공통 정보를 담는 테이블
- report_meal: 레포트 중 식단 분석 정보를 담는 테이블
- report_defecation_stress: 레포트 중 배변/스트레스 분석 정보를 담는 테이블

<br>

## 📕 API 명세서
Swagger: https://api.bellywelly.kro.kr/swagger-ui/index.html <br>

![image](https://github.com/BellyWelly/BellyWelly-BE/assets/121334671/6fc68ed1-5abd-458d-9b2b-f30757702680)

<br>
