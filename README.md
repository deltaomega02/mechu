![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white) ![SQLite](https://img.shields.io/badge/SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white) ![OpenAI](https://img.shields.io/badge/OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white)

# MECHU (메추) - AI 메뉴 추천 안드로이드 앱

사용자의 기분과 상황에 맞는 메뉴를 추천해 주는 안드로이드 앱입니다.  
전문학사 3학년 1학기 3인 팀 프로젝트로 진행했으며, 팀장을 맡았습니다.  
외부 AI API를 실제 모바일 서비스에 처음 연동해 본 프로젝트입니다.

---

## 🛠 기술 스택

- **Language / Platform**: Java, Android SDK
- **AI**: OpenAI `gpt-4-turbo` (`chat/completions` 엔드포인트를 OkHttp로 직접 호출)
- **Local DB**: SQLite
- **Mail**: JavaMail (SMTP)
- **UI / Animation**: Lottie, Custom Canvas View

---

## 📱 구현 기능

### 1. 초기 실행 및 로컬 데이터 세팅
- 첫 화면에 진입하면 Lottie 기반 로고 애니메이션이 재생된 뒤 로그인 영역이 표시됩니다. (현재 회원가입 버튼과 채팅 진입 버튼은 동작하며, 로그인 버튼은 UI만 배치되어 있습니다.)
- 앱을 처음 실행할 때 약 200개의 메뉴 데이터(이름, 칼로리, 탄수화물, 단백질, 지방, 카테고리)를 SQLite에 적재합니다. 데이터 적재는 트랜잭션과 백그라운드 작업으로 처리하며, 메뉴 이미지 파일은 앱 내부 저장소(Internal Storage)에 저장해 불러옵니다.

### 2. AI 채팅 및 맞춤 메뉴 추천
- 사용자가 기분이나 상황을 자연어로 입력하면 SQLite에 등록된 메뉴 이름 목록을 프롬프트 컨텍스트에 포함해 GPT 모델에 전달합니다.
- 모델에 목록의 메뉴 중 1개를 골라 추천 사유와 함께 반환하도록 지시하고 응답 텍스트에서 첫 번째 `**메뉴명**` 패턴을 파싱합니다.
- 파싱한 메뉴명과 일치하는 로컬 DB 데이터를 찾아 텍스트 답변 아래에 이미지와 칼로리가 포함된 메뉴 카드를 동적으로 렌더링합니다.
- 메뉴 카드를 터치하면 해당 음식의 상세 영양 정보를 확인할 수 있는 상세 화면(`ShowDetail`)으로 이동합니다.

### 3. 영양 정보 상세 (`ShowDetail`)
- 단백질, 탄수화물, 지방 함량을 가로형 진행 막대(ProgressBar) UI로 시각화해 보여줍니다.

### 4. 3단계 회원가입
- **1단계**: 약관 동의
- **2단계**: 아이디, 비밀번호, 이메일 입력 (SMTP 기반 인증번호 발송 및 입력 필드 구성)
- **3단계**: 성별, 키, 몸무게, 목표 체중, 운동 목표 입력 후 SQLite 저장
- 가입이 완료되면 메인 홈 화면으로 이동합니다.

### 5. 홈 화면
- 하단 탭 내비게이션(홈, 검색, 메뉴추천, 내정보)이 구성되어 있습니다.
- 상단에는 Canvas로 직접 구현한 반원형 커스텀 게이지 뷰가 배치되어 있습니다. (현재는 50% 고정 수치로 렌더링됩니다.)

---

## 🔍 한계점 및 아쉬운 점

프로젝트를 진행하면서 끝까지 구현하지 못했거나 추가로 보완이 필요하다고 느낀 부분입니다.

- **미완성 화면 및 미사용 코드**: 하단 탭 중 검색, 메뉴추천, 내정보 화면과 `MenuDetail` 액티비티는 기본 뷰 틀만 잡혀 있고 실제 기능은 연결되지 않았습니다.
- **추천 로직의 개인화 부재**: 회원가입 시 입력받은 신체 스펙과 운동 목표는 로컬 DB에 저장되지만, 프롬프트를 생성할 때 추천 조건으로 활용하지는 못했습니다.
- **대화 맥락 미유지 (Stateless)**: 채팅 요청 시 이전 대화 기록을 누적하지 않고 매번 단일 메시지만 전달하기 때문에 직전 대화의 흐름을 기억하지 못합니다.
- **클라이언트 내 시크릿 키 하드코딩**: OpenAI API Key와 SMTP 계정 정보를 클라이언트 코드에 직접 작성해 빌드하는 방식을 사용했습니다. 역컴파일이나 코드 유출 시 키가 탈취될 위험이 있다는 점을 알게 되었고, 이를 계기로 다음 프로젝트(Rubato)부터는 별도의 백엔드 서버를 두고 요청을 중계하는 구조로 개선했습니다.

---

## 🚀 실행 방법

1. 프로젝트를 clone한 뒤 Android Studio로 엽니다.
2. 아래 파일의 플레이스홀더를 본인의 실제 인증 정보로 변경합니다.
   - `Chatting.java`: `YOUR_OPENAI_API_KEY`에 본인 OpenAI API 키 입력
   - `SignUp2.java`: `YOUR_NAVER_ACCOUNT@naver.com` 및 `YOUR_NAVER_APP_PASSWORD`에 본인 네이버 계정/2단계 앱 비밀번호 입력
3. Gradle Sync 후 기기 또는 에뮬레이터에서 빌드 및 실행합니다.
