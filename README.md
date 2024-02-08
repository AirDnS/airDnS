# 💁‍♂️ airDnS

##  📋서비스 소개

### 1️⃣ 한줄 설명 
- 개인 스터디 룸을 시간로 단위 대여 해주는 서비스입니다.

### 2️⃣️ 상세 내용
- 등록하는 사람이 스터디 룸을 등록합니다. 
  - 위치와 카테고리를 통한 장비에 관한 내용과 사진도 추가적으로 등록합니다.
- 사용자는 원하는 분류를 통해 스터디 룸 목록을 볼수 있습니다.
  - 장비 관련된 분류를 제공합니다.
- 등록자는 등록 하면서 해당 방에 대한 시간당 이용시간을 표기해야합니다.
- 사용자는 원하는 방을 예약 가능한 시간에 예약 할 수 있습니다.
- 예약 이후 결제 시스템을 통해 결제를 진행합니다.
- 리뷰를 통해 후기를 남길 수 있다.

### 3️⃣ 기대효과
- 호스트는  장기 여행을 하게 되거나 집을 오랫동안 비울 수 있는 상황에 개인 공간을 대여함으로써 추가 수익을 창출할 수 있습니다.

## 📋서비스 아키텍쳐

<img width="1237" alt="스크린샷 2024-01-31 오후 3 13 35" src="https://github.com/AirDnS/airDnS-back/assets/62927374/9cc73444-01bd-4a48-bf04-c389f91547d3">

## 📋UI/UX
### 0️⃣ 홈
![스크린샷 2024-01-31 오후 3 24 56](https://github.com/AirDnS/airDnS-back/assets/62927374/43909275-302c-447f-b54f-84a1248df75d)
### 1️⃣ 방 상세
![스크린샷 2024-01-31 오후 3 28 24](https://github.com/AirDnS/airDnS-back/assets/62927374/c2886edd-68c6-421e-bf47-aa4726b6a225)

### 2️⃣ 방 등록
![스크린샷 2024-01-31 오후 3 27 11](https://github.com/AirDnS/airDnS-back/assets/62927374/0543e3f4-6862-456e-ba23-e7a806506649)

### 3️⃣ 결제
![스크린샷 2024-01-31 오후 3 28 47](https://github.com/AirDnS/airDnS-back/assets/62927374/f6c85efb-96db-4021-b29b-cea7fb8a3e76)

### 4️⃣ 조회
![AirDnS 회원조회](https://github.com/AirDnS/airDnS-back/assets/147155601/a228aed7-54f1-4f79-bd4f-86defd36f54d)


## 📋 ERD
![airdns](https://github.com/AirDnS/airDnS-back/assets/62927374/73e6e9b3-2a12-4c9d-9aab-4477200753bf)

## 📋 Github 규칙

### 1️⃣ Github commit 규칙

##### 1. Commit 메세지 구조 
- 제목, 본문, 꼬리말
```markdown
Tag : subject

body 

footer
```
#### 2. Commit 타입
- 타입은 태그와 제목으로 구성되고, 태그는 영어로 쓰되 첫 문자는 대문자로 한다.
    - **`태그: 제목`의 형태이며, `:`뒤에만 space가 있음에 유의한다.**
- `Feat` : 새로운 기능 추가
- `Fix` : 버그 수정
- `Docs` : 문서 수정
- `Style` : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- `Refactor` : 코드 리펙토링
- `Test` : 테스트 코드, 리펙토링 테스트 코드 추가
- `Chore` : 빌드 업무 수정, 패키지 매니저 수정

#### 3. Commit 예시

```markdown
Feat: "회원 가입 기능 구현"

SMS, 이메일 중복확인 API 개발

Resolves: #123
Ref: #456
Related to: #48, #45
```

## 📋 Directory 구조

<details>
    <summary>Directory 구조</summary>

``` markdown
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─example
    │  │          └─airdns
    │  │              ├─domain
    │  │              │  ├─deleteinfo
    │  │              │  │  ├─entity
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─equipment
    │  │              │  │  ├─controller
    │  │              │  │  ├─dto
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─equipmentcategory
    │  │              │  │  ├─entity
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─image
    │  │              │  │  ├─converter
    │  │              │  │  ├─dto
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─like
    │  │              │  │  ├─controller
    │  │              │  │  ├─dto
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─oauth2
    │  │              │  │  ├─common
    │  │              │  │  ├─dto
    │  │              │  │  ├─exception
    │  │              │  │  ├─handler
    │  │              │  │  ├─repository
    │  │              │  │  ├─service
    │  │              │  │  └─social
    │  │              │  ├─payment
    │  │              │  │  ├─config
    │  │              │  │  ├─controller
    │  │              │  │  ├─dto
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─reservation
    │  │              │  │  ├─controller
    │  │              │  │  ├─dto
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  ├─service
    │  │              │  │  └─servicefacade
    │  │              │  ├─restschedule
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─review
    │  │              │  │  ├─controller
    │  │              │  │  ├─dto
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  ├─room
    │  │              │  │  ├─controller
    │  │              │  │  ├─converter
    │  │              │  │  ├─dto
    │  │              │  │  ├─entity
    │  │              │  │  ├─exception
    │  │              │  │  ├─repository
    │  │              │  │  ├─service
    │  │              │  │  └─servicefacade
    │  │              │  ├─roomequipment
    │  │              │  │  ├─entity
    │  │              │  │  ├─repository
    │  │              │  │  └─service
    │  │              │  └─user
    │  │              │      ├─controller
    │  │              │      ├─dto
    │  │              │      ├─entity
    │  │              │      ├─enums
    │  │              │      ├─exception
    │  │              │      ├─repository
    │  │              │      └─service
    │  │              └─global
    │  │                  ├─advice
    │  │                  ├─awss3
    │  │                  ├─common
    │  │                  │  ├─dto
    │  │                  │  └─entity
    │  │                  ├─config
    │  │                  ├─cookie
    │  │                  ├─exception
    │  │                  ├─jwt
    │  │                  ├─loadbalance
    │  │                  ├─redis
    │  │                  ├─scheduler
    │  │                  └─security
    │  └─resources
    │    
    └─test
```

</details>


## 📋 Technical Decision

<details>
  <summary>Soft Delete</summary>
    
- 도입 이유
  - 방이 삭제되어도 해당 정보에 대해 접근 할 수 있는 방법이 필요
- 문제 상황
  - 방이 삭제될 경우에 예약 목록에서 방에 대한 정보를 확인할 수 없는 문제 상황 발생
- 해결 방안
  - is_deleted, deleted_at 컬럼을 추가함
- 의사 조율
  - Entity에 @SqlDelete를 사용하여 jpaRepository.delete 메소드 사용으로 Soft 삭제

       delete 메소드를 사용하는 순간, 해당 Table의 row가 삭제되는 것이 아닌 SqlDelete문에서 활용한 update문이 실행되어 실제로 삭제된 것처럼 보이게 함

  - jpaRepository.delete를 Soft 삭제로 오버라이드하는 방법

         Interface에 @Query를 달거나, 직접 쿼리를 작성하는 방안을 통해 삭제된 것처럼 보이게 함

- 의견 결정
  - @SqlDelete를 사용한 Soft Delete 도입으로 실제 데이터 삭제를 유예하기로 함
  - 스케줄러를 통해 특정 기간이 지난 Table의 row에 대해서는 별도의 데이터베이스에 적재 후 Table에서 완전 삭제하는 기능을 추가
  - 완전 삭제된 데이터는 별도 데이터베이스에 적재되어 있기에 조회할 수 있는 기능

</details>

<details>
  <summary>예약 동시성</summary>

- 도입 이유
    - 한 사람이 예약 버튼을 누르는 시점에서 다른 사람이 해당 시간대의 예약을  막아야 한다고 생각했기 때문에 도입했습니다.
  - 문제 상황
    - 동시에 예약하기 버튼을 눌렀을 때 데이터의 정합성을 보장을 못하는 문제 발생
  - 해결 방안
    - Synchronized
      - 자바의 Synchronized는 하나의 프로세스 안에서만 보장이 된다.
    - Mysql에서 제공하는 Lock
      - Pessimistic Lock
      - Optimistic Lock
      - Named Lock
    - 레디스
      - Lettuce
      - Redisson
  - 의사 조율
    - 현재 아키텍쳐는 분산 서버가 아니기 때문에 Synchronized 메서드도 가능하다고 판단 했지만, 서비스 확장성에는 안맞다고 생각했습니다.
    - Mysql에서 제공하는 Lock 중에서 Named Lock은  트랜잭션 종료 시에 락 헤제, 세션 관리를 잘해줘야 하고,  현재 하나의 데이터 소스를 사용하는 중이라 적절하지 않다고 판단했습니다.
    - Redis와 Mysql 을 비교 했을 때, 현재 Refresh Token을 이용하기 때문에 Redis를 구축하는데 필요한 비용이 없다고 생각을 했고, 성능상 mysql보다 redis가 좋기 때문에 추후 고도화 작업도 고려해 보면  Redis가 더 적합하다고 생각했습니다.
    - Redis 라이브러리 중에서 Lettuce 같은 경우 스핀 락 방식이기 때문에 부하가 많이 갈거 같다고 생각했고, Redisson이  더 적합하다고 생각했습니다.
  - 의견 결정
    - 여러 요건을 따졌을 때, Redisson 방식이 가장 적절하다고 생각해 Redisson을 이용해 동시성을 제어 했습니다.

</details>

<details>
  <summary>Application Load Balancer</summary>

  - 도입이유
    - Mixed Contents 문제 해결을 위해
  - 문제 상황
    - 로그인 로직 중 refresh Token을 통해 토큰 탈취와 같은 보안적인 부분을 보완하는 방향으로 결정했습니다.
    - Route53을 통해 도메인을 구매하고 AWS Certificate Manager를 통해 해당 도메인에 대한 인증서를 발급받고 Cloud Front에 인증서를 적용한 상황에서 백엔드 서버에 API 요청을 보낼 때 Mixed Content 에러가 발생
    - Https가 적용된 사이트에서 HTTP 요청을 전송할 경우 보안이 적용된 사이트에서 더 낮은 사이트로 연결 시도 하는 것이라 block이 발생
  - 해결 방안
    1. EC2 인스턴스에 인증서 적용
    1. 인스턴스 1개만 사용하는 경우 ec2 인스턴스에 직접 SSL 인증서를 적용하여 HTTPS로 통신
    2. Nginx를 이용해 인증서 검증
    1. 프록시 서버를 생성하고 cloud front와 인증서를 검증하고 EC2 인스턴스와의 통신을 보안화함으로써 해결 방식
    3. Application Load Balancer
    1. Elastic Load Balancer 중 Application Load Balancer를 이용해 Https로 연결된 cloudFront와 인증서를 검증하고 EC2 인스턴스와 통신을 Http로 변경
  - 의사 조율
    - 현재 상황은 이미 AWS Certification Manager를 통해 인증서를 발급받았고, 서비스는 도커와 도커 컴포즈를 통해 확장성에 대한 이점을 챙긴 상황
    - Nginx를 이용하면 별도의 설정이 필요하다는 불편함과 프로젝트 기간이 짧은 점을 고려해서 배제했습니다.
  - 의견 결정
    - 간단하게 Elastic Load Balancer를 이용한 확장성도 고려한 선택이 합리적이라고 생각했습니다.

</details>

<details>
  <summary>프론트</summary>

  - 도입 이유
    - 프로젝트 결과를 시각적으로 확인하기 위해 프론트 단의 웹페이지 화면이 필요하다고 생각했습니다.
  - 문제 상황
    - 팀원 중 아무도 프론트에 대한 지식이 없고 경험이 없다.
    - API 형식으로 프론트와 백엔드 송/수신 하기로 기획
  - 해결 방안
    - Thymeleaf
    - Vue
    - React
  - 의사 조율
    - Thymelea는 기획 단계에서 API 통신 한다는 전제로 했기 때문에 적절하지 않다는 생각에 배제 했습니다.
    - Vue와 React 중 고민하다 러닝 커브가 더 낮은 프레임워크를 사용하기로 결정
  - 의결 결정
    - Vue는 Html Css Js 구조와 비슷하여 Js로 모든 작업을 하는 리액트보다 더 직관적이고 러닝커브가  더 낮다고 생각해 vue를 채택했습니다.

</details>

<details>
<summary>CI/CD</summary>

  - Jenkins & Git Action
    - Jenkins는 다양한 플러그인 존재, 수 많은 커뮤니티가 존재하지만 플러그인 하나를 설정할 때마다 새로운 지식이 필요하다는 단점이 있음
    - Git Action은 형상 관리 도구인 Git과 연동이 편리하다는 장점이 존재하나 한정적인 플러그인만 존재한다는 단점이 있음
    - 프로젝트 규모를 비교하여 다양한 플러그인을 가지고 있는 Jenkins의 플러그인들까지 사용할 필요가 없다고 느끼고 설정 난이도가 상대적으로 낮은 Git Action이 사용하는 것이 좋다고 판단하여 Git Action 사용
  - Docker Compose
    - 도커 컴포즈를 통해 두개의 컨테이너를 손쉽게 관리할 수 있다고 판단 했고, 분산 서버로 인해 여러개의 컨테이너가 생길 경우도 생각해서 도커 컴포즈를 채택했습니다.


</details>

<details>
<summary>kafka, elasticSearch 도입 고찰</summary>

- 프로젝트의 기술적 다양성을 목표로 해당 사안에 대해 논의함
- kafka는 분산스트리밍 플랫폼으로 "대량으로 들어오는 지속적이고 실시간 데이터를 분산처리"할 때 필요함
- 현재 시스템에서 kafka는 너무 과도한 기술, 러닝 커브 완만함으로 구성원들이 적용하기 어려움
- 메세지 큐를 사용하고 싶다면, 특정 부분에 AWS SQS 정도로 하기로 함
- elasticSearch는 동일한 이유로 폐기 (과도함, 굳이 설명 같은 부분도 검색을 제공해야하는가?)

</details>

<details>
  <summary>반복되는 작업 공통화</summary>

- `Jackson`의 기본 datetimeFormat 설정
  - `LocalDateTime` 타입 변수를 Dto로 받을 때마다, `@JsonFormat` 을 적어줘야했음.
  - 반복적이고 불필요한 작업이라 판단했기 때문에 자주 쓰이는 `yyyy-MM-dd HH:mm:ss`를 공통 데이터 포맷으로 설정하고, 전역 설정에 추가
- `@EnableJpaAuditing` 사용
  - createdAt, ModifiedAt 은 반복적으로 사용하는 DB 데이터라고 판단하여 Auditing 기능을 통해 동시 적용하도록 변경
- `Swagger` 적용
  - API 문서화를 위해 드는 비용이 너무 크다고 판단해 `Swagger`를 적용하여 자동으로 문서화 되도록 함


</details>


-----

