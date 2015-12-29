package lzl.edu.com.movierecommend.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/29.
 */
public class MovieLabel {
    /**
     * 爱情片、剧情片、喜剧片、家庭片、伦理片、
     文艺片、音乐片、歌舞片、动漫片、
     西部片、武侠片、古装片、动作片、
     恐怖片、惊悚片、冒险片、犯罪片、悬疑片、
     记录片、战争片、历史片、传记片、体育片、
     科幻片、魔幻片、奇幻片
     */
    private static List<String> movieList = new ArrayList<>();

    public static List<String> getMovieList(){
        movieList.add("爱情片");
        movieList.add("剧情片");
        movieList.add("喜剧片");
        movieList.add("家庭片");
        movieList.add("伦理片");
        movieList.add("文艺片");
        movieList.add("武侠片");
        movieList.add("动作片");
        movieList.add("科幻片");
        movieList.add("奇幻片");
        movieList.add("恐怖片");
        movieList.add("历史片");
        return movieList;
    }
}
