package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import java.util.HashMap;
import java.util.Map;

public class StepEventListener implements ConcurrentEventListener {

    private static final Map<Long, String> stepTextMap = new HashMap<>();

    public static String getCurrentStepText() {
        return stepTextMap.get(Thread.currentThread().getId());
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
    }

    private void handleStepStarted(TestStepStarted event) {
        TestStep testStep = event.getTestStep();

        if (testStep instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) testStep;
            stepTextMap.put(Thread.currentThread().getId(), pickleStep.getStep().getText());
        }
    }
}
