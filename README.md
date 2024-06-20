# BellyWelly-BE
[캡스톤디자인과창업프로젝트] 17. 출발드림팀 <br>
BellyWelly 백엔드 레포지토리

<br>

## 💡 프로젝트 개요
![image](https://github.com/BellyWelly/BellyWelly-BE/assets/121334671/a495a745-c481-4934-9822-e459c845da1a)

현대 사회에 불규칙적인 생활 패턴과 스트레스로 인해 장 건강에 큰 영향을 받는 사람들이 점차 증가하고 있습니다. 국내에서 연간 약 150만명의 과민대장증후군 환자가 발생했고, 이들은 일상 생활에 큰 불편을 겪고 있습니다.

대부분의 사람들은 자신의 장 건강 상태를 정확하게 알지 못하며, 자신에게 맞는 건강한 식습관과 생활 패턴을 구축하는 것에 어려움을 느끼고 있습니다. 하지만 이들의 문제를 해결할 수 있는 사용성 좋은 어플리케이션은 현재 부재합니다.

따라서, 사용자 개개인의 식습관, 배변 상태, 스트레스 지수 등을 효과적으로 기록하고 분석하여, 개인별 맞춤형 건강 관리 방안을 제공할 수 있는 서비스의 필요성이 점점 커지고 있기에, 과민대장증후군 개선을 위한 헬스케어 서비스를 제안합니다. 

<br>

## ⚙️ 시스템 아키텍쳐 
![image](https://github.com/BellyWelly/BellyWelly-BE/assets/121334671/58a2a335-9a7c-4eb5-a5c9-a601b28b601e)

백엔드는 Spring Boot를 사용하여 RESTful API를 개발하였고, 이를 Docker 컨테이너화하여 AWS EC2 상에서 애플리케이션을 실행하였습니다. 
EC2에 Nginx를 설치하여 80번 포트로 들어오는 요청을 8080번 포트로 리다이렉트하며, HTTPS도 설정하였습니다. 
서버의 DB로는 MySQL을 사용하고, 이를 RDS로 관리하였으며, RDS는 EC2와 연결하여 3306번 포트로 요청을 전달하였습니다.
서비스 내에서 사용자가 업로드하는 이미지는 AWS S3를 이용하여 관리하였습니다.


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

## ERDiagram
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

## API 명세서
Swagger: https://api.bellywelly.kro.kr/swagger-ui/index.html <br>

![image](https://github.com/BellyWelly/BellyWelly-BE/assets/121334671/e8d492ee-f8ad-4de8-bc6f-2ce9d8e16d79)

<br>

## 🔧 설치 및 실행 방법

### (1) 레포지토리 클론
```
git clone https://github.com/BellyWelly/BellyWelly-BE.git
```

### (2) 설정 파일 작성

BellyWelly 프로젝트를 실행하기 위해서는 application.yml을 작성해야 합니다. <br>
`BellyWelly-BE/src/main/resources/` 에 application.yml을 생성하고, 아래 코드를 복사하여 붙여넣습니다. <br>
마스킹된 부분은 본인의 실행 환경에 맞게 넣어줍니다.
```
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
```
cd BellyWelly-BE
./gradlew build -x test
```

### (4) JAR 실행
```
cd build/libs
java -jar bellywelly-0.0.1-SNAPSHOT.jar
```
<br>

백그라운드에서 실행하고 싶다면 아래 명령어를 입력합니다.
```
nohup java -jar bellywelly-0.0.1-SNAPSHOT.jar &
```

### (5) 브라우저에서 접속
`{IPv4}:8080/{path}`
<br>

로컬에서 실행했다면 `localhost:8080/home` 과 같이 입력하여 접속할 수 있습니다.

<br>

## 🌐 배포 주소
배포된 서버 URL 주소: https://api.bellywelly.kro.kr


## 📕 향후 과제 및 개선 방향
BellyWelly는 지속적인 기능 강화와 사용자 경험 개선을 목표로 하고 있습니다. AI 챗봇 기능을 사용자 맞춤형으로 정교화하고, 과민대장증후군 관리 솔루션을 더욱 효과적으로 제공하는 데 집중할 것입니다. 또한, 스마트 워치와 연동하여 사용자의 운동 기록 및 수면 기록 등을 주간 레포트에 반영함으로써 BellyWelly의 유용성을 더욱 향상할 것입니다.

지속적인 사용자 피드백을 반영하여 서비스 품질을 향상시키고, 고객의 니즈에 맞춘 서비스를 제공할 예정이며 나아가 BellyWelly는 추후 이미지 인식 기술과 챗봇 기술 등을 활용하여 타겟 시장을 확장할 계획입니다. 현재는 과민대장증후군 관리를 중심으로 서비스를 제공하고 있지만, 향후 당뇨, 만성 위염, 다이어트와 같이 다양한 이유로 생활 습관을 꾸준하게 관리해야 하는 사람들을 위한 기능을 추가하여 더욱 많은 사람들이 사용할 수 있는 서비스로 거듭날 것입니다.
<br>
