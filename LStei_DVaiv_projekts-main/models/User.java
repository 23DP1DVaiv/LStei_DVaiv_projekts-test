package models;

public class User {
    private final String id;         // Уникальный идентификатор пользователя
    private String username;   // Имя пользователя

    // Конструктор создает объект User с указанными параметрами
    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // Геттер для id - возвращает ID пользователя
    public String getId() {
        return id;
    }

    // Геттер для username - возвращает имя пользователя
    public String getUsername() {
        return username;
    }

    // Сеттер для username - устанавливает новое имя пользователя
    public void setUsername(String username) {
        this.username = username;
    }

    // Переопределение метода toString для удобного вывода информации о пользователе
    @Override
    public String toString() {
        return "User: " + username;
    }
}