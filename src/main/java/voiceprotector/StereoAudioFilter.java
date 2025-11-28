package voiceprotector;

/**
 * Interface defining a stereo audio filter that processes audio samples in
 * real-time.
 */
public interface StereoAudioFilter {

    /**
     * Processes stereo audio samples.
     *
     * @param samples a 2D array of short values where samples[0] contains left
     *                channel data
     *                and samples[1] contains right channel data
     */
    void process(short[][] samples);

    /**
     * Checks if the filter is currently enabled.
     *
     * @return true if the filter is enabled, false otherwise
     */
    boolean isEnabled();
}