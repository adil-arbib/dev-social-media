package com.arbib.admin_panel.objects;

public class Experience {
    private String id;
    private String companyName;
    private String jobTitle;
    private String from;
    private String to;
    private String description;

    public Experience(String id, String companyName, String jobTitle, String from, String to, String description) {
        this.id = id;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.from = from;
        this.to = to;
        this.description = description;
    }

    public Experience(String companyName, String jobTitle, String from, String to, String description) {
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.from = from;
        this.to = to;
        this.description = description;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
