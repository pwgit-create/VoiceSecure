# VoiceSecure
**Anti-AI Voice Cloning Protection (Java 17)**

![Robert Torres ](https://github.com/pwgit-create/VoiceSecure/blob/master/robert-torres-4j_BW5x_njs-unsplash.jpg?raw=true)


## **Overview**

This project is a **Java 17 application** designed to help protect your voice from **AI voice-cloning attacks**.
It works by routing your audio through a **virtual microphone interface** using a voice meeter application (such as **Voicemeeter Banana**).
This allows you to safely use apps like **Microsoft Teams, Discord, Zoom**, and other VoIP platforms without exposing your real, raw audio.

The application includes a collection of real-time audio filters and a stereo audio pipeline designed to make your voice **harder to clone**, while still sounding natural enough for everyday communication.

---

## **Features**

* ✔️ Java 17, Maven-based
* ✔️ Real-time stereo audio pipeline
* ✔️ Multiple voice protection filters
* ✔️ Voicemeeter Banana compatible
* ✔️ Virtual microphone output
* ✔️ Test mode with direct speaker output
* ✔️ Works in Discord, Teams, and most VoIP apps

---

## **Class Overview**

### **Main**

`Main.java` is the **primary entry point** for running the application with **Voicemeeter Banana** (or any other virtual mixer).
It captures your microphone, applies the selected filters, and outputs the processed audio to a virtual microphone interface.

Use this when you want to use the app with:

* Discord
* Teams
* Zoom
* Any VoIP or game chat that supports selecting a microphone device

---

### **TestOutputSound**

`TestOutputSound.java` is a **test/demo entry point** used to experiment with filters.
Instead of routing audio to a virtual microphone, this mode outputs the processed sound **directly to your system speakers**.

Use this to:

* Hear how filters alter your voice
* Test latency, quality, and artifacts
* Debug or tune filter parameters

---

## **Filter Modules**

Located in:

```
src/main/java/voiceprotector/filters
```

Included filters:

* `AIFormantScramblerStereo.java`
* `ChaoticPhaseStereo.java`
* `LPCWarpStereo.java`
* `NoiseFilterStereo.java`
* `SpectralHoleStereo.java`
* `XorObfuscationStereo.java`

Each filter applies different transformations to resist voice-cloning models.

---

## **Audio Pipeline & Utilities**

Located in:

```
src/main/java/voiceprotector/
```

Key classes:

* **StereoPipeline.java** — core audio processing pipeline
* **StereoFilterChain.java** — manages ordered filter execution
* **StereoAudioFilter.java** — base class for filter modules
* **AudioDeviceFinder.java** — lists/selects system audio devices
* **AudioUtils.java** — shared audio helpers

---

## **Project Structure**

```
VoiceSecure
│
├── src/
│   └── main/
│       ├── java/
│       │   └── voiceprotector/
│       │       ├── filters/
│       │       │   ├── AIFormantScramblerStereo.java
│       │       │   ├── ChaoticPhaseStereo.java
│       │       │   ├── LPCWarpStereo.java
│       │       │   ├── NoiseFilterStereo.java
│       │       │   ├── SpectralHoleStereo.java
│       │       │   └── XorObfuscationStereo.java
│       │       │
│       │       ├── gui/
│       │       │   └── StereoFilterGUI.java
│       │       │
│       │       ├── AudioDeviceFinder.java
│       │       ├── AudioUtils.java
│       │       ├── Main.java
│       │       ├── StereoAudioFilter.java
│       │       ├── StereoFilterChain.java
│       │       ├── StereoPipeline.java
│       │       └── TestOutputSound.java
│       │
│       └── resources/
│           └── (place all resource files here: audio profiles, presets, config files, etc.)
│
├── target/
└── pom.xml

```



---

## **Setting Up Voicemeeter Banana (Download + Install)**

1. Go to VB-Audio’s official page for Voicemeeter Banana:
   [https://vb-audio.com/Voicemeeter/banana.htm](https://vb-audio.com/Voicemeeter/banana.htm)

2. Scroll down to the **Download** section.

3. Download the Windows installer (**EXE**).

4. Run the installer.

5. You may need to **restart your computer** after installation.

6. Launch **Voicemeeter Banana**.

---

## **How to Use With Voicemeeter Banana**

1. Install and start Voicemeeter Banana.
2. Select its virtual input/output devices as your default Windows audio device or per-app device.
3. Start the application by either:

   * Running `Main.java`, **or**
   * By building a JAR file using `mvn clean package` and then running it with:

     ```powershell
     java -jar target/ai.VoiceProtector-1.0-SNAPSHOT-jar-with-dependencies.jar
     ```
4. Route the processed audio into Voicemeeter’s **Virtual Input**.
5. Choose Voicemeeter’s **Virtual Output** (e.g., “Voicemeeter VAIO”) as your mic in Discord/Teams/etc.
6. You’re now speaking through a protected, filter-processed microphone.

---

##  Illustrations

### **1. Voicemeeter Banana – Virtual Inputs Overview**

Shows where your processed audio enters Voicemeeter.

![Virtual Inputs](https://github.com/pwgit-create/VoiceSecure/blob/docs/readme-media-update/.github/assets/images/Voicemeeter_Banana_virtual_input.png)

---

### **2. VoiceProtector – Anti Voice-Cloning Filters (GUI)**

This interface lets you adjust all voice-protection filters.

![Audio Filters GUI](https://github.com/pwgit-create/VoiceSecure/blob/docs/readme-media-update/.github/assets/images/mixer_for_audio_filters.png?raw=true)

---

### **3. Full Voicemeeter Banana Routing Setup**

A complete view of how audio flows through Voicemeeter.
![Voicemeeter Banana Full Setup](https://github.com/pwgit-create/VoiceSecure/blob/docs/readme-media-update/.github/assets/images/Voicemeeter_Banana_full_settings_2.png?raw=true)

## Configure Windows Audio to Use Voicemeeter**

For a simple setup:

* **Default Input (Microphone):** `Voicemeeter Output (VAIO)`
* **Default Output (Speakers):** `Voicemeeter Input (VAIO)`

## 🎥 See VoiceSecure in Action –  Demo Video

**Video Title:** Protect Your Voice with VoiceSecure – Anti AI Voice Cloning App

[Watch on YouTube ▶](https://www.youtube.com/watch?v=iyqujOaw3jc)

**What this video shows:**

* A man speaking while VoiceSecure’s **Anti-AI voice filters** are active.
* Real-time routing through **Voicemeeter Banana**, demonstrating how the voice is processed and protected.
* How your voice can be anonymized and safeguarded against commercial AI voice cloning tools.

