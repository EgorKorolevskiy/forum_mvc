package forum.exception;

import forum.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Статус BAD_REQUEST (код 400) указывает, что сервер не может обработать запрос из-за некорректного синтаксиса
 * запроса или других клиентских ошибок. Этот статус часто используется, когда запрос содержит
 * недействительные данные или отсутствующие параметры.
 * Класс HttpStatus предоставляет множество других статусов HTTP, которые могут быть использованы
 * для различных ситуаций. Вот некоторые из них:
 * HttpStatus.OK (код 200): успешный запрос, содержит запрашиваемые данные.
 * HttpStatus.CREATED (код 201): запрос успешно создал новый ресурс.
 * HttpStatus.NOT_FOUND (код 404): запрашиваемый ресурс не найден на сервере.
 * HttpStatus.INTERNAL_SERVER_ERROR (код 500): внутренняя ошибка сервера, которая не была обработана.
 */

/**
 * ResponseEntity - это класс, предоставляемый Spring Framework для управления HTTP-ответом.
 * Он позволяет вам явно установить статус HTTP, заголовки и тело ответа. Вы можете использовать ResponseEntity
 * для возврата более сложных данных, таких как объекты моделей или списки,
 * а также для настройки дополнительных параметров ответа.
 */

/**
 * Класс GlobalExceptionHandler помечен аннотацией @ControllerAdvice, чтобы указать, что это обработчик исключений
 * для всех контроллеров в приложении.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Метод handleRuntimeException помечен аннотацией @ExceptionHandler(RuntimeException.class), чтобы указать,
     * что он обрабатывает исключение типа RuntimeException. Внутри метода создается объект ErrorResponse, который
     * содержит текст ошибки и статус ответа. Затем создается экземпляр ResponseEntity, используя статус ответа
     * BAD_REQUEST и объект ErrorResponse в качестве тела ответа.
     * @param ex
     * @return
     */

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        var errorResponse = new ErrorResponse(ex.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ErrorResponse> handleOkException(CustomException ex) {
        var errorResponse = new ErrorResponse(ex.getMessage(), 200);
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}
