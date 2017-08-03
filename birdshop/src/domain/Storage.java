package domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Red8 on 03/08/2017.
 */
public class Storage{
    private List<StorageObject> mainStorage = new ArrayList<>();

    public <T extends Named> void addStorageObject(T b, int initialQuantity){
        StorageObject obj = getStorageObject(b, initialQuantity);

        if (!checkDuplicatedObj(obj)){
            int index = mainStorage.size();
            obj.setStorageUID(index);
            mainStorage.add(obj.getStorageUID(), obj);
        }else{
            System.out.println("Such object already exists!");
        }
    }

    public StorageObject findObjByName(String name){
        StorageObject result = null;
        for (StorageObject s : mainStorage){
            if (s.getObjectName().equals(name)){
                result = s;
                break;
            }
        }
        return result;
    }

    public StorageObject getObjectByUID(int uid){
        try{
            return mainStorage.get(uid);
        }catch(NullPointerException e){
            System.out.println("ERROR: UID not found!");
            return null;
        }
    }

    public boolean sellObject(int storageUID){
        boolean result;

        StorageObject storageObject = getObjectByUID(storageUID);
        if (storageObject.getStorageLeftNumber() >= 1){
            storageObject.decrementLeft();
            storageObject.incrementSold();
            result = true;
        }else{
            result = false;
        }

        return result;
    }

    public Iterator<Storage.StorageObject> getIterator() {
        return mainStorage.iterator();
    }

    public <T extends Named> void updateStoredObject(T object, int storageUID){
        StorageObject newObject = mainStorage.get(storageUID);
        newObject.setObjectName(object.getName());
        newObject.setStorageObject(object);
        updateStoredObject(newObject);
    }

    public void updateStoredObject(StorageObject storageObject){
        mainStorage.set(storageObject.getStorageUID(), storageObject);
    }

    public static class StorageObject{
        private int storageUID;
        private String objectName;
        private int storageLeftNumber;
        private int soldNumber;
        private Named storageObject;

        private StorageObject(){}

        public Object getStorageObject() {
            return storageObject;
        }

        public void setStorageObject(Named storageObject) {
            this.storageObject = storageObject;
        }

        public int getStorageUID() {
            return storageUID;
        }

        public void setStorageUID(int storageUID) {
            this.storageUID = storageUID;
        }

        public String getObjectName() {
            return objectName;
        }

        public void setObjectName(String objectName) {
            this.objectName = objectName;
        }

        public int getStorageLeftNumber() {
            return storageLeftNumber;
        }

        public void setStorageLeftNumber(int storageLeftNumber) {
            this.storageLeftNumber = storageLeftNumber;
        }

        public int getSoldNumber() {
            return soldNumber;
        }

        public void setSoldNumber(int soldNumber) {
            this.soldNumber = soldNumber;
        }

        public void incrementSold(){
            soldNumber++;
        }

        public void decrementLeft(){
            storageLeftNumber--;
        }
    }

    private boolean checkDuplicatedObj(StorageObject storageObject){
        boolean result = false;
        for (StorageObject s : mainStorage){
            if (s.getObjectName().equals(storageObject.getObjectName())){
                result = true;
                break;
            }
        }
        return result;
    }

    private <T extends Named> StorageObject getStorageObject(T b, int initialQuantity){
        StorageObject newObject = new StorageObject();
        newObject.setStorageObject(b);
        newObject.setObjectName(b.getName());

        if (initialQuantity >= 0){
            newObject.setStorageLeftNumber(initialQuantity);
        }else{
            newObject.setStorageLeftNumber(0);
        }

        return newObject;
    }

}
