package com.asmat.rolando.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rolandoasmat on 8/15/17.
 */

public class Step implements Parcelable {
    private final int id;
    private final String shortDescription;
    private final String description;
    private final String videoURL;
    private final String thumbnailURL;

    public Step(int id,  String shortDescription,  String description,  String videoURL,  String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Step(Parcel source) {
        this.id = source.readInt();
        this.shortDescription = source.readString();
        this.description = source.readString();
        this.videoURL = source.readString();
        this.thumbnailURL = source.readString();
    }
    
    public static final Creator CREATOR = (Creator)(new Creator() {

        @Override
        public Step createFromParcel( Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    });

    public final int getId() {
        return this.id;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getShortDescription() {
        return this.shortDescription;
    }

    public final String getVideoURL() {
        return this.videoURL;
    }

    public void writeToParcel(Parcel dest, int flags) {
        if(dest != null) {
            dest.writeInt(this.id);
            dest.writeString(this.shortDescription);
            dest.writeString(this.description);
            dest.writeString(this.videoURL);
            dest.writeString(this.thumbnailURL);
        }
    }

    public int describeContents() {
        return 0;
    }
}