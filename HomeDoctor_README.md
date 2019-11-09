# HomeDoctor Engineering Wiki

## 개발 환경

- Java 1.8 (OpenJDK)
- Android Studio 3.5.2 ([https://developer.android.com/studio](https://developer.android.com/studio))
- Gradle
- Firebase (Auth, Database, Hosting)

## 프로젝트

### 개요

- 대응 버전 (SDK)
    - Min 23 (Mashmallow)
    - Target 28 (Pie/9.0)
- 단말 방향(Orientation) : Only Portrait
- 버전 관리 : #.#.# (Major.Minor.Build)

### 구조

- Activity : 화면단위

## Git 사용

### 구조

- 총 3개의 브랜치로 구성.
1. dev : 개발 단계에서 사용하는 브랜치
2. master : 마이너 버전에 사용되는 브랜치
3. release : 배포 버전에 사용되는 브랜치
- Commit Comment에는 시간을 입력하지 않으며 작업 또는 수정한 내용을 명확하게 적는다.

## Coding 가이드

### 명명법

코드의 통일성을 갖추기 위함으로 아래와 같이 명명할 것을 권고한다.

1. Value, Function
- CamelCasing

        public void examFuncName() {
            String name = "";
        }

2. Class, Protocol, Struct, Enum

- Pascal Casing

        public enum ExamEnum {
            case one,
            case two
        }

        class MainViewController extends Activity{
            struct ExamStruct {
            }
        }

3. Constant

- GNU Naming Convention + UnderBar

        static String ID_TEST_MAIN = "idTestMain"

4. File name

- xml file: 파일타입_파일이름, snake_case

        activity_main.xml
        fragment_tutorail1.xml
        item_basket.xml
        dialog_purchase.xml

- Java file: 파일이름 파일타입, PascalCase

        MainActivity.java
        FramentTutorial.java
        BasketItem.java
        BasketAdapter.java

### 주석

모든 클래스와 함수, 변수, enum, struct등 주석을 추가하는 것을 원칙으로 한다.

1. 클래스, 변수, enum, struct

        // 예시 클래스
        class examClass {
            // 예시 변수
            String examValue;
        
            // 구조체
            struct StoryBoard {
                static String main = "Main";
            }
        }

2. 함수

        /**
         * Description
         *
         * @param abc
         * @param deg
         * @return
         */
        public void onView(abc, deg) {
        }

# 추가중....