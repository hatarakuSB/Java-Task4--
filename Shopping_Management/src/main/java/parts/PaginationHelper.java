package parts;

import java.util.List;

public class PaginationHelper {

    public static <T> List<T> getPage(List<T> fullList, int page, int pageSize) {
        int start = page * pageSize;
        int end = Math.min(start + pageSize, fullList.size());

        if (start > end) {
        	// 空リスト返す
            return List.of(); 
        }

        return fullList.subList(start, end);
    }

    public static boolean hasNext(List<?> fullList, int page, int pageSize) {
        int start = (page + 1) * pageSize;
        return start < fullList.size();
    }

    public static boolean hasPrev(int page) {
        return page > 0;
    }
}
