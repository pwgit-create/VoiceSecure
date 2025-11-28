package voiceprotector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * AudioDeviceFinder provides utility methods for locating and opening specific
 * audio input and output lines.
 * It can be used to find microphone (mic) inputs and Voicemeeter AUX outputs on
 * the system.
 */
public class AudioDeviceFinder {

    /**
     * Opens a TargetDataLine representing a microphone input with the specified
     * audio format.
     *
     * @param format The desired audio format for the microphone input
     * @return A TargetDataLine instance configured to capture microphone audio
     * @throws LineUnavailableException If no suitable microphone line is available
     */
    public static TargetDataLine openMic(AudioFormat format) throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        for (Mixer.Info m : AudioSystem.getMixerInfo()) {
            Mixer mixer = AudioSystem.getMixer(m);
            if (mixer.isLineSupported(info)) {
                TargetDataLine line = (TargetDataLine) mixer.getLine(info);
                line.open(format);
                return line;
            }
        }
        throw new LineUnavailableException("Ingen mikrofon hittades!");
    }

    /**
     * Opens a SourceDataLine representing the Voicemeeter AUX input with the
     * specified audio format.
     *
     * @param format The desired audio format for the Voicemeeter AUX output
     * @return A SourceDataLine instance configured to play audio to the Voicemeeter
     *         AUX input
     * @throws LineUnavailableException If no suitable Voicemeeter AUX line is
     *                                  available
     */
    public static SourceDataLine openVoicemeeterAUX(AudioFormat format) throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        for (Mixer.Info m : AudioSystem.getMixerInfo()) {
            if (m.getName().toLowerCase().contains("voicemeeter aux input")) {
                Mixer mixer = AudioSystem.getMixer(m);
                if (mixer.isLineSupported(info)) {
                    SourceDataLine line = (SourceDataLine) mixer.getLine(info);
                    line.open(format);
                    return line;
                }
            }
        }
        throw new LineUnavailableException("Voicemeeter AUX Input not found!");
    }
}