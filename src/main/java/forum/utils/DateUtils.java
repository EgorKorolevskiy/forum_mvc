package forum.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Утилитарный класс для работы с датой.
 */
public class DateUtils {

    /**
     * Утилитарный метод получения текущей даты с форматом даты: dd.MM.yyyy
     */
    public static LocalDate getDate() {
        var currentDate = LocalDate.now();
        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var format = currentDate.format(dateTimeFormatter);
        return LocalDate.parse(format, dateTimeFormatter);
    }
}
