package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import models.Album;
import models.Rating;
import models.User;

public class CSVReader {

    // Метод читает альбомы из CSV файла и возвращает список объектов Album
    public static List<Album> readAlbums(String filename) {
        List<Album> albums = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // Пропускаем заголовок CSV файла
            String line;
            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");  // Разделяем строку по запятым
                // Создаем объект Album и добавляем в список
                albums.add(new Album(parts[0], parts[1], parts[2], parts[3]));
            }
        } catch (Exception e) {
            // Пустой блок catch - ошибки игнорируются
        }
        return albums;
    }

    // Метод читает пользователей из CSV файла и возвращает список объектов User
    public static List<User> readUsers(String filename) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();  // Пропускаем заголовок CSV файла
            String line;
            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");  // Разделяем строку по запятым
                // Создаем объект User и добавляем в список
                users.add(new User(parts[0], parts[1]));
            }
        } catch (Exception e) {
            // Пустой блок catch - ошибки игнорируются
        }
        return users;
    }

    // Метод читает оценки из CSV файла и возвращает список объектов Rating
    public static List<Rating> readRatings(String filename) {
        List<Rating> ratings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine();  // Пропускаем заголовок CSV файла
            String line;
            // Читаем файл построчно
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");  // Разделяем строку по запятым
                // Извлекаем данные из частей строки
                String userId = parts[0];
                String albumId = parts[1];
                int rating = Integer.parseInt(parts[2]);  // Преобразуем строку в число
                boolean listened = Boolean.parseBoolean(parts[3]);  // Преобразуем строку в boolean
                // Создаем объект Rating и добавляем в список
                ratings.add(new Rating(userId, albumId, rating, listened));
            }
        } catch (Exception e) {
            // Пустой блок catch - ошибки игнорируются
        }
        return ratings;
    }
}