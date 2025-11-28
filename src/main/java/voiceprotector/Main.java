package voiceprotector;

import javax.sound.sampled.AudioFormat;
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
 * Main class for running VoiceProtector with VoiceMeeter (e.g., VoiceMeeter
 * Banana).
 * This setup is useful for virtual microphone use cases where audio needs to be
 * routed
 * through a virtual mic interface in Windows.
 */
public class Main {

    /**
     * The main entry point of the application. Initializes the filter chain, GUI,
     * and audio devices, then starts processing audio data.
     *
     * @param args Command-line arguments (not used)
     * @throws Exception If an error occurs during initialization or execution
     */
    public static void main(String[] args) throws Exception {

        StereoFilterChain chain = new StereoFilterChain();

        chain.add(new XorObfuscationStereo());
        chain.add(new NoiseFilterStereo());
        chain.add(new AIFormantScramblerStereo());
        chain.add(new LPCWarpStereo());
        chain.add(new ChaoticPhaseStereo());
        chain.add(new SpectralHoleStereo());

        new StereoFilterGUI(chain);

        AudioFormat format = new AudioFormat(48000, 16, 2, true, false);

        TargetDataLine mic = AudioDeviceFinder.openMic(format);
        SourceDataLine vmAux = AudioDeviceFinder.openVoicemeeterAUX(format);

        mic.start();
        vmAux.start();

        byte[] buffer = new byte[4096];

        while (true) {
            int read = mic.read(buffer, 0, buffer.length);

            short[][] samples = AudioUtils.decodePCM16Stereo(buffer, read);

            chain.process(samples);

            byte[] out = AudioUtils.encodePCM16Stereo(samples);

            vmAux.write(out, 0, out.length);
        }
    }
}