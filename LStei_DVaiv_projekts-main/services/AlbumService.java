package services;

import java.util.List;
import java.util.stream.Collectors;
import models.Album;
import utils.CSVReader;

public class AlbumService {
    private final List<Album> albums;  // Список всех альбомов в системе

    // Конструктор загружает все альбомы из CSV файла при создании сервиса
    public AlbumService() {
        this.albums = CSVReader.readAlbums("src\\databases\\albums.csv");
    }

    // Метод возвращает список всех альбомов
    public List<Album> getAllAlbums() {
        return albums;
    }

    // Метод ищет альбом по ID и возвращает его или null, если не найден
    public Album getAlbumById(String id) {
        for (Album album : albums) {
            if (album.getId().equals(id)) return album;
        }
        return null;
    }

    // Метод ищет альбомы по части названия (не учитывая регистр)
    public List<Album> searchAlbums(String query) {
        return albums.stream()
                .filter(album -> album.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    // Дублирующий метод поиска альбома по ID (функционально идентичен getAlbumById)
    public Album findAlbumById(String id) {
        for (Album album : albums) {
            if (album.getId().equals(id)) {
                return album;
            }
        }
        return null;
    }
}