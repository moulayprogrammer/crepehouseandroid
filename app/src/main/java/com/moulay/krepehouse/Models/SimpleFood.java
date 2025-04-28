package com.moulay.krepehouse.Models;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class SimpleFood implements Parcelable {

    private static final long serialVersionUID = 1L;

    private int uniqueId;
    private String nameAr;
    private String nameFr;
    private float Price;
    private String description;
    private byte[]  picture;
    private int archive;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private transient Bitmap imageBitmap; // Not parcelable



    public SimpleFood() {
    }

    public SimpleFood(int uniqueId, String nameAr, String nameFr, float price, String description, byte[] picture, int archive, LocalDateTime createAt, LocalDateTime updateAt) {
        this.uniqueId = uniqueId;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.Price = price;
        this.description = description;
        this.picture = picture;
        this.archive = archive;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public SimpleFood(String nameAr, String nameFr, float price, String description, byte[] picture) {
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.Price = price;
        this.description = description;
        this.picture = picture;
    }

    protected SimpleFood(Parcel in) {
        uniqueId = in.readInt();
        nameAr = in.readString();
        nameFr = in.readString();
        Price = in.readFloat();
        description = in.readString();
        picture = new byte[in.readInt()];
        in.readByteArray(picture);
        archive = in.readInt();
    }

    public static final Creator<SimpleFood> CREATOR = new Creator<SimpleFood>() {
        @Override
        public SimpleFood createFromParcel(Parcel in) {
            return new SimpleFood(in);
        }

        @Override
        public SimpleFood[] newArray(int size) {
            return new SimpleFood[size];
        }
    };

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameFr() {
        return nameFr;
    }

    public void setNameFr(String nameFr) {
        this.nameFr = nameFr;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Bitmap getImageBitmap() {
        if (imageBitmap == null && picture != null) {
            imageBitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        }
        return imageBitmap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleFood food = (SimpleFood) o;
        return Objects.equals(uniqueId, food.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId);
    }

    @Override
    public String toString() {
        return "SimpleFood{" +
                "uniqueId=" + uniqueId +
                ", nameAr='" + nameAr + '\'' +
                ", nameFr='" + nameFr + '\'' +
                ", Price=" + Price +
                ", description='" + description + '\'' +
                ", picture=" + Arrays.toString(picture) +
                ", archive=" + archive +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(uniqueId);
        parcel.writeString(nameAr);
        parcel.writeString(nameFr);
        parcel.writeFloat(Price);
        parcel.writeString(description);
        if (picture != null) {
            parcel.writeByteArray(picture);
        }
        parcel.writeInt(archive);
    }
}
