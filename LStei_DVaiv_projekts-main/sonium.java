import java.util.List;
import java.util.Scanner;
import models.Album;
import models.Rating;
import models.User;
import services.AlbumService;
import services.RatingService;
import services.UserService;

public class sonium {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {  // Создаем сканнер для чтения ввода с консоли
    
            // Выводим ASCII-арт логотип приложения
            System.out.println("""

              ██████  ▒█████   ███▄    █  ██▓ █    ██  ███▄ ▄███▓
            ▒██    ▒ ▒██▒  ██▒ ██ ▀█   █ ▓██▒ ██  ▓██▒▓██▒▀█▀ ██▒
            ░ ▓██▄   ▒██░  ██▒▓██  ▀█ ██▒▒██▒▓██  ▒██░▓██    ▓██░
              ▒   ██▒▒██   ██░▓██▒  ▐▌██▒░██░▓▓█  ░██░▒██    ▒██ 
            ▒██████▒▒░ ████▓▒░▒██░   ▓██░░██░▒▒█████▓ ▒██▒   ░██▒
            ▒ ▒▓▒ ▒ ░░ ▒░▒░▒░ ░ ▒░   ▒ ▒ ░▓  ░▒▓▒ ▒ ▒ ░ ▒░   ░  ░
            ░ ░▒  ░ ░  ░ ▒ ▒░ ░ ░░   ░ ▒░ ▒ ░░░▒░ ░ ░ ░  ░      ░
            ░  ░  ░  ░ ░ ░ ▒     ░   ░ ░  ▒ ░ ░░░ ░ ░ ░      ░   
                  ░      ░ ░           ░  ░     ░            ░   
                                                                 
            
            """);
    
            // Инициализируем все необходимые сервисы
            AlbumService albumService = new AlbumService();
            RatingService ratingService = new RatingService();
            UserService userService = new UserService();

            // Приветствие и запрос имени пользователя
            System.out.println("Welcome to Sonium!");
            System.out.print("Enter your name: ");
            String username = scanner.nextLine();

            // Регистрация или получение существующего пользователя
            User currentUser = userService.registerUser(username);
            
            System.out.println("Hi, " + currentUser.getUsername() + "!");

            // Основной цикл программы
            while (true) {
                // Показываем главное меню
                System.out.println("Menu");
                System.out.println("1. Find an album");
                System.out.println("2. My albums");
                System.out.println("3. Quit");

                // Запрашиваем выбор пользователя
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {  // Пользователь выбрал поиск альбома
                        System.out.print("Enter album's name: ");
                        String query = scanner.nextLine();
                        // Ищем альбомы по запросу
                        List<Album> foundAlbums = albumService.searchAlbums(query);

                        if (foundAlbums.isEmpty()) {
                            System.out.println("Nothing found.");
                            break;
                        }

                        // Выводим результаты поиска
                        for (int i = 0; i < foundAlbums.size(); i++) {
                            System.out.println((i + 1) + ". " + foundAlbums.get(i));
                        }

                        // Просим выбрать конкретный альбом
                        System.out.print("Choose album by number: ");
                        int albumIndex = Integer.parseInt(scanner.nextLine()) - 1;  // -1 для индексации с 0
                        Album selectedAlbum = foundAlbums.get(albumIndex);

                        // Показываем выбранный альбом и предлагаем действия
                        System.out.println("You chose: " + selectedAlbum);
                        System.out.println("1. Mark as listened");
                        System.out.println("2. Rate an album");
                        System.out.println("3. View average rating");

                        String action = scanner.nextLine();

                        switch (action) {
                            case "1" -> {  // Отметить как прослушанное
                                ratingService.markAsListened(currentUser.getId(), selectedAlbum.getId());
                                System.out.println("Marked as listened!");
                        }
                            case "2" -> {  // Оценить альбом
                                System.out.print("Enter rating (1-5): ");
                                int score = Integer.parseInt(scanner.nextLine());
                                ratingService.rateAlbum(currentUser.getId(), selectedAlbum.getId(), score);
                                System.out.println("Thanks for your rating!");
                        }
                            case "3" -> {  // Посмотреть среднюю оценку
                                double avgRating = ratingService.getAlbumAverageRating(selectedAlbum.getId());
                                System.out.println("Average album's rating: " + avgRating);
                        }
                            default -> System.out.println("Incorrect choise.");
                        }
                    }

                    case "2" -> {  // Пользователь выбрал просмотр своих альбомов
                        // Получаем список прослушанных альбомов пользователя
                        List<Rating> listened = ratingService.getUserListenedAlbums(currentUser.getId());
                        if (listened.isEmpty()) {
                            System.out.println("You haven't listened any albums yet.");
                        } else {
                            System.out.println("Your logged albums:");
                            // Выводим информацию о каждом прослушанном альбоме
                            for (Rating r : listened) {
                                Album a = albumService.getAlbumById(r.getAlbumId());
                                String info = a.getTitle() + " (" + a.getArtist() + ")";
                                if (r.getRating() > 0) {
                                    info += " - Rating: " + r.getRating();  // Добавляем оценку, если она есть
                                }
                                System.out.println(". " + info);
                            }
                        }
                    }

                    case "3" -> {  // Выход из программы
                        System.out.println("Bye-bye!!!");
                        return;  // Завершаем метод main и программу
                    }

                    default -> System.out.println("Incorrect. Try again.");  // Некорректный выбор
                }
            }
        } catch (NumberFormatException e) {
            // Пустой блок catch - исключение при парсинге чисел игнорируется
        }
    }
}