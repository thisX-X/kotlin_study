package com.fcm.simpleblog.config.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig {

    /**
     * ==== back ====
     * 필터와 인텃헵터를 이용해서 만든 인증처리 관련 프레임 워크를 스프링에서 지원 (스프링 시큐리티)
     * 1. 비동기 처리
     * 2. 파일 핸들링
     * 3. sse event + web socket을 활용한 실시간 채봇
     * 4. aws 배포
     * 5. actuator + admin-server를 통한 간단한 모니터링
     * 6. code deploy + github action을 통한 CI/CD
     * 7. 스프링 시큐리티 + JWT 인증처리
     * 8. Junit + mockk 테스트 환경 설정
     * 9. restdoc 통한 API 문서 자동화
     * 10. gradle 멀티 모튤을 통한 domain을 공유하는 Batch 서버 작성
     * 11. 인메모리 concurrentHashmap을 통한 cache 적용
     * 12. 계층현 테이블 전략
     * 13. 스프링 클라우드 모듈을 활용해서 간단한게 MSA 환경 구축
     * 14. Docker 연동 배포
     *
     * ==== front ====
     * 1. react - typescript 환경 세팅
     * 2. recoil + zustand를 통한 상태 관리
     * 3. pm2를 활용한 배포, 모니터링
     * 4. 정적 페이지 서버로서 s3에 배포
     * 5. next.js를 활용해서 서버사이드렌더링 체험 + 검색엔진 최적화(SEO)
     * 6. antd를 활용한 ui 컴포넌트 활용
     * 7. 반응형 스타일링
     * 8. webpack 최적화 + usecallback을 활용한 랜더링 최적화
     */

//    @Bean
//    fun registMyAuthentionFilter(): FilterRegistrationBean<MyAuthentionFilter> {
//        val bean = FilterRegistrationBean(MyAuthentionFilter())
//
//        bean.addUrlPatterns("/api/*")
//        bean.order = 0
//
//        return bean
//    }
}