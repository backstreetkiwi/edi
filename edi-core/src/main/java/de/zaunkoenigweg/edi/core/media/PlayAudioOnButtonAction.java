package de.zaunkoenigweg.edi.core.media;

import java.time.Duration;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;

import de.zaunkoenigweg.rspio.core.omx.AudioTrack;
import de.zaunkoenigweg.rspio.core.omx.MediaLibrary;

/**
 * Plays audio files from media library when button is pushed.
 * 
 * Eventually, there should be more possibilities to play audio files
 *
 * @author mail@nikolaus-winter.de
 */
public class PlayAudioOnButtonAction {

    private final static Log LOG = LogFactory.getLog(PlayAudioOnButtonAction.class);

    @Autowired
    protected MediaLibrary mediaLibrary;
    
    private List<AudioTrack> playlist;
    private int pos;
    private AudioTrack currentTrack;
    private Duration duration;

    @PostConstruct
    public void initPlaylist() {
        if(mediaLibrary==null) {
            String errorMessage = "media library is not set.";
            LOG.error(errorMessage);
            throw new BeanCreationException(errorMessage);
        }
        playlist = mediaLibrary.getPlaylist();
        if(playlist==null || playlist.isEmpty()) {
            String errorMessage = "playlist is empty.";
            LOG.error(errorMessage);
            throw new BeanCreationException(errorMessage);
        }
        currentTrack = playlist.get(pos);
        LOG.info(String.format("Playlist initialized with %d tracks.", playlist.size()));
        duration = mediaLibrary.getDuration();
        LOG.info(String.format("Playlist initialized with a duration of %s.", duration));
    }
    
    public void action() {
        switch (currentTrack.getState()) {
        case PLAYING:
            LOG.trace(String.format("playback of audio track '%s' is running, no action taken", currentTrack));
            break;
        case PAUSED:
        case NOT_YET_STARTED:
            currentTrack.play(duration);
            LOG.trace(String.format("playback of audio track '%s' started", currentTrack));
            break;
        case TERMINATED:
            skip();
            currentTrack.play(duration);
            LOG.trace(String.format("playback of audio track '%s' started", currentTrack));
            break;
        }
    }
    
    private void skip() {
        pos = (pos+1)%playlist.size();
        if(pos==0) {
            playlist = mediaLibrary.getPlaylist();
        }
        currentTrack = playlist.get(pos);
    }

}
