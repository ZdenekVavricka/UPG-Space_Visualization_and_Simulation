package galaxy_animation;

import java.io.IOException;

/**
 * Hlavní třída programu
 */
public class Main {

    /**
     * Vstupní bod programu
     *
     * @param args vstupní parametry programu
     */
    public static void main(String[] args) {
        Animation galaxyAnimation;

        if (IO_Utils.controlInputData(args)) {
            try {
                galaxyAnimation = IO_Utils.loadInputData(args[0]);

                if (galaxyAnimation == null) {
                    IO_Utils.print(Constants.incorrectInputDataFormat);
                    System.exit(Constants.errorExitCode);
                }

                galaxyAnimation.runAnimation();
            } catch (IOException e) {
                IO_Utils.print(Constants.incorrectInputDataFormat);
                System.exit(Constants.errorExitCode);
            }
        } else {
            IO_Utils.print(Constants.incorrectInputParameters);
            System.exit(Constants.errorExitCode);
        }
    }
}
