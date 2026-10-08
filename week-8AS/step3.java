import java.util.*;

interface Capability {
    String getName();

    String apply(String value, String deviceName);
}

class PowerCapability implements Capability {
    private boolean on;

    public String getName() {
        return "Power";
    }

    public String apply(String value, String deviceName) {
        if (!value.equals("ON") && !value.equals("OFF")) {
            return "Rejected: " + deviceName
                    + " power must be ON or OFF.";
        }

        on = value.equals("ON");

        return deviceName + ": " + value;
    }
}

class BrightnessCapability implements Capability {
    private int brightness;

    public String getName() {
        return "Brightness";
    }

    public String apply(String value, String deviceName) {
        int valueNumber;

        try {
            valueNumber = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return "Rejected: " + deviceName
                    + " brightness must be between 0% and 100%.";
        }

        if (valueNumber < 0 || valueNumber > 100) {
            return "Rejected: " + deviceName
                    + " brightness must be between 0% and 100%.";
        }

        brightness = valueNumber;

        return deviceName + ": brightness set to "
                + brightness + "%.";
    }
}

class TemperatureCapability implements Capability {
    private int temperature;

    public String getName() {
        return "Temperature";
    }

    public String apply(String value, String deviceName) {
        int valueNumber;

        try {
            valueNumber = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return "Rejected: " + deviceName
                    + " temperature must be between 16°C and 30°C.";
        }

        if (valueNumber < 16 || valueNumber > 30) {
            return "Rejected: " + deviceName
                    + " temperature must be between 16°C and 30°C.";
        }

        temperature = valueNumber;

        return deviceName + ": temperature set to "
                + temperature + "°C.";
    }
}

class Device {
    private String name;
    private Map<String, Capability> capabilities;

    public Device(String name) {
        this.name = name;
        capabilities = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName(), capability);
        System.out.println(name + ": "
                + capability.getName()
                + " capability added.");
    }

    public Capability getCapability(String capabilityName) {
        return capabilities.get(capabilityName);
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName);
    }

    public String apply(String capabilityName, String value) {
        Capability capability = capabilities.get(capabilityName);

        if (capability == null) {
            return null;
        }

        return capability.apply(value, name);
    }
}

class SceneStep {
    private String capabilityName;
    private String value;

    public SceneStep(String capabilityName, String value) {
        this.capabilityName = capabilityName;
        this.value = value;
    }

    public void execute(List<Device> devices) {
        for (Device device : devices) {
            if (device.hasCapability(capabilityName)) {
                String result = device.apply(capabilityName, value);

                if (result != null) {
                    System.out.println(result);
                }
            }
        }
    }
}

class Scene {
    private String name;
    private List<SceneStep> steps;

    public Scene(String name) {
        this.name = name;
        steps = new ArrayList<>();
    }

    public void addStep(SceneStep step) {
        steps.add(step);
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");

        int actions = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(getCapabilityName(step))) {
                    actions++;
                }
            }

            step.execute(devices);
        }

        System.out.println("Scene '" + name
                + "' completed: " + actions
                + " actions applied.");
    }

    private String getCapabilityName(SceneStep step) {
        try {
            java.lang.reflect.Field field = SceneStep.class.getDeclaredField("capabilityName");

            field.setAccessible(true);

            return (String) field.get(step);
        } catch (Exception e) {
            return "";
        }
    }
}

public class step3 {
    public static void main(String[] args) {
        Device labAC = new Device("Lab AC");
        labAC.addCapability(new PowerCapability());
        labAC.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = Arrays.asList(
                labAC,
                lights,
                projector);

        Scene lectureMode = new Scene("Lecture Mode");

        lectureMode.addStep(
                new SceneStep("Power", "ON"));

        lectureMode.addStep(
                new SceneStep("Brightness", "40"));

        lectureMode.addStep(
                new SceneStep("Temperature", "24"));

        lectureMode.execute(devices);

        String result = labAC.apply("Temperature", "12");

        if (result != null) {
            System.out.println(result);
        }

        projector.addCapability(new BrightnessCapability());

        result = projector.apply("Brightness", "70");

        if (result != null) {
            System.out.println(result);
        }
    }
}