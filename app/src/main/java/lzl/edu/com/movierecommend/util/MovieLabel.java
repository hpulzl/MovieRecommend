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
    public static final String LOVE_MOVIE ="爱情片";
    public static final String STORY_MOVIE ="剧情片";
    public static final String COMEDY_MOVIE ="喜剧片";
    public static final String FAMILY_MOVIE ="家庭片";
    public static final String ETHIC_MOVIE ="伦理片";
    public static final String ART_MOVIE ="文艺片";
    public static final String SWORD_MOVIE ="武侠片";
    public static final String ACTION_MOVIE ="动作片";
    public static final String SCIENCE_MOVIE ="科幻片";
    public static final String STRANGE_MOVIE ="奇幻片";
    public static final String HISTORY_MOVIE ="历史片";
    public static final String Scare_MOVIE ="恐怖片";

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
    public static String getMovieType(int i){
        return movieList.get(i%10);
    }
    @Deprecated
    public static String whatMovieType(String type){
        if(type.equals(LOVE_MOVIE)){
            return LOVE_MOVIE;
        }
        if(type.equals(STORY_MOVIE)){
            return STORY_MOVIE;
        }
        if(type.equals(COMEDY_MOVIE)){
            return COMEDY_MOVIE;
        }
        if(type.equals(ETHIC_MOVIE)){
            return ETHIC_MOVIE;
        }
        if(type.equals(ART_MOVIE)){
            return ART_MOVIE;
        }
        if(type.equals(SWORD_MOVIE)){
            return SWORD_MOVIE;
        }
        if(type.equals(ACTION_MOVIE)){
            return ACTION_MOVIE;
        }
        if(type.equals(SCIENCE_MOVIE)){
            return SCIENCE_MOVIE;
        }
        if(type.equals(STRANGE_MOVIE)){
            return STRANGE_MOVIE;
        }
        if(type.equals(HISTORY_MOVIE)){
            return HISTORY_MOVIE;
        }
        if(type.equals(Scare_MOVIE)){
            return Scare_MOVIE;
        }
        return null;
    }
}
