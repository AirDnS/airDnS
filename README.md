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
    │      └─templates
    └─test
        ├─java
        │  └─com
        │      └─example
        │          └─airdns
        │              ├─domain
        │              │  └─room
        │              │      ├─constant
        │              │      ├─repository
        │              │      └─service
        │              ├─global
        │              │  └─awss3
        │              ├─like
        │              └─review
        └─resources
```


## 📋 Technical Decision

----

## 💁‍♂️참조

