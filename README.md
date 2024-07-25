<details>
  <summary>요구사항</summary>

## Description

- **`콘서트 예약 서비스`**를 구현해 봅니다.
- 대기열 시스템을 구축하고, 예약 서비스는 작업가능한 유저만 수행할 수 있도록 해야합니다.
- 사용자는 좌석예약 시에 미리 충전한 잔액을 이용합니다.
- 좌석 예약 요청시에, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 합니다.

## Requirements

- 아래 5가지 API 를 구현합니다.
    - 유저 토큰 발급 API
    - 예약 가능 날짜 / 좌석 API
    - 좌석 예약 요청 API
    - 잔액 충전 / 조회 API
    - 결제 API
- 각 기능 및 제약사항에 대해 단위 테스트를 반드시 하나 이상 작성하도록 합니다.
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.
- 대기열 개념을 고려해 구현합니다.

## API Specs

1️⃣**`주요` 유저 대기열 토큰 기능**

- 서비스를 이용할 토큰을 발급받는 API를 작성합니다.
- 토큰은 유저의 UUID 와 해당 유저의 대기열을 관리할 수 있는 정보 ( 대기 순서 or 잔여 시간 등 ) 를 포함합니다.
- 이후 모든 API 는 위 토큰을 이용해 대기열 검증을 통과해야 이용 가능합니다.

> 기본적으로 폴링으로 본인의 대기열을 확인한다고 가정하며, 다른 방안 또한 고려해보고 구현해 볼 수 있습니다.
>

**2️⃣`기본` 예약 가능 날짜 / 좌석 API**

- 예약가능한 날짜와 해당 날짜의 좌석을 조회하는 API 를 각각 작성합니다.
- 예약 가능한 날짜 목록을 조회할 수 있습니다.
- 날짜 정보를 입력받아 예약가능한 좌석정보를 조회할 수 있습니다.

> 좌석 정보는 1 ~ 50 까지의 좌석번호로 관리됩니다.
>

3️⃣**`주요` 좌석 예약 요청 API**

- 날짜와 좌석 정보를 입력받아 좌석을 예약 처리하는 API 를 작성합니다.
- 좌석 예약과 동시에 해당 좌석은 그 유저에게 약 5분간 임시 배정됩니다. ( 시간은 정책에 따라 자율적으로 정의합니다. )
- 만약 배정 시간 내에 결제가 완료되지 않는다면 좌석에 대한 임시 배정은 해제되어야 하며 다른 사용자는 예약할 수 없어야 한다.

4️⃣**`기본`**  **잔액 충전 / 조회 API**

- 결제에 사용될 금액을 API 를 통해 충전하는 API 를 작성합니다.
- 사용자 식별자 및 충전할 금액을 받아 잔액을 충전합니다.
- 사용자 식별자를 통해 해당 사용자의 잔액을 조회합니다.

5️⃣**`주요` 결제 API**

- 결제 처리하고 결제 내역을 생성하는 API 를 작성합니다.
- 결제가 완료되면 해당 좌석의 소유권을 유저에게 배정하고 대기열 토큰을 만료시킵니다.

<aside>
💡 **KEY POINT**

</aside>

- 유저간 대기열을 요청 순서대로 정확하게 제공할 방법을 고민해 봅니다.
- 동시에 여러 사용자가 예약 요청을 했을 때, 좌석이 중복으로 배정 가능하지 않도록 합니다.

</details>



<details>
  <summary>Milestone</summary>

[Milestone](https://github.com/users/JaneKangOfficial/projects/3)
</details>



<details> 
  <summary>시퀀스 다이어그램</summary>

### 1. 토큰 발급 API

![1 토큰 발급](https://github.com/JaneKangOfficial/hhplus-concert-reservation/assets/50077963/678f952c-f307-413a-8b06-7a8dfdc4c1fd)

### 2. 예약 가능 날짜 / 좌석 API

![2 예약가능날짜:좌석](https://github.com/JaneKangOfficial/hhplus-concert-reservation/assets/50077963/677ffee7-894b-4200-8b06-629b347bc68f)

### 3. 좌석 예약 요청 API

![3 좌석예약요청](https://github.com/JaneKangOfficial/hhplus-concert-reservation/assets/50077963/73b7598d-1efc-45b5-b032-2cf26da6eea7)

### 4. 잔액 충전 / 조회 API

![4 잔액충전:조회](https://github.com/JaneKangOfficial/hhplus-concert-reservation/assets/50077963/8449b9ad-4173-442f-a6f2-741e3495e5cf)

### 5. 결제 API

![5 결제](https://github.com/JaneKangOfficial/hhplus-concert-reservation/assets/50077963/07baa628-0af0-4a72-886a-d978bfcb1ec9)

</details>



<details>
  <summary>ERD 다이어그램</summary>

[ERD](https://dbdiagram.io/d/concert3-668552fb9939893daef24953)
<img width="981" alt="" src="https://github.com/JaneKangOfficial/hhplus-concert-reservation/assets/50077963/962f1400-b055-4618-8375-1ce71f2d5df0">

</details>



<details>
  <summary>API 명세</summary>

### 1. 유저 대기열 토큰 ###

|         |                             |
|---------|-----------------------------|
| GET     | /api/concert/token/{userId} |
| Request | Long userId                 |               

```
Response
{
    "userId": 1,
    "token": "token"
}
```

### 2. 예약 가능 날짜 API ###

|               |                   |
|---------------|-------------------|
| GET           | /api/concert/date |
| Request       | Long concertId    |
| Authorization | "token"           |

```
Response
{
    "dateId": [
        1,
        2
    ],
    "concertId": 1,
    "availableDates": [
        "2024-06-20",
        "2024-06-21"
    ]
}
```

### 3. 예약 가능 좌석 API ###

|               |                             |
|---------------|-----------------------------|
| GET           | /api/concert/seat           |
| Request       | Long concertId, Long dateId |
| Authorization | "token"                     |

```
Response
{
    "concertId": 1,
    "dateId": 1,
    "seatId": [
        1,
        2,
        3
    ]
}
```

### 4. 좌석 예약 요청 API ###

|               |                          |
|---------------|--------------------------|
| POST          | /api/concert/reservation |
| Authorization | "token"                  |

```
Request
{
    "userId":1,
    "concertId": 1,
    "dateId": 1,
    "seatId": 1
}

```

```
Response
{
    "reservationId": 1,
    "userId": 2,
    "concertId": 1,
    "dateId": 3,
    "seatId": 5,
    "status": "APPLY"
}
```

### 5. 잔액 충전 API ###

|               |                    |
|---------------|--------------------|
| POST          | /api/concert/point |
| Authorization | "token"            |

```
Request
{
    "userId":1,
    "point": 100,
    "status": "CHARGE"
}
```

```
Response
{
    "total": 1000
}
```

### 6. 잔액 조회 API ###

|         |                      |
|---------|----------------------|
| GET     | /api/concert/balance |
| Request | Long userId          |

```
Response
{
    "total": 1000
}
```

### 5. 결제 API ###

|               |                      |
|---------------|----------------------|
| POST          | /api/concert/payment |
| Authorization | "token"              |

```
Request
{
    "userId":1,
    "concertId": 1,
    "dateId": 1,
    "seatId": 1
}
```

```
Response
{
    "paymentId": 1,
    "userId": 1,
    "concertId": 1,
    "dateId": 1,
    "seatId": 1,
    "total": 10000,
    "status": "COMPLETE"
}
```

</details>

<details>
  <summary>동시성 문제</summary>

### 1. 콘서트 서비스에서 동시성 이슈 발생할 수 있는 로직

- 포인트 충전/사용
- 좌석 예약

### 2. Transaction의 범위, 낙관적 락, 비관적 락 테스트 검증

1) 낙관적 락

- @Version - 읽기 가능, 수정 불가능
- Transaction의 범위 : Select, Update
- 동시에 진입해서 Version 0을 읽은 여러 Thread들 중에서 처음 Update한 Thread만 성공
- Update 성공 후에 Version 1로 변경됨
- 이후 나머지 Version 0은 Rollback으로 실패
- Version 1을 읽은 여러 Thread들 중에서 처음 Update한 Thread만 성공
- Update 성공 후에 Version 2로 변경됨

```
Thread 1 { Select Vesrion0    Update     END }
Thread 2 { Select Vesrion0                     Update Rollback }
Thread 3 {                                     Select Vesrion1       Update       END }
Thread 4 {                                     Select Vesrion1                          Update Rollback }
```

2) 비관적 락

- @Lock(LockModeType.PESSIMISTIC_WRITE) - 읽기, 쓰기 모두 불가능

- Transaction의 범위 -> Select, Update
- Thread 1 {Select -> Update -> Thread 종료} -> Thread 2 {Select -> Update -> 스레드 종료} 가 순서대로 일어남
- 실패 없이 모든 Thread Update
- Thread 종료까지 대기해야 해서 범위가 크면 Timeout과 DB Connection Pool 점령 등 전체적인 성능 저하 문제가 발생

```
Thread 1 { Select    Update     END }
Thread 2 {                           Select    Update     END }
Thread 3 {                                                     Select    Update     END }
```

### 3. 결론

1. 포인트 충전/사용

- 낙관적 락 평균 소요 시간 (100회) : 280ms
- 비관적 락 평균 소요 시간 (100회) : 558ms

> 포인트 충전/사용은 들어오는 요청을 실패 없이 순서대로 처리해야 하는 로직이기 때문에 `비관적 락`이 적합하다고 판단

2. 좌석 예약

- 낙관적 락 평균 소요 시간 (100인) : 119ms
- 비관적 락 평균 소요 시간 (100인) : 272ms

> 좌석 예약은 1명만 성공하고 나머지는 실패해야 하는 로직이기 때문에 `낙관적 락`이 적합하다고 판단


</details>
