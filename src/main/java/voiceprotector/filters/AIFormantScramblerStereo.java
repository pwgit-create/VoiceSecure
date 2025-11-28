package voiceprotector.filters;

import voiceprotector.StereoAudioFilter;

/**
 * AIFormantScramblerStereo is an audio filter that applies formant scrambling
 * to stereo audio samples.
 * It uses a chaotic function based on sine and cosine waves to modify the gain
 * applied to each sample,
 * resulting in a characteristic "scrambled" effect on the vocals.
 */
public class AIFormantScramblerStereo implements StereoAudioFilter {

    /**
     * Indicates whether the filter is enabled or not.
     */
    public boolean enabled = true;
    /**
     * Controls the amount of formant scrambling effect applied to the audio.
     * The default value is 0.22f, but it can be adjusted between 0.0f and 1.0f.
     */
    public float amount = 0.22f;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Processes the stereo audio samples by applying formant scrambling based on
     * the 'amount' parameter.
     *
     * @param samples a 2D array of short values where samples[0] contains left
     *                channel data
     *                and samples[1] contains right channel data
     */
    @Override
    public void process(short[][] samples) {
        short[] L = samples[0];
        short[] R = samples[1];

        for (int i = 0; i < L.length; i++) {

            float chaotic = (float) (Math.sin(i * 0.004) +
                    Math.cos(i * 0.002) * 0.5) * amount;

            float gain = 1.0f + chaotic;

            int vl = (int) (L[i] * gain);
            int vr = (int) (R[i] * gain);

            L[i] = (short) Math.max(Math.min(vl, 32767), -32768);
            R[i] = (short) Math.max(Math.min(vr, 32767), -32768);
        }

    }
}