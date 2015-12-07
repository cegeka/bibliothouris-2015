package cgk.bibliothouris.learning.application.transferobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class Status {

    private String environment;

    private String upTime;

    private Boolean isDatabaseConnected;

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

    public Boolean getIsDatabaseConnected() {
        return isDatabaseConnected;
    }

    public void setIsDatabaseConnected(Boolean isDatabaseConnected) {
        this.isDatabaseConnected = isDatabaseConnected;
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

        public StatusBuilder withDatabaseConnectionStatus(Boolean isDatabaseConnected) {
            status.isDatabaseConnected = isDatabaseConnected;
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