package lzl.edu.com.movierecommend;

import junit.framework.TestCase;

import org.junit.Test;

import lzl.edu.com.movierecommend.http.volleyutil.URLAddress;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends TestCase {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void getPath()throws Exception{
       String url = URLAddress.getURLPath("GetMovieCountSer");
        System.out.print(url);
    }
}