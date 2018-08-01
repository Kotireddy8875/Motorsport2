package com.example.kotireddy.motorsport;

import android.os.Parcel;
import android.os.Parcelable;

public class ParticipantsDescriptionPojo implements Parcelable {

    String teamName;

    protected ParticipantsDescriptionPojo(Parcel in) {
        teamName = in.readString();
        teamId = in.readString();
        teamShortName = in.readString();
        alternateName = in.readString();
        description = in.readString();
        formedYear = in.readString();
        manager = in.readString();
        logo = in.readString();
        website = in.readString();
        country = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teamName);
        dest.writeString(teamId);
        dest.writeString(teamShortName);
        dest.writeString(alternateName);
        dest.writeString(description);
        dest.writeString(formedYear);
        dest.writeString(manager);
        dest.writeString(logo);
        dest.writeString(website);
        dest.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParticipantsDescriptionPojo> CREATOR = new Creator<ParticipantsDescriptionPojo>() {
        @Override
        public ParticipantsDescriptionPojo createFromParcel(Parcel in) {
            return new ParticipantsDescriptionPojo(in);
        }

        @Override
        public ParticipantsDescriptionPojo[] newArray(int size) {
            return new ParticipantsDescriptionPojo[size];
        }
    };

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamShortName() {
        return teamShortName;
    }

    public void setTeamShortName(String teamShortName) {
        this.teamShortName = teamShortName;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public void setAlternateName(String alternateName) {
        this.alternateName = alternateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormedYear() {
        return formedYear;
    }

    public void setFormedYear(String formedYear) {
        this.formedYear = formedYear;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    String teamId;
    String teamShortName;
    String alternateName;
    String description;

    public ParticipantsDescriptionPojo(String teamName, String teamId, String teamShortName, String alternateName, String description, String formedYear, String manager, String logo, String website, String country) {
        this.teamName = teamName;
        this.teamId = teamId;
        this.teamShortName = teamShortName;
        this.alternateName = alternateName;
        this.description = description;
        this.formedYear = formedYear;
        this.manager = manager;
        this.logo = logo;
        this.website = website;
        this.country = country;
    }

    String formedYear;
    String manager;
    String logo;
    String website;
    String country;


}


