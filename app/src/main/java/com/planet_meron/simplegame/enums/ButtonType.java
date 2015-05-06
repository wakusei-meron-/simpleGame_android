package com.planet_meron.simplegame.enums;

import com.planet_meron.simplegame.R;

/**
 * Created by wakusei-meron- on 2015/05/07.
 */
public enum ButtonType {
    RED, BLUE, GREEN, YELLOW, START;

    public static ButtonType FromInt(int i) {
        switch (i) {
            case 0: return RED;
            case 1: return BLUE;
            case 2: return GREEN;
            case 3: return YELLOW;
            default: return null;
        }
    }

    public static ButtonType FromResouceId(int id) {
        switch (id) {
            case R.id.red_image_button:    return ButtonType.RED;
            case R.id.green_image_button:  return ButtonType.GREEN;
            case R.id.blue_image_button:   return ButtonType.BLUE;
            case R.id.yellow_image_button: return ButtonType.YELLOW;
            default: return null;
        }
    }

    public static int ToResourceId(ButtonType type) {
        switch (type) {
            case RED: return R.id.red_image_button;
            case GREEN: return R.id.green_image_button;
            case BLUE: return R.id.blue_image_button;
            case YELLOW: return R.id.yellow_image_button;
            default: return 0;
        }
    }
}
