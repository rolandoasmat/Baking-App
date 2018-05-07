package com.asmat.rolando.rocoton.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by rolandoasmat on 7/1/17.
 */

class Step: Parcelable {
    val id: Int
    val shortDescription: String
    val description: String
    val videoURL: String
    val thumbnailURL: String

    constructor(id: Int, shortDescription: String, description: String, videoURL: String, thumbnailURL: String) {
        this.id = id
        this.shortDescription = shortDescription
        this.description = description
        this.videoURL = videoURL
        this.thumbnailURL = thumbnailURL
    }

    constructor(source: Parcel) {
        this.id = source.readInt()
        this.shortDescription = source.readString()
        this.description = source.readString()
        this.videoURL = source.readString()
        this.thumbnailURL = source.readString()
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if(dest != null) {
            dest.writeInt(id)
            dest.writeString(shortDescription)
            dest.writeString(description)
            dest.writeString(videoURL)
            dest.writeString(thumbnailURL)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Step> = object : Parcelable.Creator<Step> {
            override fun createFromParcel(source: Parcel): Step{
                return Step(source)
            }

            override fun newArray(size: Int): Array<Step?> {
                return arrayOfNulls(size)
            }
        }
    }

}