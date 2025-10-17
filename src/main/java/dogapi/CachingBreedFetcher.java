package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    private BreedFetcher fetcher;
    private final List <String> breedsFetched = new ArrayList<>();
    List<String> subBreeds = new ArrayList<>();
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (!breedsFetched.contains(breed)) {
            try {
                callsMade++;
                subBreeds = fetcher.getSubBreeds(breed);
                breedsFetched.add(breed);
            } catch (Exception e) {
                throw new BreedNotFoundException(e.getMessage());
            }
        }

        // return statement included so that the starter code can compile and run.
        return subBreeds;
    }

    public int getCallsMade() {
        return callsMade;
    }
}