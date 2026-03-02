package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

public class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;
    private final String description;
    private final String priority;       
    private final List<String> tags;     
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;    
    private final String source;         

    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
        this.tags = new ArrayList<>(b.tags);
    }

    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { 
        ArrayList<String> copy = new ArrayList<>(tags);
        return copy; 
    } 
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    public Builder toBuilder() {
        Builder builder = new Builder()
                .id(this.id)
                .reporterEmail(this.reporterEmail)
                .title(this.title)
                .description(this.description)
                .priority(this.priority)
                .assigneeEmail(this.assigneeEmail)
                .customerVisible(this.customerVisible)
                .slaMinutes(this.slaMinutes)
                .source(this.source);
        
        for (String tag : this.tags) {
            builder.addTag(tag);
        }
        return builder;
    }

    public static class Builder {
        private String id;
        private String reporterEmail;
        private String title;
        private String description = null;
        private String priority = null;     
        private List<String> tags = new ArrayList<>(); 
        private String assigneeEmail = null;
        private boolean customerVisible = false;
        private Integer slaMinutes = null;    
        private String source = null;     

        public Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder reporterEmail(String reporterEmail) { this.reporterEmail = reporterEmail; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder priority(String priority) { this.priority = priority; return this; }

        
        public Builder tags(List<String> tags) {
            this.tags = new ArrayList<>(tags);
            return this;
        }
        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        public Builder assigneeEmail(String assigneeEmail) { this.assigneeEmail = assigneeEmail; return this; }
        public Builder customerVisible(boolean customerVisible) { this.customerVisible = customerVisible; return this; }
        public Builder slaMinutes(Integer slaMinutes) { this.slaMinutes = slaMinutes; return this; }
        public Builder source(String source) { this.source = source; return this; }
       
        public IncidentTicket build() {
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");
            
            if (priority != null) Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");
            if (slaMinutes != null) Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");
            if (assigneeEmail != null) Validation.requireEmail(assigneeEmail, "assigneeEmail");

            return new IncidentTicket(this);
        }
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                '}';
    }
}