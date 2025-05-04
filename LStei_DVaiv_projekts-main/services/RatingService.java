package services;

import java.util.List;
import java.util.stream.Collectors;
import models.Rating;
import utils.CSVReader;
import utils.CSVWriter;

public class RatingService {
    private final List<Rating> ratings;  // Список всех оценок в системе

    // Конструктор загружает все оценки из CSV файла при создании сервиса
    public RatingService() {
        this.ratings = CSVReader.readRatings("src\\databases\\ratings.csv");
    }

    // Метод отмечает альбом как прослушанный пользователем
    public void markAsListened(String userId, String albumId) {
        Rating existing = findRating(userId, albumId);  // Ищем существующую оценку
        if (existing != null) {
            // Если оценка существует, просто отмечаем альбом как прослушанный
            existing.setListened(true);
        } else {
            // Если оценки нет, создаем новую запись с нулевой оценкой и флагом "прослушано"
            Rating rating = new Rating(userId, albumId, 0, true);
            ratings.add(rating);  // Добавляем в список в памяти
            CSVWriter.writeRating("src\\databases\\ratings.csv", rating);  // Сохраняем в файл
        }
    }

    // Метод добавляет или обновляет оценку альбома
    public void rateAlbum(String userId, String albumId, int score) {
        Rating existing = findRating(userId, albumId);  // Ищем существующую оценку
        if (existing != null) {
            // Если оценка существует, обновляем оценку и отмечаем как прослушанное
            existing.setRating(score);
            existing.setListened(true);
        } else {
            // Если оценки нет, создаем новую запись с указанной оценкой
            Rating rating = new Rating(userId, albumId, score, true);
            ratings.add(rating);  // Добавляем в список в памяти
            CSVWriter.writeRating("src\\databases\\ratings.csv", rating);  // Сохраняем в файл
        }
    }

    // Метод возвращает список альбомов, отмеченных пользователем как прослушанные
    public List<Rating> getUserListenedAlbums(String userId) {
        return ratings.stream()
                .filter(r -> r.getUserId().equals(userId) && r.isListened())
                .collect(Collectors.toList());
    }

    // Метод вычисляет среднюю оценку альбома на основе всех оценок
    public double getAlbumAverageRating(String albumId) {
        List<Rating> albumRatings = ratings.stream()
                .filter(r -> r.getAlbumId().equals(albumId) && r.getRating() > 0)
                .collect(Collectors.toList());

        if (albumRatings.isEmpty()) return 0;  // Если оценок нет, возвращаем 0

        double total = albumRatings.stream().mapToInt(Rating::getRating).sum();  // Сумма всех оценок
        return total / albumRatings.size();  // Делим на количество оценок для среднего значения
    }

    // Метод ищет оценку по ID пользователя и ID альбома
    public Rating findRating(String userId, String albumId) {
        for (Rating rating : ratings) {
            if (rating.getUserId().equals(userId) && rating.getAlbumId().equals(albumId)) {
                return rating;
            }
        }
        return null;  // Если оценка не найдена
    }
}