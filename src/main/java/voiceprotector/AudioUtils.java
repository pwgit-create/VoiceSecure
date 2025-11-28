package voiceprotector;

/**
 * AudioUtils provides utility methods for decoding and encoding PCM16 stereo
 * audio data.
 */
public class AudioUtils {

    /**
     * Decodes a byte array containing PCM16 stereo audio data into separate left
     * and right channel samples.
     *
     * @param data   The byte array containing PCM16 stereo audio data
     * @param length The total number of bytes in the input data
     * @return A 2D short array where the first dimension represents channels
     *         (left=0, right=1)
     *         and the second dimension represents the samples
     */
    public static short[][] decodePCM16Stereo(byte[] data, int length) {
        short[][] samples = new short[2][length / 4]; // 2 channels

        for (int i = 0, s = 0; i < length; i += 4, s++) {
            samples[0][s] = (short) ((data[i] & 0xFF) | (data[i + 1] << 8));
            samples[1][s] = (short) ((data[i + 2] & 0xFF) | (data[i + 3] << 8));
        }
        return samples;
    }

    /**
     * Encodes a set of stereo PCM16 audio samples into a byte array.
     *
     * @param channels A 2D short array containing left and right channel samples,
     *                 where
     *                 the first dimension represents channels (left=0, right=1) and
     *                 the second dimension represents the samples
     * @return A byte array containing PCM16 stereo audio data
     */
    public static byte[] encodePCM16Stereo(short[][] channels) {
        int frames = channels[0].length;
        byte[] data = new byte[frames * 4];

        for (int s = 0, i = 0; s < frames; s++) {
            short L = channels[0][s];
            short R = channels[1][s];

            data[i++] = (byte) (L & 0xFF); // Lower byte of left sample
            data[i++] = (byte) (L >> 8); // Higher byte of left sample
            data[i++] = (byte) (R & 0xFF); // Lower byte of right sample
            data[i++] = (byte) (R >> 8); // Higher byte of right sample
        }
        return data;
    }
}