package forum.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс для красивого вывода ответа от сервера
 */
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String message;
    private int code;
}
