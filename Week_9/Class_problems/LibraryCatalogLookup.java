public class LibraryCatalog {
    public static String findBook(String[][] catalog, String targetIsbn) {
        int low = 0, high = catalog.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            String isbn = catalog[mid][0];
            String title = catalog[mid][1];
            
            int cmp = isbn.compareTo(targetIsbn);
            if (cmp == 0) {
                return title;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return "Not Found";
    }
}