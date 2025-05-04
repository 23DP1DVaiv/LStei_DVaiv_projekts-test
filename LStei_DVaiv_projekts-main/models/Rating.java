package models;

public class Rating {
    private final String userId;     // ID пользователя, оставившего оценку
    private final String albumId;    // ID оцененного альбома
    private int rating;        // Оценка от 1 до 5, 0 если не оценено
    private boolean listened;  // Флаг, прослушан ли альбом

    // Конструктор создает объект Rating со всеми необходимыми полями
    public Rating(String userId, String albumId, int rating, boolean listened) {
        this.userId = userId;
        this.albumId = albumId;
        this.rating = rating;
        this.listened = listened;
    }

    // Геттер для userId - возвращает ID пользователя
    public String getUserId() {
        return userId;
    }

    // Геттер для albumId - возвращает ID альбома
    public String getAlbumId() {
        return albumId;
    }

    // Геттер для rating - возвращает оценку
    public int getRating() {
        return rating;
    }

    // Геттер для listened - возвращает true если альбом прослушан
    public boolean isListened() {
        return listened;
    }

    // Сеттер для rating - устанавливает новую оценку
    public void setRating(int rating) {
        this.rating = rating;
    }

    // Сеттер для listened - устанавливает статус прослушивания
    public void setListened(boolean listened) {
        this.listened = listened;
    }

    // Переопределение метода toString для удобного вывода информации об оценке
    @Override
    public String toString() {
        return "User: " + userId + ", Album: " + albumId + ", Rating: " + rating + ", Listened: " + listened;
    }
}