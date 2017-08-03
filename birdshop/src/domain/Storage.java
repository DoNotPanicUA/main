package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Red8 on 03/08/2017.
 */
public class Storage {
    List<StorageObject> mainStorage = new ArrayList<>();
    private static int sequenceUID = 0;

    public <T extends Named> void addStorageObject(T b, int initialQuantity){
        StorageObject obj = getStorageObject(b, initialQuantity);

        if (!checkDuplicatedObj(obj)){
            obj.setStorageUID(getUID());
            mainStorage.add(obj);
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

    private int getUID(){
        return ++sequenceUID;
    }

    private boolean checkDuplicatedObj(StorageObject storageObject){
        boolean result = false;
        for (StorageObject s : mainStorage){
            if (s.getObjectName().equals(storageObject) || s.getStorageUID() == storageObject.getStorageUID()){
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
    }

}
