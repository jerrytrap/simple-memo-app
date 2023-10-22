# Simple Memo App
```
📝 정말 간단한 메모 앱입니다.
```

## 주요 기능
  - 메모 목록 표시 (제목 + 생성 시간)
  - 메모 생성, 수정, 삭제 기능
  - 메모 목록에서 항목 선택 시 제목 및 내용 표시

## 기술 스택
| 브랜치                    | 사용 기술                                                      |
|------------------------|------------------------------------------------------------|
| rxjava-livedata        | RxJava2, LiveData                                          |
| rxjava-livedata-paging | RxJava2, LiveData, Paging3                                 |
| coroutine-flow         | Kotlin Coroutines, Kotlin Flow, StateFlow                  |
| coroutine-flow-paging  | Kotlin Coroutines, Kotlin Flow, StateFlow, Paging3         |
| flow-compose           | Kotlin Coroutines, Kotlin Flow, StateFlow, Jetpack Compose |

- 공통
  - MVVM
  - Clean Architecture (Multi Module)
  - Room
  - Hilt
