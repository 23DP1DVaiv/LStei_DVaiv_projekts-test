package utils;

import java.io.FileWriter;
import java.io.IOException;
import models.Rating;
import models.User;

public class CSVWriter {

    // Метод записывает информацию о пользователе в CSV файл
    public static void writeUser(String filename, User user) {
        try (FileWriter writer = new FileWriter(filename, true)) {  // true - для добавления в конец файла
            // Записываем ID и имя пользователя через запятую
            writer.append(user.getId()).append(",").append(user.getUsername()).append("\n");
        } catch (IOException e) {
            // Пустой блок catch - ошибки игнорируются
        }
    }

    // Метод записывает информацию об оценке в CSV файл
    public static void writeRating(String filename, Rating rating) {
        try (FileWriter writer = new FileWriter(filename, true)) {  // true - для добавления в конец файла
            // Записываем все поля Rating через запятую
            writer.append(rating.getUserId()).append(",")
                  .append(rating.getAlbumId()).append(",")
                  .append(String.valueOf(rating.getRating())).append(",")  // Преобразуем int в String
                  .append(String.valueOf(rating.isListened())).append("\n");  // Преобразуем boolean в String
        } catch (IOException e) {
            // Пустой блок catch - ошибки игнорируются
        }
    }
}