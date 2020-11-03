package com.favor;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;

import javax.sound.sampled.AudioInputStream;

public class Voice {
    private MaryInterface marytts;
    private AudioPlayer ap;

    public Voice(String voiceName)
    {
        try
        {
            marytts = new LocalMaryInterface();
            marytts.setVoice(voiceName);
        }
        catch (MaryConfigurationException ex)
        {
            ex.printStackTrace();
        }
    }

    public void say(String input) throws InterruptedException
    {
        try
        {
            AudioPlayer ap = new AudioPlayer();
            AudioInputStream audio = marytts.generateAudio(input);
            ap.setAudio(audio);
            ap.start();
            ap.join();
        }
        catch (SynthesisException ex)
        {
            System.err.println("Error saying phrase.");
        }
    }
}
