package game.spaceinvaders;

public class Time {
    public static String formatToTime(int seconds) {
        if (seconds < 0) {
            throw new IllegalArgumentException("Number can't be negative");
        }
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

}
