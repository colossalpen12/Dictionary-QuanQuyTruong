package tudienbachkhoa.dictionary;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    Voice voice;
    TextToSpeech() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.setVolume(0.75f);
    }
    void SetVolume(float value) {
        voice.setVolume(value/100);
    }
    void speak(String text) {
        voice.speak(text);
    }
}