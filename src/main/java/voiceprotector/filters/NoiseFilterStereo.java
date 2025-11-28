package voiceprotector.filters;

import voiceprotector.StereoAudioFilter;

/**
 * NoiseFilterStereo is an audio filter that adds random noise to stereo audio
 * samples.
 * The amount of noise added is controlled by the 'amplitude' parameter, which
 * sets the maximum noise level.
 */
public class NoiseFilterStereo implements StereoAudioFilter {

    /**
     * Indicates whether the filter is enabled or not.
     */
    private boolean enabled = true;

    /**
     * Controls the amplitude of the random noise added to each sample.
     * The default value is 1, but it can be adjusted as needed. A higher value
     * will result in more intense
     * noise. 500 = Max noice leevel
     */
    public int amplitude = 1; // Max brusnivå

    @Override
    /**
     * Processes stereo audio samples by adding random noise based on the
     * 'amplitude' parameter.
     *
     * @param samples a 2D array of short values where samples[0] contains left
     *                channel data
     *                and samples[1] contains right channel data
     */
    public void process(short[][] samples) {
        // samples[0] = left channel, samples[1] = right channel
        for (int ch = 0; ch < samples.length; ch++) {
            short[] channel = samples[ch];

            for (int i = 0; i < channel.length; i++) {
                int noise = (int) ((Math.random() * amplitude * 2) - amplitude);
                int v = channel[i] + noise;

                // Clip for 16-bit
                channel[i] = (short) Math.max(Math.min(v, 32767), -32768);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}