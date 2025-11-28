package voiceprotector.filters;

import voiceprotector.StereoAudioFilter;

/**
 * LPCWarpStereo is an audio filter that applies linear predictive coding (LPC)
 * warping to stereo audio samples.
 * It modifies each sample based on the previous sample and a warp amount,
 * creating a characteristic warped effect
 * that can be used for voice transformation or obfuscation.
 */
public class LPCWarpStereo implements StereoAudioFilter {

    /**
     * Indicates whether the filter is enabled or not.
     */
    private boolean enabled = true;
    /**
     * Controls the amount of warping effect applied to the audio.
     * The default value is 0.15f, but it can be adjusted between 0.0f and 1.0f.
     */
    public float warpAmount = 0.15f;

    @Override
    /**
     * Processes stereo audio samples by applying LPC warping based on the
     * 'warpAmount' parameter.
     *
     * @param samples a 2D array of short values where samples[0] contains left
     *                channel data
     *                and samples[1] contains right channel data
     */
    public void process(short[][] samples) {
        // samples[0] = left channel, samples[1] = right channel
        for (int ch = 0; ch < samples.length; ch++) {
            short[] channel = samples[ch];

            for (int i = 1; i < channel.length; i++) {
                int warped = (int) (channel[i] - warpAmount * channel[i - 1]);

                // Clip for 16-bit
                channel[i] = (short) Math.max(Math.min(warped, 32767), -32768);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}