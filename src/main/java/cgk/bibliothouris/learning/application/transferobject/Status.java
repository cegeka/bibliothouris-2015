package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Status {

    private String environment;

    private String upTime;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getUpTime() {
        return upTime;
    }

    public static class StatusBuilder {
        private Status status;

        private StatusBuilder() {
            status = new Status();
        }

        public StatusBuilder withEnvironment(String environment) {
            status.environment = environment;
            return this;
        }

        public StatusBuilder withUpTime(String upTime) {
            status.upTime = upTime;
            return this;
        }

        public static StatusBuilder status() {
            return new StatusBuilder();
        }

        public Status build() {
            return status;
        }
    }
}
