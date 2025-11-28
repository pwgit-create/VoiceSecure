package voiceprotector.filters;

import voiceprotector.StereoAudioFilter;

/**
 * ChaoticPhaseStereo is an audio filter that applies chaotic phase modulation
 * to stereo audio samples.
 * It interpolates between consecutive samples in a way that varies chaotically
 * based on the 'intensity'
 * parameter,
 * creating interesting phase shifts and distortion effects.
 */
public class ChaoticPhaseStereo implements StereoAudioFilter {

    /**
     * Indicates whether the filter is enabled or not.
     */
    private boolean enabled = true;

    /**
     * Controls the intensity of the chaotic phase modulation effect applied to the
     * audio.
     * The default value is 0.12f, but it can be adjusted between 0.0f and 1.0f.
     */
    public float intensity = 0.12f;

    /**
     * Processes stereo audio samples by applying chaotic phase modulation based on
     * the 'intensity' parameter.
     *
     * @param samples a 2D array of short values where samples[0] contains left
     *                channel data
     *                and samples[1] contains right channel data
     */
    @Override
    public void process(short[][] samples) {
        // samples[0] = left channel, samples[1] = right channel
        for (int ch = 0; ch < samples.length; ch++) {
            short[] channel = samples[ch];

            for (int i = 0; i < channel.length - 1; i++) {
                short s1 = channel[i];
                short s2 = channel[i + 1];

                // Chaotic phase modulation
                float t = (float) Math.sin(i * 0.0009) * intensity;

                int newS1 = (int) ((s1 * (1 - t)) + (s2 * t));
                int newS2 = (int) ((s2 * (1 - t)) + (s1 * t));

                // Clip to not overreach 16-bit
                channel[i] = (short) Math.max(Math.min(newS1, 32767), -32768);
                channel[i + 1] = (short) Math.max(Math.min(newS2, 32767), -32768);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}