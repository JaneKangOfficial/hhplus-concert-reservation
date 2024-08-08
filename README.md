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


<details>
  <summary>Redis</summary>

### 1. 대량의 트래픽 발생시 지연이 발생할 수 있는 조회쿼리에 대한 분석 및 캐싱 전략

1. 쿼리 분석
    - 콘서트 목록 조회
        - 조회 많음, 변동 가능성 많지 않음
        - 일정 기간 같은 데이터를 주고 빠르게 조회가 가능한 `@Cacheable`에 적합하다고 판단

    - 각 콘서트에 대한 이용 가능한 날짜/좌석 조회
        - 조회 많음, 변동 가능성 많음
        - 조회마다 새로운 정보를 가져와야 한다.

    - 좌석 예약
        - 조회 많음, 변동 가능성 많음, 사용자가 일정시간 점유 가능
        - 사용자가 점유하는 짧은 시간이어도 다른 사용자의 많은 조회를 `@Cacheable`로 막을 수 있다.
        - 비관적 락이기 때문에 DB 조회를 하지 않게 되면서 좌석 조회가 가능해진다.


2. 캐싱전략
    1. 메모리 캐시
        2. 서버가 여러대일 경우 서로 `다른` 데이터를 사용할 수 있다.

    2. 별도의 캐시 서비스 `Redis`
        3. 서버가 다르더라도 같은 Cache Service를 사용하기 때문에 `같은` 데이터를 사용할 수 있다.


- DB 저장소에 접근하는 시간을 줄이기 위해 임시 저장소에 데이터를 저장한다.
- 임시 저장소에 데이터가 없다면 `요청 -> 임시 저장소 -> DB` 순으로 조회한다.
- 임시 저장소에 데이터가 있다면 `요청 -> 임시 저장소` 순으로 DB에 접근하지 않아 시간을 줄일 수 있다.
- 데이터 변동이 발생하는 로직에 캐시를 삭제하는 `@CacheEvict` 을 작성하여 다음 조회에서 새로운 데이터를 캐시로 생성할 수 있도록 한다.

### 2. 대기열 시스템을 제공하기 위한 적절한 설계

- `zadd(key, score, member)` : sorted set의 자료구조로 새로운 key를 생성한다.
- `zrank(key, member)` : 순서를 반환한다.
- `zrange(key, start, stop)` : 범위만큼 목록을 가져온다.
- `zrangeWithScores(key, start, stop)` : 범위만큼 score와 목록을 가져온다.
- `expire(key, seconds)` : TTL 적용하여 만료시킨다.
- `zrem(key, members)` : key를 삭제한다.

  > 1. 사용자 요청시 대기열에 사용자가 있는지 확인한다.
  >2. 있다면 순서를 반환하고, 없다면 생성한다.
  >3. @Scheduled 를 사용하여 대기열에서 30초마다 50명씩 가져온다.
  >4. 대기상태에서 삭제하고 TTL과 함께 활성상태로 생성한다.

</details>

<details>
  <summary>Index</summary>

### 1. EXPLAIN 이해

> type : 테이블 조인 시 사용되는 연산 유형, 테이블에서 아래로 갈수록 성능이 더 좋다.

|              |               |
|:------------:|:-------------:|
|     ALL      |   전체 테이블 스캔   |
|    index     |    인덱스 스캔     | 
|    range     |     범위 스캔     | 
|     ref      |    인덱스 탐색     | 
|    eq_ref    | 정확한 일치 인덱스 탐색 | 
| const/system |   상수 테이블 접근   | 

> possible_keys : 쿼리 실행에 사용될 수 있는 인덱스의 목록을 나타낸다. 이 컬럼이 비어 있으면 인덱스가 없다는 의미

> rows : 쿼리 실행 시 이만큼의 rows를 읽었다. 이 값이 클수록 성능이 저하될 가능성이 있다.

> filtered : 조건을 만족하는 행의 비율을 나타낸다. 필터링된 행의 비율 (100이면 100% 필터링 되었다 = PK)

> Extra : 쿼리의 실행 과정에서 추가적인 정보를 제공한다. 주로 쿼리 실행의 세부 사항을 나타낸다.

|                       |                                 |
|:---------------------:|:-------------------------------:|
|      Using where      |          WHERE 절이 사용됨           |
|      Using index      |        인덱스만 사용하여 데이터를 읽음        | 
|    Using filesort     |         정렬을 위한 파일 정렬 사용         |
|    Using temporary    |      임시 테이블을 사용하여 쿼리를 처리함       | 
| Using index condition | 인덱스를 스캔하면서 쿼리의 조건을 적용하여 성능을 개선함 |

### 2. 예약 가능 좌석 조회

```

SELECT COUNT(*) FROM seat; -- 100000건
SELECT COUNT(*) FROM concert_option; -- 100000건

EXPLAIN SELECT *
FROM seat s
JOIN concert_option co 
     ON s.seat_concert_option_id = co.id
WHERE co.concert_id = 90000 
     AND s.seat_concert_option_id = 90000 
     AND s.status = 'AVAILABLE';

```

---

##### 인덱스 설정 전

- `평균 속도 : 294.6ms`
- mysql은 innoDB로 처음 실행 시에 데이터를 메모리에 적재시켜 다음 실행 시 소요되는 속도를 줄일 수 있다고 한다.
- 그래서 인덱스보다 살짝 빠를 수는 있지만 인덱스가 월등히 빠르다.
- innoDB 사용 확인은 `show engines;` 명령어로 확인이 가능하다

| type  | possible_keys | rows  | filtered |    Extra    |
|:-----:|:-------------:|:-----:|:--------:|:-----------:|
| const |    PRIMARY    |   1   |   100    |             |
|  ALL  |               | 99822 |    5     | Using where |

- const은 가장 빠른 type이다. PRIMARY 사용으로 단 하나의 rows 를 읽었으며 100% 필터링하였다. = 빠르다
- type이 ALL로 데이터 풀스캔을 하였고 Using where 조건절 사용으로 99822의 rows를 읽었다 = 느리다

--- 

##### 하나의 인덱스 설정 후

- idx_seat_concert_option_id_status 만 설정하였을 경우
- `평균 속도 : 295.3ms`


- idx_seat_concert_option_id_status (seat_concert_option_id, status)
    - seat_id는 PK로 하나의 값만 가지며 높은 카디널리티를 가졌다. 이미 index 설정이 되어 있다
    - seat_concert_option_id는 FK로 조건절에서 조인하기 위해 자주 사용한다
    - status는 seat_concert_option_id 별로 좌석의 상태값을 조회해야 하므로 index 설정

| type  |           possible_keys           | rows  | filtered |         Extra         |
|:-----:|:---------------------------------:|:-----:|:--------:|:---------------------:|
| const |              PRIMARY              |   1   |   100    |                       |
|  ref  | idx_seat_concert_option_id_status | 18810 |   100    | Using index condition |

- const은 가장 빠른 type이다. PRIMARY 사용으로 단 하나의 rows 를 읽었으며 100% 필터링하였다. = 빠르다
- idx_seat_concert_option_id_status 인덱스 사용으로 18810 rows를 100% 필터링하였다. = 인덱스 설정 전 풀스캔보다 읽는 rows가 적다 = 빠르다

--- 

##### 두 개의 인덱스 설정 후

- idx_seat_concert_option_id_status idx_concert_option_concert_id 설정하였을 경우
- `평균 속도 : 237.2ms`
- `속도가 확실히 빨라졌다!`


- idx_seat_concert_option_id_status (seat_concert_option_id, status)
    - seat_id는 PK로 하나의 값만 가지며 높은 카디널리티를 가졌다. 이미 index 설정이 되어 있다
    - seat_concert_option_id는 FK로 조건절에서 조인하기 위해 자주 사용한다
    - status는 seat_concert_option_id 별로 좌석의 상태값을 조회해야 하므로 index 설정

- idx_concert_option_concert_id (concert_id)
    - id는 PK로 하나의 값만 가지며 높은 카디널리티를 가졌다. 이미 index 설정이 되어 있다
    - concert_id는 FK로 조건절에서 조인하기 위해 자주 사용한다

| type  |             possible_keys              | rows  | filtered |         Extra         |
|:-----:|:--------------------------------------:|:-----:|:--------:|:---------------------:|
| const | PRIMARY, idx_concert_option_concert_id |   1   |   100    |                       |
|  ref  |   idx_seat_concert_option_id_status    | 18810 |   100    | Using index condition |

- const은 가장 빠른 type이다. PRIMARY 와 idx_concert_option_concert_id 인덱스 사용으로 단 하나의 rows 를 읽었으며 100% 필터링하였다. = 빠르다
- idx_seat_concert_option_id_status 인덱스 사용으로 18810 rows를 100% 필터링하였다 = 빠르다
- WHERE 조건절에서 가장 먼저 사용되는 concert_id를 인덱스 설정하여 속도를 더 단축하였다 = 빠르다
- SELECT * 를 사용하면 모든 컬럼을 가져와야 하기 때문에 느려질 수 있으므로 필요한 컬럼만 작성하는 것이 좋다

</details>

<details>
  <summary>Event Driven</summary>

### 1. 서비스를 분리할 트랜잭션

##### 결제 트랜잭션

- 중요한 메인 로직(결제)과 메인과는 상관없는 부가 로직(히스토리 저장)이 같은 Transaction에 존재한다
- 만약 부가 로직 실행 시간이 길면 메인 로직 실행 또한 느려진다
- 하나의 단일 트랜잭션이기 때문에 메인 로직이 실패하면 부가 로직이 롤백되고, 부가 로직이 실패하면 메인 로직이 롤백된다
- 하지만 부가 로직이 실패한다고 메인 로직이 롤백될 필요는 없다
- 데이터의 일관성을 보장할 수 없다

### 2. 서비스 분리 (MSA : MicroService Architecture)

- 서비스 규모가 확장되면 여러 개의 작은 규모 서비스로 분리할 필요가 있다
- 각 서비스마다 분리하여 관리한다 (예약 서비스, 결제 서비스, 좌석 서비스,..)
- 부하가 생기는 서비스만 확장하여 성능을 최적화할 수 있다
- 독립적 운영으로 문제가 발생하더라도 다른 서비스에 영향을 미치지 않는다
- 데이터의 일관성을 관리할 수 있다

### 3. Event Driven

- 이벤트 발행 서비스, 이벤트 구독 서비스가 있다
- 비동기로 처리되기 때문에 이벤트를 발행한 후 즉시 다른 작업을 수행할 수 있다 = 처리 속도가 빠르다
- 특정 이벤트에 부하가 생기면 해당 이벤트만 확장하여 부하를 해소할 수 있다
- 부가 로직을 Event Driven 방식으로 수정한다
- 트랜잭션을 분리하고 이벤트에 AFTER_COMMIT 을 사용하여 메인 로직이 COMMIT된 이후에 부가 로직을 실행하도록 한다

</details>