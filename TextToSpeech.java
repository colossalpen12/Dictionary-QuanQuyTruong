package tudienbachkhoa.dictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    Voice voice;
    TextToSpeech() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
    }
    void SetVolume(int value) {
        voice.setVolume(value);
    }
    void Speak(String text) {
        voice.speak(text);
    }
}
