package org.fedorahosted.libcommon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by letroll on 24/05/15.
 */
public class ListItem extends ArrayList<Item> implements Parcelable {

    public ListItem()
    {

    }

    public ListItem(Parcel in)
    {
        this.getFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public ListItem createFromParcel(Parcel in)
        {
            return new ListItem(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
            Item it= this.get(i); //On vient lire chaque objet Item
            dest.writeString(it.getIcon());
            dest.writeString(it.getText());
        }
    }

    public void getFromParcel(Parcel in)
    {
        // On vide la liste avant tout remplissage
        this.clear();

        //Récupération du nombre d'objet
        int size = in.readInt();

        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
            Item it= new Item();
            it.setIcon(in.readString());
            it.setText(in.readString());
            this.add(it);
        }
    }
}
