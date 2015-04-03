package imageboard.util;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import imageboard.model.ThreadsModel;
import imageboard.model.PostsModel;

public class AscendingPopularityComparator implements Comparator<ThreadsModel> {
    public int compare(ThreadsModel thread1, ThreadsModel thread2) {
        return thread1.getReplyCount() - thread2.getReplyCount();
    }
}
