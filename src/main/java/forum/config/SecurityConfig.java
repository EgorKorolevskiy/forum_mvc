package forum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // создаем условия, не пускаем не залогиненых юзеров
        http
                .httpBasic().and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/home/**", "/home", "/users/user/test2").permitAll()
                .antMatchers(HttpMethod.GET, "/authorize/**").authenticated()

                .antMatchers(HttpMethod.GET, "/users/admin/**", "/users/admin").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/moder/**", "/users/moder").hasAnyRole("ADMIN", "MODER")
                .antMatchers(HttpMethod.GET, "/users/user/**", "/users/user").hasAnyRole("ADMIN", "MODER", "USER")

                .antMatchers(HttpMethod.GET, "/users/user/**", "/users/user").hasAnyRole("ADMIN", "MODER", "USER")
                // для всех остальных запросов - нужна аутентификация
                // при включенном .anyRequest() .authenticated - не пускает даже по 30 строчке по этому пути
//                .anyRequest()
//                .authenticated()
                .and()
                // форма для авторизации или можно использовать .httpBasic()
                .formLogin()
                // перекидываем юзера после авторизации в меню форума
                // либо можно использовать .defaultSuccessUrl()
                .successHandler(customAuthSuccessHandler)
                .and()
                // после выхода логаута, можно выйти по нужному адресу
                .logout().logoutSuccessUrl("/home/")
                .permitAll();
    }

    //Подгружаем юзеров из бд в спринг (когда будешь искать юзера, использую в качестве поиска
    // нашу реализацию класса userDetailsService
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    // Проверка юзеров в БД. Отдаем провайдеру логин и пароль, а его задача сказать есть или нет такой юзер в бд.
    // и если существует - положи в спринг секьюрити контекст. Плюс установка хеш пароля для юзера

    // Задача authenticationProvider провести аутентификацию юзера. Он выдергивает из пост запроса логин и пароль
    // в виде токена с логином и паролем и обращается к userDetailsService. Далее userDetailsService дергает из БД
    // настоящего юзера по byUserName и возвращает юзера в виде userDetailsService. Далее authenticationProvider
    // берет из токена логин + хеширует паролль юзера и сравнивает с тем что вернул userDetailsService (возвращает юзера
    // и хешированный пароль который получил из БД). Если равны то authenticationProvider кладет данные в
    // секьюрити контекст (в нем мы можем запросить только данные которые есть в principal - логин и роли)

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // устанавливает хешированный пароль для юзера
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // предоставляем юзера
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    // получает хеш паролей юзеров
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * ОПИСАНИЕ СЕКЬЮРИТИ (ВСЕ ЧТО НИЖЕ С ПРИМЕРОМ КОДА)
     *
     * 1. `http.httpBasic()` - Указывает, что будет использоваться базовая аутентификация (Basic Authentication) для аутентификации пользователя.
     * 2. `sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)` - Устанавливает стратегию управления сессиями на `STATELESS`, что означает, что сессии не будут создаваться.
     * 3. `csrf().disable()` - Отключает защиту от CSRF (межсайтовая подделка запроса), чтобы упростить тестирование и разработку.
     * 4. `authorizeRequests()` - Начинает настройку правил доступа.
     * 5. `.antMatchers("/", "/test.html", "/menu.html", "/resources/static/*").permitAll()` - Позволяет доступ ко всем данным URL-адресам, указанным в `antMatchers`, без требования аутентификации. В данном случае, разрешен доступ к корневому URL (`/`), файлам `test.html`, `menu.html` и всем статическим ресурсам в папке `resources/static/`.
     * 6. `.antMatchers(HttpMethod.GET,"/").permitAll()` - Позволяет GET-запросы к корневому URL без требования аутентификации.
     * 7. `.antMatchers(HttpMethod.POST,"/user/registration").permitAll()` - Позволяет POST-запросы к `/user/registration` без требования аутентификации.
     * 8. `.anyRequest().authenticated()` - Требует аутентификацию для всех остальных запросов, которые не были указаны выше.
     * 9. `.formLogin().defaultSuccessUrl("/")` - Включает форму аутентификации и устанавливает URL-адрес, на который пользователь будет перенаправлен после успешной аутентификации.
     * 10. `.logout().permitAll()` - Включает поддержку выхода из системы (logout) и разрешает доступ к этой функции без требования аутентификации.
     */

    //    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/", "/test.html", "/menu.html", "/resources/static/*").permitAll()
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

    // РОЛИ

//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/", "/test.html", "/menu.html", "/resources/static/*").permitAll()
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
