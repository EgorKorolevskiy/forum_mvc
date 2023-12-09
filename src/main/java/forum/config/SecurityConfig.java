package forum.config;

public class SecurityConfig {
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/", "/test.html", "/index.html", "/resources/static/*").permitAll()
//                .antMatchers(HttpMethod.GET,"/").permitAll()
//                .antMatchers(HttpMethod.POST,"/user/registration").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }


    /**
     * 1. `http.httpBasic()` - Указывает, что будет использоваться базовая аутентификация (Basic Authentication) для аутентификации пользователя.
     * 2. `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` - Устанавливает стратегию управления сессиями на `STATELESS`, что означает, что сессии не будут создаваться.
     * 3. `csrf().disable()` - Отключает защиту от CSRF (межсайтовая подделка запроса), чтобы упростить тестирование и разработку.
     * 4. `authorizeRequests()` - Начинает настройку правил доступа.
     * 5. `.antMatchers("/", "/test.html", "/index.html", "/resources/static/*").permitAll()` - Позволяет доступ ко всем данным URL-адресам, указанным в `antMatchers`, без требования аутентификации. В данном случае, разрешен доступ к корневому URL (`/`), файлам `test.html`, `index.html` и всем статическим ресурсам в папке `resources/static/`.
     * 6. `.antMatchers(HttpMethod.GET,"/").permitAll()` - Позволяет GET-запросы к корневому URL без требования аутентификации.
     * 7. `.antMatchers(HttpMethod.POST,"/user/registration").permitAll()` - Позволяет POST-запросы к `/user/registration` без требования аутентификации.
     * 8. `.anyRequest().authenticated()` - Требует аутентификацию для всех остальных запросов, которые не были указаны выше.
     * 9. `.formLogin().defaultSuccessUrl("/")` - Включает форму аутентификации и устанавливает URL-адрес, на который пользователь будет перенаправлен после успешной аутентификации.
     * 10. `.logout().permitAll()` - Включает поддержку выхода из системы (logout) и разрешает доступ к этой функции без требования аутентификации.
     */

    // РОЛИ

//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/", "/test.html", "/index.html", "/resources/static/*").permitAll()
//                .antMatchers(HttpMethod.GET,"/").permitAll()
//                .antMatchers(HttpMethod.POST,"/user/registration").permitAll()
//                .antMatchers("/admin/**").hasRole("admin") // Разрешение доступа только с ролью "admin"
//                .antMatchers("/user/**").hasAnyRole("user", "moderator", "admin" ) // Разрешение доступа с ролью "admin" или "user"
//                .antMatchers("/moderator/**").hasAnyRole("moderator", "admin") // Разрешение доступа с ролью "admin" или "moderator"
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }



}
