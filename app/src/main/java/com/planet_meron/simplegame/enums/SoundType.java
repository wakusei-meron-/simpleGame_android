package com.planet_meron.simplegame.enums;

import com.planet_meron.simplegame.R;

/**
 * Created by wakusei-meron- on 2015/05/07.
 */
public enum SoundType {
    RED, BLUE, GREEN, YELLOW, FAILURE;

    public static SoundType FromResouceId(int id) {
        switch (id) {
            case R.id.red_image_button:    return SoundType.RED;
            case R.id.green_image_button:  return SoundType.GREEN;
            case R.id.blue_image_button:   return SoundType.BLUE;
            case R.id.yellow_image_button: return SoundType.YELLOW;
            default: return null;
        }
    }
}
