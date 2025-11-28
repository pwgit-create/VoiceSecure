package voiceprotector;

import java.util.ArrayList;
import java.util.List;

/**
 * StereoFilterChain manages a collection of audio filters that process stereo
 * audio data.
 * It allows for adding filters, processing audio samples through the chain, and
 * retrieving the list of filters.
 */
public class StereoFilterChain {

    /**
     * List to hold the stereo audio filters
     */
    private final List<StereoAudioFilter> filters = new ArrayList<>();

    /**
     * Adds a new filter to the chain.
     *
     * @param filter The StereoAudioFilter to be added to the chain
     */
    public void add(StereoAudioFilter filter) {
        filters.add(filter);
    }

    /**
     * Processes stereo audio samples through all enabled filters in the chain.
     *
     * @param samples A 2D short array containing left and right channel samples,
     *                where the first dimension represents channels (left=0,
     *                right=1)
     *                and the second dimension represents the samples
     */
    public void process(short[][] samples) {
        for (StereoAudioFilter f : filters) {
            if (f.isEnabled())
                f.process(samples);
        }
    }

    /**
     * Returns the list of stereo audio filters in the chain.
     *
     * @return A List of StereoAudioFilter objects
     */
    public List<StereoAudioFilter> getFilters() {
        return filters;
    }
}