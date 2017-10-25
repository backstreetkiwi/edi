package de.zaunkoenigweg.edi.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

import de.zaunkoenigweg.rspio.audio.dummy.DummyAudioPlayer;

@ConfigurationProperties("audio")
public class AudioProperties {

    private String audioPlayerClassName = DummyAudioPlayer.class.getCanonicalName();

	public String getAudioPlayerClassName() {
		return audioPlayerClassName;
	}

	public void setAudioPlayerClassName(String audioPlayerClassName) {
		this.audioPlayerClassName = audioPlayerClassName;
	}
}
