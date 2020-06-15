package facades;

import com.google.gson.Gson;
import dto.CompleteDTO;
import fetcher.ChuckFetcher;
import fetcher.FetcherInterface;
import fetcher.KanyeRestFetcher;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author rando
 */
public class FetchFacade {

    private static FetchFacade instance;
    private Gson gson = new Gson();

    //Private Constructor to ensure Singleton
    private FetchFacade() {
    }

    public static FetchFacade getFetchFacade() {
        if (instance == null) {
            instance = new FetchFacade();
        }
        return instance;
    }
    
    public CompleteDTO runParalel() throws InterruptedException {
        ChuckFetcher chuckFetcher = new ChuckFetcher("https://api.chucknorris.io/jokes/random?category=sport");
        KanyeRestFetcher kanyerestFetcher = new KanyeRestFetcher("https://api.kanye.rest/");
        List<FetcherInterface> urls = new ArrayList();
        urls.add(chuckFetcher);
        urls.add(kanyerestFetcher);
        ExecutorService workingJack = Executors.newFixedThreadPool(3);
        for (FetcherInterface fetch : urls) {
            Runnable task = () -> {
                try {
                    fetch.doWork();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            workingJack.submit(task);
        }
        workingJack.shutdown();
        workingJack.awaitTermination(15, TimeUnit.SECONDS);
        return new CompleteDTO(chuckFetcher.getChuckDTO(), kanyerestFetcher.getKanyeRestDto());
    }
    
}
