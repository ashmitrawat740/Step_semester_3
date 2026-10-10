import java.util.*;

interface Capability {
    String getName();
    boolean applyValue(String deviceName, Object value);
}

class PowerCapability implements Capability {
    private boolean on = false;

    @Override
    public String getName() { return "Power"; }

    @Override
    public boolean applyValue(String deviceName, Object value) {
        if (value instanceof Boolean) {
            this.on = (Boolean) value;
            System.out.println(deviceName + ": " + (on ? "ON" : "OFF"));
            return true;
        } else if (value instanceof String) {
            String valStr = ((String) value).toUpperCase();
            if (valStr.equals("ON") || valStr.equals("OFF")) {
                this.on = valStr.equals("ON");
                System.out.println(deviceName + ": " + valStr);
                return true;
            }
        }
        return false;
    }
}

class BrightnessCapability implements Capability {
    private int brightness = 0;

    @Override
    public String getName() { return "Brightness"; }

    @Override
    public boolean applyValue(String deviceName, Object value) {
        int level = (Integer) value;
        if (level < 0 || level > 100) {
            System.out.println("Rejected: " + deviceName + " brightness must be between 0% and 100%");
            return false;
        }
        this.brightness = level;
        System.out.println(deviceName + ": brightness set to " + level + "%.");
        return true;
    }
}

class TemperatureCapability implements Capability {
    private int temperature = 20;

    @Override
    public String getName() { return "Temperature"; }

    @Override
    public boolean applyValue(String deviceName, Object value) {
        int temp = (Integer) value;
        if (temp < 16 || temp > 30) {
            System.out.println("Rejected: " + deviceName + " temperature must be between 16°C and 30°C");
            return false;
        }
        this.temperature = temp;
        System.out.println(deviceName + ": temperature set to " + temp + "°C.");
        return true;
    }
}

class Device {
    private final String name;
    private final Map<String, Capability> capabilities = new LinkedHashMap<>();

    public Device(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public void addCapability(Capability capability) {
        capabilities.put(capability.getName().toLowerCase(), capability);
    }

    public boolean hasCapability(String capabilityName) {
        return capabilities.containsKey(capabilityName.toLowerCase());
    }

    public boolean setCapabilityValue(String capabilityName, Object value) {
        Capability cap = capabilities.get(capabilityName.toLowerCase());
        if (cap != null) {
            return cap.applyValue(name, value);
        }
        return false;
    }
}

class SceneStep {
    private final String targetCapability;
    private final Object value;

    public SceneStep(String targetCapability, Object value) {
        this.targetCapability = targetCapability;
        this.value = value;
    }

    public String getTargetCapability() { return targetCapability; }
    public Object getValue() { return value; }
}

class Scene {
    private final String name;
    private final List<SceneStep> steps = new ArrayList<>();

    public Scene(String name) {
        this.name = name;
    }

    public void addStep(String capabilityName, Object value) {
        steps.add(new SceneStep(capabilityName, value));
    }

    public void execute(List<Device> devices) {
        System.out.println("Scene '" + name + "' started.");
        int appliedCount = 0;

        for (SceneStep step : steps) {
            for (Device device : devices) {
                if (device.hasCapability(step.getTargetCapability())) {
                    boolean success = device.setCapabilityValue(step.getTargetCapability(), step.getValue());
                    if (success) appliedCount++;
                }
            }
        }
        System.out.println("Scene '" + name + "' completed: " + appliedCount + " actions applied.");
    }
}

public class Question3 {
    public static void main(String[] args) {
        // 1. Create devices and assign capabilities dynamically
        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> labDevices = Arrays.asList(ac, lights, projector);

        // 2. Define and run scene 'Lecture Mode'
        Scene lectureMode = new Scene("Lecture Mode");
        lectureMode.addStep("Power", "ON");
        lectureMode.addStep("Brightness", 40);
        lectureMode.addStep("Temperature", 24);
        lectureMode.execute(labDevices);

        // 3. Admin attempts invalid temperature update
        ac.setCapabilityValue("Temperature", 12);

        // 4. Admin adds Brightness capability to Projector at runtime and sets brightness
        projector.addCapability(new BrightnessCapability());
        System.out.println("Projector: Brightness capability added.");
        projector.setCapabilityValue("Brightness", 70);
    }
}