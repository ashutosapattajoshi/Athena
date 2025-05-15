package utils;

public class ScenarioContext {

    private static final ThreadLocal<String> currentStep = new ThreadLocal<>();

    public static void setCurrentStep(String stepText) {
        currentStep.set(stepText);
    }

    public static String getCurrentStep() {
        return currentStep.get();
    }

    public static void clear() {
        currentStep.remove();
    }
}
