package com.y4j.final_project.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.y4j.final_project.interceptor.AdminAuthHandler;
import com.y4j.final_project.interceptor.AuditionAuthHandler;
import com.y4j.final_project.interceptor.AuthorityAuthHandler;
import com.y4j.final_project.interceptor.MenuHandler;
import com.y4j.final_project.interceptor.SaleAuthHandler;
import com.y4j.final_project.interceptor.ScheduleAuthHandler;
import com.y4j.final_project.interceptor.UserAuthHandler;

@Configuration
@ConditionalOnMissingBean(CacheManager.class)
public class WebConfig implements WebMvcConfigurer{

	@Bean
	public MenuHandler menuHandler() {
		return new MenuHandler();
	}
	
	@Bean
	public AuditionAuthHandler auditionAuthHandler() {
		return new AuditionAuthHandler();
	}
	
	@Bean
	public AuthorityAuthHandler authorityAuthHandler() {
		return new AuthorityAuthHandler();
	}
	
	@Bean
	public ScheduleAuthHandler scheduleAuthHandler() {
		return new ScheduleAuthHandler();
	}
	
	@Bean
	public SaleAuthHandler saleAuthHandler() {
		return new SaleAuthHandler();
	}
	
	@Bean
	public UserAuthHandler userAuthHandler() {
		return new UserAuthHandler();
	}
	
	@Bean
	public AdminAuthHandler adminAuthHandler() {
		return new AdminAuthHandler();
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/*
		registry.addInterceptor(userAuthHandler())
		//		.addPathPatterns(Arrays.asList("경로", "경로", "경로"))
		//		.addPathPatterns("/*")  //경로에 포함
		//		.excludePathPatterns("/user/login", "/user/join", "/js/*", "/css/*", "/img/*");  //경로에서 제외
				//REST API도 제외 path에서 제외해줄 지 결정해야 됨
				.addPathPatterns("/product/*", "/user/*", "/main")
				.excludePathPatterns("/user/login", "/user/join");
		*/
		
//		registry.addInterceptor(userAuthHandler())
//		.addPathPatterns("/**")
//		.excludePathPatterns("/user/user_login")
//		.excludePathPatterns("/user/user_join")
//		.excludePathPatterns("/entertainment/ent_list")
//		.excludePathPatterns("/product/product_list")
//		.excludePathPatterns("/user/kakao")
//		.excludePathPatterns("/js/*")
//		.excludePathPatterns("/css/*");

		
		//breadcrumb nav 및 사이드 메뉴바 선택 탭 확장 유지
		registry.addInterceptor(menuHandler())
			.addPathPatterns("/authority/*", "/audition/*", "/schedule/*", "/order/*");

		//일반 회원 접근 권한 처리
		registry.addInterceptor(userAuthHandler())
		.addPathPatterns("/user/mypage", "/user/user_msg", "/user/cart");
//			.addPathPatterns("/user/mypage", "/user/user_msg", "/user/cart", "/audition/");
//			.addPathPatterns("/user/*")
//			.excludePathPatterns("/user/login")
//			.excludePathPatterns("/user/join");
		
		//관리자 페이지 접근 권한 처리
		registry.addInterceptor(adminAuthHandler())
		.addPathPatterns("/admin/admin_home", "/admin/admin_msg", "/admin/admin_hold");
		
		//권한 관리 탭 접근 권한 처리
		registry.addInterceptor(authorityAuthHandler())
			.addPathPatterns("/authority/*");
		
		//일정 관리 탭 접근 권한 처리
		registry.addInterceptor(scheduleAuthHandler())
			.addPathPatterns("/schedule/*");
		
		//상품 관리 탭 접근 권한 처리
		registry.addInterceptor(saleAuthHandler())
			.addPathPatterns("/order/*");
		
		//오디션 관리 탭 접근 권한 처리
		registry.addInterceptor(auditionAuthHandler())
			.addPathPatterns("/audition/*")
			.excludePathPatterns("/audition/audition_notice")
			.excludePathPatterns("/audition/audApplyForm");
		
		
	}
	
}
