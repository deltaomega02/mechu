# Mechu — AI 메뉴 추천 앱

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white) ![OpenAI](https://img.shields.io/badge/OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white)

사용자 프로필(신체 정보·식습관)을 기반으로 AI 챗봇과 대화하며 식사 메뉴를 추천받는 Android 앱.
3인 팀 프로젝트(2학년) — 팀장으로 기획부터 구현까지 대부분의 기능을 직접 개발했다.
처음으로 AI API를 실제 서비스에 통합해 본 프로젝트로, 이후 모든 AI 활용 프로젝트의 출발점.

## 기술 스택

| 영역 | 기술 | 용도 |
|---|---|---|
| 클라이언트 | Android (Java) | 네이티브 앱 |
| AI | OpenAI GPT-4-turbo (chat/completions) | 대화형 메뉴 추천 챗봇 |
| DB | SQLite | 사용자 프로필·메뉴 데이터 로컬 저장 |
| 인증 | JavaMail (SMTP) | 회원가입 이메일 인증 |
| UI | Custom View (Canvas) | 반원형 영양 게이지 직접 구현 |

## 동작 방식

```
[회원가입] SignUp1~3 → 이메일 인증(SMTP) → 신체정보·식습관 입력 → SQLite 저장
[추천]    Recommend → 프로필 데이터를 프롬프트에 포함 → GPT-4-turbo 호출 → 맞춤 메뉴 제안
[채팅]    Chatting  → 자유 대화로 추천 이유를 묻거나 조건 변경 ("매운 건 빼줘")
[상세]    MenuDetail → 메뉴 영양 정보를 반원형 게이지(HalfCircleGaugeView)로 시각화
```

- 추천의 개인화는 프롬프트 엔지니어링으로 구현 — 사용자 프로필(키·몸무게·선호)을 시스템 프롬프트에 주입해 같은 질문에도 사용자별 다른 답을 받는다.
- `HalfCircleGaugeView`는 라이브러리 없이 Canvas API로 직접 그린 커스텀 뷰.

## 프로젝트 구조

```
mechu/app/src/main/java/com/example/mechu_project/
├── Introductory / SignUp1~3 / SignUpEnd   # 온보딩 + 이메일 인증 가입 플로우
├── MainActivity / Search / ShowDetail     # 홈, 메뉴 검색·상세
├── Recommend                              # AI 메뉴 추천
├── Chatting + adapter/ + model/           # GPT 챗봇 (메시지 모델·어댑터)
├── MenuDetail / HalfCircleGaugeView       # 영양 정보 시각화 (커스텀 뷰)
├── DatabaseHelper                         # SQLite 스키마·CRUD
└── Profile / ImageUtils                   # 프로필 관리
```

## 실행

Android Studio에서 열고 `Chatting.java`의 OpenAI API 키 placeholder를 본인 키로 교체 후 빌드.

## 한계와 배운 것

- API 키를 클라이언트에 두는 구조의 위험(키 노출, 과금)을 이후에 알게 됐다 — 다음 프로젝트(Rubato)부터 서버를 두고 키를 서버에만 보관한 이유.
- 채팅 히스토리를 매 요청에 어떻게 실어 보낼지(토큰 한도) 처음 고민해 본 프로젝트.
