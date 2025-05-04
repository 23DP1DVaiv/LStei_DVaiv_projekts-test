package services;

import java.util.List;
import java.util.UUID;
import models.User;
import utils.CSVReader;
import utils.CSVWriter;

public class UserService {
    private final String USER_FILE = "src\\databases\\users.csv";  // Путь к файлу с пользователями

    // Метод регистрирует нового пользователя или возвращает существующего
    public User registerUser(String username) {
        List<User> users = CSVReader.readUsers(USER_FILE);  // Загружаем всех пользователей
    
        // Проверяем, существует ли пользователь с таким именем (игнорируя регистр)
        for (User user : users) {
            if (user.getUsername().trim().equalsIgnoreCase(username.trim())) {
                return user;  // Если существует, возвращаем найденного пользователя
            }
        }
    
        // Если пользователь не найден, создаем нового
        String id = UUID.randomUUID().toString();  // Генерируем уникальный UUID
        User newUser = new User(id, username.trim());  // Создаем объект пользователя
    
        // Записываем нового пользователя в файл
        CSVWriter.writeUser(USER_FILE, newUser);
        return newUser;  // Возвращаем созданного пользователя
    }
}