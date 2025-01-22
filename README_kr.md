# CosmosCore 🌌
CosmosCore는 천체 계산, 궤도 역학, 천체 관측을 위한 Java 라이브러리입니다.

[README_en](README.md)

## 모듈
- **cosmoscore-common**: 공통 유틸리티 및 기본 연산
- **cosmoscore-position**: 천체 위치 계산 및 좌표계 변환
- **cosmoscore-orbit**: 궤도 역학 계산 및 시뮬레이션
- **cosmoscore-observer**: 천체 관측 조건 및 추천 시스템

## 시작하기
### 요구사항
- Java 21 이상
- Gradle 7.3 이상

### 빌드
```bash
./gradlew build
```

### 테스트
```bash
./gradlew test
```

## 사용법
```java
// 태양 위치 계산
SunPosition sunPosition = new SunPosition();
EquatorialCoordinate position = sunPosition.calculate(LocalDateTime.now());

// 케플러 궤도 요소 계산
KeplerianElements elements = new KeplerianElements(
    6378.137 + 400, // 반장축 (km)
    0.0006, // 이심률
    51.6, // 궤도 경사각 (도)
    0 // 근지점 편각 (도)
);
```

## 라이센스
이 프로젝트는 MIT 라이센스로 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 기여하기
프로젝트에 기여하고 싶다면 Pull Request를 올려주세요. 얼마든지 환영입니다!