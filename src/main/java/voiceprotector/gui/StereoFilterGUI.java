package voiceprotector.gui;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import voiceprotector.StereoAudioFilter;
import voiceprotector.StereoFilterChain;
import voiceprotector.filters.AIFormantScramblerStereo;
import voiceprotector.filters.ChaoticPhaseStereo;
import voiceprotector.filters.LPCWarpStereo;
import voiceprotector.filters.NoiseFilterStereo;
import voiceprotector.filters.SpectralHoleStereo;
import voiceprotector.filters.XorObfuscationStereo;

/**
 * StereoFilterGUI is a graphical user interface for managing and configuring
 * various stereo audio filters in a chain. It allows users to adjust filter
 * parameters
 * using sliders, spinners, etc., with each filter having its own panel.
 */
public class StereoFilterGUI extends JFrame {

    /**
     * Constructs the GUI and sets up the UI components for all filters in the given
     * chain.
     *
     * @param chain The StereoFilterChain containing various stereo audio filters to
     *              configure
     */
    public StereoFilterGUI(StereoFilterChain chain) {
        setTitle("VoiceProtector - Anti Voice-Cloning Filters");
        setSize(600, 600);
        setLayout(new GridLayout(chain.getFilters().size(), 1));

        for (StereoAudioFilter f : chain.getFilters()) {
            JPanel p = new JPanel();
            p.setBorder(BorderFactory.createTitledBorder(f.getClass().getSimpleName()));
            p.setLayout(new GridLayout(0, 2));

            if (f instanceof XorObfuscationStereo xor) {
                JLabel stepLabel = new JLabel("Step:");
                JSpinner stepSpinner = new JSpinner(new SpinnerNumberModel(xor.step, 1, 32, 1));
                stepSpinner.addChangeListener(e -> xor.step = (int) stepSpinner.getValue());

                JLabel xorLabel = new JLabel("XOR Value:");
                JSpinner xorSpinner = new JSpinner(new SpinnerNumberModel(xor.xorValue, 0, 0xFFFF, 1));
                xorSpinner.addChangeListener(e -> xor.xorValue = (int) xorSpinner.getValue());

                p.add(stepLabel);
                p.add(stepSpinner);
                p.add(xorLabel);
                p.add(xorSpinner);

            } else if (f instanceof NoiseFilterStereo noise) {
                JLabel ampLabel = new JLabel("Amplitude:");
                JSlider ampSlider = new JSlider(0, 2000, noise.amplitude);
                ampSlider.addChangeListener(e -> noise.amplitude = ampSlider.getValue());
                p.add(ampLabel);
                p.add(ampSlider);

            } else if (f instanceof LPCWarpStereo lpc) {
                JLabel warpLabel = new JLabel("Warp Amount:");
                JSlider warpSlider = new JSlider(0, 100, (int) (lpc.warpAmount * 100));
                warpSlider.addChangeListener(e -> lpc.warpAmount = warpSlider.getValue() / 100f);
                p.add(warpLabel);
                p.add(warpSlider);

            } else if (f instanceof ChaoticPhaseStereo cp) {
                JLabel intensityLabel = new JLabel("Intensity:");
                JSlider intensitySlider = new JSlider(0, 100, (int) (cp.intensity * 100));
                intensitySlider.addChangeListener(e -> cp.intensity = intensitySlider.getValue() / 100f);
                p.add(intensityLabel);
                p.add(intensitySlider);

            } else if (f instanceof SpectralHoleStereo sh) {
                JLabel holeLabel = new JLabel("Hole Width:");
                JSpinner holeSpinner = new JSpinner(new SpinnerNumberModel(sh.holeWidth, 1, 128, 1));
                holeSpinner.addChangeListener(e -> sh.holeWidth = (int) holeSpinner.getValue());

                JLabel depthLabel = new JLabel("Depth:");
                JSlider depthSlider = new JSlider(0, 100, (int) (sh.depth * 100));
                depthSlider.addChangeListener(e -> sh.depth = depthSlider.getValue() / 100f);

                p.add(holeLabel);
                p.add(holeSpinner);
                p.add(depthLabel);
                p.add(depthSlider);

            } else if (f instanceof AIFormantScramblerStereo ai) {
                JLabel formantLabel = new JLabel("Formant Shift:");
                JSlider formantSlider = new JSlider(-1200, 1200, (int) (ai.amount * 100));
                formantSlider.addChangeListener(e -> ai.amount = formantSlider.getValue() / 100f);
                p.add(formantLabel);
                p.add(formantSlider);
            }

            add(p);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}