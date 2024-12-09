# shop

<br>
<br>

이 프로젝트는 책을 상품으로 판매하는 쇼핑몰 시스템을 구현한 백엔드 프로젝트로,

사용자가 책을 검색하고, 장바구니에 담고, 주문까지 완료할 수 있는 주요 기능을 포함하고 있습니다. 

이를 통해 도서 상품 관리, 사용자 인증 및 권한 부여, 주문 처리와 같은 핵심 비즈니스 로직을 설계하고 개발하며, 

전반적인 백엔드 기술 스택을 연습하고 익히는 데 중점을 두었습니다.

<br>
<br>

### 목차

[1. 개발 환경](#1-개발-환경)

[2. 기능](#2-기능)

[3. 브랜치 전략](#3-브랜치-전략)

[4. 프로젝트 구조](#4-프로젝트-구조)

<br><br>

# 1. 개발 환경

- JAVA 17
- springboot 3.2.1
- gradle
- MySQL
- Redis
<br><br>

# **2. 주요 기능**

| 번호 | Method | URL | **Authorization** | Description |
| --- | --- | --- | --- | --- |
| 1 | POST | localhost:8080/users/join |  | 사용자 회원가입 |
| 2 | POST | localhost:8080/login/{provider} | ✔️ | 사용자 로그인 및 토큰 발급 |
| 3 | POST | localhost:8080/orders/ | ✔️ | 주문 생성 |
| 4 | GET | localhost:8080/orders/{orderId} | ✔️ | 주문 상세 조회 |
| 5 | POST | localhost:8080/carts/{cartId}/items | ✔️ | 장바구니에 상품 추가 |
| 6 | GET | localhost:8080/carts/user/{userId} | ✔️ | 사용자 장바구니 조회 |
| 7 | POST | localhost:8080/carts/{cartId}/convert-to-order | ✔️ | 장바구니를 주문으로 변환 |
| 8 | DELETE | localhost:8080/orders/{orderId} | ✔️ | 주문 취소 |
| 9 | GET | localhost:8080/reviews/user/{userId} | ✔️ | 사용자 리뷰 조회 |

<br>

# 3. 브랜치 전략

- main : 개발 단계
- refactor : 리팩토링 단계
<br><br>

**Commit**

| Commit Type | Description |
| --- | --- |
| feat | 새로운 기능 |
| refactor | 기능, 코드 개선 |
| docs | 문서 수정 |
| fix | 기능 수정 |
<br>

# 5. 프로젝트 구조
```jsx
├─main
│  ├─generated
│  │  └─project
│  │      └─shop1
│  │          └─domain
│  │              ├─address
│  │              │  └─entity
│  │              ├─auth
│  │              │  └─emailAuth
│  │              │      └─entity
│  │              ├─cart
│  │              │  └─entity
│  │              ├─order
│  │              │  └─entity
│  │              ├─product
│  │              │  └─entity
│  │              ├─review
│  │              │  └─entity
│  │              └─user
│  │                  └─entity
│  ├─java
│  │  └─project
│  │      └─shop1
│  │          ├─config
│  │          ├─domain
│  │          │  ├─address
│  │          │  │  ├─controller
│  │          │  │  ├─dto
│  │          │  │  │  ├─request
│  │          │  │  │  └─response
│  │          │  │  ├─entity
│  │          │  │  ├─repository
│  │          │  │  │  └─impl
│  │          │  │  └─service
│  │          │  │      └─impl
│  │          │  ├─auth
│  │          │  │  ├─emailAuth
│  │          │  │  │  ├─controller
│  │          │  │  │  ├─dto
│  │          │  │  │  │  └─request
│  │          │  │  │  ├─entity
│  │          │  │  │  ├─repository
│  │          │  │  │  └─service
│  │          │  │  │      └─impl
│  │          │  │  ├─login
│  │          │  │  │  ├─common
│  │          │  │  │  ├─controller
│  │          │  │  │  ├─dto
│  │          │  │  │  │  ├─request
│  │          │  │  │  │  └─response
│  │          │  │  │  ├─enums
│  │          │  │  │  └─service
│  │          │  │  │      ├─common
│  │          │  │  │      └─impl
│  │          │  │  └─logout
│  │          │  │      └─controller
│  │          │  ├─cart
│  │          │  │  ├─controller
│  │          │  │  ├─dto
│  │          │  │  │  ├─request
│  │          │  │  │  └─response
│  │          │  │  ├─entity
│  │          │  │  ├─repository
│  │          │  │  │  └─impl
│  │          │  │  └─service
│  │          │  │      └─impl
│  │          │  ├─order
│  │          │  │  ├─controller
│  │          │  │  ├─dto
│  │          │  │  │  ├─request
│  │          │  │  │  └─response
│  │          │  │  ├─entity
│  │          │  │  ├─enums
│  │          │  │  ├─repository
│  │          │  │  │  └─impl
│  │          │  │  └─service
│  │          │  │      └─impl
│  │          │  ├─product
│  │          │  │  ├─controller
│  │          │  │  ├─dto
│  │          │  │  │  ├─RequestDto
│  │          │  │  │  └─ResponseDto
│  │          │  │  ├─entity
│  │          │  │  ├─repository
│  │          │  │  │  └─impl
│  │          │  │  └─service
│  │          │  │      └─impl
│  │          │  ├─review
│  │          │  │  ├─controller
│  │          │  │  ├─dto
│  │          │  │  │  ├─request
│  │          │  │  │  └─response
│  │          │  │  ├─entity
│  │          │  │  ├─repository
│  │          │  │  │  └─impl
│  │          │  │  └─service
│  │          │  │      └─impl
│  │          │  └─user
│  │          │      ├─controller
│  │          │      ├─dto
│  │          │      │  ├─request
│  │          │      │  └─response
│  │          │      ├─entity
│  │          │      ├─enums
│  │          │      ├─repository
│  │          │      └─service
│  │          │          └─impl
│  │          └─global
│  │              ├─exception
│  │              ├─security
│  │              │  ├─jwt
│  │              │  │  ├─dto
│  │              │  │  └─handler
│  │              │  └─redis
│  │              │      ├─configuration
│  │              │      ├─dto
│  │              │      ├─repository
│  │              │      └─service
│  │              └─util
│  │                  ├─interceptor
│  │                  ├─reponse
│  │                  └─validation
│  └─resources
│      ├─static
│      └─templates
└─test
    ├─generated_tests
    ├─java
       └─project
           └─shop1
               └─domain
                   ├─auth
                   │  └─service
                   │      └─impl
                   ├─cart
                   │  └─service
                   │      └─impl
                   ├─order
                   │  └─service
                   │      └─impl
                   ├─product
                   │  └─service
                   │      └─impl
                   ├─review
                   │  └─service
                   │      └─impl
                   └─user
                       └─service
                           └─impl
```
