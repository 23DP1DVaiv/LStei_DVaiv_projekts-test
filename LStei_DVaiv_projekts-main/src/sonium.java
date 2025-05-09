import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import models.Album;
import models.Rating;
import models.User;
import services.AlbumService;
import services.RatingService;
import services.RatingService.DecadeFilter;
import services.RatingService.SortType;
import services.UserService;

public class sonium {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
                
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
    
            AlbumService albumService = new AlbumService();
            RatingService ratingService = new RatingService();
            UserService userService = new UserService();

            System.out.println("Welcome to Sonium!");
            System.out.print("Enter your name: ");
            String username = scanner.nextLine();

            // Регистрация 
            User currentUser = userService.registerUser(username);

            System.out.println("Hi, " + currentUser.getUsername() + "!");

            while (true) {
                // Главное меню
                System.out.println("\nMenu");
                System.out.println("1. Find an album");
                System.out.println("2. My albums");
                System.out.println("3. Top 20 albums by local users");
                System.out.println("4. Quit");

                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        System.out.print("Enter album's name: ");
                        String query = scanner.nextLine();
                        List<Album> foundAlbums = albumService.searchAlbums(query);

                        if (foundAlbums.isEmpty()) {
                            System.out.println("Nothing found.");
                            break;
                        }

                        // Результат поиска
                        for (int i = 0; i < foundAlbums.size(); i++) {
                            System.out.println((i + 1) + ". " + foundAlbums.get(i));
                        }

                        System.out.print("Choose album by number: ");
                        int albumIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if (albumIndex < 0 || albumIndex >= foundAlbums.size()) {
                            System.out.println("Invalid choice.");
                            break;
                        }
                        Album selectedAlbum = foundAlbums.get(albumIndex);

                        System.out.println("You chose: " + selectedAlbum);
                        System.out.println("1. Mark as listened");
                        System.out.println("2. Rate an album");
                        System.out.println("3. View average rating");

                        String action = scanner.nextLine();

                        switch (action) {
                            case "1" -> {
                                ratingService.markAsListened(currentUser.getId(), selectedAlbum.getId());
                                System.out.println("Marked as listened!");
                        }
                            case "2" -> {
                                System.out.print("Enter rating (1-5): ");
                                int score = Integer.parseInt(scanner.nextLine());
                                if (score < 1 || score > 5) {
                                    System.out.println("Rating must be between 1 and 5.");
                                    break;
                                }
                                ratingService.rateAlbum(currentUser.getId(), selectedAlbum.getId(), score);
                                System.out.println("Thanks for your rating!");
                        }
                            case "3" -> {
                                double avgRating = ratingService.getAlbumAverageRating(selectedAlbum.getId());
                                System.out.println("Average album's rating: " + avgRating);
                        }
                            default -> System.out.println("Incorrect choise.");
                        }
                    }

                    case "2" -> {
                        // Меню сортировки
                        System.out.println("\nSort by:");
                        System.out.println("1. Date added (newest first)");
                        System.out.println("2. Date added (oldest first)");
                        System.out.println("3. Rating (highest first)");
                        System.out.println("4. Rating (lowest first)");
                        
                        System.out.print("Choose sorting option: ");
                        String sortChoice = scanner.nextLine();
                        
                        SortType sortType;
                        switch (sortChoice) {
                            case "1" -> sortType = SortType.DATE_ADDED_DESC;
                            case "2" -> sortType = SortType.DATE_ADDED_ASC;
                            case "3" -> sortType = SortType.RATING_DESC;
                            case "4" -> sortType = SortType.RATING_ASC;
                            default -> {
                                System.out.println("Invalid choice. Showing newest first.");
                                sortType = SortType.DATE_ADDED_DESC;
                            }
                        }
                        
                        List<Rating> listened = ratingService.getUserListenedAlbums(currentUser.getId(), sortType);
                        if (listened.isEmpty()) {
                            System.out.println("You haven't listened any albums yet.");
                        } else {
                            
                            final String RESET = "\u001B[0m";
                            final String CYAN = "\u001B[36m";
                            final String GREEN = "\u001B[32m";

                            // Таблица
                            System.out.println("\nYour logged albums:");
                            System.out.println("+--------------------------------------------------------------------------------------+");
                            System.out.printf("| %-3s | %-25s | %-15s | %-6s | %-20s |\n", "#", "Title", "Artist", "Rating", "Date Added");
                            System.out.println("+--------------------------------------------------------------------------------------+");

                            int counter = 1;
                            boolean altColor = false;
                            for (Rating r : listened) {
                                Album a = albumService.getAlbumById(r.getAlbumId());
                                if (a == null) continue;

                                String color = altColor ? GREEN : CYAN;
                                String ratingStr = r.getRating() > 0 ? String.valueOf(r.getRating()) : "N/A";
                                String dateStr = r.getDateAdded().format(DATE_FORMAT);

                                String title = a.getTitle();
                                if (title.length() > 25) title = title.substring(0, 22) + "...";

                                String artist = a.getArtist();
                                if (artist.length() > 15) artist = artist.substring(0, 12) + "...";

                                System.out.printf(color + "| %-3d | %-25s | %-15s | %-6s | %-20s |" + RESET + "\n",
                                                counter++, title, artist, ratingStr, dateStr);

                                altColor = !altColor;
                            }
                            System.out.println("+--------------------------------------------------------------------------------------+");
                            
                            // Add delete option
                            System.out.println("\nOptions:");
                            System.out.println("1. Delete an album from your list");
                            System.out.println("2. Return to main menu");
                            
                            System.out.print("Choose: ");
                            String albumAction = scanner.nextLine();
                            
                            if (albumAction.equals("1")) {
                                System.out.print("Enter the number of the album to delete: ");
                                try {
                                    int deleteIndex = Integer.parseInt(scanner.nextLine()) - 1;
                                    if (deleteIndex >= 0 && deleteIndex < listened.size()) {
                                        Rating ratingToDelete = listened.get(deleteIndex);
                                        Album albumToDelete = albumService.getAlbumById(ratingToDelete.getAlbumId());
                                        
                                        if (albumToDelete != null) {
                                            System.out.println("Are you sure you want to delete \"" + 
                                                albumToDelete.getTitle() + "\" by " + 
                                                albumToDelete.getArtist() + " from your list? (y/n)");
                                            
                                            String confirm = scanner.nextLine().toLowerCase();
                                            if (confirm.equals("y") || confirm.equals("yes")) {
                                                if (ratingService.deleteAlbum(currentUser.getId(), ratingToDelete.getAlbumId())) {
                                                    System.out.println("Album successfully deleted from your list!");
                                                } else {
                                                    System.out.println("Failed to delete the album.");
                                                }
                                            } else {
                                                System.out.println("Deletion cancelled.");
                                            }
                                        }
                                    } else {
                                        System.out.println("Invalid album number.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter a valid number.");
                                }
                            }
                        }
                    }
                    
                    case "3" -> {
                        // Меню выбора десятилетия
                        System.out.println("\nTop 20 Albums Rated by Local Users");
                        System.out.println("Choose decade filter:");
                        System.out.println("1. All time");
                        System.out.println("2. 1960s");
                        System.out.println("3. 1970s");
                        System.out.println("4. 1980s");
                        System.out.println("5. 1990s");
                        System.out.println("6. 2000s and later");
                        
                        System.out.print("Choose: ");
                        String decadeChoice = scanner.nextLine();
                        
                        DecadeFilter decadeFilter;
                        String decadeTitle;
                        
                        switch (decadeChoice) {
                            case "2" -> {
                                decadeFilter = DecadeFilter.SIXTIES;
                                decadeTitle = "1960s";
                            }
                            case "3" -> {
                                decadeFilter = DecadeFilter.SEVENTIES;
                                decadeTitle = "1970s";
                            }
                            case "4" -> {
                                decadeFilter = DecadeFilter.EIGHTIES;
                                decadeTitle = "1980s";
                            }
                            case "5" -> {
                                decadeFilter = DecadeFilter.NINETIES;
                                decadeTitle = "1990s";
                            }
                            case "6" -> {
                                decadeFilter = DecadeFilter.TWO_THOUSANDS;
                                decadeTitle = "2000s and later";
                            }
                            default -> {
                                decadeFilter = DecadeFilter.ALL;
                                decadeTitle = "All time";
                            }
                        }
                        
                        List<Map.Entry<String, Double>> topAlbums = ratingService.getTopRatedAlbums(20, decadeFilter);
                        
                        if (topAlbums.isEmpty()) {
                            System.out.println("No rated albums found for " + decadeTitle + ".");
                        } else {
                            final String RESET = "\u001B[0m";
                            final String YELLOW = "\u001B[33m";
                            final String GREEN = "\u001B[32m";
                            
                            System.out.println("\nTop 20 Albums - " + decadeTitle + ":");
                            System.out.println("+--------------------------------------------------------------------------------------+");
                            System.out.printf("| %-3s | %-25s | %-15s | %-8s | %-22s |\n", "Rank", "Title", "Artist", "Year", "Average Rating");
                            System.out.println("+--------------------------------------------------------------------------------------+");
                            
                            int rank = 1;
                            boolean altColor = false;
                            for (Map.Entry<String, Double> entry : topAlbums) {
                                Album album = albumService.getAlbumById(entry.getKey());
                                if (album == null) continue;
                                
                                String color = altColor ? GREEN : YELLOW;
                                String avgRating = String.format("%.2f/5.00 (%d ratings)", 
                                    entry.getValue(), 
                                    (int) ratingService.getRatingCount(entry.getKey()));
                                
                                String title = album.getTitle();
                                if (title.length() > 25) title = title.substring(0, 22) + "...";
                                
                                String artist = album.getArtist();
                                if (artist.length() > 15) artist = artist.substring(0, 12) + "...";
                                
                                String year = album.getCountry(); 
                                
                                System.out.printf(color + "| %-3d | %-25s | %-15s | %-8s | %-22s |" + RESET + "\n",
                                        rank++, title, artist, year, avgRating);
                                
                                altColor = !altColor;
                            }
                            System.out.println("+--------------------------------------------------------------------------------------+");
                            
                            System.out.println("\nPress Enter to return to the main menu");
                            scanner.nextLine();
                        }
                    }

                    case "4" -> {
                        System.out.println("Bye-bye!!!");
                        return;
                    }

                    default -> System.out.println("Incorrect. Try again.");
                }
            }
        }
    }
}