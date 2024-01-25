package forum.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Кастомная ошибка для обработки исключений рантайма. Далее перехватывается хендлером и обрабатывается
 */
@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private String message;
}
