package voiceprotector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * StereoPipeline manages the audio processing pipeline, handling input from a
 * microphone,
 * applying filters through a filter chain, and outputting to a Voicemeeter AUX
 * input.
 */
public class StereoPipeline {

    /**
     * The stereo filter chain for processing audio
     */
    private final StereoFilterChain chain;

    /**
     * The audio format used throughout the pipeline
     */
    private final AudioFormat format;

    /**
     * Constructs a new StereoPipeline with the specified filter chain and a
     * predefined audio format.
     *
     * @param chain The StereoFilterChain to use for processing audio samples
     */
    public StereoPipeline(StereoFilterChain chain) {
        this.chain = chain;
        this.format = new AudioFormat(48000, 16, 2, true, false);
    }

    /**
     * Starts the audio processing pipeline.
     *
     * @throws Exception If an error occurs while opening audio lines or during
     *                   processing
     */
    public void start() throws Exception {

        TargetDataLine mic = AudioDeviceFinder.openMic(format);
        SourceDataLine vmAUX = AudioDeviceFinder.openVoicemeeterAUX(format);

        mic.start();
        vmAUX.start();

        byte[] buffer = new byte[4096];

        while (true) {
            int read = mic.read(buffer, 0, buffer.length);

            // decode stereo PCM
            short[][] samples = AudioUtils.decodePCM16Stereo(buffer, read);

            // apply all filters
            chain.process(samples);

            // encode back
            byte[] out = AudioUtils.encodePCM16Stereo(samples);

            // send to Voicemeeter AUX Input
            vmAUX.write(out, 0, out.length);
        }
    }
}