package voiceprotector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import voiceprotector.filters.AIFormantScramblerStereo;
import voiceprotector.filters.ChaoticPhaseStereo;
import voiceprotector.filters.LPCWarpStereo;
import voiceprotector.filters.NoiseFilterStereo;
import voiceprotector.filters.SpectralHoleStereo;
import voiceprotector.filters.XorObfuscationStereo;
import voiceprotector.gui.StereoFilterGUI;

/**
 * TestOutputSound is a main class for testing audio filters by outputting the
 * processed
 * sound to the system speakers. This allows users to hear their own voice with
 * various
 * filters applied without needing additional software like Voicemeeter.
 */
public class TestOutputSound {

    /**
     * The entry point (used for testing and playing around) of the application,
     * which sets up and runs the audio processing pipeline.
     *
     * @param args Command-line arguments (not used in this context)
     * @throws Exception If an error occurs while opening audio lines or during
     *                   processing
     */
    public static void main(String[] args) throws Exception {

        // Create a new filter chain
        StereoFilterChain chain = new StereoFilterChain();

        chain.add(new XorObfuscationStereo());
        chain.add(new NoiseFilterStereo());
        chain.add(new AIFormantScramblerStereo());
        chain.add(new LPCWarpStereo());
        chain.add(new ChaoticPhaseStereo());
        chain.add(new SpectralHoleStereo());

        // Launch the GUI for configuring filters
        new StereoFilterGUI(chain);

        // Define the audio format as 48 kHz sample rate, 16-bit PCM, stereo, big-endian
        // byte order
        AudioFormat format = new AudioFormat(48000, 16, 2, true, false);

        // Open microphone input line with the specified format
        TargetDataLine mic = AudioDeviceFinder.openMic(format);
        mic.start();

        // Open System Speakers output instead of Voicemeeter (Virtual Mic Interface
        // that you can use with other apps like Teams and Discord)
        SourceDataLine outputLine = openSystemOutput(format);
        outputLine.start();

        // Buffer for storing audio data
        byte[] buffer = new byte[4096];

        // Main loop for continuously processing and outputting audio datas
        while (true) {
            int read = mic.read(buffer, 0, buffer.length); // Read audio data from the microphone

            // Decode PCM16 stereo audio data into separate left and right channel samples
            short[][] samples = AudioUtils.decodePCM16Stereo(buffer, read);

            // Process the samples through the filter chain
            chain.process(samples);

            // Encode the processed samples back to a byte array for playback
            byte[] out = AudioUtils.encodePCM16Stereo(samples);

            // Write the encoded audio data to the system speakers output line
            outputLine.write(out, 0, out.length);
        }
    }

    /**
     * Opens the system speakers (output) with the specified audio format.
     *
     * @param format The AudioFormat to use for the output line
     * @return A SourceDataLine representing the system speakers output
     * @throws LineUnavailableException If no suitable output line is available
     */
    private static SourceDataLine openSystemOutput(AudioFormat format) throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        for (Mixer.Info m : AudioSystem.getMixerInfo()) {
            Mixer mixer = AudioSystem.getMixer(m);
            if (mixer.isLineSupported(info)) {
                SourceDataLine line = (SourceDataLine) mixer.getLine(info);
                line.open(format);
                System.out.println("Output found: " + m.getName());
                return line;
            }
        }
        throw new LineUnavailableException("System output not found !");
    }
}