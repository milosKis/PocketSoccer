package com.example.nikolakis.pocketsoccer.utils;

import com.example.nikolakis.pocketsoccer.R;

public class ResourceUtils {

    public static int getTeamImageId(int code) {
        switch (code) {
            case 0: {
                return R.drawable.c0;
            }
            case 1: {
                return R.drawable.c1;
            }
            case 2: {
                return R.drawable.c2;
            }
            case 3: {
                return R.drawable.c3;
            }
            case 4: {
                return R.drawable.c4;
            }
            case 5: {
                return R.drawable.c5;
            }
            case 6: {
                return R.drawable.c6;
            }
            case 7: {
                return R.drawable.c7;
            }
            case 8: {
                return R.drawable.c8;
            }
            case 9: {
                return R.drawable.c9;
            }
            case 10: {
                return R.drawable.c10;
            }
            case 11: {
                return R.drawable.c11;
            }
            case 12: {
                return R.drawable.c12;
            }
        }
        return R.drawable.c0;
    }

    public static int getCourtImageId(int code) {
        switch (code) {
            case 0: {
                return R.drawable.court0;
            }
            case 1: {
                return R.drawable.court1;
            }
            case 2: {
                return R.drawable.court2;
            }
            case 3: {
                return R.drawable.court3;
            }
        }
        return R.drawable.court0;
    }
}
