package com.saneesh.psc_kerala;

public class RandomGen
{
    public static String getAnswer() {

        switch (new java.util.Random().nextInt(4) + 1) {
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
        }
        return "1";
    }

    public static long getGuessTime() {
        return new java.util.Random().nextInt(4000) + 3000;
    }

    public static String getOpponentName()
    {
        switch (new java.util.Random().nextInt(4) + 1) {
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
        }
        return "1";
    }


}
