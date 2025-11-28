package voiceprotector.filters;

import voiceprotector.StereoAudioFilter;

/**
 * XorObfuscationStereo applies a simple XOR obfuscation to stereo audio
 * samples.
 * It modifies every 'step'th sample in both channels using an XOR operation
 * with a specified value,
 * which can be used for basic audio obfuscation or encryption purposes.
 */
public class XorObfuscationStereo implements StereoAudioFilter {

    /**
     * Indicates whether the filter is enabled or not.
     */
    public boolean enabled = true;

    /**
     * Defines how often (in steps) to apply the XOR obfuscation. Default value is
     * 4.
     */
    public int step = 4;

    /**
     * The XOR value used for obfuscating samples. A high value is 0x1327, but it
     * can be adjusted as needed.
     */
    public int xorValue = 0x00;

    @Override
    /**
     * Processes stereo audio samples by applying XOR obfuscation to every 'step'th
     * sample in both channels.
     *
     * @param samples a 2D array of short values where samples[0] contains left
     *                channel data
     *                and samples[1] contains right channel data
     */
    public void process(short[][] samples) {
        short[] L = samples[0];
        short[] R = samples[1];

        for (int i = 0; i < L.length; i += step) {
            L[i] ^= xorValue;
            R[i] ^= xorValue;
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
