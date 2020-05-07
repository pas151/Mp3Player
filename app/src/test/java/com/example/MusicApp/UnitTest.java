package com.example.MusicApp;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {
    /**
     * Create time label for song
     * @result time text will be computed accurately
     */
    @Test
    public void TimeLabelShouldReturnProperTime(){
        MainActivity activity = new MainActivity();
        assertEquals("5:00",activity.createTimeLabel(300000));
        assertEquals("6:40",activity.createTimeLabel(400000));
        assertEquals("1:59",activity.createTimeLabel(119059));
        assertEquals("3:30",activity.createTimeLabel(210000));
    }

    /**
     * Set index based on song size
     * @result index will be computed accurately
     */
    @Test
    public void ShouldSetCorrectIndex(){
        MainActivity activity = new MainActivity();
        assertEquals(0,activity.nextSongPosition(15));
        assertEquals(0,activity.nextSongPosition(500000));
        assertEquals(0,activity.nextSongPosition(807075));
    }

}