package voiceprotector.filters;

import voiceprotector.StereoAudioFilter;

/**
 * SpectralHoleStereo is an audio filter that creates spectral "holes" in stereo
 * audio samples.
 * It attenuates the audio signal for specific blocks of samples, creating a
 * comb-like frequency response.
 */
public class SpectralHoleStereo implements StereoAudioFilter {

    /**
     * Indicates whether the filter is enabled or not.
     */
    private boolean enabled = true;

    /**
     * Defines the width (in samples) of each "hole" block. Default value is 16.
     */
    public int holeWidth = 16; // Number of samples per "hole""
    /**
     * Controls how much the audio signal is attenuated within each hole.
     * A value between 0.0 and 1.0, with 0.0 being no attenuation and 1.0 being full
     * attenuation.
     */
    public float depth = 0.3f; // How much the sound is attenuated in the hole (0.0-1.0)

    @Override
    /**
     * Processes stereo audio samples by creating spectral holes based on
     * 'holeWidth' and 'depth'.
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
                // Every other 'holeWidth' block is attenuated
                if ((i / holeWidth) % 2 == 0) {
                    int v = (int) (channel[i] * (1.0f - depth));

                    // Clip for 16-bit
                    channel[i] = (short) Math.max(Math.min(v, 32767), -32768);
                }
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}