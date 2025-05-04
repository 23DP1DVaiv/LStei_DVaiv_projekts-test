package models;

public class Album {
    private final String id;       // Уникальный идентификатор альбома, неизменяемый
    private String title;          // Название альбома
    private String artist;         // Исполнитель
    private String genre;          // Жанр альбома

    // Конструктор создает новый объект Album со всеми необходимыми полями
    public Album(String id, String title, String artist, String genre) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }

    // Геттер для id - возвращает идентификатор альбома
    public String getId() {
        return id;
    }

    // Геттер для title - возвращает название альбома
    public String getTitle() {
        return title;
    }

    // Геттер для artist - возвращает исполнителя альбома
    public String getArtist() {
        return artist;
    }

    // Геттер для genre - возвращает жанр альбома
    public String getGenre() {
        return genre;
    }

    // Сеттер для title - устанавливает новое название альбома
    public void setTitle(String title) {
        this.title = title;
    }

    // Сеттер для artist - устанавливает нового исполнителя
    public void setArtist(String artist) {
        this.artist = artist;
    }

    // Сеттер для genre - устанавливает новый жанр
    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Переопределение метода toString для удобного вывода информации об альбоме
    @Override
    public String toString() {
        return title + " by " + artist + " [" + genre + "]";
    }
}